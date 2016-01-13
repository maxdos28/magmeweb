/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.interceptor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import cn.magme.common.JsonResult;
import cn.magme.constants.WebConstant;
import cn.magme.pojo.AdUser;
import cn.magme.pojo.Admin;
import cn.magme.pojo.NewsManageUser;
import cn.magme.pojo.Publisher;
import cn.magme.pojo.User;
import cn.magme.pojo.charge.UserDeviceBind;
import cn.magme.pojo.look.LooAdminUser;
import cn.magme.pojo.ma.MaAdminUser;
import cn.magme.pojo.phoenix.PhoenixUser;
import cn.magme.pojo.sms.SmsUser;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * 检查用户是否登陆
 * @author jacky_zhou
 * @date 2011-5-19
 * @version $id$
 */
@SuppressWarnings("serial")
public class AuthInterceptor extends MethodFilterInterceptor {
    
    /**
     * 定义了不需要进行登陆校验的请求,精确到方法,多个方法之间以","号分隔,
     * 不支持模糊匹配,例如 cn.magme.web.action.admin.TestHeheAction.a1
     */
    Set<String> excludeActionMethods = Collections.emptySet();

    @Override
    protected String doIntercept(ActionInvocation invocation) throws Exception {
//        //屏蔽掉验证码
//        ActionContext ctx=ActionContext.getContext();
//        HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST); 
//        ctx.getSession().put(WebConstant.SESSION.AUTHCODE,request.getParameter("authcode"));
        
        String result=null;
        
        ActionProxy proxy=invocation.getProxy();
        //请求对应的方法
        String actionMethod=proxy.getAction().getClass().getName()+"."+proxy.getMethod();
        
        /*if(actionMethod.startsWith("cn.magme.web.action.stat")){
        	return invocation.invoke();
        }*/

        if( actionMethod.startsWith("cn.magme.web.action.app")){
            return invocation.invoke();
        }  
        if( actionMethod.startsWith("cn.magme.web.action.page")){
        	return invocation.invoke();
        } 
        if( actionMethod.startsWith("cn.magme.web.action.test")){
        	return invocation.invoke();
        }
        //凤凰周刊不需要登录校验
        if( actionMethod.startsWith("cn.magme.web.action.phoenix")){
        	PhoenixUser phoenixUser = (PhoenixUser)ActionContext.getContext().getSession().get(WebConstant.SESSION.PHOENIX_USER);
        	 if(phoenixUser!=null || excludeActionMethods.contains(actionMethod)){
             	return invocation.invoke();
             }else if(actionMethod.toUpperCase().endsWith("JSON")){
             	//json请求，返回json格式的未登陆信息
                 JsonResult jsonResult=new JsonResult(JsonResult.CODE.NOT_LOGIN,"用户未登陆",null,null);
                 String jsonStr=JSONObject.fromObject(jsonResult).toString();
                 HttpServletResponse response=ServletActionContext.getResponse();
                 response.setContentType("text/html;charset=UTF-8"); 
                 response.getWriter().print(jsonStr);
                 return null;
             }
             //非JSON请求,直接跳转到未登录页面
         	return "phoenix_user_login";
        }
        //look登录校验
        if( actionMethod.startsWith("cn.magme.web.action.look")){
        	LooAdminUser lookUser = (LooAdminUser)ActionContext.getContext().getSession().get(WebConstant.SESSION.LOOK_USER);
        	 if(lookUser!=null || excludeActionMethods.contains(actionMethod)){
             	return invocation.invoke();
             }else if(actionMethod.toUpperCase().endsWith("JSON")){
             	//json请求，返回json格式的未登陆信息
                 JsonResult jsonResult=new JsonResult(JsonResult.CODE.NOT_LOGIN,"用户未登陆",null,null);
                 String jsonStr=JSONObject.fromObject(jsonResult).toString();
                 HttpServletResponse response=ServletActionContext.getResponse();
                 response.setContentType("text/html;charset=UTF-8"); 
                 response.getWriter().print(jsonStr);
                 return null;
             }
             //非JSON请求,直接跳转到未登录页面
         	return "look_user_login";
        }  
        //ma登录校验
        if( actionMethod.startsWith("cn.magme.web.action.ma")){
        	MaAdminUser maUser = (MaAdminUser)ActionContext.getContext().getSession().get(WebConstant.SESSION.MA_USER);
        	 if(maUser!=null || excludeActionMethods.contains(actionMethod)){
             	return invocation.invoke();
             }else if(actionMethod.toUpperCase().endsWith("JSON")){
             	//json请求，返回json格式的未登陆信息
                 JsonResult jsonResult=new JsonResult(JsonResult.CODE.NOT_LOGIN,"用户未登陆",null,null);
                 String jsonStr=JSONObject.fromObject(jsonResult).toString();
                 HttpServletResponse response=ServletActionContext.getResponse();
                 response.setContentType("text/html;charset=UTF-8"); 
                 response.getWriter().print(jsonStr);
                 return null;
             }
             //非JSON请求,直接跳转到未登录页面
         	return "ma_user_login";
        }
        
        if( actionMethod.startsWith("cn.magme.web.action.sms")){
        	SmsUser smsUser = (SmsUser)ActionContext.getContext().getSession().get(WebConstant.SESSION.SMS_USER);
        	//已经登陆或者不需要登陆
            if(smsUser!=null || excludeActionMethods.contains(actionMethod)){
            	return invocation.invoke();
            }else if(actionMethod.toUpperCase().endsWith("JSON")){
            	//json请求，返回json格式的未登陆信息
                JsonResult jsonResult=new JsonResult(JsonResult.CODE.NOT_LOGIN,"用户未登陆",null,null);
                String jsonStr=JSONObject.fromObject(jsonResult).toString();
                HttpServletResponse response=ServletActionContext.getResponse();
                response.setContentType("text/html;charset=UTF-8"); 
                response.getWriter().print(jsonStr);
                return null;
            }
            //非JSON请求,直接跳转到未登录页面
        	return "sms_user_login";
        }

        //活动页面都不用登陆
        if(actionMethod.startsWith("cn.magme.web.action.activity")){
        	return invocation.invoke();
        }

        if(actionMethod.startsWith("cn.magme.web.action.newPublisher")){
            if(actionMethod.startsWith("cn.magme.web.action.newPublisher.LoginAction")
            		||actionMethod.startsWith("cn.magme.web.action.newPublisher.AccountAliveAction")
                    ||actionMethod.startsWith("cn.magme.web.action.newPublisher.RegisterAction")){
                return invocation.invoke();
            }
            Publisher publisher = (Publisher)ActionContext.getContext().getSession().get(WebConstant.SESSION.PUBLISHER);
            Admin admin =(Admin) ActionContext.getContext().getSession().get(WebConstant.SESSION.ADMIN);
            if(admin==null&&publisher==null){
                return "new_publisher_login";
            }
        	return invocation.invoke();
        }
        if(actionMethod.startsWith("cn.magme.web.action.golf")){
            if(actionMethod.startsWith("cn.magme.web.action.golf.LoginAction")){
                return invocation.invoke();
            }
            NewsManageUser newsManageUser = (NewsManageUser)ActionContext.getContext().getSession().get(WebConstant.SESSION.USER_GOLF);
            if(newsManageUser==null){
                return "golf_login";
            }
        	return invocation.invoke();
        }
        
        //对于cn.magme.web.action.wap.WapAction的请求,不进行任何处理
        if( actionMethod.startsWith("cn.magme.web.action.wap.WapAction")){
            return invocation.invoke();
        }  
        
        if(actionMethod.startsWith("cn.magme.web.action.mobile")){
        	return invocation.invoke();
        }
        
        //对于cn.magme.web.action.qq和cn.magme.web.action.admin包下的请求,暂时不进行任何处理
        if(actionMethod.startsWith("cn.magme.web.action.stat") || actionMethod.startsWith("cn.magme.web.action.admin") || actionMethod.startsWith("cn.magme.web.action.qq")){
        	//请求是后台的(admin)
        	if(actionMethod.startsWith("cn.magme.web.action.stat")||( actionMethod.startsWith("cn.magme.web.action.admin") 
        	        && (actionMethod.indexOf("checkCode") == -1 
        	        && actionMethod.indexOf("login") == -1 
        	        && actionMethod.indexOf("dataI") == -1 
        	        && actionMethod.indexOf("dataIICount") == -1 
        	        && actionMethod.indexOf("findEventsJson") == -1
        	        && actionMethod.indexOf("areaLst") == -1))){
        		Admin admin = (Admin) ServletActionContext.getRequest().getSession().getAttribute(WebConstant.SESSION.ADMIN);
        		if(admin != null){
        			return invocation.invoke();
        		}else{
        			if(actionMethod.toUpperCase().endsWith("JSON")){
	                    JsonResult jsonResult=new JsonResult(JsonResult.CODE.NOT_LOGIN,"用户未登陆",null,null);
	                    String jsonStr=JSONObject.fromObject(jsonResult).toString();
	                    HttpServletResponse response=ServletActionContext.getResponse();
	                    response.setContentType("text/html;charset=UTF-8"); 
	                    response.getWriter().print(jsonStr);
	                    return null;
	                //非JSON请求,直接跳转到未登录页面
	                }else{
	                	return "admin_no_login";
	                }        			
        		}
        	}
            return invocation.invoke();
        }
        
        //支付宝请求
        if( actionMethod.startsWith("cn.magme.web.action.alipay")){
            return invocation.invoke();
        }  
        
        //对publish中的包处理登录过滤
       if(actionMethod.startsWith("cn.magme.web.action.publish") || actionMethod.startsWith("cn.magme.web.action.newpublisherstat")){
        	 //此处是校验用户登陆的代码,需根据实际情况进行调整
        	AdUser publisher=(AdUser)ActionContext.getContext().getSession().get(WebConstant.SESSION.ADUSER);
            boolean isLogin=(publisher!=null );
            
            //阅读器单独一个拦截===================
            if(actionMethod.startsWith("cn.magme.web.action.publish.MagReadAction")){
            	AdUser adUser=(AdUser)ActionContext.getContext().getSession().get(WebConstant.SESSION.ADUSER);
            	isLogin=(adUser!=null);
            }
            
            if(actionMethod.startsWith("cn.magme.web.action.publish.MagReadAction") || actionMethod.startsWith("cn.magme.web.action.ad.AdvertiseAction.addInsertAdvertise")){
            	AdUser adUser=(AdUser)ActionContext.getContext().getSession().get(WebConstant.SESSION.ADUSER);
            	isLogin=(adUser!=null);
            }
            
            //阅读器单独一个拦截===================
            
            //如果用户已经登陆,正常处理
            if(excludeActionMethods.contains(actionMethod)){
                result=invocation.invoke();
                return result;
            //默认需要进行用户登陆校验
            }else{
	            if(isLogin){
	                result=invocation.invoke();
	            //如果用户尚未登陆,跳转到用户登陆
	            }else{
	                //对于JSON请求,返回封装好的JSON格式的用户未登陆信息
	                if(actionMethod.toUpperCase().endsWith("JSON")){
	                    JsonResult jsonResult=new JsonResult(JsonResult.CODE.NOT_LOGIN,"用户未登陆",null,null);
	                    String jsonStr=JSONObject.fromObject(jsonResult).toString();
	                    HttpServletResponse response=ServletActionContext.getResponse();
	                    response.setContentType("text/html;charset=UTF-8"); 
	                    response.getWriter().print(jsonStr);
	                    result = null;
	                //非JSON请求,直接跳转到未登录页面
	                }else{
	                    result="new_publisher_login";
	                }
	            }
	            return result;
	        }
        }
        if(actionMethod.startsWith("cn.magme.web.action.ad")){
        	Map<String,Object> session=ActionContext.getContext().getSession();
        	AdUser adUser=(AdUser)session.get(WebConstant.SESSION.ADUSER);
        	 boolean isLogin=(adUser!=null);
        	 //如果用户已经登陆,正常处理
             if(excludeActionMethods.contains(actionMethod)){
                 result=invocation.invoke();
                 return result;
             //默认需要进行用户登陆校验
             }else{
 	            if(isLogin){
 	                result=invocation.invoke();
 	            //如果用户尚未登陆,跳转到用户登陆
 	            }else{
 	                //对于JSON请求,返回封装好的JSON格式的用户未登陆信息
 	                if(actionMethod.toUpperCase().endsWith("JSON")){
 	                    JsonResult jsonResult=new JsonResult(JsonResult.CODE.NOT_LOGIN,"用户未登陆",null,null);
 	                    String jsonStr=JSONObject.fromObject(jsonResult).toString();
 	                    HttpServletResponse response=ServletActionContext.getResponse();
 	                    response.setContentType("text/html;charset=UTF-8"); 
 	                    response.getWriter().print(jsonStr);
 	                    result = null;
 	                //非JSON请求,直接跳转到未登录页面
 	                }else{
 	                    result="new_publisher_login";
 	                }
 	            }
 	            return result;
 	        }
        }
        //收费模块
        if(actionMethod.startsWith("cn.magme.web.action.charge")){
        	UserDeviceBind userDeviceBind=(UserDeviceBind)ActionContext.getContext().getSession().get(WebConstant.SESSION.USER_DEVICE_BIND);
//        	UserDeviceBind userDeviceBind= new UserDeviceBind();
//        	if (userDeviceBind != null) {//登入了mac信息
//                result=invocation.invoke();
//			} 
//        	return result;
        	//开放，无需登录
            result=invocation.invoke();
        }

        //如果excludeActionMethods包含请求对应的方法,那么不需要进行用户是否登陆的校验
        if(excludeActionMethods.contains(actionMethod)){
            result=invocation.invoke();
        //默认需要进行用户登陆校验
        }else{
            //此处是校验用户登陆的代码,需根据实际情况进行调整
            User user=(User)ActionContext.getContext().getSession().get(WebConstant.SESSION.USER);
            boolean isLogin=(user!=null);
            //如果用户已经登陆,正常处理
            if(isLogin){
                result=invocation.invoke();
            //如果用户尚未登陆,跳转到用户登陆
            }else{
                //对于JSON请求,返回封装好的JSON格式的用户未登陆信息
                if(actionMethod.toUpperCase().endsWith("JSON")){
                    JsonResult jsonResult=new JsonResult(JsonResult.CODE.NOT_LOGIN,"用户未登陆",null,null);
                    String jsonStr=JSONObject.fromObject(jsonResult).toString();
                    HttpServletResponse response=ServletActionContext.getResponse();
                    response.setContentType("text/html;charset=UTF-8"); 
                    response.getWriter().print(jsonStr);
                    result = null;
                //非JSON请求,直接跳转到未登录页面
                }else{
                    result="no_login";
                }
            }
        }
        return result;
    }
    
    public void setExcludeActionMethods(String commaDelim) {
        this.excludeActionMethods = new HashSet<String>();
        String[] split = commaDelim.split(",");
        for (String aSplit : split) {
            String trimmed = aSplit.trim();
            if (trimmed.length() > 0)
                excludeActionMethods.add(trimmed);
        }
    }
}
