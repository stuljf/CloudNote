package henu.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

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
		String sql="inert into note values(NULL,?,?,?,?,DEFAULT,DEFAULT);";
		int result=jt.update(sql, note.getTitle(), note.getContent(), note.getBook(),
				note.getUid());
		return result;
	}
	
	//标记一条笔记为删除状态（放入回收站）
	public int remove(Integer id) {
		String sql="update note set del=1 where id=?;";
		int result=jt.update(sql, id);
		return result;
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
	
	//改同步标记
	public int update(Note note) {
		String sql="update note set sync=? where id=?;";
		int result=jt.update(sql, note.getSync(), note.getId());
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
	
	//选择一个用户的所有笔记
	public List<Map<String, Object>> selectByUid(Integer uid){
		String sql="select note.id id,title,book,user.name name from note,user where uid=user.id and uid=? and del=0;";
		List<Map<String, Object>> notes=jt.queryForList(sql, uid);
		return notes;
	}
	
	//选择一个笔记本中的所有笔记
	public List<Map<String, Object>> selectByBook(Note note){
		String sql="select note.id id,title,book,user.name name from note,user where uid=user.id and uid=? and book=? and del=0;";
		List<Map<String, Object>> notes=jt.queryForList(sql, note.getUid(), note.getBook());
		return notes;
	}
	
}
