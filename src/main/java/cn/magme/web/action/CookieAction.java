package cn.magme.web.action;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import cn.magme.common.JsonResult;
import cn.magme.util.StringUtil;

public class CookieAction extends BaseAction{

	private static final long serialVersionUID = -4990314466872103111L;

	public String regex_muid="^(.*)\\_\\d{10,15}$";
	
	public String getMuid(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String muid=null;
		Cookie[] cookies=request.getCookies();
		//获取Cookie中的muid
		if(cookies!=null){
			for(Cookie cookie:cookies){
				if(cookie.getName().equalsIgnoreCase("magmecn_muid")){
					muid=cookie.getValue();
					break;
				}
			}
		}
		
		//如果Cookie中没有muid,则由程序生成muid,并写回Cookie
		if(StringUtil.isBlank(muid)||!muid.matches(regex_muid)){
			HttpServletResponse response=ServletActionContext.getResponse();
			muid=this.generateMuid();
			Cookie cookie=new Cookie("magmecn_muid",muid);
			cookie.setPath("/");
			cookie.setMaxAge(60*60*24*5000);
			response.addCookie(cookie);
			
			Cookie cookie2=new Cookie("magmecn_oldVis","0");
			cookie2.setPath("/");
			cookie2.setMaxAge(60*60*24*5000);
			response.addCookie(cookie2);			
		}
		
		this.generateJsonResult(JsonResult.CODE.SUCCESS, 
				JsonResult.MESSAGE.SUCCESS, "muid", muid);
		return JSON;
	}
	
	//生成MUID,此算法与JS中的生成MUID算法保持一致
	private String generateMuid(){
		Double db=Math.random();
		String str=Double.toHexString(db);
		return str.substring(4)+"_"+(new Date().getTime());
	}
}
