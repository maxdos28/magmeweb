/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.admin;

import javax.annotation.Resource;
import cn.magme.pojo.Issue;
import cn.magme.service.IssueService;
import cn.magme.util.ToJson;
import cn.magme.web.manager.cache.IssueCacheService;

/**
 * 期刊管理
 * @author guozheng
 * @date 2011-5-30
 * @version $id$
 */
@SuppressWarnings("serial")
public class IssueAction extends BaseAction {

	@Resource
	private IssueService issueService;
    @Resource
	private IssueCacheService issueCacheService;
    // 页面信息(查询条件:期刊名称，期刊号)
    private Issue issue;

	/**
	 * 分页查询
	 */
	public void page() {
		page = this.issueService.searchByCondition(page, issue);
		String info = ToJson.object2json(page);
		print(info);
	}
	
	/**
	 * 添加或更新
	 */
	public void commit() {
		Object[] arr = super.toJavaArr(info, Issue.class);
		Issue[] issues = castToIssue(arr);
		this.issueService.commit(issues);
	}

	/**
	 * 更新内存
	 */
    public void upmemory() {
        issueCacheService.init();
        print("{success:true}");
    }
	
	/**
	 * 数组类型转换
	 * @param arr
	 * @return
	 */
	private Issue[] castToIssue(Object[] arr) {
		Issue[] infos = new Issue[arr.length];
		for (int i = 0; i < infos.length; i++) {
			infos[i] = (Issue) arr[i];
		}
		return infos;
	}
	
	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}
}
