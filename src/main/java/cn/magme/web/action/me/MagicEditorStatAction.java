package cn.magme.web.action.me;

import javax.annotation.Resource;

import cn.magme.common.JsonResult;
import cn.magme.pojo.mobile.MobileMuidDic;
import cn.magme.service.me.MagicEditorStatService;
import cn.magme.web.action.BaseAction;

/**
 * 采编系统统计接口
 * @author jasper
 * @date 2013.8.27
 *
 */
public class MagicEditorStatAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5006662855311633250L;

	@Resource
	private MagicEditorStatService magicEditorStatService;

	private String muid;
	private String os;
	private String version;
	private String device;
	private String model;
	
	private Long appId;
	private Long publicationId;
	
	/**
	 * 注册设备信息,得到muid
	 * @return
	 */
	public String genericMuid()
	{
		this.jsonResult = JsonResult.getFailure();
		this.jsonResult.setMessage(null);
		try {
			if(muid==null||muid.trim().length()==0)
				return JSON;
			MobileMuidDic m = new MobileMuidDic();
			m.setMuid(muid);
			m.setDevice(device);
			m.setModel(model);
			m.setOs(os);
			m.setVersion(version);
			Long smuid = this.magicEditorStatService.geneicMuid(m);
			this.jsonResult.put("muid", smuid);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return JSON;
	}
	
	/**
	 * 得到广告列表
	 * @return
	 */
	public String getAdList()
	{
		this.jsonResult = JsonResult.getFailure();
		this.jsonResult.setMessage(null);
		try {
			if(appId==null||appId<=0||publicationId==null||publicationId<=0)
			{
				return JSON;
			}
			this.jsonResult = this.magicEditorStatService.getAdList(appId, publicationId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return JSON;
	}

	public String getMuid() {
		return muid;
	}

	public void setMuid(String muid) {
		this.muid = muid;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public Long getPublicationId() {
		return publicationId;
	}

	public void setPublicationId(Long publicationId) {
		this.publicationId = publicationId;
	}
}
