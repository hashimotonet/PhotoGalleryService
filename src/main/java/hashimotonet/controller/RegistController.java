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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hashimotonet.service.RegistrationService;

@RestController
public class RegistController {

    Logger log = LogManager.getLogger(RegistController.class);

    @Autowired
    RegistrationService service;

    @Autowired
    private FindByIndexNameSessionRepository<? extends Session> sessionRepository;

    @Autowired
    HttpServletRequest request;

    /**
     * メールアドレス
     */
    public static final String ADDRESS = "mailAddress";

    /**
     * アカウント名
     */
    public static final String ACCOUNT_ID = "accountId";

    /**
     * パスワード
     */
    public static final String PASSWORD = "password";

    @GetMapping("/Regist")
    public String registEndpoint(@RequestParam("sessionid") String sessionId) throws ClassNotFoundException, SQLException, IOException, URISyntaxException {
        boolean success = false; 
    	
    	// SessionRepositoryを使用してセッションを取得
        Session session = sessionRepository.findById(sessionId);

        if (session != null) {
            // セッションが見つかった場合の処理
            String email = (String)session.getAttribute(ADDRESS);		      // メールアドレス
            String accountId = (String) session.getAttribute(ACCOUNT_ID);  // アカウント名
            String password = (String) session.getAttribute(PASSWORD);     // パスワード
            
            success = service.execute(email, accountId, password);

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
