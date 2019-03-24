package henu.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import henu.entity.Tag;
import henu.entity.User;
import henu.model.ResultModel;
import henu.service.NoteService;
import henu.service.RecycleService;

@Controller
@RequestMapping("/recycle")
public class RecycleController {

	@Resource
	private RecycleService recycleService;
	
	@RequestMapping("/notes")
	public String getNotes(HttpServletRequest request, Model model){
		HttpSession session = request.getSession();
		User user=(User) session.getAttribute("user");
		List<Map<String, Object>> notes = recycleService.getDelete(user.getId());
		model.addAttribute("notes", notes);
		model.addAttribute("title", "回收站");
		return "recycle";
	}	
	
	@RequestMapping("/delete")
	@ResponseBody
	public ResultModel delete(Integer id) {
		return recycleService.delete(id);
	}
	
	@RequestMapping("/clear")
	@ResponseBody
	public ResultModel clear(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user=(User) session.getAttribute("user");
		return recycleService.deleteAll(user.getId());
	}
	
	@RequestMapping("/recover")
	@ResponseBody
	public ResultModel recover(Integer id) {
		return recycleService.recover(id);
	}
}
