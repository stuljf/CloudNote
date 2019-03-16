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
		String sql="insert into user values(NULL,?,?,NULL,?,NULL,NULL);";
		return jt.update(sql, user.getName(), user.getPassword(), user.getEmail());
	}
	
	public User selectByEmail(String email) throws DataAccessException {
		String sql="select * from user where email=?;";
		User user=jt.queryForObject(sql, rowMapper, email);
		return user;
	}
	
	public String selectNameById(int id) {
		String sql = "select name from user where id=?;";
		String name=jt.queryForObject(sql, String.class, id);
		return name;
	}
	
	public List<User> selectAll(){
		String sql="select * from user;";
		List<User> users=jt.query(sql, rowMapper);
		return users;
	}
}
