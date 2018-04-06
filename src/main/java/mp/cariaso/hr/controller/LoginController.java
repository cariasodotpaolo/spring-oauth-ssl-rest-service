package mp.cariaso.hr.controller;

import java.util.UUID;

import javax.servlet.http.HttpSession;

import mp.cariaso.hr.exceptions.InvalidAcceptHeaderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import mp.cariaso.hr.response.LoginResponse;


@Controller
@RequestMapping(value = "/login")
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger("com.shipserv.hr.controller.LoginController");

	//@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@RequestMapping(method = RequestMethod.POST, produces = "application/vnd.shipserv.hr+json")
	@ResponseBody
    public LoginResponse login(@RequestParam("username") String username,
    						   @RequestParam("password") String password,
    						   @RequestHeader("Accept") String acceptHeader,
    						   HttpSession session) throws Exception {

		if (!"application/vnd.eops.eserve+json".equals(acceptHeader)) {
			/*
			 * the client Accept Header should match the produces value (Media Type/Content Type)
			 * Note that content type is arbitrary value here. Normally, it should be application/json.
			 * However, any value provided here does not affect the json response handled by jackson.
			 * This is additional security measure.
			 */
			throw new InvalidAcceptHeaderException(acceptHeader);
		}

		logger.info("Username param: " + username + "\n" + "Password Param: " + password);

		LoginResponse r = new LoginResponse();

		if("test".equals(username) && "test".equals(password)) {

			r.setStatus(true);
			r.setMessage("Valid user: " + username + "/" + password + ", " + "Accept Header: " + acceptHeader);
			r.setToken(UUID.randomUUID() + "");
			r.setData("Accept Header: " + acceptHeader);

			session.setAttribute("user", username);
		}

        return r;
    }

	/*@ExceptionHandler(InvalidAcceptHeaderException.class)
	@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
	@ResponseBody
	public String handleInvalidAcceptHeaderException(InvalidAcceptHeaderException e) {

		//TODO: do some logging here
		return e.getMessage();
	}*/

	/*
	 * the reason value becomes the response body.
	 * It should not provide any clue on why this exception/Status code was returned
	 * for security purposes
	 */
	@ExceptionHandler(InvalidAcceptHeaderException.class)
	@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason="Not Acceptable")
	public void handleInvalidAcceptHeaderException(InvalidAcceptHeaderException e) {

		//TODO: do some logging here
		//logger.error(e.getMessage());
	}

}
