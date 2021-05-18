package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.UserDao;
import com.example.demo.dao.UserRepository;
import com.example.demo.model.User;

@RestController
public class UserController {
	
	@Autowired
	UserRepository dao;
	
	
	@GetMapping("/users/{uid}")
	public Optional<User> getUser(@PathVariable(value = "uid") int uid) {
		return dao.findById(uid);
	}
	@GetMapping("/users")
	public List<User> allUsers()
	{
		return dao.findAll();
		}
	@PostMapping("/users")
	public User createUser(@RequestBody User user)
	
	{
		User newUser=dao.save(user);
		return newUser;
		
	}
	@PutMapping("/deposit/{uid}/{amount})
	public Optional<User> updateAmount(@PathVariable(value = "uid") int uid, @RequestParam("amount") double amount) {

		Optional<User> user = dao.findById(uid);
       if(user.getBalance()>=100000.00)
       {
    	   System.out.println("you cannot deposit!!! you have reached maximun limit");
       }
       
		user.setBalance(user.getBalance() + amount);
		Optional<User> updatedUser = dao.save(user);
		return updatedUser;
	}
	
	@PutMapping("/withdraw/{uid}/{withdrawalAmt}")
	public Optional<User> withdraw(@PathVariable("uid") int uid,@PathVariable("withdrawalAmt") double withdrawalAmt)
	{
		Optional<User> user= getUser(uid);
		double balance= user.getBalance();
		if(balance<withdrawalAmt||balance<=1000)
			System.out.println("Amount cannot be withdrawn!!!!");
		double newBalance= balance-withdrawalAmt;
	     user.setBalance(newBalance);
		return dao.save(user);
		
	}
	@GetMapping("/users/balance/{uid}")
	public double getTotalBalance(@PathVariable(value = "uid") int uid) {
		Optional<User> user=dao.findById(uid);
		return user.getBalance();
	}

}
