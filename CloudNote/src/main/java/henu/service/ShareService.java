package henu.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import henu.dao.ShareDao;
import henu.model.ResultModel;

@Service
public class ShareService {

	@Resource
	private ShareDao shareDao;
	//查看与我协作的笔记
	public List<Map<String, Object>> getNotes(Integer uid){
		return shareDao.selectNotes(uid);
	}
	//查看已经分享的好友
	public List<Map<String, Object>> getUsers(Integer nid){
		return shareDao.selectUsers(nid);
	}
	//分享给好友
	public ResultModel share(Integer nid, Integer uid, Integer privacy) {
		try {
			int i = shareDao.insert(nid, uid, privacy);
			if(i==1) {
				return ResultModel.ok();
			}
		}catch(DataAccessException e) {
			e.printStackTrace();
		}
		return ResultModel.result(400, "分享给好友失败");
	}
	//取消分享
	public ResultModel remove(Integer nid, Integer uid) {
		int i = shareDao.delete(nid, uid);
		if(i==1) {
			return ResultModel.ok();
		}else {
			return ResultModel.result(400, "取消分享失败");
		}
	}
	//修改分享权限
	public ResultModel change(Integer nid, Integer uid, Integer privacy) {
		int i = shareDao.updatePri(nid, uid, privacy);
		if(i==1) {
			return ResultModel.ok();
		}else {
			return ResultModel.result(400, "修改失败");
		}
	}
	
}
