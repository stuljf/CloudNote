package henu.service;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import henu.dao.FileAccess;
import henu.dao.NoteDao;
import henu.entity.Note;
import henu.model.ResultModel;

@Service
public class SaveService {

	@Resource
	private ServletContext servletContext;
	
	@Resource
	private NoteDao noteDao;
	
	@Resource
	private FileAccess fileAccess;
	
	private Logger log=Logger.getLogger(SaveService.class);
	
	public ResultModel saveImage(CommonsMultipartFile file) {
		String path="/sources/noteimg/"+file.hashCode();
		File f=new File(servletContext.getRealPath("WEB-INF")+path);
		try {
			if(!f.exists())
				f.createNewFile();
			file.transferTo(f);
			return ResultModel.ok(servletContext.getContextPath()+path);
		} catch (IOException e) {
			e.printStackTrace();
			return ResultModel.result(400, "图片上传失败");
		}
	}

	public String getContent(String path) {
		String read = fileAccess.read(path);
		log.info(read);
		return read;
	}
	
	public ResultModel save(String content, Integer nid) {
		Note note = noteDao.select(nid);
		String path = note.getContent();
		String p=fileAccess.write(content, path);
		if(!"".equals(p)) {
			int i=noteDao.updateSync(nid);
			if(i==1)
				return ResultModel.ok();
		}
		return ResultModel.result(400, "文件保存失败");
	}
	
	public Integer sync(Integer id) {
		Note note = noteDao.select(id);
		return note.getSync();
	}
	
}
