package cn.magme.web.action.sms;

import java.util.List;

import javax.annotation.Resource;

import cn.magme.pojo.sms.SmsProject;
import cn.magme.service.sms.SmsProjectService;
import cn.magme.web.action.BaseAction;

public class SmsStatBaseAction extends BaseAction {
	
	@Resource
	private SmsProjectService smsProjectService;
	
	
	protected void getProjectIdAndList(){
		proList=smsProjectService.queryByUserId(this.getSessionSmsUser().getId());
		if(smsProjectId==null && proList!=null && proList.size()>0){
			smsProjectId=proList.get(0).getId();
		}
	}
	
	protected List<SmsProject> proList;
	
	protected Long smsProjectId;
	
	public Long getSmsProjectId() {
		return smsProjectId;
	}

	public void setSmsProjectId(Long smsProjectId) {
		this.smsProjectId = smsProjectId;
	}

}
