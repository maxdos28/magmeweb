/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.wap;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.constants.PojoConstant;
import cn.magme.pojo.Category;
import cn.magme.pojo.Issue;
import cn.magme.service.CategoryService;
import cn.magme.web.action.BaseAction;
import cn.magme.web.manager.cache.IssueCacheService;

/**
 * 处理WAP页面相关的请求
 * @author jacky_zhou
 * @date 2011-8-30
 * @version $id$
 */
@SuppressWarnings("serial")
@Results({@Result(name="index_success",location="/WEB-INF/pages/wap/index.ftl"),
          @Result(name="read_success",location="/WEB-INF/pages/wap/read.ftl")})
public class WapAction extends BaseAction {

    @Resource
    private CategoryService categoryService;
    
    @Resource
    private IssueCacheService issueCacheService;
    
    @Resource
    private IssueCacheService issueService;
    
    /**
     * WAP的首页,同时也是杂志分类页
     * @return
     */
    public String index(){
        this.categoryList=new ArrayList<Category>();
        Category defaultCategory=new Category();
        defaultCategory.setId(null);
        defaultCategory.setName("推荐");
        this.categoryList.add(defaultCategory);
        this.categoryList.addAll(categoryService.queryAllChildCategories());
        
        if(this.begin==null){
            this.begin=0;
        }
        if(this.size==null){
            this.size=16;
        }
        if(this.categoryId==null){
            this.issueList=this.issueCacheService.queryRecommendPubsByLimits(this.size);
        }else{
            this.issueList=this.issueCacheService.queryLastestIssueByCategoryId(categoryId,this.begin,this.size);
        }
        return "index_success";
    }
    
    /**
     * 阅读期刊
     * @return
     */
    public String read(){
        this.issue=issueService.queryById(this.issueId);
        if(this.issue!=null&&this.issue.getStatus().equals(PojoConstant.ISSUE.STATUS.ON_SALE)){
            this.issue=null;
        }
        return "read_success";
    }
    
    //所有分类列表
    private List<Category> categoryList;
    
    //所有分类列表
    private List<Issue> issueList;
    
    //搜索关键字,暂不支持
    //private String keyWord;
    
    //杂志分类ID
    private Long categoryId;
    
    private Long issueId;
    
    private Integer pageNo;
    
    private Issue issue;
    
    private Integer begin;
    
    private Integer size;

    public List<Issue> getIssueList() {
        return issueList;
    }

    public void setIssueList(List<Issue> issueList) {
        this.issueList = issueList;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }
}
