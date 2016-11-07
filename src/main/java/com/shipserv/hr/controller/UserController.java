package com.shipserv.hr.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.test.model.User;

import com.shipserv.hr.response.UserResponse;

public class UserController {

	@RequestMapping(value = "/users/{id}", produces="application/json")
	@ResponseBody
	public UserResponse getUser(@PathVariable Long id, @RequestHeader("Accept") String acceptHeader) {
		//logger.trace("Serving resource for Accept header: {}", acceptHeader);
                UserResponse u = new UserResponse();
                u.setStatus(true);
                u.setMessage("");
                u.setData(new User(id, "John Doe"));
		return u;
	}
}
