/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.admin;

import java.util.List;

import javax.annotation.Resource;

import cn.magme.pojo.IssueStatCvTmpI;
import cn.magme.pojo.ext.Stat;
import cn.magme.service.FlowStatService;
import cn.magme.service.IssueStatCvTmpService;
import cn.magme.util.ToJson;

/**
 * 统计：杂志流量
 * @author shenhao
 * @date 2011-8-8
 * @version $id$
 */
@SuppressWarnings("serial")
public class FlowStatPubAction extends BaseAction {

	@Resource
	private FlowStatService flowStatService;
	@Resource
	private IssueStatCvTmpService issueStatCvTmpService;
	// 页面信息(查询条件:杂志编号)
	private Stat stat;
	// 杂志编号
	private String pubId;
	
	/**
	 * 杂志流量: 分页查询
	 */
	public void page() {
		page = this.flowStatService.getPageByConditionPub(page,stat);
		String info = ToJson.object2json(page);
		print(info);
	}
	
	/**
	 * 明细报表数据查询
	 */
	public void detail() {
		List<IssueStatCvTmpI> lst = issueStatCvTmpService.getExtStatPub(pubId);
		String result = ToJson.object2json(lst);
		print(result);
	}
	
	/**
	 * 页面信息取得
	 * @return
	 */
	public Stat getStat() {
		return stat;
	}
	
	/**
	 * 页面信息设定
	 * @param stat
	 */
	public void setStat(Stat stat) {
		this.stat = stat;
	}
	
	/**
	 * 杂志编号取得
	 * @return
	 */
	public String getPubId() {
		return pubId;
	}
	
	/**
	 * 杂志编号设定
	 * @param pubId
	 */
	public void setPubId(String pubId) {
		this.pubId = pubId;
	}
}
