/**
 *
 */
package hashimotonet.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

import hashimotonet.domain.dao.RegistDao;
import hashimotonet.service.base.AbstractBaseService;

/**
 *
 */
@Service
public class RegistrationService extends AbstractBaseService {

    public boolean execute(String accountId, String mail, String password) throws SQLException,
                                                                                     ClassNotFoundException,
                                                                                     IOException,
                                                                                     URISyntaxException {
        boolean success = false;
        
        // パスワードをエンコード
        password = digestEncode(password);

        // TODO DAO の実装（新規作成系）
        RegistDao dao = new RegistDao();
        success = dao.regist(accountId, mail, password);

        return success;
    }
}
