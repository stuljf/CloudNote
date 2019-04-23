package henu.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import henu.dao.UserDao;
import henu.entity.User;
import henu.model.ResultModel;

@Service
public class UserService {

	private Logger log=Logger.getLogger(UserService.class);
	
	@Resource
	private UserDao userDao;
	
	public ResultModel login(User user) {
		try {
			User u=userDao.selectByEmail(user.getEmail());
			if(u.getPassword().equals(user.getPassword())) {
				return ResultModel.ok(u);
			} else {
				return ResultModel.result(400, "密码不正确");
			}
		}catch(DataAccessException e) {
			String name=user.getEmail().split("@")[0];
			user.setName(name);
			int n=userDao.insert(user);
			if(n==1) {
				log.info(user.getName());
				User u=userDao.selectByEmail(user.getEmail());
				return ResultModel.ok(u);
			} else {
				return ResultModel.result(400, "注册用户失败");
			}
		}
	}

	public List<User> getFriend(Integer id){
		return userDao.selectFriend(id);
	}
	
	public ResultModel addFriend(Integer uid, String email) {
		try {
			User u=userDao.selectByEmail(email);
			if(u.getId()!=uid) {
				int i = userDao.insertFriend(uid, u.getId());
				if(i==2) {
					return ResultModel.ok();
				}
			}
			return ResultModel.result(400, "添加好友失败");
		}catch(DataAccessException e) {
			return ResultModel.result(400, "用户不存在");
		}
	}
	
	public ResultModel deleteFriend(Integer uid, Integer fid) {
		int i = userDao.deleteFriend(uid, fid);
		if(i==2) {
			return ResultModel.ok();
		}else {
			return ResultModel.result(400, "删除好友失败");
		}
	}
	
}
