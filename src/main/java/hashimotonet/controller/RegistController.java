/**
 *
 */
package hashimotonet.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hashimotonet.controller.base.ControllerBase;
import hashimotonet.service.RegistrationService;

@Controller
//@RestController
public class RegistController implements ControllerBase {

    Logger log = LogManager.getLogger(RegistController.class);

    @Autowired
    RegistrationService service;

    @Autowired
    private FindByIndexNameSessionRepository<? extends Session> sessionRepository;

    @Autowired
    HttpServletRequest request;

    @GetMapping("/Regist")
    public String registEndpoint(@RequestParam("sessionid") String sessionId) {
        boolean success = false; 
    	
    	// SessionRepositoryを使用してセッションを取得
        Session session = sessionRepository.findById(sessionId);

        if (session != null) {
            // セッションが見つかった場合の処理
            String email = (String)session.getAttribute(ADDRESS);		      // メールアドレス
            String accountId = (String) session.getAttribute(ACCOUNT_ID);  // アカウント名
            String password = (String) session.getAttribute(PASSWORD);     // パスワード
            
            try {
				success = service.execute(accountId, email, password);
			} catch (ClassNotFoundException | SQLException | IOException | URISyntaxException e) {
				log.catching(e);
			}

            log.info("Welcome, " + accountId + "!");
            
            if (success) {
                return "RegistComplete";
            } else {
                return "unauthorized";
            }
            
        } else {
            // セッションが見つからない場合の処理
            log.error("Not found any of same identity.");
            return "unauthorized";
        }
    }
}
