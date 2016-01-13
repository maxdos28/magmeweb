package cn.magme.web.action.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.magme.common.JsonResult;
import cn.magme.web.action.BaseAction;

public class MeIfAction extends BaseAction {

	public String getAdSize()
	{
		this.jsonResult = JsonResult.getFailure();
		List l = new ArrayList();
		Map m1 = new HashMap();
		m1.put("name", "320-50");
		m1.put("value", "320-50");
		m1.put("type", "pad");
		l.add(m1);
		Map m2 = new HashMap();
		m2.put("name", "320-250");
		m2.put("value", "320-250");
		m2.put("type", "pad");
		l.add(m2);
		Map m3 = new HashMap();
		m3.put("name", "768-256");
		m3.put("value", "768-256");
		m3.put("type", "pad");
		l.add(m3);
		Map m4 = new HashMap();
		m4.put("name", "768-1024");
		m4.put("value", "768-1024");
		m4.put("type", "pad");
		l.add(m4);
		Map m5 = new HashMap();
		m5.put("name", "320-50");
		m5.put("value", "320-50");
		m5.put("type", "phone");
		l.add(m5);
		Map m6 = new HashMap();
		m6.put("name", "320-250");
		m6.put("value", "320-250");
		m6.put("type", "phone");
		l.add(m6);
		Map m7 = new HashMap();
		m7.put("name", "320-142");
		m7.put("value", "320-142");
		m7.put("type", "phone");
		l.add(m7);
		Map m8 = new HashMap();
		m8.put("name", "320-568");
		m8.put("value", "320-568");
		m8.put("type", "phone");
		l.add(m8);
		this.jsonResult.put("size", l);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}
	
	public String getKeyword()
	{
		this.jsonResult = JsonResult.getFailure();
		List l = new ArrayList();
		Map m1 = new HashMap();
		m1.put("name", "减肥");
		m1.put("value", "减肥");
		l.add(m1);
		Map m2 = new HashMap();
		m2.put("name", "度假");
		m2.put("value", "度假");
		l.add(m2);
		Map m3 = new HashMap();
		m3.put("name", "穷折腾");
		m3.put("value", "穷折腾");
		l.add(m3);
		Map m4 = new HashMap();
		m4.put("name", "读书");
		m4.put("value", "读书");
		l.add(m4);
		Map m5 = new HashMap();
		m5.put("name", "学业");
		m5.put("value", "学业");
		l.add(m5);
		Map m6 = new HashMap();
		m6.put("name", "奋斗");
		m6.put("value", "奋斗");
		l.add(m6);
		Map m7 = new HashMap();
		m7.put("name", "霸气");
		m7.put("value", "霸气");
		l.add(m7);
		Map m8 = new HashMap();
		m8.put("name", "更难就业季");
		m8.put("value", "更难就业季");
		l.add(m8);
		this.jsonResult.put("keyworld", l);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}

}
