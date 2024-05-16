package hashimotonet.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.firewall.RequestRejectedHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import hashimotonet.action.SignInAction;
import hashimotonet.controller.base.ControllerBaseImpl;
import hashimotonet.domain.dto.Account;
import hashimotonet.handler.CustomHttpStatusRequestRejectedHandler;

/**
 * @author hashi
 *
 */
@Controller
@SessionAttributes(types = {Account.class}, names= {"account"})
@RequestMapping("/")
public class SignInController extends ControllerBaseImpl {

    Logger log = (Logger) LogManager.getLogger(SignInController.class);

    @Autowired
    HttpServletRequest request;


    @Autowired
    HttpServletResponse response;

    public SignInController() {
        super();
    }

    @ModelAttribute("account")
    public Account account() {
        return new Account();
    }

    @PostMapping("/SignIn")
    public String index(@ModelAttribute Account account, HttpSession session) {
        Integer counter = account.getLoginCount();
        account.setLoginCount(counter++);
        account.setUserId(session.getId());

        SignInAction action = null;
        try {
            action = new SignInAction();
        } catch (ClassNotFoundException | IOException | URISyntaxException e) {
            log.catching(e);
        }
        boolean success = false;
        String name = request.getParameter("userName");
        String password = request.getParameter("password");

        account.setId(name);
        account.setPassword(password);

        session.setAttribute("account", account);

        String referer = request.getHeader("REFERER");

        if (referer != null) {
            if (referer.endsWith("ListImages")) {
                success = true;
            }
        }

        if (false == success) {

            try {

                success = action.execute(request, name, password);

            } catch(ClassNotFoundException | IOException | SQLException | URISyntaxException e) {

                log.catching(e);

            }
        }
        
        String smart = "";

        if (success && (referer.endsWith("param=signin") || referer.endsWith("/SignIn"))) {
        	request.getSession().setAttribute(ACCOUNT_ID, name);
        	request.getSession().setAttribute(PASSWORD, password); // TODO 参照の際には digestPasswordにする必要あり

            response.setStatus(HttpServletResponse.SC_OK);
            
            switch(getDeviceType(request.getHeader("user-agent"))) {
            case SMART_PHONE:
                return "smart";
            case TABLET:
            	return "smart";
            case PC:
            	return "photo";
            default:
                break;
            }

            return "photo";

        } else {

            account.setAuth("unauthorized");

            session.setAttribute("account", account);

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            return null;
        }

      }

      @Bean
      public RequestRejectedHandler requestRejectedHandler() {
          return new CustomHttpStatusRequestRejectedHandler();
      }

}
