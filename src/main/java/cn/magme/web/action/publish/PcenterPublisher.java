/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.publish;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.SystemProp;
import cn.magme.pojo.Category;
import cn.magme.pojo.Issue;
import cn.magme.pojo.Publication;
import cn.magme.pojo.Publisher;
import cn.magme.service.CategoryService;
import cn.magme.service.IssueService;
import cn.magme.service.PublicationService;
import cn.magme.web.action.BaseAction;

/**
 * @author fredy.liu
 * @date 2011-6-1
 * @version $id$
 */
@Results({@Result(name="success",location="/WEB-INF/pages/publishadmin/manageMgzHome.ftl"),
	@Result(name="magListSuccess",location="/WEB-INF/pages/publishadmin/manageMgzList.ftl"),
	@Result(name="index_success",location="/WEB-INF/pages/ad/adindex.ftl"),
	@Result(name="product_success",location="/WEB-INF/pages/ad/productService.ftl")})
public class PcenterPublisher extends BaseAction{//manageMgzList.ftl

	/**
	 * 
	 */
	private static final long serialVersionUID = -9116996339209417241L;
	
	private static final Logger log=Logger.getLogger(PcenterPublisher.class);
	
	
	@Resource
	private PublicationService publicationService;
	
	@Resource
	private IssueService issueService;
	
	@Resource
	private SystemProp systemProp;
	@Resource
	private CategoryService categoryService;
	
	
	public String execute(){
		this.menuId="publish";
		this.magList();
		return "success";
	}
	
	public String index(){
		this.menuId="index";
		return "index_success";
	}
	
	public String product(){
		this.menuId="product";
		return "product_success";
	}
	
	public String magList(){
		this.menuId="publish";
		try {
			Publisher publisher=this.getSessionPublisher();
			categoryList=categoryService.queryAllChildCategories();
			publicationList=this.publicationService.queryNormalByPublisherId(publisher.getId());
			if(publicationList!=null && publicationList.size()>0){
				//统计杂志的期刊总数
				for(Publication pub:publicationList){
					pub.setTotalIssues(issueService.queryTotalIssuesByPublicaionId(pub.getId()));
				}
				//首页中的所有期刊
				if(publicationId==null || publicationId==0){
					publicationId=publicationList.get(0).getId();
				}
				//if(publicationId!=null && publicationId!=0){
				//选中了杂志
				issueList=issueService.queryAllIssuesByPublisherIdAndPublisherId(publicationId, publisher.getId());
				Publication pub=this.publicationService.queryById(publicationId);
				if(pub!=null){
					publicationName=pub.getName();
					needCompress=pub.getNeedCompress();
				}
			}
			
			/*}else{
				//没有选中杂志
				issueList=new ArrayList<Issue>();
				//issueList=issueService.queryAllIssuesByPublisherId(publisher.getId());
				List<Publication> publicationList=publicationService.queryNormalByPublisherId(publisher.getId());
				if(publicationList!=null && publicationList.size()>0){
					for(Publication pub:publicationList){
						if(pub!=null){
							Issue issue=issueService.queryAdminLastestIssueByPubId(pub.getId());
							if(issue!=null){
								issueList.add(issue);
							}
							
						}
					}
				}
				//没有选中杂志，
			}*/
		} catch (Exception e) {
			log.error("", e);
		}

		return "magListSuccess";
	}
	
	

	private List<Publication> publicationList;
	
	private List<Issue> issueList;
	
	private List<Category> categoryList;
	
	private Long publicationId;
	
	private String publicationName;
	
	private String menuId="index";
	
	private Integer needCompress;
	
	
	

	public Integer getNeedCompress() {
		return needCompress;
	}

	public void setNeedCompress(Integer needCompress) {
		this.needCompress = needCompress;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getPublicationName() {
		return publicationName;
	}

	public void setPublicationName(String publicationName) {
		this.publicationName = publicationName;
	}

	public Long getPublicationId() {
		return publicationId;
	}

	public void setPublicationId(Long publicationId) {
		this.publicationId = publicationId;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public SystemProp getSystemProp() {
		return systemProp;
	}

	public void setSystemProp(SystemProp systemProp) {
		this.systemProp = systemProp;
	}

	public List<Issue> getIssueList() {
		return issueList;
	}

	public void setIssueList(List<Issue> issueList) {
		this.issueList = issueList;
	}

	public List<Publication> getPublicationList() {
		return publicationList;
	}

	public void setPublicationList(List<Publication> publicationList) {
		this.publicationList = publicationList;
	}
	
	
}
