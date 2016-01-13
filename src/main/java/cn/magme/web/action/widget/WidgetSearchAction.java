/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.widget;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.constants.PojoConstant.SORT;
import cn.magme.pojo.Issue;
import cn.magme.pojo.Sort;
import cn.magme.service.IssueService;
import cn.magme.service.LuceneService;
import cn.magme.service.SortService;
import cn.magme.util.NumberUtil;
import cn.magme.util.PageInfo;
import cn.magme.util.SearchIssueSorter;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

/**
 * @author qiaowei
 * @date 2011-7-14
 * @version $id$
 */
@Results({ @Result(name = "searchSuccess", location = "/WEB-INF/pages/widget/widgetSearch.ftl") })
public class WidgetSearchAction extends BaseAction {

    private final static String MAGZINE = "magzine";

    private String pageTitle;

    private String queryStr;

    private List<Sort> sortList;

    private Long sortId = -1l;
    private String xx;

    @Resource
    private IssueService issueService;

    @Resource
    private LuceneService luceneService;

    @Resource
    private SortService sortService;

    private PageInfo magPageInfo = new PageInfo();

    private static final long serialVersionUID = 272332340363998948L;

    private static final Logger log = Logger.getLogger(WidgetSearchAction.class);

    private static final String SEARCH_SUCCESS = "searchSuccess";

    public String execute() throws Exception {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setHeader("Cache-Control", "max-age=" + systemProp.getPageCacheTimeout());

        try {
            sortList = sortService.getListByGroup(SORT.GROUP_COMPUTER);
            
            if (StringUtil.isBlank(queryStr)) {
                log.error("搜索关键词为空");
                return SEARCH_SUCCESS;
            }

            queryStr = queryStr.trim();
            Long[] objIds = luceneService.seacher(queryStr, "Issue", null);
            objIds = NumberUtil.longFilter(objIds);
            
            List<Issue> magList = null;
            if (objIds != null && objIds.length > 0) {
                magList = new ArrayList<Issue>();
                for (Long objId : objIds) {
                    Issue issue = this.issueService.queryById(objId);
                    if (issue != null) {
                        magList.add(issue);
                    }
                }
                magList = SearchIssueSorter.sort(magList, StringUtil.containsNumber(queryStr));

                int totalSize = magList.size();
                magPageInfo.setData(magList);
                magPageInfo.setCurPage(1);
                magPageInfo.setLimit(15);
                magPageInfo.setTotal(totalSize);

                int totalPage = 0;
                if (totalSize % 15 == 0) {
                    totalPage = totalSize / 15;
                } else if (totalSize % 15 > 0) {
                    totalPage = totalSize / 15 + 1;
                }
                magPageInfo.setTotalPage(totalPage);
            }
            pageTitle = MAGZINE;

        } catch (Exception e) {
            log.error("", e);
        }

        return SEARCH_SUCCESS;
    }

    public String getQueryStr() {
        return queryStr;
    }

    public void setQueryStr(String queryStr) {
        this.queryStr = queryStr;
    }

    public PageInfo getMagPageInfo() {
        return magPageInfo;
    }

    public void setMagPageInfo(PageInfo magPageInfo) {
        this.magPageInfo = magPageInfo;
    }

    public List<Sort> getSortList() {
        return sortList;
    }

    public void setSortList(List<Sort> sortList) {
        this.sortList = sortList;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public Long getSortId() {
        return sortId;
    }

    public void setSortId(Long sortId) {
        this.sortId = sortId;
    }

	/**
	 * @param xx the xx to set
	 */
	public void setXx(String xx) {
		this.xx = xx;
	}

	/**
	 * @return the xx
	 */
	public String getXx() {
		return xx;
	}

}
