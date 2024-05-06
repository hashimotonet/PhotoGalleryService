/**
 *
 */
package hashimotonet.domain.dao;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import hashimotonet.domain.dao.base.AbstractBaseDao;

/**
 * サインアップ画面からのユーザアカウント登録Daoクラス
 */
public class RegistDao extends AbstractBaseDao {

    String sql = "insert into USER_ACCOUNT(ACCOUNT_ID, EMAIL_ADDRESS, PASSWORD) values (?, ?, ?)";

    /**
     * デフォルトコンストラクタ
     * 
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws URISyntaxException
     */
    public RegistDao() throws ClassNotFoundException, IOException, URISyntaxException {
        super();
    }

    /**
     * ユーザアカウント登録メソッド
     * 
     * @param accountId
     * @param email
     * @param password
     * @return
     * @throws SQLException
     */
    public boolean regist(String accountId, String email, String password) throws SQLException {
        boolean result = false;

        // user_account表へのインサート
        Connection conn = super.getConnection();

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, accountId);
        stmt.setString(2, email);
        stmt.setString(3, password);

        stmt.executeUpdate();
        
        conn.commit();
        
        stmt.close();
        
        conn.close();

        result = true;

        return result;
    }

}
