package mp.cariaso.hr.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import mp.cariaso.hr.exceptions.InvalidAcceptHeaderException;
import mp.cariaso.hr.response.EmployeeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import mp.cariaso.hr.domain.Employee;
import mp.cariaso.hr.response.EmployeeListResponse;
import mp.cariaso.hr.service.EmployeeService;

@Controller
@RequestMapping("/protected")
public class EmployeeController {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeService employeeService;

	/*
	 * Can not be static. Spring will not inject on static properties
	 */
	@Value("${mediatype.hr.json}")
	private String MEDIA_TYPE_MP_CARIASO_HR_JSON;

	@RequestMapping(value = "/employee/list",
			method = RequestMethod.GET,
				produces = "application/vnd.mp.cariaso.hr+json")
	// @PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PreAuthorize("hasRole('ROLE_USER')")
	public @ResponseBody List<EmployeeResponse> getEmployees(@RequestParam String status,
												     @RequestHeader("Accept") String acceptHeader,
												     HttpServletRequest request, ModelMap model, Principal principal)
	                                                 throws Exception {

		logger.debug("CLIENT IP ADDRESS: " + request.getRemoteAddr());

		if (!MEDIA_TYPE_MP_CARIASO_HR_JSON.equals(acceptHeader)) {

			throw new InvalidAcceptHeaderException(acceptHeader);
		}

		List<Employee> employees = employeeService.getEmployees(status);

		EmployeeListResponse responseList = new EmployeeListResponse();

		for(Employee e: employees) {
			responseList.getEmployees().add(responseMapper(e));
		}

		return responseList.getEmployees();
	}

	@RequestMapping(value = "/employee/{id}",
					method = RequestMethod.GET,
					produces = "application/vnd.mp.cariaso.hr+json")
	// @PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PreAuthorize("hasRole('ROLE_USER')")
	public @ResponseBody EmployeeResponse getEmployee(@PathVariable long id,
											  @RequestHeader("Accept") String acceptHeader,
											  HttpServletRequest request, ModelMap model, Principal principal)
                                              throws Exception {

		logger.debug("CLIENT IP ADDRESS: " + request.getRemoteAddr());

		if (!MEDIA_TYPE_MP_CARIASO_HR_JSON.equals(acceptHeader)) {

			throw new InvalidAcceptHeaderException(acceptHeader);
		}

		Employee employee = employeeService.getEmployee(id);

		return responseMapper(employee);

	}

	@RequestMapping(value = "/employee/add", method = RequestMethod.POST, produces = "application/vnd.mp.cariaso.hr+json")
	// @PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PreAuthorize("hasRole('ROLE_USER')")
	public @ResponseBody EmployeeResponse addEmployee(@RequestParam(name="name") String name,
													  @RequestParam(name="address") String address,
													  @RequestParam(name="contactNumber") String contactNumber,
													  @RequestHeader("Accept") String acceptHeader,
													  HttpServletRequest request, ModelMap model, Principal principal)
													  throws Exception {

		logger.debug("CLIENT IP ADDRESS: " + request.getRemoteAddr());

		if (!MEDIA_TYPE_MP_CARIASO_HR_JSON.equals(acceptHeader)) {

			throw new InvalidAcceptHeaderException(acceptHeader);
		}

		Employee employee = new Employee();
		employee.setAddress(address);
		employee.setName(name);
		employee.setContactNumber(contactNumber);
		employee.setStatus("ACTIVE");

		Employee createdEmployee = employeeService.addEmployee(employee);

		return responseMapper(createdEmployee);
	}

	@RequestMapping(value = "/employee/update", method = RequestMethod.PUT, produces = "application/vnd.mp.cariaso.hr+json")
	// @PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PreAuthorize("hasRole('ROLE_USER')")
	public @ResponseBody EmployeeResponse updateEmployee(@RequestParam(name="id") long id,
														 @RequestParam(name="name") String name,
														 @RequestParam(name="address") String address,
														 @RequestParam(name="contactNumber") String contactNumber,
														 @RequestParam(name="status") String status,
														 @RequestHeader("Accept") String acceptHeader,
														 HttpServletRequest request, ModelMap model, Principal principal)
														 throws Exception {

		logger.debug("CLIENT IP ADDRESS: " + request.getRemoteAddr());

		if (!MEDIA_TYPE_MP_CARIASO_HR_JSON.equals(acceptHeader)) {

			throw new InvalidAcceptHeaderException(acceptHeader);
		}

		Employee employee = new Employee();
		employee.setId(id);
		employee.setAddress(address);
		employee.setName(name);
		employee.setContactNumber(contactNumber);
		employee.setStatus(status);

		Employee updatedEmployee = employeeService.updateEmployee(employee);


		return responseMapper(updatedEmployee);
	}

	@RequestMapping(value = "/employee/delete/{id}", method = RequestMethod.DELETE, produces = "application/vnd.mp.cariaso.hr+json")
	// @PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PreAuthorize("hasRole('ROLE_USER')")
	public @ResponseBody EmployeeResponse deleteEmployee(@PathVariable long id,
											 @RequestHeader("Accept") String acceptHeader,
											 HttpServletRequest request, ModelMap model, Principal principal)
											 throws Exception {

		logger.debug("CLIENT IP ADDRESS: " + request.getRemoteAddr());

		if (!MEDIA_TYPE_MP_CARIASO_HR_JSON.equals(acceptHeader)) {

			throw new InvalidAcceptHeaderException(acceptHeader);
		}

		Employee deletedEmployee = employeeService.deleteEmployee(id);


		return responseMapper(deletedEmployee);

		//return 200 OK instead
	}

	/*
	 * the reason value becomes the response body. It should not provide any
	 * clue on why this exception/Status code was returned for security purposes
	 */
	@ExceptionHandler(InvalidAcceptHeaderException.class)
	@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Not Acceptable")
	public void handleInvalidAcceptHeaderException(
			InvalidAcceptHeaderException e) {

		// TODO: do some logging here
		// logger.error(e.getMessage());
	}

	private EmployeeResponse responseMapper(Employee employee) {

		EmployeeResponse response = new EmployeeResponse();
		response.setId(employee.getId());
		response.setName(employee.getName());
		response.setAddress(employee.getAddress());
		response.setContactNumber(employee.getContactNumber());
		response.setStatus(employee.getStatus());

		return response;

	}

}
