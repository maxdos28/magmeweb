package cn.magme.web.action.app;

import java.util.Enumeration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionContext;

import cn.magme.pojo.Issue;
import cn.magme.service.IssueService;
import cn.magme.util.Struts2Utils;
import cn.magme.web.action.BaseAction;

/**
 * @author devin.song
 * @date 2013-04-01
 *
 */
@Results({
	@Result(name="success",location="/WEB-INF/pages/publish/deviceRead.ftl")
})
public class DeviceReadAction extends BaseAction {
	private Long pid;
	private Long eid;
	private int pno;
	private String deviceType="pc";
	private int size=768;
	private String webTitle;
	
	@Resource
	private IssueService issueService;
	
	
	@SuppressWarnings("rawtypes")
	@Override
	public String execute() throws Exception {
		Issue issue =issueService.queryById(eid);
		webTitle = issue.getPublicationName()+"  "+issue.getIssueNumber();
		HttpServletRequest request = Struts2Utils.getRequest();
		Enumeration headerNames = request.getHeaderNames();
		  while(headerNames.hasMoreElements()) {
		  String headerName = (String)headerNames.nextElement();
		  String keyValue = request.getHeader(headerName);
		  String keyName = headerName;
		 
		  String tmp = "User-Agent".toUpperCase();
		  keyName = keyName.toUpperCase();
				  if(tmp.equals(keyName)){
					  keyValue = keyValue.toUpperCase();
					  if(keyValue.contains("iPad".toUpperCase())){
//						  System.out.println("ipad");
						  deviceType="iPad";
					  }else if(keyValue.contains("iPhone".toUpperCase())){
						  //System.out.println("iPhone");
						  deviceType="iPhone";
					  }else if(keyValue.contains("Android".toUpperCase())){
//						  System.out.println("Android");
						  deviceType="Android";
					  }else{
//						  System.out.println(keyValue);
						  deviceType="pc";
					  }
				  }
//				  System.out.println(headerName+"\n  ==:deviceType:"+deviceType+":===");
//				  System.out.println(">>>:"+keyValue);
		  }
		  
		return "success";
	}



	public Long getPid() {
		return pid;
	}





	public void setPid(Long pid) {
		this.pid = pid;
	}





	public Long getEid() {
		return eid;
	}





	public void setEid(Long eid) {
		this.eid = eid;
	}





	public int getPno() {
		return pno;
	}





	public void setPno(int pno) {
		this.pno = pno;
	}





	public String getDeviceType() {
		return deviceType;
	}



	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}



	public int getSize() {
		return size;
	}



	public void setSize(int size) {
		this.size = size;
	}


	public String getWebTitle() {
		return webTitle;
	}



	public void setWebTitle(String webTitle) {
		this.webTitle = webTitle;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = -1760595117847759018L;
	
}
