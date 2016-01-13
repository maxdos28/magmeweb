package cn.magme.web.action.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.util.FileOperate;
import cn.magme.web.action.BaseAction;
@Results({
	@Result(name="success", location="/WEB-INF/pages/looker/admin/adManagerByPage.ftl")
	
})
public class TestMultipartAction extends BaseAction {

	private File file ; //具体上传文件的 引用 , 指向临时目录中的临时文件  
    private String fileFileName ;  // 上传文件的名字 ,FileName 固定的写法 
    private String username;
    private String password;
	public String upload() throws UnsupportedEncodingException {
		// commons-fileupload.jar commons-io
		
		FileOperate.copyFile(file.getAbsolutePath(), "d:/ssssss.zip");
		
		return SUCCESS;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
