package com.apap.tutorial6.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.apap.tutorial6.model.UserRoleModel;
import com.apap.tutorial6.repository.UserRoleDb;

@Service
public class UserRoleServiceImpl implements UserRoleService{
	@Autowired
	private UserRoleDb userDb;
	
	@Override
	public UserRoleModel addUser(UserRoleModel user) {
		String pass = encrypt(user.getPassword());
		user.setPassword(pass);
		return userDb.save(user);
	}

	@Override
	public String encrypt(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		return hashedPassword;
	}


	@Override
	public UserRoleModel getUser(String username) {
		return userDb.findByUsername(username);
	}

	@Override
	public Boolean validatePassword(String oldpass, String oldpasscoba) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.matches(oldpasscoba, oldpass);
	}

	@Override
	public void updatePass(String username, String newpass) {
		UserRoleModel user = userDb.findByUsername(username);
		String newpassencrypt = encrypt(newpass);
		user.setPassword(newpassencrypt);
		userDb.save(user);
	}

}
