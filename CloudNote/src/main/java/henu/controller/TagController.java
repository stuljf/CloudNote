package henu.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import henu.entity.Tag;
import henu.entity.User;
import henu.service.TagService;

@Controller
@RequestMapping("/tag")
public class TagController {

	@Resource
	private TagService tagService;
	
	@RequestMapping("/notes")
	public String getNotes(Tag tag, HttpServletRequest request, Model model){
		HttpSession session = request.getSession();
		User user=(User) session.getAttribute("user");
		List<Map<String, Object>> notes = tagService.getNotes(tag.getId());
		model.addAttribute("notes", notes);
		model.addAttribute("title", tag.getText());
		return "notes";
	}
	
}
