/**
 * 
 */
package hashimotonet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 
 */
@Controller
public class PhotoController {

	/**
	 * 
	 */
	public PhotoController() {
		super();
	}
	
	@PostMapping("/Photo")
	public String photo() {
		return "photo";
	}

}
