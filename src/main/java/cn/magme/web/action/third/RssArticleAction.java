package cn.magme.web.action.third;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.pojo.ArticleWithExAndCreative;
import cn.magme.service.sns.ArticleService;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

@Results({@Result(name="success",location="/WEB-INF/pages/rss/rssnetease.ftl",params={"contentType","text/xml"})})
public class RssArticleAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2525324507718552242L;
	
	@Resource
	private ArticleService articleService;
	
	public String execute(){
		articleList=this.articleService.queryArticleWithExAndCreative(200,this.categoryIds);
		if(articleList!=null && articleList.size()>0){
			for(ArticleWithExAndCreative article:articleList){
				article.setContent(StringUtil.replaceBrTagToPTag(article.getContent()));
			}
		}
		return SUCCESS;
	}
	
	private List<ArticleWithExAndCreative> articleList;
	
	private Long categoryId;
	
	private String categoryIds;
	
	private Date today=new Date();
	
	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	

	public List<ArticleWithExAndCreative> getArticleList() {
		return articleList;
	}

	public void setArticleList(List<ArticleWithExAndCreative> articleList) {
		this.articleList = articleList;
	}

	public Date getToday() {
		return today;
	}

	public void setToday(Date today) {
		this.today = today;
	}

	public String getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(String categoryIds) {
		this.categoryIds = categoryIds;
	}
	
	public static void main(String[] args) {
		//String aa="曾<br/>你好<br/>几何时，书法不再是老年人消磨退休生活的主要休闲方式，而出现在现代都市白领的生活里，成为修身养性的新流行。这是一种动静结";
		String aa="曾你好几何时，书法不再是老<br/>年人消磨退休生活的主要休闲方式，而出现在现代都市白领的生活里，成为修身养性的新流行。这是一种动静结<br/>";
		aa=aa.replaceAll("<br/>", "</p><p>");
		System.out.println(aa);
		int pos=aa.lastIndexOf("<p>");
		String aa1=aa.substring(0, pos);
		String aa2=aa.substring(pos+3);
		System.out.print("<p>"+aa1+aa2);
		System.out.println(StringUtil.replaceBrTagToPTag(aa));
	}
	

	

}
