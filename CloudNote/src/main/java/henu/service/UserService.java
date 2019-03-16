package henu.service;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import henu.dao.UserDao;
import henu.entity.User;
import henu.model.ResultModel;

@Service
public class UserService {

	@Resource
	private UserDao userDao;
	
	public ResultModel login(User user) {
		try {
			User u=userDao.selectByEmail(user.getEmail());
			if(u.getPassword().equals(user.getPassword())) {
				return ResultModel.ok();
			} else {
				return ResultModel.result(400, "密码不正确");
			}
		}catch(DataAccessException e) {
			e.printStackTrace();
			return ResultModel.result(500, "用户不存在");
		}
	}
	
	public ResultModel regist(User user) {
		try {
			int n=userDao.insert(user);
			if(n==1) {
				return ResultModel.ok();
			} else {
				return ResultModel.result(400, "注册用户失败");
			}
		}catch(DataAccessException e) {
			e.printStackTrace();
			return ResultModel.result(500, "数据插入错误");
		}
		
	}
	
}
