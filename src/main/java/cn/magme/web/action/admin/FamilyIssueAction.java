/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.admin;

import java.util.List;

import javax.annotation.Resource;
import cn.magme.pojo.FamilyIssue;
import cn.magme.pojo.Issue;
import cn.magme.pojo.Publication;
import cn.magme.pojo.ext.CategoryIdRenderBean;
import cn.magme.service.FamilyCategoryService;
import cn.magme.service.FamilyIssueService;
import cn.magme.service.IssueService;
import cn.magme.service.PublicationService;
import cn.magme.util.ToJson;
import cn.magme.web.manager.cache.FamilyIssueCacheService;

/**
 * 看米管理：familyIssue
 * @author guozheng
 * @date 2011-6-1
 * @version $id$
 */
@SuppressWarnings("serial")
public class FamilyIssueAction extends BaseAction {
	@Resource
	FamilyIssueService familyIssueService;
	
	@Resource
	PublicationService publicationService;
	
	@Resource
	IssueService issueService;

    @Resource
    FamilyCategoryService familyCategoryService;

    @Resource
    private FamilyIssueCacheService familyIssueCacheService;
    // 页面信息
	private FamilyIssue familyIssue;
	// 杂志编号
	private Long publicationId;
	
	/**
	 * 杂志期刊分类: 分页查询
	 */
	public void page() {
		page = this.familyIssueService.getPageByCondition(page, familyIssue);
		String info = ToJson.object2json(page);
		print(info);
	}
	
	/**
	 * 杂志期刊分类: 选中数据删除
	 */
	public void delete() {
		String[] strArr = ids.split(",");
		Long[] arr = super.strArrToLongArr(strArr);
		this.familyIssueService.delete(arr);
	}
	/**
	 * 杂志期刊分类: 添加或选中数据更新
	 */
	public void commit() {
		Object[] arr = super.toJavaArr(info, FamilyIssue.class);
		FamilyIssue[] infos = castToFamilyIssue(arr);
		this.familyIssueService.commit(infos);
	}

	/**
	 * 杂志下拉框: 获取对应全部信息
	 */
	public void publication() {
		List<Publication> list = this.publicationService.getAll();
		String info = ToJson.list2json(list);
		print(info);
	}

	/**
	 * 期刊下拉框: 根据被选中杂志获取对应内容
	 */
	public void issue() {
		List<Issue> list = this.issueService.getByPublicationId(publicationId);
		String info = ToJson.list2json(list);
		print(info);
	}

	/**
	 * 数据类型转换 Object[] => FamilyIssue[]
	 * @param arr
	 * @return
	 */
	private FamilyIssue[] castToFamilyIssue(Object[] arr) {
		FamilyIssue[] infos = new FamilyIssue[arr.length];
		for (int i = 0; i < infos.length; i++) {
			infos[i] = (FamilyIssue) arr[i];
		}
		return infos;
	}

    /**
     * 获取familyCategoryIds list
     */
    public void getByFamilyCategoryIds(){
        List<CategoryIdRenderBean> ret = this.familyCategoryService.getAllChildIds();
        String info = ToJson.list2json(ret);
        print(info);
    }
	
    /**
     * 杂志期刊分类: 更新内存
     */
    public void upMemory() {
        familyIssueCacheService.init();
        print("{success:true}");
    }
    
    /**
     * 页面信息取得
     * @return
     */
	public FamilyIssue getFamilyIssue() {
		return familyIssue;
	}

	/**
	 * 页面信息设定
	 * @param familyIssue
	 */
	public void setFamilyIssue(FamilyIssue familyIssue) {
		this.familyIssue = familyIssue;
	}

	/**
	 * 杂志编号取得
	 * @return
	 */
	public Long getPublicationId() {
		return publicationId;
	}

	/**
	 * 杂志编号设定
	 * @param publicationId
	 */
	public void setPublicationId(Long publicationId) {
		this.publicationId = publicationId;
	}
}
