package henu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mysql.jdbc.Statement;

import henu.entity.Note;

@Repository
public class NoteDao {

	@Resource
	private JdbcTemplate jt;
	
	private RowMapper<Note> rowMapper=new BeanPropertyRowMapper<Note>(Note.class);

	//选择所有笔记本
	public List<String> queryBooks(Integer uid){
		String sql="select distinct book from note where uid=? and del=0;";
		List<String> books=jt.queryForList(sql, String.class, uid);
		return books;
	}
	
	//插入一条笔记
	public int insert(Note note) {
		String sql="insert into note values(NULL,?,?,?,?,DEFAULT,DEFAULT);";
		KeyHolder keyHolder=new GeneratedKeyHolder();
		jt.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, note.getTitle());
				ps.setString(2, note.getContent());
				ps.setString(3, note.getBook());
				ps.setInt(4, note.getUid());
				return ps;
			}
			
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	//从数据库中删除笔记
	public int delete(Integer id) {
		String sql="delete from note where id=? and del=1;";
		int result=jt.update(sql, id);
		return result;
	}
	
	//从数据库中删除所有标记删除的笔记
	public int deleteAll(Integer uid) {
		String sql="delete from note where uid=? and del=1;";
		int result=jt.update(sql, uid);
		return result;
	}
	
	//改删除标记
	public int updateDel(Note note) {
		String sql="update note set del=? where id=?;";
		int result=jt.update(sql, note.getDel(), note.getId());
		return result;
	}
	
	//改同步标记
	public int updateSync(Integer id) {
		String sql="update note set sync=sync+1 where id=?;";
		int result=jt.update(sql, id);
		return result;
	}
	
	//改标题
	public int updateTitle(Note note) {
		String sql="update note set title=? where id=?;";
		int result=jt.update(sql, note.getTitle(), note.getId());
		return result;
	}
	
	//改笔记本
	public int updateBook(Note note) {
		String sql="update note set book=? where id=?;";
		int result=jt.update(sql, note.getBook(), note.getId());
		return result;
	}
	
	//选择一条笔记
	public Note select(Integer id) {
		String sql="select * from note where id=?";
		Note note = jt.queryForObject(sql, rowMapper, id);
		return note;
	}
	
	//选择所有笔记
	public List<Map<String, Object>> selectNotes(Note note){
		String sql="select note.id id,title,book,user.name name from note,user where uid=user.id and uid=? and del=?;";
		List<Map<String, Object>> notes=jt.queryForList(sql, note.getUid(), note.getDel());
		return notes;
	}
	
	//选择一个笔记本中的所有笔记
	public List<Map<String, Object>> selectByBook(Note note){
		String sql="select note.id id,title,book,user.name name from note,user where uid=user.id and uid=? and book=? and del=0;";
		List<Map<String, Object>> notes=jt.queryForList(sql, note.getUid(), note.getBook());
		return notes;
	}
	
}
