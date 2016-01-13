package cn.magme.web.action.sns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.constants.sns.SNSConstant;
import cn.magme.result.sns.CreativeListResult;
import cn.magme.service.LuceneService;
import cn.magme.service.sns.SnsCreativeService;
import cn.magme.util.HtmlParserUtil;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

/**
 * @author chenxiao
 * @date 2012-5-29
 * @version $id$
 */
@Results({
	@Result(name="sns",location="/WEB-INF/pages/sns/creativeList.ftl") 
})
public class SearchAction extends BaseAction {
	private static final long serialVersionUID = -8042580105712171542L;
	
	private String searchType;
    private String queryStr;
    private Integer begin;
    private List<CreativeListResult> creativeList;

    @Resource
    private LuceneService luceneService;
    @Resource
    private SnsCreativeService snsCreativeService;

    private static final Logger log = Logger.getLogger(SearchAction.class);

    public String execute() throws Exception {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setHeader("Cache-Control", "max-age=" + systemProp.getPageCacheTimeout());

        try {
            if (StringUtil.isBlank(queryStr)) {
                log.error("搜索关键词为空");
            }

            if (StringUtil.isBlank(searchType)) {
                log.error("搜索类型为空");
            }
            if(searchType.equals("sns")){
            	creativeList= new ArrayList<CreativeListResult>();
            	queryStr = queryStr.trim();
            	if(begin==null || begin<1)
            		begin=0;
                Long[] objIds = luceneService.seacherByPage(queryStr, "Creative", begin,SNSConstant.SNS_PAGE_SZIE);
                for (long c:objIds) {
                	Map<String, Object> map = new HashMap<String, Object>();
                	if(getSessionUserId()!=null)
                		map.put("u", getSessionUserId());
                	else
                		map.put("u", 0);
                	map.put("id", c);
                	CreativeListResult crl= snsCreativeService.getCreativeAllByLucene(map);
                	
                	String conTemp = crl.getContent();
					if(null!=conTemp && conTemp.length()>SNSConstant.CUT_SIZE)
						crl.setCut(true);
					else
						crl.setCut(false);
					String temp = HtmlParserUtil.parser(conTemp,SNSConstant.CUT_SIZE);
					crl.setContent(temp);
					
                	creativeList.add(crl);
				}
            }

        } catch (Exception e) {
            log.error("", e);
        }
        return "sns";
    }

    public void setSearchType(String searchType) {
		this.searchType = searchType;
	}


	public void setQueryStr(String queryStr) {
		this.queryStr = queryStr;
	}


	public void setBegin(Integer begin) {
		this.begin = begin;
	}

	public Integer getBegin() {
		return begin;
	}

	public List<CreativeListResult> getCreativeList() {
		return creativeList;
	}

}
