package cn.magme.web.action.ma;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.pojo.ma.MaAdHead;
import cn.magme.pojo.ma.MaAdKeyword;
import cn.magme.pojo.ma.MaAdSize;
import cn.magme.service.ma.MaAdLocationService;
import cn.magme.service.ma.MaAdTradeService;
import cn.magme.service.ma.MaKeywordService;
import cn.magme.service.ma.MaSizeService;
import cn.magme.util.DateUtil;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;
/**
 * 广告投放
 * @author jasper
 * @date 2014.3.13
 *
 */
@Results({@Result(name="success",location="/WEB-INF/pages/ma/adTradeNewOfPubStep.ftl")})
public class AdTradeNewOfPubAction extends BaseAction {
	
	@Resource
	private MaAdTradeService maAdTradeService;
	@Resource
	private MaKeywordService maKeywordService;
	@Resource
	private MaSizeService maSizeService;
	@Resource
	private MaAdLocationService maAdLocationService;
	
	private List<Map> publicationCategoryList;
	private List<MaAdSize> sizeList;
	private List<MaAdKeyword> keywordList;
	private List<Map> adLocationListByPub;
	private Long publicationId;
	private Integer devicePad;
	private Integer devicePhone;
	private String adTradeName;
	private String padSizes;
	private String phoneSizes;
	private String keywords;
	private String startDate;
	private String endDate;
	private String contentStartDate;
	private String contentEndDate;
	private String adPublications;
	private List<String> scriptKeyList;
	private List<String> scriptValueList;	
	
	public String execute()
	{
		//尺寸
		sizeList = this.maSizeService.getMaAdSize();
		//分类
		publicationCategoryList = this.maKeywordService.getPublicationCategoryForKeyword();
		//标签
		this.keywordList = this.maKeywordService.getMaAdKeywordList();
		return SUCCESS;
	}
	
	/**
	 * 查询匹配广告
	 * 按杂志
	 * @return
	 */
	public String searchAdByPub()
	{
		this.jsonResult = JsonResult.getFailure();
		List<String> padl = padList();
		List<String> phonel = phoneList();
		List<String> kl = keywordList();
		this.adLocationListByPub = this.maAdLocationService.searchAdLocationByPub(devicePad, devicePhone, padl, phonel, kl, startDate, endDate, contentStartDate, contentEndDate);
		this.jsonResult.put("adLocationListByPub", adLocationListByPub);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}

	private List<String> keywordList() {
		List<String> kl = null;
		if(StringUtil.isNotBlank(keywords))
		{
			kl = new ArrayList();
			String[] ks = keywords.split(",");
			for(String k:ks)
			{
				kl.add(k);
			}
		}
		return kl;
	}

	private List<String> phoneList() {
		List<String> phonel = null;
		if(StringUtil.isNotBlank(phoneSizes))
		{
			phonel = new ArrayList();
			String[] ss = phoneSizes.split(",");
			for(String s:ss)
			{
				phonel.add(s);
			}
		}
		return phonel;
	}

	private List<String> padList() {
		List<String> padl = null;
		if(StringUtil.isNotBlank(padSizes))
		{
			padl = new ArrayList();
			String[] ds = padSizes.split(",");
			for(String d:ds)
			{
				padl.add(d);
			}
		}
		return padl;
	}

	/**
	 * 创建广告交易
	 * @return
	 */
	public String saveAdTrade()
	{
		this.jsonResult = JsonResult.getFailure();
		HttpServletRequest request = ServletActionContext.getRequest();
		Map parameter = request.getParameterMap();
		if(parameter==null||parameter.size()==0)
		{
			this.jsonResult.setMessage("操作失败");
			return JSON;
		}
		if(StringUtil.isBlank(adTradeName))
		{
			this.jsonResult.setMessage("广告名称未填写");
			return JSON;
		}
		if(StringUtil.isBlank(startDate)||StringUtil.isBlank(endDate))
		{
			this.jsonResult.setMessage("广告日期未填写");
			return JSON;
		}
		if(StringUtil.isBlank(contentStartDate)||StringUtil.isBlank(contentEndDate))
		{
			this.jsonResult.setMessage("内容上线日期未填写");
			return JSON;
		}
		if(StringUtil.isBlank(keywords))
		{
			this.jsonResult.setMessage("广告标签未选择");
			return JSON;
		}
		String[] scriptKeyList = (String[])parameter.get("scriptKeyList[]");
		String[] scriptValueList = (String[])parameter.get("scriptValueList[]");
		if(scriptKeyList==null||scriptKeyList.length==0||scriptValueList==null||scriptValueList.length==0||scriptKeyList.length!=scriptValueList.length)
		{
			this.jsonResult.setMessage("广告尺寸或脚本未填写");
			return JSON;
		}
		if(StringUtil.isBlank(adPublications))
		{
			this.jsonResult.setMessage("广告位置未选择");
			return JSON;
		}
		MaAdHead mh = new MaAdHead();
		mh.setAdName(adTradeName);
		mh.setStartDate(DateUtil.parse(startDate));
		mh.setEndDate(DateUtil.parse(endDate));
		mh.setContentStartDate(DateUtil.parse(contentStartDate));
		mh.setContentEndDate(DateUtil.parse(contentEndDate));
		this.jsonResult = this.maAdTradeService.createAdTradeOfPub(mh,keywords,scriptKeyList,scriptValueList,adPublications);
		return JSON;
	}

	public List<Map> getPublicationCategoryList() {
		return publicationCategoryList;
	}

	public void setPublicationCategoryList(List<Map> publicationCategoryList) {
		this.publicationCategoryList = publicationCategoryList;
	}

	public List<MaAdSize> getSizeList() {
		return sizeList;
	}

	public void setSizeList(List<MaAdSize> sizeList) {
		this.sizeList = sizeList;
	}

	public List<MaAdKeyword> getKeywordList() {
		return keywordList;
	}

	public void setKeywordList(List<MaAdKeyword> keywordList) {
		this.keywordList = keywordList;
	}

	public List<Map> getAdLocationListByPub() {
		return adLocationListByPub;
	}

	public void setAdLocationListByPub(List<Map> adLocationListByPub) {
		this.adLocationListByPub = adLocationListByPub;
	}

	public Long getPublicationId() {
		return publicationId;
	}

	public void setPublicationId(Long publicationId) {
		this.publicationId = publicationId;
	}

	public Integer getDevicePad() {
		return devicePad;
	}

	public void setDevicePad(Integer devicePad) {
		this.devicePad = devicePad;
	}

	public Integer getDevicePhone() {
		return devicePhone;
	}

	public void setDevicePhone(Integer devicePhone) {
		this.devicePhone = devicePhone;
	}

	public String getPadSizes() {
		return padSizes;
	}

	public void setPadSizes(String padSizes) {
		this.padSizes = padSizes;
	}

	public String getPhoneSizes() {
		return phoneSizes;
	}

	public void setPhoneSizes(String phoneSizes) {
		this.phoneSizes = phoneSizes;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public List<String> getScriptKeyList() {
		return scriptKeyList;
	}

	public void setScriptKeyList(List<String> scriptKeyList) {
		this.scriptKeyList = scriptKeyList;
	}

	public List<String> getScriptValueList() {
		return scriptValueList;
	}

	public void setScriptValueList(List<String> scriptValueList) {
		this.scriptValueList = scriptValueList;
	}

	public String getAdPublications() {
		return adPublications;
	}

	public void setAdPublications(String adPublications) {
		this.adPublications = adPublications;
	}

	public String getAdTradeName() {
		return adTradeName;
	}

	public void setAdTradeName(String adTradeName) {
		this.adTradeName = adTradeName;
	}

	public String getContentStartDate() {
		return contentStartDate;
	}

	public void setContentStartDate(String contentStartDate) {
		this.contentStartDate = contentStartDate;
	}

	public String getContentEndDate() {
		return contentEndDate;
	}

	public void setContentEndDate(String contentEndDate) {
		this.contentEndDate = contentEndDate;
	}
}
