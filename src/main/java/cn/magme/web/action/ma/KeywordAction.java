package cn.magme.web.action.ma;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.common.Page;
import cn.magme.pojo.ma.MaAdKeyword;
import cn.magme.service.ma.MaKeywordService;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

/**
 * 广告标签管理
 * @author jasper
 * @date 2014.3.13
 *
 */
@Results({@Result(name="success",location="/WEB-INF/pages/ma/keyword.ftl")})
public class KeywordAction extends BaseAction {
	
	@Resource
	private MaKeywordService maKeywordService;
	private Integer currentPage;
	private String keyword;
	private Long id;
	
	public String execute()
	{
		return SUCCESS;
	}
	
	/**
	 * 查询标签
	 * @return
	 */
	public String searchKeyword()
	{
		this.jsonResult = JsonResult.getFailure();
		if (currentPage == null || currentPage <= 0)
			currentPage = 1;
		Page p = maKeywordService.searchMaAdKeyword(keyword, currentPage);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		List<MaAdKeyword> rl = p.getResults();
		this.jsonResult.put("commondatas", rl);
		this.jsonResult.put("pageNo", p.getTotalPage());
		return JSON;
	}
	
	/**
	 * 得到所有标签
	 * @return
	 */
	public String getKeywordList()
	{
		return JSON;
	}

	/**
	 * 增加标签
	 * @return
	 */
	public String addKeyword()
	{
		this.jsonResult = JsonResult.getFailure();
		if(StringUtil.isBlank(keyword))
		{
			this.jsonResult.setMessage("请填写标签");
			return JSON;
		}
		MaAdKeyword mk = new MaAdKeyword();
		mk.setKeyword(keyword);
		int r = this.maKeywordService.saveMaAdKeyword(mk);
		if(r<=0)
		{
			this.jsonResult.setMessage("操作失败");
			return JSON;
		}
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}

	/**
	 * 删除标签
	 * @return
	 */
	public String deleteKeyword()
	{
		this.jsonResult = JsonResult.getFailure();
		if(id==null||id<0)
		{
			this.jsonResult.setMessage("id为空");
			return JSON;
		}
		int r = this.maKeywordService.deleteMaAdKeyword(id);
		if(r<=0)
		{
			this.jsonResult.setMessage("操作失败");
			return JSON;
		}
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
