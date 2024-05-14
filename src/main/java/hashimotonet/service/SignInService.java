package hashimotonet.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hashimotonet.domain.dao.UserAccountDao;
import hashimotonet.domain.dto.UserAccount;
import hashimotonet.service.base.AbstractBaseService;

@Service
@Transactional
public class SignInService extends AbstractBaseService {

	/**
	 * Logger.
	 */
    @SuppressWarnings("unused")
	private Logger log = LogManager.getLogger(SignInService.class);
    
    UserAccountDao userAccountDao;

    public SignInService() throws ClassNotFoundException, IOException, URISyntaxException {
		super();
		userAccountDao = new UserAccountDao();
	}

	public boolean execute(String name) throws SQLException {
    	boolean result = true;
    	
    	List<UserAccount> users = null;
    	users = userAccountDao.select(name);
    	result = (users != null) && (users.isEmpty() == false);
    	
    	return result;
    }
    
    public boolean execute(String name, String password) throws SQLException {
    	boolean result = false;
    	String digestPassword = super.digestEncode(password);
    	result = userAccountDao.select(name, digestPassword);
    	return result;
    }
    
}
