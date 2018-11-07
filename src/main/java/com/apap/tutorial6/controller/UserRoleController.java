package com.apap.tutorial6.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial6.model.UserRoleModel;
import com.apap.tutorial6.service.UserRoleService;

@Controller 
@RequestMapping("/user")
public class UserRoleController {
	@Autowired
	private UserRoleService userService;
	
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	private String addUserSubmit(@ModelAttribute UserRoleModel user, Model model) {
		if (user.getPassword().length() < 8) {
			model.addAttribute("msg", "Password tidak boleh kurang dari 8 karakter!");
			return "home";
		} else {
			if (user.getPassword().matches(".*[a-zA-Z].*") && user.getPassword().matches(".*[0-9].*")) {
				userService.addUser(user);
				model.addAttribute("msg", "user baru berhasil ditambahkan");
				return "home";
			} else {
				model.addAttribute("msg", "password harus mengandung angka dan huruf");
				return "home";
			}
		}
		//source : https://stackoverflow.com/questions/11533474/java-how-to-test-if-a-string-contains-both-letter-and-number
		
	}
	
	@RequestMapping(value = "/updatePassword/{username}", method = RequestMethod.GET)
	private String updatePassword(@PathVariable(value="username") String username, Model model) {
		UserRoleModel user = userService.getUser(username);
		model.addAttribute("user", user);
		model.addAttribute("username", username);
		model.addAttribute("msg", "");
		return "update-password";
	}
	
	@RequestMapping(value = "/updatePassword/{username}", method = RequestMethod.POST)
    private String updatePasswordSubmit(@PathVariable(value="username") String username, String oldpass, String newpass, String newpasskonfirmasi, Model model) {
		UserRoleModel user = userService.getUser(username);
		System.out.println("username : " + user.getUsername());
		System.out.println("old : " + oldpass);
		System.out.println("new : " + newpass);
		System.out.println("newkonf : " + newpasskonfirmasi);
		
		if (newpass.matches(".*[a-zA-Z].*") && newpass.matches(".*[0-9].*") && newpass.length() >= 8) {
			System.out.println("masukkkkk");
			//jika new pass tidak sama dengan new pass konfirmasi 
			if (newpass.equals(newpasskonfirmasi) == false) {
				model.addAttribute("msg", "konfirmasi password tidak sama!");
				return "update-password";
			} else {
				//mengubah password jika valid 	
				System.out.println("masuk");
				System.out.println("username : " + user.getUsername());
				boolean valid = userService.validatePassword(user.getPassword(), oldpass);
				System.out.println("valid : " + valid);
				if (valid == true) {
					System.out.println("pass valid");
					userService.updatePass(username, newpass);
					return "update-pass-success";
				} else {
					System.out.println("pass tidak valid");
					model.addAttribute("msg", "password tidak sesuai!");
					return "update-password";	
					
				}
			}
		} else {
			model.addAttribute("msg", "password harus mengandung huruf dan angka serta tidak boleh kurang dari 8 karakter!");
			return "update-password";
		}
		
		
    }
	
	
}
