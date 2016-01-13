package cn.magme.web.action;

import javax.annotation.Resource;

import cn.magme.common.JsonResult;
import cn.magme.pojo.UserSettings;
import cn.magme.service.UserSettingsService;

@SuppressWarnings("serial")
public class UserSettingsAction extends BaseAction {
	
	@Resource
	private UserSettingsService userSettingsService;
	
	private Long userId;
	
	@Override
	public String execute() throws Exception {
		this.jsonResult = JsonResult.getFailure();
		UserSettings settings = userSettingsService.getByUserId(userId);
		if(settings != null){
			this.jsonResult = JsonResult.getSuccess();
			this.jsonResult.put("settings", settings);
		}
		return JSON;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}
}
