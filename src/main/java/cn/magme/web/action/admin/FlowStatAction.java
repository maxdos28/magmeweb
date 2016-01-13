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
 * 统计: 期刊流量
 * @author shenhao
 * @date 2011-7-27
 * @version $id$
 */
@SuppressWarnings("serial")
public class FlowStatAction extends BaseAction {
    
    @Resource
    private FlowStatService flowStatService;
    
    @Resource
    IssueStatCvTmpService issueStatCvTmpService;
    // 页面信息(查询条件:杂志名，期刊号，开始，结束)
    private Stat stat;
    // 期刊编号
    private Long issueId;
    
    /**
     * 期刊流量: 分页查询
     */
    public void page() {
        page = this.flowStatService.getPageByCondition(page,stat);
        String info = ToJson.object2json(page);
        print(info);
    }

    /**
     * 明细图表对应数据查询
     */
    public void detail() {
        List<IssueStatCvTmpI> lst = issueStatCvTmpService.getTmpI(null,null,issueId,1);
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
     * 期刊编号取得
     * @return
     */
    public Long getIssueId() {
        return issueId;
    }

    /**
     * 期刊编号设定
     * @param issueId
     */
    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }
}
