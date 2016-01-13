package cn.magme.web.action.newPublisher;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;


import cn.magme.pojo.Publisher;
import cn.magme.service.PublisherService;
import cn.magme.util.ExtPageInfo;
import cn.magme.util.ToJson;
import cn.magme.common.JsonResult;




/**
 * 出版商管理
 * @author devin.song
 * @date 2012-04-28
 * @version $id$
 */
@SuppressWarnings("serial")
@Results({@Result(name="config",location="/WEB-INF/pages/newPublisher/managePublisher.ftl")})
public class EditPublisherAction extends PublisherBaseAction {
	private Logger log = Logger.getLogger(this.getClass());
	
	@Resource
	private PublisherService publisherService;
	
	private final Long pageSize = 30L;

	/**
	 * 杂志设置的起始页 、以及单个杂志 的数据初始化
	 * @return
	 */
	public String to(){
		ExtPageInfo page = new ExtPageInfo();
		page.setStart(0);
		page.setLimit(pageSize);
		 page = this.publisherService.getPageByCondition(page, null);
        //计算总页数数 ---begin
        if(pageNo<=0){//重新计算总页数，并进行数据库查询
        	long listCount = page.getTotal();
        	pageNo = (listCount+pageSize-1)/pageSize;
        	currentPage=1;
        }
//	     //   String info = ToJson.object2json(page);
	    publisherList = page.getData();
		return "config";
	}
	
	public String doJson(){
		ExtPageInfo page = new ExtPageInfo();
		page.setLimit(pageSize);
		page.setStart((currentPage-1)*pageSize);
		page = this.publisherService.getPageByCondition(page,publisher);
		this.jsonResult = new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		this.jsonResult.put("publisherList", page.getData());
		long listCount = page.getTotal();
	    pageNo = (listCount+pageSize-1)/pageSize;
		this.jsonResult.put("pageNo", pageNo);
		return JSON;
	}
	
	/**
	 * 保存对象
	 * @return
	 */
	public String doSaveJson(){
		this.jsonResult = new JsonResult();
		if(publisher!=null){
			publisherService.updatePublisherById(publisher);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			publisher = this.publisherService.queryById(publisher.getId());
			this.jsonResult.put("publisher", publisher);
		}else{
			this.jsonResult.setCode(JsonResult.CODE.FAILURE);
			this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		}
		return JSON;
	}
	
	/**
	 * 获取对象
	 * @return
	 */
	public String doGetPojoJson(){
		this.jsonResult = new JsonResult();
		if(publisher!=null){
			publisher = this.publisherService.queryById(publisher.getId());
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			this.jsonResult.put("publisher", publisher);
		}else{
			this.jsonResult.setCode(JsonResult.CODE.FAILURE);
			this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		}
		return JSON;
	}

	private Publisher publisher;
	private List<Publisher> publisherList;
	private long currentPage;
    private long pageNo;

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public List<Publisher> getPublisherList() {
		return publisherList;
	}

	public void setPublisherList(List<Publisher> publisherList) {
		this.publisherList = publisherList;
	}

	public long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
	}

	public long getPageNo() {
		return pageNo;
	}

	public void setPageNo(long pageNo) {
		this.pageNo = pageNo;
	}
	
	
	
}
