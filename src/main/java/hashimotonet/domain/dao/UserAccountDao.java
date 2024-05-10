/**
 *
 */
package hashimotonet.domain.dao;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import hashimotonet.domain.dao.base.AbstractBaseDao;
import hashimotonet.domain.dto.UserAccount;

/**
 *
 */
@Component
public class UserAccountDao extends AbstractBaseDao {

	static final String select = "select * from USER_ACCOUNT where ACCOUNT_ID=? or EMAIL_ADDRESS=?";
	
	static final String selectAuth = "select * from USER_ACCOUNT where (ACCOUNT_ID=? or EMAIL_ADDRESS=?) and password=?";
	
    /**
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws URISyntaxException
     */
    public UserAccountDao() throws ClassNotFoundException, IOException, URISyntaxException {
        super();
    }
    
    public List<UserAccount> select(String name) throws SQLException {
    	UserAccount userAccount = null;
    	List<UserAccount> list = new ArrayList<UserAccount>();
    	
    	Connection conn = super.getConnection();
    	
    	PreparedStatement pStmt = conn.prepareStatement(select);
    	
    	pStmt.setString(1, name);
    	pStmt.setString(2, name);
    	
    	ResultSet rs = pStmt.executeQuery();
    	
    	if (rs.next()) {
        	userAccount = new UserAccount();
    		userAccount.setAccountId(rs.getString("ACCOUNT_ID"));
    		userAccount.setEmailAddress(rs.getString("EMAIL_ADDRESS"));
    		userAccount.setPassword(rs.getString("PASSWORD"));
    		userAccount.setAuthority(rs.getInt("AUTHORITY"));
    		list.add(userAccount);
    	}
    	
    	rs.close();
    	pStmt.close();
    	conn.close();
    	
    	return list;
    }
    
    public boolean select(String name, String password) throws SQLException {
    	boolean result = false;
    	
    	Connection conn = super.getConnection();
    	
    	PreparedStatement pStmt = conn.prepareStatement(selectAuth);
    	
    	pStmt.setString(1, name);
    	pStmt.setString(2, name);
    	pStmt.setString(3, password);
    	
    	ResultSet rs = pStmt.executeQuery();
    	
    	if (rs.next()) {
    		result = true;
    	}
    	return result;
    }
    
}
