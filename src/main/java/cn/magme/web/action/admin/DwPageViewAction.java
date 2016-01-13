/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.admin;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import cn.magme.pojo.IssueStatCvTmpI;
import cn.magme.pojo.ext.DmIssue;
import cn.magme.service.stat.DwPageViewService;
import cn.magme.util.ExtPageInfo;
import cn.magme.util.ToJson;

/**
 * @author shenhao
 * @date 2012-7-16
 * @version $id$
 */
@SuppressWarnings("serial")
public class DwPageViewAction extends BaseAction {
	
	@Resource
	private DwPageViewService dwPageViewService;

	private String start;
	private String end;
	
	
	/**
	 * 分页查询
	 */
	public void pageView() {
		page = dwPageViewService.getViewPageByCondition(page,start, end);
		String info = ToJson.object2json(page);
		print(info);
	}
	
	public void pageRate() {
		page = dwPageViewService.getRatePageByCondition(page,start, end);
		String info = ToJson.object2json(page);
		print(info);
	}
	
	public void outputExcelView() throws IOException {
		List<IssueStatCvTmpI> list 
			= dwPageViewService.getAdhesionVisits(start, end,null,null);
		StringBuilder sb = new StringBuilder();
		//展示数据的拼装
		for (int i = 0; i < list.size(); i++) {
			IssueStatCvTmpI pro = (IssueStatCvTmpI)list.get(i);
				sb.append("<tr><td>");
				sb.append(pro.getDate()==null?"":pro.getDate()).append("</td><td>");
				sb.append(pro.getCount()==null?"":pro.getCount()).append("</td></tr>");
		}
		
		excel(new String[]{"时间","流量"}, sb.toString(), "黏着访问量");
	}
	
	public void outputExcelRate() throws IOException {
		List<IssueStatCvTmpI> list 
			= dwPageViewService.getAdhesionRate(start, end,null,null);
		StringBuilder sb = new StringBuilder();
		//展示数据的拼装
		for (int i = 0; i < list.size(); i++) {
			IssueStatCvTmpI pro = (IssueStatCvTmpI)list.get(i);
				sb.append("<tr><td>");
				sb.append(pro.getDate()==null?"":pro.getDate()).append("</td><td>");
				sb.append(pro.getCount()==null?"":pro.getCount()).append("</td></tr>");
		}
		
		excel(new String[]{"时间","流量百分比"}, sb.toString(), "黏着度");
	}
	
	public void rate() {
		List<IssueStatCvTmpI> lst = dwPageViewService.getAdhesionRate(start, end,null,null);
		String result = ToJson.object2json(lst);
		print(result);
	}
	
	public void visits() {
		List<IssueStatCvTmpI> lst = dwPageViewService.getAdhesionVisits(start, end,null,null);
		String result = ToJson.object2json(lst);
		print(result);
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}
}
