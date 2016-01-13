/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.publish;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.magme.common.JsonResult;
import cn.magme.pojo.Publisher;
import cn.magme.web.action.BaseAction;
import cn.magme.web.entity.UploadInfo;


/**
 * @author fredy.liu
 * @date 2011-6-10
 * @version $id$
 */
public class IssueUploadInfoAction extends BaseAction {
	    private static final long serialVersionUID = -731099706639586331L;

	    private static final Logger log = Logger.getLogger(IssueUploadInfoAction.class);
	    
	    private static final Double PER_M=1024d*1024d;

	    /**
	     * (显示进度)页面不断使用ajax方法对当前进度进行探测，此方法采用异步交互的方式提供"返回值".
	     * 
	     * @return null
	     */
	    public String execute() {
	    	this.jsonResult=new JsonResult();
	        //HttpServletResponse response = ServletActionContext.getResponse();
	        //从session中取得UploadInfo，以便得到当前上传百分比.
	        try {
				HttpSession session = ServletActionContext.getRequest().getSession();
				Publisher pub=this.getSessionPublisher();
				UploadInfo info = (UploadInfo) session.getAttribute("info"+pub.getId());
				//如此时已经跳转页面，而上传没有真正开始，先返回0%,但不用把new出来的UploadInfo放在session范围内.
				if (info == null) {
				    info = new UploadInfo();
				    session.setAttribute("info"+pub.getId(), info);
				}
				int percent = info.getPercent();

				log.info("percent======" + percent);
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
				this.jsonResult.put("percent", percent);
				this.jsonResult.put("total", info.getTotalLong()/PER_M);
				this.jsonResult.put("uploaded", info.getUploaded()/PER_M);
			} catch (Exception e) {
				log.error("获取进度条出错", e);
				this.jsonResult.setCode(JsonResult.CODE.FAILURE);
				this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
			}

	        
	        return JSON;
	    }

}
