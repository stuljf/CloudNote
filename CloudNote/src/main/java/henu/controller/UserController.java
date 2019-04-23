package henu.controller;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import henu.entity.User;
import henu.model.ResultModel;
import henu.service.ShareService;
import henu.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	private Logger log=Logger.getLogger(UserController.class);
	
	@Resource
	private UserService userService;
	
	@Resource
	private ShareService shareService;
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	public ResultModel login(@RequestBody User user, HttpServletRequest request) {
		log.info("用户"+user.getEmail()+"通过"+user.getPassword()+"登录");
		String pattern="^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
		if(Pattern.matches(pattern, user.getEmail())) {
			ResultModel result=userService.login(user);
			if(result.getStatus()==200) {
				HttpSession session = request.getSession();
				session.setAttribute("user", result.getData());
			}
			return result;
		}else {
			return ResultModel.result(300, "请输入正确的邮箱格式");
		}
	}
	
	@RequestMapping(value="/friend")
	public String friend(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		User user=(User) session.getAttribute("user");
		List<User> users=userService.getFriend(user.getId());
		model.addAttribute("users", users);
		model.addAttribute("title", "我的好友");
		return "friend";
	}
	
	@RequestMapping(value="/friend2")
	@ResponseBody
	public ResultModel friend2(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user=(User) session.getAttribute("user");
		List<User> users=userService.getFriend(user.getId());
		return ResultModel.ok(users);
	}

	@RequestMapping(value="/add")
	@ResponseBody
	public ResultModel add(String email, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user=(User) session.getAttribute("user");
		return userService.addFriend(user.getId(), email);
	}
	
	@RequestMapping(value="/delete")
	@ResponseBody
	public ResultModel delete(Integer id, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user=(User) session.getAttribute("user");
		return userService.deleteFriend(user.getId(), id);
	}
	
	@RequestMapping(value="/shareUser")
	public String shareUser(Integer nid, Model model){
		List<Map<String, Object>> users = shareService.getUsers(nid);
		model.addAttribute("users", users);
		return "shareUser";
	}
	
	@RequestMapping(value="/change")
	@ResponseBody
	public ResultModel shareChange(Integer nid, Integer uid, Integer privacy) {
		return shareService.change(nid, uid, privacy);
	}
	
	@RequestMapping(value="/cancel")
	@ResponseBody
	public ResultModel shareCancel(Integer nid, Integer uid) {
		return shareService.remove(nid, uid);
	}
	
	@RequestMapping(value="/share")
	@ResponseBody
	public ResultModel share(Integer nid, Integer uid, Integer privacy) {
		return shareService.share(nid, uid, privacy);
	}
	
}
