package cn.magme.web.action.charge;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import cn.magme.common.JsonResult;
import cn.magme.constants.PojoConstant.ISSUE;
import cn.magme.constants.PojoConstant.ISSUE.PERMISSION;
import cn.magme.constants.PojoConstant.SALE;
import cn.magme.pojo.Issue;
import cn.magme.pojo.Publication;
import cn.magme.pojo.charge.PublicationProduct;
import cn.magme.pojo.charge.Sale;
import cn.magme.pojo.charge.SaleContent;
import cn.magme.pojo.charge.UserDeviceBind;
import cn.magme.service.IssueService;
import cn.magme.service.PublicationService;
import cn.magme.service.charge.PublicationProductService;
import cn.magme.service.charge.SaleContentService;
import cn.magme.service.charge.SaleService;
import cn.magme.web.action.BaseAction;

@SuppressWarnings("serial")
public class ChargeUtilAction extends BaseAction {

	@Resource
	private IssueService issueService;
	@Resource
	private SaleContentService saleContentService;
	@Resource
	private SaleService saleService;
	@Resource
	private PublicationProductService publicationProductService;
	@Resource
	private PublicationService publicationService;

	private Integer begin;
	private Integer size;
	private Long superId;
	private Long issueCode;
	private Long publicationId;
	private Long productId;
	private Long sortId;
	private String platformuuid;
	private String queryString;
	private String uuid;
    private String mac;//移动设备mac地址或机器码
    private String os;//移动设备操作系统
    private BigDecimal totalAmt;//订单总金额
    private BigDecimal price;//期刊单价
    private String receiptKey;//APPLE返回密钥

	/**
	 * 检查是否有下载阅读权限
	 * 参数列表：<br/>
	 * issueCode 期刊唯一编号<br/>
	 * @return
	 */
	public String checkPermission() {
		this.jsonResult = JsonResult.getFailure();
		//检查订阅收费冲突(因为只能订阅往期的单本或预订未来期刊，故只需检查当前订阅的这一本是否已购买)
		UserDeviceBind bind = getSessionUserDeviceBind();
		if(bind == null){//未登记设备信息
			if(mac==null)
			{
				this.jsonResult.setMessage("未登记设备信息");
				return JSON;
			}
			else
			{
				bind = new UserDeviceBind(null,os,mac);
			}
		}
		Issue issue = issueService.queryByIssueCode(issueCode);
		if(issue == null){
			this.jsonResult.setMessage("期刊不存在");
			return JSON;
		} else if(issue.getIsFree() == 1){//免费期刊
			this.generateJsonResult(JsonResult.CODE.SUCCESS, "本期免费");
			return JSON;
		}
//		Long userId = userDeviceBind.getUserId();
		String mac = bind.getMac();
		String os = bind.getOs();
		List<SaleContent> list = saleContentService.getByCondition(null, mac, os, null, issueCode);
		if(null != list && !list.isEmpty()){
			this.generateJsonResult(JsonResult.CODE.SUCCESS, "已购买");
			return JSON;
		}
		this.generateJsonResult(JsonResult.CODE.CHARGE, "未购买");
		return JSON;
	}
	/**
	 * 检查是否可以订阅
	 * issueCode 期刊唯一编号<br/>
	 * publicationId 杂志id<br/>
	 * productId 产品（套餐类型）id<br/>
	 * totalAmt 订单金额，如果不传则自动计算
	 * price 价格，如果不传则取product中的价格
	 * os 系统类型，IOS/ANDROID
	 * mac 设备号
	 * @return
	 */
	public String checkSubscribe(){
		this.jsonResult = JsonResult.getFailure();
		//以后可能取消设备绑定，此处需要判断是否可以得到用户绑定信息
		//如果session中没有UserDeviceBind信息，则必须有mac
		UserDeviceBind bind = getSessionUserDeviceBind();
		if(bind == null){//未登记设备信息
			if(mac==null)
			{
				this.jsonResult.setMessage("未登记设备信息");
				return JSON;
			}
			else
			{
				bind = new UserDeviceBind(null,os,mac);
			}
		}
		//productId可以不传，通过issueCode来查找product，这样前台就可以不调用得到product的接口
		PublicationProduct product = null;
		if(productId==null||productId<=0)
		{
			product = publicationProductService.getByIssueCode(publicationId, issueCode);
		}
		else
		{
			product = publicationProductService.getById(productId);
		}
		if(product == null) {//产品不存在
			this.jsonResult.setMessage("产品选择错误！");
			return JSON;
		} 
//		Long userId = bind.getUserId();
		Long userId = null;
		String mac = bind.getMac();
		String os = bind.getOs();
		Issue issue = null;
		if(product.getNumber() > 1){//预定未来期刊，需判断是否还有订单未发完
			issueCode = -1L;
			List<Sale> sales = saleService.getByCondition(userId, mac, os, publicationId, 1);
			if (isNotEmpty(sales)) {
				this.jsonResult.setMessage("已购买");
				return JSON;
			}
		} else if(product.getNumber() == 1) {
			issue = issueService.queryByIssueCode(issueCode);
			if (issue == null) {
				this.jsonResult.setMessage("期刊不存在");
				return JSON;
			}
			if (issue.getIsFree() == 1) {
				this.jsonResult.setMessage("本期免费");
				return JSON;
			}
			List<SaleContent> saleContents = saleContentService.getByCondition(userId, mac, os, publicationId, issueCode);
			if (isNotEmpty(saleContents)) {
				this.jsonResult.setMessage("已购买");
				return JSON;
			}
			
		} else {
			this.jsonResult.setMessage("订阅数量非法");
			return JSON;
		}
		this.jsonResult = JsonResult.getSuccess();
		
			List<Sale> sales = saleService.getByCondition(userId, mac, os, publicationId, 0);
			String uuid ="";
			if(sales!=null && !sales.isEmpty()){
				uuid = sales.get(0).getUuid();
			}else{
				//insert
				Sale sale = new Sale();
				sale.setMac(bind.getMac());
				sale.setOs(bind.getOs());
//				sale.setUserId(bind.getUserId());只认设备购买
				//金额计算
				if(totalAmt!=null)
					sale.setTotalAmt(totalAmt.multiply(new BigDecimal(1000)).longValue());
				else 
				{
					if(price==null)
						price = new BigDecimal(product.getPrice()/1000);
					sale.setTotalAmt(price.multiply(new BigDecimal(product.getNumber()*1000)).longValue());
				}
				sale.setIssueCode(issueCode);
				sale.setProductId(productId);
				sale.setPublicationId(publicationId);
				sale.setPlatformuuid("");//数据库不允许为空
				uuid = UUID.randomUUID().toString();
				sale.setUuid(uuid);
				sale.setStatus(0);
				sale.setUserId(0L);
				sale.setProductId(0L);
				saleService.insert(sale);
			}
			this.jsonResult.put("uuid", uuid);
		return JSON;
	}
	
	/**
	 * 执行订阅
	 * 参数列表：<br/>
	 * issueCode 期刊唯一编号<br/>
	 * publicationId 杂志id<br/>
	 * productId 产品（套餐类型）id<br/>
	 * platformuuid 平台（如iOS）返回的订单流水号<br/>
	 * totalAmt 订单金额，如果不传则自动计算
	 * price 价格，如果不传则取product中的价格
	 * os 系统类型，IOS/ANDROID
	 * mac 设备号
	 * @return
	 */
	public String subscribe() {
		checkSubscribe();//检查是否可以订阅
		if (jsonResult.getCode() != JsonResult.CODE.SUCCESS) {
			return JSON;
		}
		if(uuid==null || uuid.isEmpty()){
			this.jsonResult.setCode(JsonResult.CODE.FAILURE);
			this.jsonResult.setMessage("uuid不允许为空");
			return JSON;
		}
		this.jsonResult = JsonResult.getFailure();

		//productId可以不传，通过issueCode来查找product，这样前台就可以不调用得到product的接口
		PublicationProduct product = null;
		if(productId==null||productId<=0)
		{
			product = publicationProductService.getByIssueCode(publicationId, issueCode);
		}
		else
		{
			product = publicationProductService.getById(productId);
		}
		Sale sale = null;
		List<Sale> tmpSale = saleService.getByCondition(uuid, 0);
		if(tmpSale!=null && !tmpSale.isEmpty()){
			sale = tmpSale.get(0);
			sale.setPlatformuuid(platformuuid);
			sale.setStatus(SALE.STATUS_PAYED);
			//金额计算
			if(totalAmt!=null)
				sale.setTotalAmt(totalAmt.multiply(new BigDecimal(1000)).longValue());
			else 
			{
				if(price==null)
					price = new BigDecimal(product.getPrice()/1000);
				sale.setTotalAmt(price.multiply(new BigDecimal(product.getNumber()*1000)).longValue());
			}
			saleService.updateSale(sale);
		}
		
		//订阅当前期,生成对应的订单详情,赋予用户对当前期刊的阅读权限
		if (product.getNumber() == 1 && sale!=null) {
			SaleContent saleContent = new SaleContent();
			saleContent.setIssueCode(issueCode);
			saleContent.setSaleId(sale.getId());
			if(price!=null)
				saleContent.setPrice(price.longValue()*1000);
			else
				saleContent.setPrice(new Long(product.getPrice()));
			saleContentService.insert(saleContent);
			sale.setStatus(SALE.STATUS_FINISHED);
			
			saleService.updateSale(sale);
		}
		this.jsonResult = JsonResult.getSuccess();
		return JSON;
	}
	private boolean isNotEmpty(List<?> sales) {
		return sales != null && !sales.isEmpty();
	}
	
	/**
	 * 获取杂志的所有有效产品
	 * 参数：publicationId 杂志id
	 * @return
	 */
	public String allProducts() {
		this.jsonResult = JsonResult.getFailure();
		if(publicationId == null){
			this.generateJsonResult(JsonResult.CODE.FAILURE, "参数错误");
			return JSON;
		}
		List<PublicationProduct> productList = publicationProductService.getByPublicationId(publicationId);
		this.jsonResult = JsonResult.getSuccess();
		this.jsonResult.put("productList", productList);
		
		return JSON;
	}
	
	/**
	 * 根据superId和begin、size查询所有杂志的最新一期期刊，按期刊更新时间倒排<br/>
	 * 参数： superId 集合编号<br/>
	 * begin ： 已经加载的数量<br/>
	 * size ： 需要加载的数量<br/>
	 * return 所有杂志的最新一期期刊列表
	 */
	public String latestIssues() throws Exception {
		this.jsonResult = JsonResult.getFailure();
		List<Issue> issueList = issueService.queryBySuperId(superId,sortId, begin, size);
		if(issueList == null) issueList = new ArrayList<Issue>();
		markBuy(issueList);
		this.jsonResult = JsonResult.getSuccess();
		this.jsonResult.put("issueList", issueList);
		return JSON;
	}
	
	/**
	 * 最新
	 * @return
	 * @throws Exception
	 */
	public String queryLastestIssues() throws Exception {
		this.jsonResult = JsonResult.getFailure();
		List<Issue> issueList = issueService.queryLastestIssues(sortId,superId, begin, size);
		if(issueList == null) issueList = new ArrayList<Issue>();
		markBuy(issueList);
		issueList = pubByIssue(issueList);
		this.jsonResult = JsonResult.getSuccess();
		this.jsonResult.put("issueList", issueList);
		return JSON;
	}
	
	/**
	 * 热门的期刊
	 * @return
	 * @throws Exception
	 */
	public String queryHotIssues() throws Exception {
		this.jsonResult = JsonResult.getFailure();
		List<Issue> issueList = issueService.queryHotIssues(sortId,superId, begin, size);
		if(issueList == null) issueList = new ArrayList<Issue>();
		markBuy(issueList);
		issueList = pubByIssue(issueList);
		this.jsonResult = JsonResult.getSuccess();
		this.jsonResult.put("issueList", issueList);
		return JSON;
	}
	
	private void markBuy(List<Issue> issueList) {

		UserDeviceBind bind = getSessionUserDeviceBind();
		if(bind == null){//未登记设备信息
			if(mac==null)
			{
				return;
			}
			else
			{
				bind = new UserDeviceBind(null,os,mac);
			}
		}
		if(bind != null){//登记了设备信息
//			Long userId = userDeviceBind.getUserId();
			Long userId = null;
			String mac = bind.getMac();
			String os = bind.getOs();
			List<SaleContent> list = saleContentService.getByCondition(userId, mac, os, publicationId);
			//得到期刊价格
			Map param = new HashMap();
			param.put("number", 1);
			List<PublicationProduct> ppList = publicationProductService.getPublicationProductList(param);
			if(ppList!=null)
			{
				param.clear();
				for(PublicationProduct pp:ppList)
				{
					if(pp.getIssueCode()==null||pp.getIssueCode()<=0)
						param.put(pp.getPublicationId(),new BigDecimal(pp.getPrice()).divide(new BigDecimal(1000),2, BigDecimal.ROUND_DOWN));
					else
						param.put(pp.getPublicationId()+"-"+pp.getIssueCode(), new BigDecimal(pp.getPrice()).divide(new BigDecimal(1000),2, BigDecimal.ROUND_DOWN));
				}
			}
			//0：免费， 1：收费，2: 已购买， 3 加密
			for (Issue issue : issueList) {
				String passWord = issue.getPassWord();
				if(passWord != null && !"0".equals(passWord) && !"".equals(passWord)){
					passWord = "1";
				} else {
					passWord = "0";
				}
				issue.setPassWord(passWord);
				for (SaleContent saleContent : list)
					if(issue.getIssueCode().equals(saleContent.getIssueCode())){
						issue.setBuy(true);//标记为已购买
						break;
					}
				//期刊中增加价格
				if (issue.isBuy()) {//已购买
					issue.setPermission(PERMISSION.BUYED.getCode());
					//已购买期刊价格为0
					issue.setPrice(new BigDecimal(0));
				} else if(issue.getIsFree().equals(0)){//收费
					issue.setPermission(PERMISSION.CHARGE.getCode());
					//写入期刊价格
					if(param.get(issue.getPublicationId()+"-"+issue.getIssueCode())!=null)
						issue.setPrice((BigDecimal)param.get(issue.getPublicationId()+"-"+issue.getIssueCode()));
					//使用杂志价格
					else if(param.get(issue.getPublicationId())!=null)
						issue.setPrice((BigDecimal)param.get(issue.getPublicationId()));
					else//默认价格12
						issue.setPrice(new BigDecimal(12));
				} else if("1".equals(passWord)){//有密码
					issue.setPermission(PERMISSION.PASSWORD_REQUIRED.getCode());
				} else {//免费
					issue.setPermission(PERMISSION.FREE.getCode());
					//免费期刊价格为0
					issue.setPrice(new BigDecimal(0));
				}
			}
		}
	}

	/**
	 * 根据杂志id查询所有期刊
	 * 参数：publicationId 杂志id
	 * @return
	 * @throws Exception
	 */
	public String allIssue() throws Exception {
		this.jsonResult = JsonResult.getFailure();
		int[] statuses = {ISSUE.STATUS.ON_SALE.getCode()};
		List<Issue> issueList = issueService.queryByPubIdAndStatuses(publicationId, statuses,-1);
		if(issueList == null) issueList = new ArrayList<Issue>();
		markBuy(issueList);
		this.jsonResult = JsonResult.getSuccess();
		this.jsonResult.put("issueList", issueList);
		return JSON;
	}
	/**
	 * 获取购买的所有期刊，用于同步<br/>
	 * 可选参数:publicationId<br/>
	 * <i>若为null，则返回全部已购买期刊</i><br/>
	 * <i>若不为null，则返回对应杂志中的已购买期刊</i>
	 * @return
	 * @throws Exception
	 */
	public String allBuyIssue() throws Exception {
		UserDeviceBind bind = getSessionUserDeviceBind();
		if(bind == null){//未登记设备信息
			if(mac==null)
			{
				this.jsonResult.setMessage("未登记设备信息");
				return JSON;
			}
			else
			{
				bind = new UserDeviceBind(null,os,mac);
			}
		}
		List<Issue> issueList = new ArrayList<Issue>();
		if(bind != null){//登记了设备信息
			Long userId = bind.getUserId();
			String mac = bind.getMac();
			String os = bind.getOs();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userId", userId);
			params.put("mac", mac);
			params.put("os", os);
			params.put("publicationId", publicationId);
			issueList = issueService.queryBuyList(params);
		}
		this.jsonResult = JsonResult.getSuccess();
		this.jsonResult.put("issueList", issueList);
		return JSON;
	}
	
	/**
	 * 获取购买的所有的杂志(个人的订阅记录)
	 *  saleId==>订单id
	 *  saleTotal==>购买总期刊数
	 *  saleNumber==>实际已经购买的期刊数
	 *  saleCreateTime==>订单产生日期
	 * @return 则返回对应杂志中的已购买期刊
	 * @throws Exception
	 */
	public String allBuyPublication() throws Exception{
		UserDeviceBind bind = getSessionUserDeviceBind();
		if(bind == null){//未登记设备信息
			if(mac==null)
			{
				this.jsonResult.setMessage("未登记设备信息");
				return JSON;
			}
			else
			{
				bind = new UserDeviceBind(null,os,mac);
			}
		}
		List<Issue> issueList = new ArrayList<Issue>();
		if(bind != null){//登记了设备信息
//			Long userId = userDeviceBind.getUserId();
			String mac = bind.getMac();
			String os = bind.getOs();
			Map<String, Object> params = new HashMap<String, Object>();
			//params.put("userId", userId);
			params.put("mac", mac);
			params.put("os", os);
			params.put("publicationId", publicationId);
			issueList = issueService.queryPublicationBuyList(params);
			for (Issue issue : issueList) {//根据订单号id获取对应明细表里实际已购买的期刊
				Long tmpSaleId = issue.getSaleId();
				//已经购买的期刊总数
				issue.setSaleNumber(saleContentService.getByConditionCount(tmpSaleId));
			}
			issueList = pubByIssue(issueList);
		}
		this.jsonResult = JsonResult.getSuccess();
		this.jsonResult.put("issueList", issueList);
		return JSON;
	}
	
	/**
	 * 获取购买的所有的杂志(个人的订阅记录)
	 *  saleId==>订单id
	 *  saleTotal==>购买总期刊数
	 *  saleNumber==>实际已经购买的期刊数
	 *  saleCreateTime==>订单产生日期
	 * @return 则返回对应杂志中的已购买期刊
	 * @throws Exception
	 */
	public String clearPublicationBuy() throws Exception{
		this.jsonResult = JsonResult.getFailure();
		UserDeviceBind bind = getSessionUserDeviceBind();
		if(bind == null){//未登记设备信息
			if(mac==null)
			{
				this.jsonResult.setMessage("未登记设备信息");
				return JSON;
			}
			else
			{
				bind = new UserDeviceBind(null,os,mac);
			}
		}
		List<Issue> issueList = new ArrayList<Issue>();
		if(bind != null){//登记了设备信息
//			Long userId = userDeviceBind.getUserId();
			String mac = bind.getMac();
			String os = bind.getOs();
			saleContentService.updateByClear(mac, os);
		}
		this.jsonResult = JsonResult.getSuccess();
		return JSON;
	}
	

	/**
	 * 搜索期刊，按杂志名和出版商名称搜索
	 * @return
	 * @throws Exception
	 */
	public String search() throws Exception {
		this.jsonResult = JsonResult.getFailure();
		if(queryString == null || "".equals(queryString.trim())){
			this.jsonResult.setMessage("查询条件不能为空");
			return JSON;
		} else {
			//清除所有空格，并将所有字符以%分隔
			queryString = queryString.trim();
			String dirtyString = ",.\\/-+_=~!@#$%^&*()|{}[];\'\"<>?`";
			StringBuffer sb = new StringBuffer("%");
			for (int i = 0; i < queryString.length(); i++) {
				char c = queryString.charAt(i);
				if(dirtyString.indexOf(c) > -1){
					continue;
				}
				if(' ' != c && '%' != c){
					sb.append(c + "%");
				}
			}
			queryString = sb.toString();
		}
		List<Issue> issueList = issueService.searchByQueryStringAndSuperId(superId, queryString, begin, size);
		markBuy(issueList);
		this.jsonResult = JsonResult.getSuccess();
		this.jsonResult.put("issueList", issueList);
		return JSON;
	}
	
	/**
	 * 在Issue对象里存放publication对象
	 * @param issueList
	 */
	protected List<Issue> pubByIssue(List<Issue> issueList) {
		if(issueList!=null){
			for (Issue ise : issueList) {
				try{//TODO 太恐怖了，好多查数据库的动作。。。
					Publication pub = publicationService.queryById(ise.getPublicationId());
					ise.setPublicationPo(pub);
				}catch (Exception e) {
				}
			}
		}
		return issueList;
	}
	
	public String getQueryString() {
		return queryString;
	}
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
	public void setBegin(Integer begin) {
		this.begin = begin;
	}

	public Integer getBegin() {
		return begin;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getSize() {
		return size;
	}

	public void setSuperId(Long superId) {
		this.superId = superId;
	}

	public Long getSuperId() {
		return superId;
	}

	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}

	public Long getIssueCode() {
		return issueCode;
	}

	public void setPublicationId(Long publicationId) {
		this.publicationId = publicationId;
	}

	public Long getPublicationId() {
		return publicationId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setPlatformuuid(String platformuuid) {
		this.platformuuid = platformuuid;
	}

	public String getPlatformuuid() {
		return platformuuid;
	}
	public Long getSortId() {
		return sortId;
	}
	public void setSortId(Long sortId) {
		this.sortId = sortId;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public void setTotalAmt(BigDecimal totalAmt) {
		this.totalAmt = totalAmt;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public void setReceiptKey(String receiptKey) {
		this.receiptKey = receiptKey;
	}
}
