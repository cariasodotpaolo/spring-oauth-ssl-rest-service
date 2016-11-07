package com.shipserv.hr.security.oauth;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.shipserv.hr.dao.UserDao;
import com.shipserv.hr.dao.UserJdbcDao;
import com.shipserv.hr.entity.UserEntity;


public class UserDetailsServiceImpl implements UserDetailsService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	
	@Autowired
	private UserDao userDao;

	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException {
		
		logger.debug("UserName Given: " + userName);
		
		if(userDao != null) logger.debug("userdao exists");
		
		
		UserEntity userEntity = null;
		
		try {
			userEntity = userDao.getUserByUserName(userName);
		} catch (SQLException e) {
			throw new UsernameNotFoundException(e.getMessage(), e);
		}
		
		User user = null;
		
		if(userEntity != null && userEntity.getUserName() != null) {
		//List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		logger.debug("Username " + userName + " exists!");
		for(String role: userEntity.getRoles()) {
			//grantedAuthorities.add(new SimpleGrantedAuthority(role));
		}
		
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		GrantedAuthority auth1 = new SimpleGrantedAuthority("ROLE_ADMIN"); 
		grantedAuthorities.add(auth1);
        GrantedAuthority auth2 = new SimpleGrantedAuthority("ROLE_USER"); 
        grantedAuthorities.add(auth2);
        
		user = new User("test",
						 "$2a$10$wlQmaM33P4Sxg2kqYIfP2euDGZ.TXP.upnHVsVEmxBQ7c/nyyTbIy",
						 true, true, true, true, 
						 grantedAuthorities
						 );
		
        	/*user = new User(userEntity.getUserName(),
				 		    userEntity.getPassword(),
						    true, true, true, true, 
						    grantedAuthoritiess
						    );*/
        
		} else throw new UsernameNotFoundException(userName);
		
		return user;
	}

}
