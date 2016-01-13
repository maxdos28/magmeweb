/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.admin;

import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import cn.magme.pojo.stat.StatDmPagenator;
import cn.magme.result.stat.DmEventResult;
import cn.magme.result.stat.DmEventWithPublicationResult;
import cn.magme.service.stat.DmEventService;
import cn.magme.util.ToJson;

/**
 * @author shenhao
 * @date 2012-4-6
 * @version $id$
 */
@SuppressWarnings("serial")
public class DmEventAction extends BaseAction{

	@Resource
	private DmEventService dmEventService;

	private StatDmPagenator statDmPagenator;
	
	/**
	 * 分页查询
	 */
	public void page() {
		statDmPagenator.setPagesize(null);
		statDmPagenator.setStartRow(null);
		List<DmEventWithPublicationResult> totalLst = this.dmEventService.queryByPagenatorAndCatId(statDmPagenator, null);
		
		statDmPagenator.setStartRow(Integer.parseInt(String.valueOf(page.getStart())));
		statDmPagenator.setPagesize(Integer.parseInt(String.valueOf(page.getLimit())));
		List<DmEventWithPublicationResult> lst = this.dmEventService.queryByPagenatorAndCatId(statDmPagenator, null);
		page.setTotal(totalLst.size());
		page.setData(lst);
		//page = this.dmEventService.queryByPagenator(pagenator);
		String info = ToJson.object2json(page);
		print(info);
	}
	
	public void outputExcel() throws IOException {
	    statDmPagenator.setStartRow(null);
	    statDmPagenator.setPagesize(null);
	    List<DmEventWithPublicationResult> list = this.dmEventService.queryByPagenatorAndCatId(statDmPagenator,null);
		StringBuilder sb = new StringBuilder();
		//展示数据的拼装
		for (int i = 0; i < list.size(); i++) {
		    DmEventWithPublicationResult pro = (DmEventWithPublicationResult)list.get(i);
				sb.append("<tr><td>");
                sb.append(pro.getEventId()==null?"":pro.getEventId()).append("</td><td>");
				sb.append(pro.getEventTitle()==null?"":pro.getEventTitle()).append("</td><td>");
				sb.append(pro.getUv()==null?"":pro.getUv()).append("</td><td>");
				sb.append(pro.getPublicationName()==null?"":pro.getPublicationName()).append("</td></tr>");
		}
		
		excel(new String[]{"事件编号","事件标题","点击次数","出自杂志"}, sb.toString(), "事件排行报表");
	}
	
	public StatDmPagenator getStatDmPagenator() {
		return statDmPagenator;
	}

	public void setStatDmPagenator(StatDmPagenator statDmPagenator) {
		this.statDmPagenator = statDmPagenator;
	}
}
