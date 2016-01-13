package cn.magme.web.action.page.d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.constants.PojoConstant.COMMON;
import cn.magme.pojo.HomePageItem;
import cn.magme.pojo.PageD;
import cn.magme.pojo.Publication;
import cn.magme.pojo.sns.Creative;
import cn.magme.result.sns.PublicResult;
import cn.magme.service.HomePageItemService;
import cn.magme.service.PageDService;
import cn.magme.service.PublicationService;
import cn.magme.service.sns.CreativeService;
import cn.magme.service.sns.SnsPublicService;
import cn.magme.web.action.BaseAction;

@Results({@Result(name="success",location="/WEB-INF/pages/paged/pageDNav.ftl"),
		@Result(name = "detail", location = "/WEB-INF/pages/paged/pageDDetail.ftl") })
public class PageDAction extends BaseAction {

	/** 序列化id */
	private static final long serialVersionUID = 5152348631029266091L;
	/** 主题导航页的（热门推荐）首页元素查询常量 */
	private static final int NAV_HOME_PAGE_ITEM_BEGIN = 0;
	private static final int NAV_HOME_PAGE_ITEM_SIZE = 10;
	/** 主题详情页 首页元素的查询常量 */
	private static final int DETAIL_HOME_PAGE_ITEM_BEGIN = 0;
	private static final int DETAIL_HOME_PAGE_ITEM_SIZE = 30;
	
	private static final String ALL_LETTERS = "abcdefghijklmnopqrstuvwxyz";

	@Resource
	private PageDService pageDService;
	@Resource
	private HomePageItemService homePageItemService;
	@Resource
	private CreativeService creativeService;
	@Resource
	private PublicationService publicationService;
	@Resource
	private SnsPublicService snsPublicService;
	
	private String letter;
	
	private Map<String, List<PageD>> pageDMap;

	private Map<String, List<HomePageItem>> homePageItemMap;
	private List<HomePageItem> homePageItemList;
	private List<PageD> pageDList;
	private List<Creative> creativeList;
	private List<Publication> publications;
	private List<PublicResult> publicCreative;
	
	private Long id;
	private PageD pageD;
	
	public String pageDDetail() throws Exception {
		homePageItemList = new ArrayList<HomePageItem>();
		pageD = pageDService.queryById(id);
		if(pageD != null){
			String indexDesc = pageD.getIndexDesc();
			indexDesc = indexDesc.replaceAll("\\r\\n", "<br/>");
			indexDesc = indexDesc.replaceAll("\\n", "<br/>");
			pageD.setIndexDesc(indexDesc);
			String headerDesc = pageD.getHeaderDesc();
			headerDesc = headerDesc.replaceAll("\\r\\n", "<br/>");
			headerDesc = headerDesc.replaceAll("\\n", "<br/>");
			pageD.setHeaderDesc(headerDesc);
			String tags = pageD.getTags();
			if(tags != null && !"".equals(tags)){
				String[] tagList = tags.split(",");
				homePageItemList = homePageItemService.getHomePageItemListByTagList(tagList, true, DETAIL_HOME_PAGE_ITEM_BEGIN, DETAIL_HOME_PAGE_ITEM_SIZE);
			}
			String l = pageD.getFirstLetter().toLowerCase();
			int index = ALL_LETTERS.indexOf(l);
			int start = index;
			List<PageD> list = null;
			do{
				index = (index + 1) % 26;
				list = getPageDListByLetter(ALL_LETTERS.charAt(index) + "");
				if(list != null && list.size() > 0) 
					break;
			} while (index != start);
			pageDList = list;
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("pageDId", pageD.getId());
			creativeList = creativeService.getCreativeByCondition(param);
			
			publicCreative=snsPublicService.getDRightSidebarCreative();
		}
		return "detail";
	}
	
	
	@Override
	public String execute() throws Exception {
		List<PageD> list = getPageDListByLetter(letter);
		//主题列表，按category分类，key为原始分类名，并保存在map中
		//将每一类的主题名称作为标签查找HomePageItem列表
		Map<String, String> tags = new HashMap<String, String>();
		pageDMap = new HashMap<String, List<PageD>>();
		for (PageD d : list) {
			String tag = "";
			List<PageD> dlist = new ArrayList<PageD>();
			String categoryName = d.getCategoryName();
			if(tags.containsKey(categoryName))
				tag = tags.get(categoryName) + "_";
			if(pageDMap.containsKey(categoryName))
				dlist = pageDMap.get(categoryName);
			tag += d.getName();
			tags.put(categoryName, tag);
			dlist.add(d);
			pageDMap.put(categoryName, dlist);
		}
		//热门推荐列表，key为原始分类名
		homePageItemMap = new HashMap<String, List<HomePageItem>>();
		Set<String> keySet = tags.keySet();
		for (String cname : keySet) {
			List<HomePageItem> items = new ArrayList<HomePageItem>();
			String str = tags.get(cname);
			if(str != null && !"".equals(str)){
				String[] tagList = str.split("_");
				items = homePageItemService.getHomePageItemListByTagList(tagList, true, NAV_HOME_PAGE_ITEM_BEGIN, NAV_HOME_PAGE_ITEM_SIZE);
			}
			homePageItemMap.put(cname, items);
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("letter", letter);
		publications = publicationService.queryByFirstLetter(param);
		return SUCCESS;
	}

	/**
	 * 获取指定首字母的所有主题列表
	 * @param letter
	 * @return
	 */
	private List<PageD> getPageDListByLetter(String letter) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(letter == null || "".equals(letter) || letter.length() > 1 || ALL_LETTERS.indexOf(letter.toLowerCase()) < 0)
			letter = "a";
		letter = letter.toLowerCase();
		map.put("firstLetter", letter);
		map.put("status", COMMON.STATUS_OK);
		List<PageD> list = pageDService.queryByCondition(map);
		return list;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}
	public Map<String, List<HomePageItem>> getHomePageItemMap() {
		return homePageItemMap;
	}
	public Map<String, List<PageD>> getPageDMap() {
		return pageDMap;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public PageD getPageD() {
		return pageD;
	}
	public List<HomePageItem> getHomePageItemList() {
		return homePageItemList;
	}
	public List<PageD> getPageDList() {
		return pageDList;
	}
	public List<Creative> getCreativeList() {
		return creativeList;
	}
	public void setPublications(List<Publication> publications) {
		this.publications = publications;
	}
	public List<Publication> getPublications() {
		return publications;
	}


	public String getLetter() {
		return letter;
	}


	public List<PublicResult> getPublicCreative() {
		return publicCreative;
	}
	
}
