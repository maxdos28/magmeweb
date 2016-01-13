package cn.magme.web.action.newPublisher;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;


import cn.magme.common.JsonResult;
import cn.magme.pojo.Publication;
import cn.magme.pojo.Publisher;
import cn.magme.pojo.charge.PublicationProduct;
import cn.magme.service.PublicationService;
import cn.magme.service.PublisherService;
import cn.magme.service.charge.PublicationProductService;
import cn.magme.util.ExtPageInfo;
import cn.magme.util.ToJson;




/**
 * 编辑=杂志管理
 * @author devin.song
 * @date 2012-05-02
 * @version $id$
 */
@SuppressWarnings("serial")
@Results({@Result(name="config",location="/WEB-INF/pages/newPublisher/magmeMagazine.ftl")})
public class EditMagazineAction extends PublisherBaseAction {
	private Logger log = Logger.getLogger(this.getClass());
	
	private final Long pageSize = 30L;
	@Resource
	private PublicationService publicationService;
	
	@Resource
	private PublicationProductService publicationProductService;
	

	/**
	 * 杂志设置的起始页 、以及单个杂志 的数据初始化
	 * @return
	 */
	public String to(){
		ExtPageInfo page = new ExtPageInfo();
		page.setStart(0);
		page.setLimit(pageSize);
		 page = this.publicationService.getPageByCondition(page, null);
		 //计算总页数数 ---begin
	        if(pageNo<=0){//重新计算总页数，并进行数据库查询
	        	long listCount = page.getTotal();
	        	pageNo = (listCount+pageSize-1)/pageSize;
	        	currentPage=1;
	        }
		 publicationList = page.getData();
		 checkProduct();
		return "config";
	}
	
	/**
	 * 分页
	 * @return
	 */
	public String doJson(){
		ExtPageInfo page = new ExtPageInfo();
		page.setStart((currentPage-1)*pageSize);
		page.setLimit(pageSize);
		page = this.publicationService.getPageByCondition(page, publication);
		publicationList = page.getData();
		checkProduct();
		this.jsonResult = new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		this.jsonResult.put("publicationList", publicationList);
		long listCount = page.getTotal();
	    pageNo = (listCount+pageSize-1)/pageSize;
		this.jsonResult.put("pageNo", pageNo);
		return JSON;
	}
	
	public String doRecommendJson(){
		this.jsonResult = new JsonResult();
		if(publication!=null){
			int temp = publication.getRecommend();
			publication = this.publicationService.queryById(publication.getId());
			publication.setRecommend(temp);
			this.publicationService.updateByIdAndPublisherId(publication);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}else{
			this.jsonResult.setCode(JsonResult.CODE.FAILURE);
			this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		}
		return JSON;
	}
	
	public String doSaveJson(){
		this.jsonResult = new JsonResult();
		if(publication!=null){
			this.publicationService.updateByIdAndPublisherId(publication);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}else{
			this.jsonResult.setCode(JsonResult.CODE.FAILURE);
			this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		}
		return JSON;
	}
	public String doGetPojoJson(){
		this.jsonResult = new JsonResult();
		if(publication!=null){
			publication = this.publicationService.queryById(publication.getId());
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			this.jsonResult.put("publication", publication);
		}else{
			this.jsonResult.setCode(JsonResult.CODE.FAILURE);
			this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		}
		return JSON;
	}
	
	/**
	 * 保存杂志对应的价格
	 * @return
	 */
	public String doPublicationProduct(){
		this.jsonResult = new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		if(publicationId!=null){
			publication = this.publicationService.queryById(publicationId);
			if(publication!=null&&publication.getIsFree()==0){//收费
				insertProduct(publicationId);
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			}
		}
		return JSON;
	}
	
	/**
	 * 根据杂志id查看对应的价格
	 * @return
	 */
	public String queryPublicationProduct(){
		this.jsonResult = new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		if(publicationId!=null){
			List<PublicationProduct> listProduct = publicationProductService.getByPublicationId(publicationId);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			this.jsonResult.put("listProduct", listProduct);
		}
		return JSON;
	}

	protected void insertProduct(Long pubId) {
		if(price1!=null&&price1>0){
			PublicationProduct publicationProduct = composeProduct(pubId,price1,1);
			if(publicationProduct!=null){
				publicationProductService.insert(publicationProduct);
			}
		}
		
		if(price3!=null&&price3>0){
			PublicationProduct publicationProduct = composeProduct(pubId,price3,3);
			if(publicationProduct!=null){
				publicationProductService.insert(publicationProduct);
			}
		}
		
		if(price6!=null&&price6>0){
			PublicationProduct publicationProduct = composeProduct(pubId,price6,6);
			if(publicationProduct!=null){
				publicationProductService.insert(publicationProduct);
			}
		}
		if(price12!=null&&price12>0){
			PublicationProduct publicationProduct = composeProduct(pubId,price12,12);
			if(publicationProduct!=null){
				publicationProductService.insert(publicationProduct);
			}
		}
		if(price18!=null&&price18>0){
			PublicationProduct publicationProduct = composeProduct(pubId,price18,18);
			if(publicationProduct!=null){
				publicationProductService.insert(publicationProduct);
			}
		}
		if(price24!=null&&price24>0){
			PublicationProduct publicationProduct = composeProduct(pubId,price24,24);
			if(publicationProduct!=null){
				publicationProductService.insert(publicationProduct);
			}
		}
				
	}
	
	protected PublicationProduct composeProduct(Long pubId,Integer price,Integer num) {
		PublicationProduct publicationProduct = new PublicationProduct();
		publicationProduct.setNumber(num);//个数
		publicationProduct.setAmount(price);//总价
		publicationProduct.setPrice(price/num);//单价
		if(num!=null&&num==1){
			publicationProduct.setName("单期");////产品名称
		}else{
			publicationProduct.setName(num+"期");////产品名称
		}
		publicationProduct.setPublicationId(pubId);//杂志id
		publicationProduct.setStatus(1);//可以使用
		return publicationProduct;
	}
	
	/**
	 * 根据产品id查找是否已经定过价 临时改变publication表里的totalIssues的本身含义
	 * @return
	 */
	protected void checkProduct() {
		if(publicationList!=null&&publicationList.size()>0){
			for (Publication publication : publicationList) {
				Long pubId = publication.getId();
				Integer pubCount = publicationProductService.getByCountPublicationId(pubId);
				if(pubCount!=null && pubCount>0){
					publication.setTotalIssues(1);//1标示已经定过价不允许再次定价
				}else{
					publication.setTotalIssues(0);//0标示没有定过价,允许定价
				}
			}
		}
		
	}
	
	private Publication publication;
	private List<Publication> publicationList;
	private long currentPage;
    private long pageNo;
    
    private Integer price1;
    private Integer price3;
    private Integer price6;
    private Integer price12;
    private Integer price18;
    private Integer price24;
    private Long publicationId;
    

	public Publication getPublication() {
		return publication;
	}

	public void setPublication(Publication publication) {
		this.publication = publication;
	}

	public List<Publication> getPublicationList() {
		return publicationList;
	}

	public void setPublicationList(List<Publication> publicationList) {
		this.publicationList = publicationList;
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

	public Integer getPrice1() {
		return price1;
	}

	public void setPrice1(Integer price1) {
		this.price1 = price1;
	}

	public Integer getPrice3() {
		return price3;
	}

	public void setPrice3(Integer price3) {
		this.price3 = price3;
	}

	public Integer getPrice6() {
		return price6;
	}

	public void setPrice6(Integer price6) {
		this.price6 = price6;
	}

	public Integer getPrice12() {
		return price12;
	}

	public void setPrice12(Integer price12) {
		this.price12 = price12;
	}

	public Integer getPrice18() {
		return price18;
	}

	public void setPrice18(Integer price18) {
		this.price18 = price18;
	}

	public Integer getPrice24() {
		return price24;
	}

	public void setPrice24(Integer price24) {
		this.price24 = price24;
	}

	public Long getPublicationId() {
		return publicationId;
	}

	public void setPublicationId(Long publicationId) {
		this.publicationId = publicationId;
	}
    
}
