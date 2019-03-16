package henu.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import henu.entity.User;

//作为Spring Test的类
@RunWith(SpringJUnit4ClassRunner.class)
//加载配置文件
@ContextConfiguration(locations="classpath:spring.xml")
public class TestUserDao {

	@Resource
	private UserDao userDao;
	
	@Test
	public void testSelectAll() {
		List<User> users=userDao.selectAll();
		for(User user:users) {
			System.out.println(user.getId()+":"+user.getName());
		}
	}
	
}
