package henu.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import henu.controller.UserController;

@Repository
public class FileAccess {

	@Resource
	private ServletContext servletContext;
	
	private Logger log=Logger.getLogger(FileAccess.class);
	
	public synchronized String write(String content, String path) {
		File f=new File(servletContext.getRealPath("WEB-INF")+path);
		log.info(servletContext.getRealPath("WEB-INF"));
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(f));
			writer.write(content);
			writer.close();
			log.info(f.getAbsolutePath());
			return path;
		} catch (IOException e) {
			e.printStackTrace();
			log.info(e.getMessage());
			return "";
		}
	}
	
	public synchronized String read(String path) {
		File f=new File(servletContext.getRealPath("WEB-INF")+path);
		log.info(servletContext.getRealPath("WEB-INF"));
		if(!f.exists()) {
			return "";
		}
		try {
			BufferedReader reader =new BufferedReader(new FileReader(f));
			StringBuffer sb=new StringBuffer();
			String line=reader.readLine();
			while(line!=null) {
				sb.append(line);
				line=reader.readLine();
			}
			reader.close();
			return sb.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public void delete(String path) {
		File f=new File(servletContext.getRealPath("WEB-INF")+path);
		if(f.exists()) {
			f.delete();
		}
	}
	
}
