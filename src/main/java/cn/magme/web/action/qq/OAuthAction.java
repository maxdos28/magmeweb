/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.qq;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;

import org.apache.struts2.ServletActionContext;

import cn.magme.common.QQConfig;
import cn.magme.common.SystemProp;
import cn.magme.constants.PojoConstant;
import cn.magme.constants.WebConstant;
import cn.magme.pojo.User;
import cn.magme.service.OAuthService;
import cn.magme.service.UserService;
import cn.magme.util.StringUtil;
import cn.magme.util.oauth.ConnectUtils;
import cn.magme.web.action.BaseAction;

import com.opensymphony.xwork2.ActionContext;

/**
 * @author guozheng
 * @date 2011-6-27
 * @version $id$
 */
@SuppressWarnings("serial")
public class OAuthAction extends BaseAction {

	@Resource
	private QQConfig qqConfig;
	@Resource
	private OAuthService oAuthService;
	@Resource
	private UserService userService;
	@Resource
	private SystemProp systemProp;
	
	/*
	 * 请求获取临时token
	 */
	public void request() {
		String ret = this.oAuthService.requestToken();
		String[] strs = ret.split("&");
		if (strs.length == 2) { // 返回格式正确 oauth_token应该为以一个参数
			String url = this.oAuthService.getTmpToken(strs[0]
					.substring(strs[0].indexOf("=") + 1));
			try {
			    ActionContext.getContext().getSession().put(WebConstant.SESSION.QQ_CALLBACKURL, this.callbackUrl);
				//暂写入cookie
				ServletActionContext.getResponse().addCookie(new Cookie("oauth_token_secret",strs[1].substring(strs[1].indexOf("=") + 1)));
				ServletActionContext.getResponse().sendRedirect(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else { // 返回格式不正确
			
		}

	}

	/*
	 * 请求具有访问权限的access_token
	 */
	public void callback() {
		String oauth_token_secret = null;
		Cookie[] cs = ServletActionContext.getRequest().getCookies();
		for(Cookie c : cs){
			String name = c.getName();
			if(name.equals("oauth_token_secret")){
				oauth_token_secret = c.getValue();
			}
		}
		if(oauth_token_secret == null){
			System.out.println("can't get oauth_token_secret from cookies");
			return;
		}
		
		//step5返回值 
		String ret = this.oAuthService.accessToken(oauth_token, oauth_vericode, oauth_token_secret);
		Map<String,String> map = ConnectUtils.parseTokenResult(ret);
		
		//验证
		boolean verify = ConnectUtils.verifyOpenId(map.get("openid"), map.get("timestamp"), map.get("oauth_signature"), qqConfig.getAppkey());
		if(!verify){
			this.sendRedirect(systemProp.getDomain() + "/qqlogin.html?jc=2");
			return;
		}
		//调用api获取用户信息
		String userstr = this.oAuthService.getUserInfo(map.get("oauth_token"), map.get("openid"), map.get("oauth_token_secret"));
		try {    //将user信息写入数据库
			Map<String,Object> userInfo = ConnectUtils.parseXmlToMap(userstr);
			//System.out.println("access_token:" + map.get("oauth_token"));
			//获取用户名 检查用户是否已经存在
			String  userName = map.get("openid") + "@qq.com";
			User user = null;
			user = this.userService.getUserByName(userName);
			boolean flg = true;
			if(user == null || user.getId() == null){
				user = new User();
				user.setUserName(map.get("openid") + "@qq.com");
				user.setNickName((String) userInfo.get("nickname"));
				String url = (String) userInfo.get("figureurl_2");
				user.setType(PojoConstant.USER.USER_TYPE_QQ);
				user.setEmail(map.get("openid") + "@qq.com");
				user.setPassword("");
				user.setStatus(PojoConstant.USER.STATUS_ACTIVE);
				user.setReserve1(map.get("oauth_token")); //临时字段1,用来暂存第五步返回的access_token //每个QQ号码唯一
				//添加用户
				user = this.userService.addUser(user,url);
			} else {
			    flg = false;
			}
			this.userService.updateUserLastLoginTime(user.getId());
			
			//查询用户的统计信息
			if(user!=null){
			    user.setStatsMap(this.userService.getUserStatsMapByUserId(user.getId()));
			    userService.setUserEnjoyList(user);
			}
			
			//写入当前session
			ServletActionContext.getRequest().getSession().setAttribute(WebConstant.SESSION.USER, user);
			
	          //获取回调链接
            callbackUrl=(String)ServletActionContext.getRequest().getSession().getAttribute(WebConstant.SESSION.QQ_CALLBACKURL);
            if(StringUtil.isBlank(callbackUrl)){
                callbackUrl=systemProp.getDomain();
            }
            callbackUrl=URLEncoder.encode(callbackUrl,"UTF-8");
			this.sendRedirect(systemProp.getDomain() + "/qqlogin.html?flg="+flg+"&callbackUrl="+callbackUrl);
		} catch (Exception e) {
			//获取用户信息错误
			this.sendRedirect(systemProp.getDomain() + "/qqlogin.html?jc=1"+"&callbackUrl="+systemProp.getDomain());
			e.printStackTrace();
		}
	}
	
	private void sendRedirect(String url){
		try {
			ServletActionContext.getResponse().sendRedirect(url);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private String oauth_token;
	private String oauth_token_secret;
	private String oauth_consumer_key;
	private String openid;
	private String oauth_signature;
	private String timestamp;
	private String oauth_vericode;

	public String getOauth_token() {
		return oauth_token;
	}

	public void setOauth_token(String oauthToken) {
		oauth_token = oauthToken;
	}

	public String getOauth_token_secret() {
		return oauth_token_secret;
	}

	public void setOauth_token_secret(String oauthTokenSecret) {
		oauth_token_secret = oauthTokenSecret;
	}

	public String getOauth_consumer_key() {
		return oauth_consumer_key;
	}

	public void setOauth_consumer_key(String oauthConsumerKey) {
		oauth_consumer_key = oauthConsumerKey;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getOauth_signature() {
		return oauth_signature;
	}

	public void setOauth_signature(String oauthSignature) {
		oauth_signature = oauthSignature;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getOauth_vericode() {
		return oauth_vericode;
	}

	public void setOauth_vericode(String oauthVericode) {
		oauth_vericode = oauthVericode;
	}
	
	public static void main(String[] args) {
		String path = "http://qzapp.qlogo.cn/qzapp/211226/29349BDBF890CB5FAFD1755CE01216CF/100";
		/*File file;
		
		try {
			InputStream fis = new URL("http://qzapp.qlogo.cn/qzapp/211226/29349BDBF890CB5FAFD1755CE01216CF/100").openStream();
			FileOutputStream fos = new FileOutputStream("f:\\test.jpg");
			byte[] bytes = new byte[1024];
			int count = 0;
			while((count = fis.read(bytes)) >= 0){
				fos.write(bytes);
			}
			fis.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		try {
			System.out.println(new URL(path).getFile());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//QQ登陆完成之后的回调链接
	private String callbackUrl;

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }
}
