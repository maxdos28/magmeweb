/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.admin;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import cn.magme.pojo.ext.DmPublication;
import cn.magme.service.DmPublicationService;
import cn.magme.util.ExtPageInfo;
import cn.magme.util.ToJson;

/**
 * @author shenhao
 * @date 2012-3-27
 * @version $id$
 */
@SuppressWarnings("serial")
public class DmPublicationWeekAction extends BaseAction{

	@Resource
	private DmPublicationService dmPublicationService;
	
	private DmPublication dmPublication;

	/**
	 * 分页查询
	 */
	public void page() {
		page = this.dmPublicationService.getPageByConditionWeek(page, dmPublication,true);
		String info = ToJson.object2json(page);
		print(info);
	}
	
	@SuppressWarnings("unchecked")
	public void outputExcel() throws IOException {
		List<DmPublication> list = 
			this.dmPublicationService.getPageByConditionWeek(new ExtPageInfo(), dmPublication,false).getData();
		StringBuilder sb = new StringBuilder();
		//展示数据的拼装
		for (int i = 0; i < list.size(); i++) {
			DmPublication pro = (DmPublication)list.get(i);
				sb.append("<tr><td>");
				sb.append(pro.getPublicationId()==null?"":pro.getPublicationId()).append("</td><td>");
				sb.append(pro.getPublicationName()==null?"":pro.getPublicationName()).append("</td><td>");
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
				sb.append(dmPublication.getFromDate()).append("</td><td>");
				sb.append(dmPublication.getToDate()).append("</td></tr>");
		}
		
		excel(new String[]{"杂志编号","杂志名称",
				"阅读总pv","网站阅读pv","embed阅读pv","sns阅读pv","手机wap阅读pv","其它pv",
				"阅读总uv","网站阅读uv","embed阅读uv","sns阅读uv","手机wap阅读uv","其它uv",
				"开始时间","结束时间"}, sb.toString(), "杂志阅读周排行");
	}
	
	public DmPublication getDmPublication() {
		return dmPublication;
	}

	public void setDmPublication(DmPublication dmPublication) {
		this.dmPublication = dmPublication;
	}
}
