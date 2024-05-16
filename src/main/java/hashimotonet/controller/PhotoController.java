/**
 * 
 */
package hashimotonet.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import hashimotonet.controller.base.ControllerBaseImpl;
import hashimotonet.domain.dto.Account;

/**
 * 
 */
@Controller
@SessionAttributes(types = {Account.class}, names= {"account"})
public class PhotoController extends ControllerBaseImpl {
	
	@Autowired
	HttpServletRequest request;

	/**
	 * 
	 */
	public PhotoController() {
		super();
	}
	
    @ModelAttribute("account")
    public Account account() {
        return new Account();
    }

	@PostMapping("/Photo")
	public String photo() {
        switch(getDeviceType(request.getHeader("user-agent"))) {
        case SMART_PHONE:
            return "smart";
        case TABLET:
        	return "photo";
        case PC:
        	return "photo";
        default:
            break;
        }

        return "photo";
	}

}
