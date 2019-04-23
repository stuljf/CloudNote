package henu.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import henu.dao.FileAccess;
import henu.dao.NoteDao;
import henu.entity.Note;
import henu.model.ResultModel;

@Service
public class NoteService {
	
	@Resource
	private NoteDao noteDao;
	
	@Resource
	private FileAccess fileAccess;
	
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
	public ResultModel delete(Integer id) {
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
	
	public Note getNoteById(Integer id) {
		return noteDao.select(id);
	}
	
	public Note addNote(Integer uid) {
		Date date=new Date();
		//构建新笔记文件的路径
		String path="/sources/note/"+date.getTime()+uid;
		//新建笔记文件
		fileAccess.write("<h2>欢迎使用<h2>", path);
		//将新建笔记信息写入数据库
		Note note=new Note();
		note.setBook("默认笔记本");
		note.setUid(uid);
		note.setContent(path);
		note.setTitle("新建笔记");
		int id=noteDao.insert(note);
		//返回新建笔记ID
		return noteDao.select(id);
	}
	
	public ResultModel changeTitle(Integer id, String title) {
		Note note=new Note();
		note.setTitle(title);
		note.setId(id);
		int i = noteDao.updateTitle(note);
		if(i==1) {
			return ResultModel.ok();
		}else {
			return ResultModel.result(400, "修改标题失败");
		}
	}
	
}
