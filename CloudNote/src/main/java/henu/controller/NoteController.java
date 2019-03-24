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

import henu.entity.Note;
import henu.entity.Tag;
import henu.entity.User;
import henu.model.ResultModel;
import henu.service.NoteService;
import henu.service.TagService;
import henu.service.UserService;

@Controller
@RequestMapping("/note")
public class NoteController {

	@Resource
	private TagService tagService;
	
	@Resource
	private NoteService noteService;
	
	@RequestMapping("/main")
	public String showMain(HttpServletRequest request, Model model) {
		
		HttpSession session = request.getSession();
		User user=(User) session.getAttribute("user");
		
		List<String> books = noteService.getBooks(user.getId());
		model.addAttribute("books", books);
		
		List<Tag> tags = tagService.getTags(user.getId());
		model.addAttribute("tags", tags);
		
		return "main";
	}
	
	@RequestMapping("/notes")
	public String getNotes(Integer id, String book, HttpServletRequest request, Model model){
		HttpSession session = request.getSession();
		User user=(User) session.getAttribute("user");
		List<Map<String, Object>> notes=null;
		if(id<0) {
			if(book.equals("最近访问")) {
				notes=noteService.getAllNotes(user.getId());
			}else {
				notes = noteService.getNotes(user.getId(), book);
			}
		}else {
			notes = tagService.getNotes(id);
		}
		model.addAttribute("notes", notes);
		model.addAttribute("title", book);
		model.addAttribute("tagid", id);
		return "notes";
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public ResultModel delete(Integer id) {
		return noteService.remove(id);
	}
}
