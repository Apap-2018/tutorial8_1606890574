package com.apap.tutorial6.service;

import java.util.Optional;

import com.apap.tutorial6.model.UserRoleModel;

public interface UserRoleService {
	UserRoleModel addUser(UserRoleModel user);
	public String encrypt(String password);
	UserRoleModel getUser(String  username);
	Boolean validatePassword(String oldpass, String oldpasscoba);
	void updatePass(String username, String newpass);
}
