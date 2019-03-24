package henu.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import henu.dao.NoteDao;
import henu.entity.Note;
import henu.model.ResultModel;

@Service
public class NoteService {
	
	@Resource
	private NoteDao noteDao;
	
	public List<String> getBooks(Integer uid){
		List<String> books = noteDao.queryBooks(uid);
		return books;
	}
	
	public List<Map<String, Object>> getAllNotes(Integer uid){
		return noteDao.selectByUid(uid);
	}
	
	public List<Map<String, Object>> getNotes(Integer uid, String book){
		Note note=new Note();
		note.setBook(book);
		note.setUid(uid);
		List<Map<String, Object>> notes=noteDao.selectByBook(note);
		return notes;
	}
	
	public ResultModel remove(Integer id) {
		int delete = noteDao.remove(id);
		if(delete==1) {
			return ResultModel.ok();
		}else {
			return ResultModel.result(400, "删除笔记失败");
		}
	}

}
