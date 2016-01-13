package cn.magme.web.action.sns;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.service.sns.SnsCreativeService;
import cn.magme.web.action.BaseAction;

/**
 * 作品的sitemap生成
 * @author fredy
 * @since 2012-11-23
 */
@Results({@Result(name="success",location="/WEB-INF/pages/rss/rss114la.ftl",params={"contentType","text/xml"})})
public class CreativeSitemapAction extends BaseAction {
	
	
	@Resource
	private SnsCreativeService snsCreativeService;
	
	public String lastestJson(){
		
		
		
		return JSON;
	}
	
	

}
