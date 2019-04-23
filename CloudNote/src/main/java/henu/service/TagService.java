package henu.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import henu.dao.TagDao;
import henu.entity.Note;
import henu.entity.Tag;
import henu.model.ResultModel;

@Service
public class TagService {

	@Resource
	private TagDao tagDao;
	
	public List<Tag> getTags(Integer uid){
		List<Tag> tags = tagDao.queryTags(uid);
		return tags;
	}
	
	public List<Map<String, Object>> getNotes(Integer tid){
		List<Map<String, Object>> notes=tagDao.selectByTag(tid);
		return notes;
	}
	
	public ResultModel delete(Integer nid) {
		List<Tag> tags = tagDao.queryTagsByNid(nid);
		for(Tag tag : tags) {
			try {
				tagDao.selectByTag(tag.getId());
			}catch(DataAccessException e) {
				tagDao.delete(tag.getId());
			}
		}
		return ResultModel.ok();
	}
	
}
