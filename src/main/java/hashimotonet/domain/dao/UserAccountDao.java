/**
 *
 */
package hashimotonet.domain.dao;

import java.io.IOException;
import java.net.URISyntaxException;

import hashimotonet.domain.dao.base.AbstractBaseDao;
import hashimotonet.domain.dto.UserAccount;

/**
 *
 */
public class UserAccountDao extends AbstractBaseDao {

	static final String select = "select * from account where ACCOUNT_ID=? or EMAIL_ADDRESS=?";
	
    /**
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws URISyntaxException
     */
    public UserAccountDao() throws ClassNotFoundException, IOException, URISyntaxException {
        super();
    }
    
    public UserAccount select(String name) {
    	UserAccount userAccount = new UserAccount();
    	
    	return userAccount;
    }

}
