/**
 *
 */
package hashimotonet.service.base;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 */
@Configuration
public class AbstractBaseService {

    PasswordEncoder passwordEncoder;

    /**
     *
     */
    public AbstractBaseService() {
        super();
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    protected String digestEncode(String password) {
    	return passwordEncoder.encode(password);
    }

}
