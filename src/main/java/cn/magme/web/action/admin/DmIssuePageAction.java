/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.admin;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import cn.magme.pojo.ext.DmIssue;
import cn.magme.pojo.ext.DmIssuePage;
import cn.magme.service.DmIssuePageService;
import cn.magme.util.ExtPageInfo;
import cn.magme.util.ToJson;

/**
 * @author shenhao
 * @date 2012-3-2
 * @version $id$
 */
public class DmIssuePageAction extends BaseAction{

	@Resource
	private DmIssuePageService dmIssuePageService;
	
	private DmIssuePage dmIssuePage;
	
	public void page() {
		page = this.dmIssuePageService.getPageByCondition(page, dmIssuePage,true);
		String info = ToJson.object2json(page);
		print(info);
	}
	
	@SuppressWarnings("unchecked")
	public void outputExcel() throws IOException {
		List<DmIssuePage> list = 
			this.dmIssuePageService.getPageByCondition(new ExtPageInfo(), dmIssuePage, false).getData();
		StringBuilder sb = new StringBuilder();
		//展示数据的拼装
		for (int i = 0; i < list.size(); i++) {
			DmIssuePage pro = (DmIssuePage)list.get(i);
				sb.append("<tr><td>");
				sb.append(pro.getPublicationId()==null?"":pro.getPublicationId()).append("</td><td>");
				sb.append(pro.getPublicationName()==null?"":pro.getPublicationName()).append("</td><td>");
				sb.append(pro.getIssueId()==null?"":pro.getIssueId()).append("</td><td>");
				sb.append(pro.getIssueNumber()==null?"":pro.getIssueNumber()).append("</td><td>");
				sb.append(pro.getPageNo()).append("</td><td>");
				sb.append(pro.getRetention()).append("</td><td>");
				sb.append(pro.getDataDate()).append("</td></tr>");
		}
		
		excel(new String[]{"杂志编号","杂志名称","期刊编号","期刊名称",
				"页码","停留时间(秒)","时间"}, sb.toString(), "期刊阅读报表");
	}
	
	public DmIssuePage getDmIssuePage() {
		return dmIssuePage;
	}

	public void setDmIssuePage(DmIssuePage dmIssuePage) {
		this.dmIssuePage = dmIssuePage;
	}
}
