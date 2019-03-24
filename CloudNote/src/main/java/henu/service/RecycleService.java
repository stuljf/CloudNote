package henu.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import henu.dao.NoteDao;
import henu.entity.Note;
import henu.model.ResultModel;

@Service
public class RecycleService {
	@Resource
	private NoteDao noteDao;

	public List<Map<String, Object>> getDelete(Integer uid) {
		Note note=new Note();
		note.setUid(uid);
		note.setDel(1);
		return noteDao.selectNotes(note);
	}
	
	public ResultModel delete(Integer id) {
		int delete = noteDao.delete(id);
		if(delete==1) {
			return ResultModel.ok();
		}else {
			return ResultModel.result(400, "删除笔记失败");
		}
	}
	
	public ResultModel deleteAll(Integer uid) {
		int delete = noteDao.deleteAll(uid);
		if(delete>0) {
			return ResultModel.ok();
		}else {
			return ResultModel.result(400, "删除笔记失败");
		}
	}
	
	public ResultModel recover(Integer id) {
		Note note=new Note();
		note.setId(id);
		note.setDel(0);
		int result = noteDao.updateDel(note);
		if(result==1) {
			return ResultModel.ok();
		}else {
			return ResultModel.result(400, "还原笔记失败");
		}
	}
	
}
