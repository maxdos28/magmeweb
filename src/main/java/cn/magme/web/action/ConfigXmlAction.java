package cn.magme.web.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import cn.magme.common.JsonResult;

/**
 * 获得文章或杂志的配置文件内容
 * @author jasper
 * @date 2014.8.7
 *
 */
@SuppressWarnings("serial")
public class ConfigXmlAction extends BaseAction {
	
	private String publicationId;
	private String issueId;
	
	public String execute() throws Exception{
		if(publicationId==null||publicationId.trim().length()==0)
			publicationId = "app903";
		if(issueId==null||issueId.trim().length()==0)
			return null;
        HttpServletResponse response=ServletActionContext.getResponse();
        response.setContentType("text/javascript;charset=UTF-8"); 
        PrintWriter out=response.getWriter();
        StringBuffer sb = new StringBuffer();
		try {
			URL url = new URL(
					"http://211.152.43.163/appprofile/"+publicationId.trim()+"/"+issueId.trim()+"/config.xml");
			URLConnection urlConnection = url.openConnection();// 打开url连接
			BufferedReader br = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream()));
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.jsonResult = JsonResult.getSuccess();
		this.jsonResult.put("xml", sb.toString());
		//out.println(sb.toString());
        return JSON;
	}

	public String getPublicationId() {
		return publicationId;
	}

	public void setPublicationId(String publicationId) {
		this.publicationId = publicationId;
	}

	public String getIssueId() {
		return issueId;
	}

	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}
}
