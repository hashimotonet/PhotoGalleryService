package hashimotonet.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import hashimotonet.mail.MailSendRepository;
import hashimotonet.model.UserAccount;
import hashimotonet.service.SignUpService;

@Controller
@SessionAttributes(types = {UserAccount.class}, names= {"userAccount"})
@RequestMapping
public class SignUpController {
	
    Logger log = (Logger) LogManager.getLogger(SignUpController.class);
    
    @Autowired
    SignUpService service;
    
	@Autowired
	MailSendRepository mailRepo;

	@Autowired
	HttpServletRequest request;
	
	public SignUpController() {
		super();
	}

    @ModelAttribute("userAccount")
    public UserAccount userAccount() {
    	return new UserAccount();
    }

	@PostMapping("/SignUp")
	public String signUp(@ModelAttribute UserAccount userAccount, HttpSession session) {
		String email = request.getParameter("email");
		String accountId =request.getParameter("id");
	    String password = request.getParameter("password");
	    
	    session.setAttribute(RegistController.ADDRESS, email);
	    session.setAttribute(RegistController.ACCOUNT_ID, accountId);
	    session.setAttribute(RegistController.PASSWORD, password);
	    
		String protocol = request.getProtocol();
		String prefix = "http://";
		
		String url = "";
		if (protocol.startsWith("HTTPS")) {
			prefix = "https://";
		}
	    
		url = prefix + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/Regist" + "?sessionid=" + session.getId();
		
		log.info(url);
		
		boolean success = service.execute(email);
		if (success) {
			mailRepo.sendRegistMail(url, email);
			return "mailSent";
		} else {
			return "addressDuplicated";
		}
	}
}
