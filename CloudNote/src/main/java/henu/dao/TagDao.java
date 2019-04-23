package henu.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import henu.entity.Note;
import henu.entity.Tag;

@Repository
public class TagDao {

	@Resource
	private JdbcTemplate jt;
	
	private RowMapper<Tag> rowMapper=new BeanPropertyRowMapper<Tag>(Tag.class);
	
	//新建标签
	public int insert(Tag tag) {
		String sql="inert into tag values(NULL,?,?);";
		int result=jt.update(sql, tag.getText(), tag.getUid());
		return result;
	}
	
	//删除标签
	public int delete(Integer id) {
		String sql="delete from tag where id=?;";
		int result=jt.update(sql, id);
		return result;
	}
	
	//选择所有标签
	public List<Tag> queryTags(Integer uid){
		String sql="select * from tag where uid=?";
		List<Tag> tags=jt.query(sql, rowMapper, uid);
		return tags;
	}
	
	//选择笔记下的所有标签
	public List<Tag> queryTagsByNid(Integer nid){
		String sql="select tag.* from tag,tn where tag.id=tid and nid=?;";
		List<Tag> tags=jt.query(sql, rowMapper, nid);
		return tags;
	}
	
	//选择标签下所有的笔记
	public List<Map<String, Object>> selectByTag(Integer tid) throws DataAccessException{
		String sql="select note.id id,title,book,user.name name from note,user,tn where uid=user.id and nid=note.id and tid=? and del=0;";
		List<Map<String, Object>> notes=jt.queryForList(sql, tid);
		return notes;
	}
	
}
