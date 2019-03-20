package henu.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import henu.entity.User;
import henu.model.ResultModel;
import henu.service.UserService;

@Controller
@RequestMapping("/user")
public class TestController {
	
	private Logger log=Logger.getLogger(TestController.class);
	
	@Resource
	private UserService userService;
	
	@RequestMapping(value="/regist",method=RequestMethod.POST)
	@ResponseBody
	public ResultModel regist(User user) {
		System.out.println(user.getPassword());
		return userService.regist(user);
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	public ResultModel login(@RequestBody User user) {
		
		log.info("////////////////////"+user.getEmail()+"///"+user.getPassword());
		
		return userService.login(user);
	}
	
}
