/**
 * 
 */
package hashimotonet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import hashimotonet.domain.dto.Account;

/**
 * 
 */
@Controller
@SessionAttributes(types = {Account.class}, names= {"account"})
public class PhotoController {

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
		return "photo";
	}

}
