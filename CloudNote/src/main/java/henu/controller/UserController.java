package henu.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
public class UserController {
	
	private Logger log=Logger.getLogger(UserController.class);
	
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
	public ResultModel login(@RequestBody User user, HttpServletRequest request) {
		
		log.info("用户"+user.getEmail()+"通过"+user.getPassword()+"登录");
		ResultModel result=userService.login(user);
		if(result.getStatus()==200) {
			HttpSession session = request.getSession();
			session.setAttribute("user", result.getData());
		}
		return result;
	}
	
}
