package cn.magme.web.action.test;

import java.util.Calendar;

import javax.annotation.Resource;

import com.danga.MemCached.MemCachedClient;

import cn.magme.common.JsonResult;
import cn.magme.web.action.BaseAction;

public class TestMemcacheAction extends BaseAction {
	
	@Resource
	private MemCachedClient memCachedClient;
	
	public String testMemcache()
	{
		String ss = (String) memCachedClient.get("test123");
		String msg = null;
		if(ss==null)
		{
			msg = "write to memcache";
			System.out.println("write to memcache");
			Calendar cl = Calendar.getInstance();
			cl.add(Calendar.MINUTE, 1);
			System.out.println(cl.getTime());
			memCachedClient.set("test123", "test123", cl.getTime());
		}
		else
		{
			msg = "reader from memcache";
			System.out.println("reader from memcache");
		}
		this.jsonResult = JsonResult.getSuccess();
		this.jsonResult.setMessage(msg);
		return JSON;
	}

}
