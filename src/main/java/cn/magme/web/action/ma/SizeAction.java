package cn.magme.web.action.ma;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.pojo.ma.MaAdSize;
import cn.magme.service.ma.MaSizeService;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

/**
 * 广告尺寸管理
 * @author jasper
 * @date 2014.3.13
 *
 */
@Results({@Result(name="success",location="/WEB-INF/pages/ma/size.ftl")})
public class SizeAction extends BaseAction {
	
	private List sizeList;
	
	private String sizeName;
	private String sizeValue;
	private Byte device;
	
	private Long id;

	@Resource
	private MaSizeService maSizeService;
	public String execute()
	{
		sizeList = this.maSizeService.getMaAdSize();
		return SUCCESS;
	}

	/**
	 * 得到广告尺寸
	 * @return
	 */
	public String getAdSizeList()
	{
		this.jsonResult = JsonResult.getFailure();
		sizeList = this.maSizeService.getMaAdSize();
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}

	/**
	 * 增加广告尺寸
	 * @return
	 */
	public String addSize()
	{
		this.jsonResult = JsonResult.getFailure();
		if(StringUtil.isBlank(sizeName))
		{
			this.jsonResult.setMessage("请填写尺寸名称");
			return JSON;
		}
		if(StringUtil.isBlank(sizeValue))
		{
			this.jsonResult.setMessage("请填写尺寸值");
			return JSON;
		}
		if(device==null||device<0)
		{
			this.jsonResult.setMessage("请填写设备类型");
			return JSON;
		}
		MaAdSize ms = new MaAdSize();
		ms.setSizeName(sizeName);
		ms.setSizeValue(sizeValue);
		ms.setDevice(device);
		int r = this.maSizeService.saveMaAdSize(ms);
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
	 * 删除广告尺寸
	 * @return
	 */
	public String deleteSize()
	{
		this.jsonResult = JsonResult.getFailure();
		if(id==null||id<0)
		{
			this.jsonResult.setMessage("id为空");
			return JSON;
		}
		int r = this.maSizeService.deleteMaAdSize(id);
		if(r<=0)
		{
			this.jsonResult.setMessage("操作失败");
			return JSON;
		}
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}

	public String getSizeName() {
		return sizeName;
	}

	public void setSizeName(String sizeName) {
		this.sizeName = sizeName;
	}

	public String getSizeValue() {
		return sizeValue;
	}

	public void setSizeValue(String sizeValue) {
		this.sizeValue = sizeValue;
	}

	public Byte getDevice() {
		return device;
	}

	public void setDevice(Byte device) {
		this.device = device;
	}

	public void setSizeList(List sizeList) {
		this.sizeList = sizeList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List getSizeList() {
		return sizeList;
	}
}
