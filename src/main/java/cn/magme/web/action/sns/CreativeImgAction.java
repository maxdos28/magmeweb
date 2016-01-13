package cn.magme.web.action.sns;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.service.sns.CreativeImgService;
import cn.magme.util.StringUtil;
import cn.magme.web.action.admin.BaseAction;

import com.opensymphony.xwork2.ActionContext;

/**
 * sns 图片操作
 * 
 * @author xiao.chen
 * @date 2011-5-20
 * @version $id$
 */
@Results({@Result(name="changePicJson",type="json",params={"root","jsonResult","contentType","text/html"})})
public class CreativeImgAction extends BaseAction {
	private static final long serialVersionUID = -3018796720015353009L;

	@Resource
	private CreativeImgService creativeImgService;
	
	private static final String PIC_TYPE = "\\.(jpg|jpeg|pjpeg|gif|png)$";
	
	private static final String GIF_TYPE=".gif";
	
	private static final Pattern PIC_TYPE_PATTERN=Pattern.compile(PIC_TYPE,Pattern.CASE_INSENSITIVE);
	private static final Logger log=Logger.getLogger(CreativeImgAction.class);

	@Override
	public String execute() throws Exception {
		  this.jsonResult=JsonResult.getFailure();
		  if(StringUtil.isBlank(type)){
			  this.jsonResult.setMessage("文件类型必须传");
			  return JSON;
		  }
		  if(!PIC_TYPE_PATTERN.matcher(type).matches()){
			  this.jsonResult.setMessage("文件类型不正确，只能支持jpg|jpeg|pjpeg|gif|png类型文件");
			  return JSON;
		  }
		  boolean isGif=false;
		  if(GIF_TYPE.equalsIgnoreCase(this.type)){
			 isGif=true; 
		  }
		  ActionContext ctx = ActionContext.getContext();
          HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
          BufferedInputStream inputStream = new BufferedInputStream(request.getInputStream());
          this.jsonResult = creativeImgService.upload(inputStream,isGif);
		return JSON;
	}
	/**
	 * 更换图片
	 * @return
	 */
	public String changePic(){
	  this.jsonResult=JsonResult.getFailure();
	  if(StringUtil.isBlank(type)){
		  this.jsonResult.setMessage("文件类型必须传");
		  return JSON;
	  }
	  if(!PIC_TYPE_PATTERN.matcher(type).matches()){
		  this.jsonResult.setMessage("文件类型不正确，只能支持jpg|jpeg|pjpeg|gif|png类型文件");
		  return JSON;
	  }
	  boolean isGif=false;
	  if(GIF_TYPE.equalsIgnoreCase(this.type)){
		 isGif=true; 
	  }		
      try {
		BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(changePicFile));
		this.jsonResult = creativeImgService.upload(inputStream,isGif);
	  } catch (FileNotFoundException e) {
		log.error("", e);
	  }
	  return "changePicJson";
	}
	private String type;
	
	private File changePicFile;
	
	
	
	public File getChangePicFile() {
		return changePicFile;
	}
	public void setChangePicFile(File changePicFile) {
		this.changePicFile = changePicFile;
	}
	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}
}
