package henu.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import henu.entity.Note;
import henu.model.ResultModel;
import henu.service.NoteService;
import henu.service.SaveService;
import henu.service.UserService;

@Controller
@RequestMapping("/edit")
public class EditController {

	private Logger log=Logger.getLogger(EditController.class);
	
	@Resource
	private SaveService saveService;
	
	@Resource
	private NoteService noteService;
	
	@Resource
	private UserService userService;
	
	@RequestMapping("/edit")
	public String edit(Integer id, Model model, HttpServletRequest request) {
		Note note = noteService.getNoteById(id);
		String content = saveService.getContent(note.getContent());
		log.info(content);
		model.addAttribute("note", note);
		model.addAttribute("content", content);
		return "edit";
	}
	
	@RequestMapping("/edit2")
	public String edit2(Integer id, Model model, HttpServletRequest request) {
		Note note = noteService.getNoteById(id);
		String content = saveService.getContent(note.getContent());
		model.addAttribute("note", note);
		model.addAttribute("content", content);
		return "edit2";
	}

	@RequestMapping(value="/save",method=RequestMethod.POST)
	@ResponseBody
	public ResultModel save(Integer nid, String content) {
		ResultModel save = saveService.save(content, nid);
		log.info(content);
		return save;
	}

	@RequestMapping(value="/sync")
	@ResponseBody
	public ResultModel sync(Integer id) {
		return ResultModel.ok(saveService.sync(id));
	}
	
	@RequestMapping(value="/content")
	@ResponseBody
	public ResultModel content(Integer id) {
		Note note = noteService.getNoteById(id);
		String s= saveService.getContent(note.getContent());
		return ResultModel.ok(s);
	}
	
	@RequestMapping(value="/image",method=RequestMethod.POST)
	@ResponseBody
	public ResultModel image(@RequestParam("upload") CommonsMultipartFile file) {
		return saveService.saveImage(file);
	}
	
}
