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
	
	//选择所有笔记本
	public List<String> getBooks(Integer uid){
		List<String> books = noteDao.queryBooks(uid);
		return books;
	}
	
	//选择一个用户的所有笔记
	public List<Map<String, Object>> getAllNotes(Integer uid){
		Note note=new Note();
		note.setUid(uid);
		note.setDel(0);
		return noteDao.selectNotes(note);
	}
	
	//选择一个笔记本中的所有笔记
	public List<Map<String, Object>> getNotes(Integer uid, String book){
		Note note=new Note();
		note.setBook(book);
		note.setUid(uid);
		List<Map<String, Object>> notes=noteDao.selectByBook(note);
		return notes;
	}
	
	//将笔记移入回收站
	public ResultModel remove(Integer id) {
		Note note=new Note();
		note.setId(id);
		note.setDel(1);
		int delete = noteDao.updateDel(note);
		if(delete==1) {
			return ResultModel.ok();
		}else {
			return ResultModel.result(400, "删除笔记失败");
		}
	}
}
