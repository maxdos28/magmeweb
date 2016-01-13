package cn.magme.web.action.app;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.pojo.mobile.MobileAdWaps;
import cn.magme.service.ma.MaAdWapsService;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;
@Results({ @Result(name = "json", type = "json", params = { "root", "result" }) })
@SuppressWarnings("serial")
public class WapsAdAction extends BaseAction {
	
	@Resource
	private MaAdWapsService maAdWapsService;
	private Map result = null;
	
	private String udid;
	private String app;
	private String idfa;
	private String openudid;
	private String os;
	private String callbackurl;
	/**
	 * 首次点击注册
	 * @return
	 */
	public String registerWaps()
	{
		if(result==null)
			result = new HashMap();
		result.put("success", false);
//		if(StringUtil.isBlank(udid))
//		{
//			result.put("message", "udid为空");
//			return JSON;
//		}
		if(StringUtil.isBlank(app))
		{
			result.put("message", "app为空");
			return JSON;
		}
		if(StringUtil.isBlank(idfa))
		{
			result.put("message", "idfa为空");
			return JSON;
		}
		if(StringUtil.isBlank(callbackurl))
		{
			result.put("message", "callbackurl为空");
			return JSON;
		}
		MobileAdWaps w = new MobileAdWaps();
		w.setApp(app);
		w.setCallbackurl(callbackurl);
		w.setIdfa(idfa);
		w.setOpenudid(openudid);
		w.setOs(os);
		w.setUdid(openudid);
		int r = this.maAdWapsService.registerWapsUser(w);
		if(r==0)
		{
			result.put("message", "插入失败或idfa重复");
			return JSON;
		}
		result.put("success", true);
		result.put("message", "成功");
		return JSON;
	}

	/**
	 * 用户匹配
	 * @return
	 */
	public String matchWaps()
	{
		if(result==null)
			result = new HashMap();
		result.put("success", false);
		if(StringUtil.isBlank(idfa))
		{
			result.put("message", "idfa为空");
			return JSON;
		}
		int r = this.maAdWapsService.matchWapsUser(idfa);
		if(r==0)
		{
			result.put("message", "匹配失败");
			return JSON;
		}
		result.put("success", true);
		result.put("message", "成功");
		return JSON;
	}

	public Map getResult() {
		return result;
	}

	public void setResult(Map result) {
		this.result = result;
	}

	public String getUdid() {
		return udid;
	}

	public void setUdid(String udid) {
		this.udid = udid;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public String getIdfa() {
		return idfa;
	}

	public void setIdfa(String idfa) {
		this.idfa = idfa;
	}

	public String getOpenudid() {
		return openudid;
	}

	public void setOpenudid(String openudid) {
		this.openudid = openudid;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getCallbackurl() {
		return callbackurl;
	}

	public void setCallbackurl(String callbackurl) {
		this.callbackurl = callbackurl;
	}
}
