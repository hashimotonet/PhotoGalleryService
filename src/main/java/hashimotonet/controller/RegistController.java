/**
 * 
 */
package hashimotonet.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistController {

	Logger log = LogManager.getLogger(RegistController.class);

	@Autowired
    private FindByIndexNameSessionRepository<? extends Session> sessionRepository;
	
	@Autowired
	HttpServletRequest request;

    @GetMapping("/Regist")
    public String registEndpoint(@RequestParam("sessionid") String sessionId) {
        // SessionRepositoryを使用してセッションを取得
        Session session = sessionRepository.findById(sessionId);

        if (session != null) {
            // セッションが見つかった場合の処理
        	String email = (String)session.getAttribute("mailAddress");		 // メールアドレス
            String accountId = (String) session.getAttribute("accountId");  // アカウント名
            String password = (String) session.getAttribute("password");    // パスワード  
            log.info("Welcome back, " + accountId + "!");
            return "RegistComplete";
        } else {
            // セッションが見つからない場合の処理
        	log.error("Not found any of same identity.");
            return "unauthorized";
        }
    }
}
