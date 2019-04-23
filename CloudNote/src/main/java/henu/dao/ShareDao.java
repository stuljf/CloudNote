package henu.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ShareDao {

	@Resource
	private JdbcTemplate jt;
	
	public List<Map<String, Object>> selectNotes(Integer uid){
		String sql="select note.id id,title,book,user.name name,privacy from note,user,share where note.uid=user.id and share.nid=note.id and share.uid=? and del=0;";
		List<Map<String, Object>> notes=jt.queryForList(sql, uid);
		return notes;
	}
	
	public List<Map<String, Object>> selectUsers(Integer nid){
		String sql="select id,name,head_img,privacy from user,share where uid=user.id and nid=?;";
		try {
		List<Map<String, Object>> users = jt.queryForList(sql, nid);
		return users;
		}catch(DataAccessException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public int insert(Integer nid, Integer uid, Integer privacy) {
		String sql="insert into share values(?,?,?);";
		int update = jt.update(sql, nid, uid, privacy);
		return update;
	}
	
	public int delete(Integer nid, Integer uid) {
		String sql="delete from share where nid=? and uid=?;";
		int update = jt.update(sql, nid, uid);
		return update;
	}
	
	public int updatePri(Integer nid, Integer uid, Integer privacy) {
		String sql="update share set privacy=? where nid=? and uid=?;";
		int update = jt.update(sql, privacy, nid, uid);
		return update;
	}
	
}
