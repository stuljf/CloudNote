package henu.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import henu.entity.User;

@Repository
public class UserDao {

	@Resource
	private JdbcTemplate jt;
	
	private RowMapper<User> rowMapper=new BeanPropertyRowMapper<User>(User.class);
	
	public int insert(User user) throws DataAccessException {
		String sql="insert into user values(NULL,?,?,?,NULL);";
		return jt.update(sql, user.getName(), user.getPassword(), user.getEmail());
	}
	
	public User selectByEmail(String email) throws DataAccessException {
		String sql="select * from user where email=?;";
		User user=jt.queryForObject(sql, rowMapper, email);
		return user;
	}
	
	public User selectById(Integer id) throws DataAccessException {
		String sql="select * from user where id=?;";
		User user=jt.queryForObject(sql, rowMapper, id);
		return user;
	}
	
	public String selectNameById(Integer id) {
		String sql = "select name from user where id=?;";
		String name=jt.queryForObject(sql, String.class, id);
		return name;
	}
	
	public List<User> selectAll(){
		String sql="select * from user;";
		List<User> users=jt.query(sql, rowMapper);
		return users;
	}
	
	public List<User> selectFriend(Integer uid){
		String sql="select u.* from friend f,user u where f.fid=u.id and f.uid=?;";
		List<User> friends=jt.query(sql, rowMapper, uid);
		return friends;
	}
}
