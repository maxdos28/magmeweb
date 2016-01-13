package cn.magme.web.action.sns;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.result.sns.CreativeListResult;
import cn.magme.result.sns.UserInfoResult;
import cn.magme.service.sns.SnsUserIndexService;
import cn.magme.web.action.BaseAction;

@Results({
	@Result(name="rss",location="/WEB-INF/pages/sns/sns_rss.ftl")
})
@SuppressWarnings({ "rawtypes", "unchecked" })
public class RssAction extends BaseAction{
	private static final long serialVersionUID = -5208350463788025800L;
	private static final Logger log=Logger.getLogger(RssAction.class);
	
	@Resource
	private SnsUserIndexService snsUserIndexService;
	
	@Override
	public String execute() throws Exception {
		if((u==null || "".equals(u)))
			return "index";
		Long uid=0l;
		
		if(u!=null && !"".equals(u)){
			try{
				uid=Long.valueOf(u);
			}catch (Exception e) {
				log.error(e);
				return "rss";
			}
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(uid>0){
			Map m=new HashMap();
			m.put("id", uid);
			m.put("u",0);
			userInfo= snsUserIndexService.getUserInfo(m);
			if(userInfo!=null){
				if(pageTimeLock==null || pageTimeLock.equals("")){
					 java.util.Date now = new Date();
					 pageTimeLock=df.format(now);
				 }
				Map condition = new HashMap();
				condition.put("timeLock", pageTimeLock);
				condition.put("userid", userInfo.getId());
				creativeList=snsUserIndexService.getRssCreativeList(condition);
				return "rss";
			}
		}
		return "rss";
	}
	
	private String u;
	private List<CreativeListResult> creativeList;
	private UserInfoResult userInfo;
	private String pageTimeLock;
	
	public String getPageTimeLock() {
		return pageTimeLock;
	}

	public void setPageTimeLock(String pageTimeLock) {
		this.pageTimeLock = pageTimeLock;
	}


	public UserInfoResult getUserInfo() {
		return userInfo;
	}

	public void setU(String u) {
		this.u = u;
	}

	public List<CreativeListResult> getCreativeList() {
		return creativeList;
	}
}
