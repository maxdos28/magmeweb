package cn.magme.web.action.newPublisher;

import cn.magme.web.action.BaseAction;

@SuppressWarnings("serial")
public abstract class PublisherBaseAction extends BaseAction {
	private String tabId;

	public String getTabId() {
		return tabId;
	}

	public void setTabId(String tabId) {
		this.tabId = tabId;
	}
	
	
}
