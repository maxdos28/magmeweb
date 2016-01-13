package cn.magme.web.action.phoenix;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.jsoup.helper.StringUtil;

import cn.magme.common.JsonResult;
import cn.magme.constants.WebConstant;
import cn.magme.pojo.phoenix.PhoenixAndroidRevert;
import cn.magme.pojo.phoenix.PhoenixUser;
import cn.magme.service.charge.SaleService;
import cn.magme.service.phoenix.PhoenixOrderService;
import cn.magme.util.xsl.XslUtil;
import cn.magme.web.action.BaseAction;

import com.opensymphony.xwork2.ActionContext;

/**
 * 
 * @author jasper
 * @since 2013-5-14
 */
@Results({ @Result(name = "success", location = "/WEB-INF/pages/phoenix/accountCount.ftl")
,@Result(name="downloadsuccess",type="stream",params={"contentType","application/vnd.ms-excel","bufferSize","2048",
		"inputName","inputStream","contentDisposition","attachment;filename=\"exportData.xls\""})})
@SuppressWarnings("serial")
public class PhoenixOrderAction extends BaseAction {

	private static final long serialVersionUID = 2448272383871579484L;
	private static final Logger log = Logger
			.getLogger(PhoenixOrderAction.class);

	@Resource
	private PhoenixOrderService phoenixOrderService;
	@Resource
	private SaleService saleService;

	private Long appId;
	private String deviceNo;// 设备号
	private String mac;// 设备MAC地址，可选
	private Integer type;// 订阅类型 1 订阅指定类目 2 订阅所有类目
	private String cateIds;// 栏目ID，以逗号分隔
	private Long totalAmt;//订单金额

	private String tradeNo;// 支付宝交易号
	private String orderNo;//订单号

	private String receiptKey;// ios成功后返回的密钥
	private String fromDate;// 收入核算查询
	private String endDate;//收入核算查询
	private String exportType;//导出类型

	private ByteArrayInputStream inputStream;
	
	private String newDeviceNum;
	private String oldDeviceNum;

	// 页面
	public String account() {
		return "success";
	}

	// 收入核算查询
	public String searchCountAccount() {
		this.jsonResult = JsonResult.getFailure();
		if (StringUtil.isBlank(fromDate)) {
			this.jsonResult.setMessage("年份为空");
			return JSON;
		}
		if (StringUtil.isBlank(endDate)) {
			this.jsonResult.setMessage("月份为空");
			return JSON;
		}
		PhoenixUser phoenixUser = (PhoenixUser)ActionContext.getContext().getSession().get(WebConstant.SESSION.PHOENIX_USER);
		if(phoenixUser.getAppId()==null)
		{
			this.jsonResult.setMessage("appId为空");
			return JSON;
		}
		Map m = phoenixOrderService.countAccount(fromDate,endDate,phoenixUser.getAppId());
		this.jsonResult.setData(m);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}

	// 导出收入明细
	public String exportOrderDetail() {
		this.inputStream = new ByteArrayInputStream("".getBytes());
		this.jsonResult = JsonResult.getFailure();
		if (StringUtil.isBlank(fromDate)) {
			this.jsonResult.setMessage("开始日期为空");
			return "downloadsuccess";
		}
		if (StringUtil.isBlank(endDate)) {
			this.jsonResult.setMessage("结束时期为空");
			return "downloadsuccess";
		}
		if (StringUtil.isBlank(exportType)) {
			this.jsonResult.setMessage("类型为空");
			return "downloadsuccess";
		}
		PhoenixUser phoenixUser = (PhoenixUser)ActionContext.getContext().getSession().get(WebConstant.SESSION.PHOENIX_USER);
		if(phoenixUser.getAppId()==null)
		{
			this.jsonResult.setMessage("appId为空");
			return "downloadsuccess";
		}
		Map<String,List> m = phoenixOrderService.searchOrderDetail(exportType,fromDate,endDate,phoenixUser.getAppId());
		// 生成excel文件
		List<Map> contentList = null;
		List<String> header = new ArrayList();
		header.add("设备号");
		header.add("时间");
		header.add("金额");
		header.add("名称");
		header.add("ID");
		//header.add("价格");
		HSSFWorkbook wb = null;
		if(exportType.equals("iosMagazine"))
		{
			contentList = m.get("iosMagazineList");
			if(contentList==null)
				return "downloadsuccess";
			List<List<String>> l = toList(contentList);
			wb = XslUtil.exportToExcel(null,"IOS杂志",header, l);
		}
		if(exportType.equals("iosCategory"))
		{
			contentList = m.get("iosCategoryList");
			if(contentList==null)
				return "downloadsuccess";
			List<List<String>> l = toList(contentList);
			wb = XslUtil.exportToExcel(null,"IOS会员",header, l);
		}
		if(exportType.equals("androidCategory"))
		{
			contentList = m.get("androidCategoryList");
			if(contentList==null)
				return "downloadsuccess";
			List<List<String>> l = toList(contentList);
			wb = XslUtil.exportToExcel(null,"Android会员",header, l);
		}
		if(wb==null)
			return "downloadsuccess";
			
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			wb.write(baos);
		} catch (IOException e) {
			log.error("", e);
		}
		byte[] ba = baos.toByteArray();
		this.inputStream = new ByteArrayInputStream(ba);
		return "downloadsuccess";
	}

	//金额需要除1000变为实际价格
	private List<List<String>> toList(List<Map> contentList) {
		List<List<String>> l = new ArrayList();
		String id = null;
		for(Map<String,String> c:contentList)
		{
			List cl = new ArrayList();
			if(id==null||!c.get("saleId").equals(id))
			{
				id = c.get("saleId");
				cl.add(c.get("deviceNum"));
				cl.add(c.get("orderDate"));
				if(c.get("totalAmt")!=null)
					cl.add(new BigDecimal(c.get("totalAmt")).divide(new BigDecimal(1000), 2, BigDecimal.ROUND_DOWN).doubleValue()+"");
				else
					cl.add("0");
			}
			else
			{
				cl.add("");
				cl.add("");
				cl.add("");
					
			}
			cl.add(c.get("name"));
			cl.add(c.get("categoryId"));
//			if(c.get("price")!=null)
//				cl.add(new BigDecimal(c.get("price")).divide(new BigDecimal(1000), 2, BigDecimal.ROUND_DOWN).doubleValue()+"");
//			else
//				cl.add("0");
			l.add(cl);
		}
		return l;
	}

	// 新订单，返回订单号
	public String newOrderJson() {
		this.jsonResult = JsonResult.getFailure();
		if (appId == null || appId <= 0) {
			this.jsonResult.setMessage("appId小于0");
			return JSON;
		}
		if (StringUtil.isBlank(deviceNo)) {
			this.jsonResult.setMessage("设备号为空");
			return JSON;
		}
		if (type == null || type <= 0) {
			this.jsonResult.setMessage("订阅类型为空");
			return JSON;
		}
		if (StringUtil.isBlank(cateIds)) {
			this.jsonResult.setMessage("没有栏目");
			return JSON;
		}
		try {
			Map order = new HashMap();
			order.put("appId", appId);
			order.put("deviceNo", deviceNo);
			order.put("mac", mac);
			order.put("type", type);
			order.put("totalAmt", totalAmt);
			// 栏目ID，如果订阅全部栏目，也要把全部栏目传过来
			order.put("cateIds", cateIds);
			String orderNo = phoenixOrderService.newOrder(order);
			this.jsonResult.put("orderNo", orderNo);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		} catch (Exception e) {
			log.error("PhoenixOrderForAndroidAction.class", e);
			e.printStackTrace();
		}
		return JSON;
	}
	

	// 修改订单，android付费成功
	public String updateOrderJson() {
		this.jsonResult = JsonResult.getFailure();
		if (StringUtil.isBlank(orderNo)) {
			this.jsonResult.setMessage("orderNo为空");
			return JSON;
		}
//		if (StringUtil.isBlank(tradeNo)) {
//			this.jsonResult.setMessage("交易号为空");
//			return JSON;
//		}
		try {
			phoenixOrderService.updateOrder(orderNo, tradeNo);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		} catch (Exception e) {
			log.error("PhoenixOrderForAndroidAction.class", e);
			e.printStackTrace();
		}
		return JSON;
	}

	// 得到购买的栏目
	public String queryPaidCategoryJson() {
		this.jsonResult = JsonResult.getFailure();
		if (appId == null || appId <= 0) {
			this.jsonResult.setMessage("appId小于0");
			return JSON;
		}
		if (StringUtil.isBlank(deviceNo)) {
			this.jsonResult.setMessage("设备号为空");
			return JSON;
		}
		try {
			List l = phoenixOrderService.queryPaidCategory(appId, deviceNo);
			this.jsonResult.put("paidCategoryList", l);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		} catch (Exception e) {
			log.error("PhoenixOrderForAndroidAction.class", e);
			e.printStackTrace();
		}
		return JSON;
	}

	// 还原账号
	public String revertJson() {
		this.jsonResult = JsonResult.getFailure();

		if (StringUtil.isBlank(deviceNo)) {
			this.jsonResult.setMessage("设备号为空");
			return JSON;
		}

		if (StringUtil.isBlank(tradeNo)) {
			this.jsonResult.setMessage("支付宝交易号为空");
			return JSON;
		}
		try {
			PhoenixAndroidRevert phoenixAndroidRevert = new PhoenixAndroidRevert();
			phoenixAndroidRevert.setCreateTime(new Date());
			phoenixAndroidRevert.setDeviceNum(deviceNo);
			phoenixAndroidRevert.setOrderNum(tradeNo);
			phoenixAndroidRevert.setStatus(1);
			phoenixOrderService.insertAndroidRevert(phoenixAndroidRevert);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		} catch (Exception e) {
			log.error("PhoenixOrderForAndroidAction.class", e);
			e.printStackTrace();
		}
		return JSON;
	}

	// 新订单，ios
	public String iosNewOrderJson() {
		this.jsonResult = JsonResult.getFailure();
		if (appId == null || appId <= 0) {
			this.jsonResult.setMessage("appId小于0");
			return JSON;
		}
		if (StringUtil.isBlank(mac)) {
			this.jsonResult.setMessage("MAC地址为空");
			return JSON;
		}
		if (type == null || type <= 0) {
			this.jsonResult.setMessage("订阅类型为空");
			return JSON;
		}
		if (StringUtil.isBlank(cateIds)) {
			this.jsonResult.setMessage("没有栏目");
			return JSON;
		}
		if (StringUtil.isBlank(receiptKey)) {
			this.jsonResult.setMessage("没有返回密钥");
			return JSON;
		}
		try {
			//此处测试使用，固定一个密钥，发布时需要注释掉
			//receiptKey = "ewoJInNpZ25hdHVyZSIgPSAiQW1zWXlra3BkcmNrclRseVkyUXMzZ2RBVmhzV0srSlM1a2w5NzEzaEpodGZGRWtIejNwbE9yK3JtclFXNDB6R2Jtb1BiZDNmTGtFdzFaWmlaY2xaY2xSNlNUK1Fwcnp0M0FYdUU4dFl2Mmk2OGozQVF1UGRzc25LUlYvbk9LOFRnb2xCb04zaGFxVFl0SHhFMVc0dGpRVGl0Ym90U0cwVlBlNkxrZXVqTHNlV0FBQURWekNDQTFNd2dnSTdvQU1DQVFJQ0NHVVVrVTNaV0FTMU1BMEdDU3FHU0liM0RRRUJCUVVBTUg4eEN6QUpCZ05WQkFZVEFsVlRNUk13RVFZRFZRUUtEQXBCY0hCc1pTQkpibU11TVNZd0pBWURWUVFMREIxQmNIQnNaU0JEWlhKMGFXWnBZMkYwYVc5dUlFRjFkR2h2Y21sMGVURXpNREVHQTFVRUF3d3FRWEJ3YkdVZ2FWUjFibVZ6SUZOMGIzSmxJRU5sY25ScFptbGpZWFJwYjI0Z1FYVjBhRzl5YVhSNU1CNFhEVEE1TURZeE5USXlNRFUxTmxvWERURTBNRFl4TkRJeU1EVTFObG93WkRFak1DRUdBMVVFQXd3YVVIVnlZMmhoYzJWU1pXTmxhWEIwUTJWeWRHbG1hV05oZEdVeEd6QVpCZ05WQkFzTUVrRndjR3hsSUdsVWRXNWxjeUJUZEc5eVpURVRNQkVHQTFVRUNnd0tRWEJ3YkdVZ1NXNWpMakVMTUFrR0ExVUVCaE1DVlZNd2daOHdEUVlKS29aSWh2Y05BUUVCQlFBRGdZMEFNSUdKQW9HQkFNclJqRjJjdDRJclNkaVRDaGFJMGc4cHd2L2NtSHM4cC9Sd1YvcnQvOTFYS1ZoTmw0WElCaW1LalFRTmZnSHNEczZ5anUrK0RyS0pFN3VLc3BoTWRkS1lmRkU1ckdYc0FkQkVqQndSSXhleFRldngzSExFRkdBdDFtb0t4NTA5ZGh4dGlJZERnSnYyWWFWczQ5QjB1SnZOZHk2U01xTk5MSHNETHpEUzlvWkhBZ01CQUFHamNqQndNQXdHQTFVZEV3RUIvd1FDTUFBd0h3WURWUjBqQkJnd0ZvQVVOaDNvNHAyQzBnRVl0VEpyRHRkREM1RllRem93RGdZRFZSMFBBUUgvQkFRREFnZUFNQjBHQTFVZERnUVdCQlNwZzRQeUdVakZQaEpYQ0JUTXphTittVjhrOVRBUUJnb3Foa2lHOTJOa0JnVUJCQUlGQURBTkJna3Foa2lHOXcwQkFRVUZBQU9DQVFFQUVhU2JQanRtTjRDL0lCM1FFcEszMlJ4YWNDRFhkVlhBZVZSZVM1RmFaeGMrdDg4cFFQOTNCaUF4dmRXLzNlVFNNR1k1RmJlQVlMM2V0cVA1Z204d3JGb2pYMGlreVZSU3RRKy9BUTBLRWp0cUIwN2tMczlRVWU4Y3pSOFVHZmRNMUV1bVYvVWd2RGQ0TndOWXhMUU1nNFdUUWZna1FRVnk4R1had1ZIZ2JFL1VDNlk3MDUzcEdYQms1MU5QTTN3b3hoZDNnU1JMdlhqK2xvSHNTdGNURXFlOXBCRHBtRzUrc2s0dHcrR0szR01lRU41LytlMVFUOW5wL0tsMW5qK2FCdzdDMHhzeTBiRm5hQWQxY1NTNnhkb3J5L0NVdk02Z3RLc21uT09kcVRlc2JwMGJzOHNuNldxczBDOWRnY3hSSHVPTVoydG04bnBMVW03YXJnT1N6UT09IjsKCSJwdXJjaGFzZS1pbmZvIiA9ICJld29KSW05eWFXZHBibUZzTFhCMWNtTm9ZWE5sTFdSaGRHVXRjSE4wSWlBOUlDSXlNREV6TFRBMUxURTFJREF5T2pVNE9qQTNJRUZ0WlhKcFkyRXZURzl6WDBGdVoyVnNaWE1pT3dvSkluVnVhWEYxWlMxcFpHVnVkR2xtYVdWeUlpQTlJQ0l3TURBd1lqQTBNekUzWWpnaU93b0pJbTl5YVdkcGJtRnNMWFJ5WVc1ellXTjBhVzl1TFdsa0lpQTlJQ0l4TURBd01EQXdNRGMwTVRBeU1qYzVJanNLQ1NKaWRuSnpJaUE5SUNJeExqQWlPd29KSW5SeVlXNXpZV04wYVc5dUxXbGtJaUE5SUNJeE1EQXdNREF3TURjME1UQXlNamM1SWpzS0NTSnhkV0Z1ZEdsMGVTSWdQU0FpTVNJN0Nna2liM0pwWjJsdVlXd3RjSFZ5WTJoaGMyVXRaR0YwWlMxdGN5SWdQU0FpTVRNMk9EWXhNVGc0TnpZek15STdDZ2tpZFc1cGNYVmxMWFpsYm1SdmNpMXBaR1Z1ZEdsbWFXVnlJaUE5SUNJd1FVRkJSVEV5TXkwMk56aEJMVFJCTXpJdE9FTXdOeTFHTmtFeFJEWkVOVEV5UWpnaU93b0pJbkJ5YjJSMVkzUXRhV1FpSUQwZ0ltTnZiUzVyWVd0aExrMWhaMjFsVUdWeWFXOWthV05oYkRnNU15STdDZ2tpYVhSbGJTMXBaQ0lnUFNBaU5qUTROemM0T0RFeUlqc0tDU0ppYVdRaUlEMGdJbU52YlM1cllXdGhMazFoWjIxbFVHVnlhVzlrYVdOaGJEZzVNaUk3Q2draWNIVnlZMmhoYzJVdFpHRjBaUzF0Y3lJZ1BTQWlNVE0yT0RZeE1UZzROell6TXlJN0Nna2ljSFZ5WTJoaGMyVXRaR0YwWlNJZ1BTQWlNakF4TXkwd05TMHhOU0F3T1RvMU9Eb3dOeUJGZEdNdlIwMVVJanNLQ1NKd2RYSmphR0Z6WlMxa1lYUmxMWEJ6ZENJZ1BTQWlNakF4TXkwd05TMHhOU0F3TWpvMU9Eb3dOeUJCYldWeWFXTmhMMHh2YzE5QmJtZGxiR1Z6SWpzS0NTSnZjbWxuYVc1aGJDMXdkWEpqYUdGelpTMWtZWFJsSWlBOUlDSXlNREV6TFRBMUxURTFJREE1T2pVNE9qQTNJRVYwWXk5SFRWUWlPd3A5IjsKCSJlbnZpcm9ubWVudCIgPSAiU2FuZGJveCI7CgkicG9kIiA9ICIxMDAiOwoJInNpZ25pbmctc3RhdHVzIiA9ICIwIjsKfQ==";
			Map order = new HashMap();
			//根据密钥得到IOS账单
			String responseStr = phoenixOrderService.reqAppleReceipt(receiptKey, appId);
//			if(StringUtil.isBlank(responseStr))
//			{
//				this.jsonResult.setMessage("没有得到APPLE账单数据");
//				return JSON;
//			}
			JSONObject json=(JSONObject)JSONValue.parse(responseStr);
			long status=(Long)json.get("status");
			JSONObject receipt=(JSONObject)json.get("receipt");
//			if(status!=0){
//				//不是正常状态
//				jsonResult.setMessage("返回APPLE账单状态不是0");
//				jsonResult.put("status", status);
//				return JSON;
//			}else{
				//账单有效
				//ios中mac放设备号
			if(receipt!=null)
			{
				order.put("mac", receipt.get("unique_vendor_identifier"));
				order.put("tradeNo", receipt.get("transaction_id"));
			}
								
//			}
			//保存订单
			order.put("appId", appId);
			//ios中设备号放mac
			order.put("deviceNo", mac);
			order.put("type", type);
			order.put("totalAmt", totalAmt);
			order.put("receiptKey", receiptKey);
			// 栏目ID，如果订阅全部栏目，也要把全部栏目传过来
			order.put("cateIds", cateIds);
			String orderNo = phoenixOrderService.iosNewOrder(order);
			this.jsonResult.put("orderNo", orderNo);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		} catch (Exception e) {
			log.error("PhoenixOrderForAndroidAction.class", e);
			e.printStackTrace();
		}
		return JSON;
	}

	// ios得到购买的栏目
	public String iosQueryPaidCategoryJson() {
		this.jsonResult = JsonResult.getFailure();
		if (appId == null || appId <= 0) {
			this.jsonResult.setMessage("appId小于0");
			return JSON;
		}
		if (StringUtil.isBlank(mac)) {
			this.jsonResult.setMessage("MAC地址为空");
			return JSON;
		}
		try {
			List l = phoenixOrderService.iosQueryPaidCategory(appId, mac);
			this.jsonResult.put("paidCategoryList", l);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		} catch (Exception e) {
			log.error("PhoenixOrderForAndroidAction.class", e);
			e.printStackTrace();
		}
		return JSON;
	}
	//ios还原功能
	public String iosRevertJson() {
		this.jsonResult = JsonResult.getFailure();
		if (appId == null || appId <= 0) {
			this.jsonResult.setMessage("appId小于0");
			return JSON;
		}
		if (StringUtil.isBlank(newDeviceNum)) {
			this.jsonResult.setMessage("newDeviceNum地址为空");
			return JSON;
		}
		if (StringUtil.isBlank(oldDeviceNum)) {
			this.jsonResult.setMessage("oldDeviceNum地址为空");
			return JSON;
		}
		try {
			String msg = phoenixOrderService.iosRevert(appId, newDeviceNum, oldDeviceNum);
			String msg2 = saleService.publicationRevert(appId, "IOS", newDeviceNum, oldDeviceNum);
			if(msg!=null&&msg2!=null)
			{
				this.jsonResult.setMessage(msg);
				return JSON;
			}
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		} catch (Exception e) {
			log.error("PhoenixOrderForAndroidAction.class", e);
			e.printStackTrace();
		}
		return JSON;
	}
	

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCateIds() {
		return cateIds;
	}

	public void setCateIds(String cateIds) {
		this.cateIds = cateIds;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getReceiptKey() {
		return receiptKey;
	}

	public void setReceiptKey(String receiptKey) {
		this.receiptKey = receiptKey;
	}

	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}

	public void setTotalAmt(Long totalAmt) {
		this.totalAmt = totalAmt;
	}

	public String getExportType() {
		return exportType;
	}

	public void setExportType(String exportType) {
		this.exportType = exportType;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setNewDeviceNum(String newDeviceNum) {
		this.newDeviceNum = newDeviceNum;
	}

	public void setOldDeviceNum(String oldDeviceNum) {
		this.oldDeviceNum = oldDeviceNum;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

}
