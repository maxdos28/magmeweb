/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.admin;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import cn.magme.pojo.ext.DmIssue;
import cn.magme.pojo.ext.DmPublication;
import cn.magme.service.DmIssueService;
import cn.magme.util.ExtPageInfo;
import cn.magme.util.ToJson;

/**
 * @author shenhao
 * @date 2012-3-2
 * @version $id$
 */
@SuppressWarnings("serial")
public class DmIssueAction extends BaseAction{

	@Resource
	private DmIssueService dmIssueService;
	
	private DmIssue dmIssue;
	
	/**
	 * 分页查询
	 */
	public void page() {
		page = this.dmIssueService.getPageByCondition(page, dmIssue, true);
		String info = ToJson.object2json(page);
		print(info);
	}
	
	@SuppressWarnings("unchecked")
	public void outputExcel() throws IOException {
		List<DmIssue> list = 
			this.dmIssueService.getPageByCondition(new ExtPageInfo(), dmIssue, false).getData();
		StringBuilder sb = new StringBuilder();
		//展示数据的拼装
		for (int i = 0; i < list.size(); i++) {
			DmIssue pro = (DmIssue)list.get(i);
				sb.append("<tr><td>");
				sb.append(pro.getPublicationId()==null?"":pro.getPublicationId()).append("</td><td>");
				sb.append(pro.getPublicationName()==null?"":pro.getPublicationName()).append("</td><td>");
				sb.append(pro.getIssueId()==null?"":pro.getIssueId()).append("</td><td>");
				sb.append(pro.getIssueNumber()==null?"":pro.getIssueNumber()).append("</td><td>");
				sb.append(pro.getTotalPv()).append("</td><td>");
				sb.append(pro.getWebPv()).append("</td><td>");
				sb.append(pro.getEmbedPv()).append("</td><td>");
				sb.append(pro.getSnsPv()).append("</td><td>");
				sb.append(pro.getWapPv()).append("</td><td>");
				sb.append(pro.getOtherPv()).append("</td><td>");
				
				sb.append(pro.getTotalUv()).append("</td><td>");
				sb.append(pro.getWebUv()).append("</td><td>");
				sb.append(pro.getEmbedUv()).append("</td><td>");
				sb.append(pro.getSnsUv()).append("</td><td>");
				sb.append(pro.getWapUv()).append("</td><td>");
				sb.append(pro.getOtherUv()).append("</td><td>");
				sb.append(pro.getDataDate()).append("</td></tr>");
		}
		
		excel(new String[]{"杂志编号","杂志名称","期刊编号","期刊名称",
				"阅读总pv","网站阅读pv","embed阅读pv","sns阅读pv","手机wap阅读pv","其它pv",
				"阅读总uv","网站阅读uv","embed阅读uv","sns阅读uv","手机wap阅读uv","其它uv",
				"时间"}, sb.toString(), "期刊阅读报表");
	}
	
	public DmIssue getDmIssue() {
		return dmIssue;
	}

	public void setDmIssue(DmIssue dmIssue) {
		this.dmIssue = dmIssue;
	}
}
