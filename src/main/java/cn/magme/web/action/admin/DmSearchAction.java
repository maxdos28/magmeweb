/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.admin;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;
import cn.magme.pojo.stat.StatDmPagenator;
import cn.magme.result.stat.DmSearchResult;
import cn.magme.service.stat.DmSearchService;
import cn.magme.util.ToJson;

/**
 * @author shenhao
 * @date 2012-4-6
 * @version $id$
 */
@SuppressWarnings("serial")
public class DmSearchAction extends BaseAction{

    @Resource
    private DmSearchService dmSearchService;
    
    private StatDmPagenator statDmPagenator;
    /**
     * 分页查询
     */
    public void page() {
        statDmPagenator.setPagesize(null);
        statDmPagenator.setStartRow(null);
        List<DmSearchResult> totalLst = this.dmSearchService.queryByPagenator(statDmPagenator);
        
        statDmPagenator.setStartRow(Integer.parseInt(String.valueOf(page.getStart())));
        statDmPagenator.setPagesize(Integer.parseInt(String.valueOf(page.getLimit())));
        List<DmSearchResult> lst = this.dmSearchService.queryByPagenator(statDmPagenator);
        page.setTotal(totalLst.size());
        page.setData(lst);
        String info = ToJson.object2json(page);
        print(info);
    }
    
    public void outputExcel() throws IOException {
        statDmPagenator.setStartRow(null);
        statDmPagenator.setPagesize(null);
        List<DmSearchResult> list = this.dmSearchService.queryByPagenator(statDmPagenator);
        StringBuilder sb = new StringBuilder();
        //展示数据的拼装
        for (int i = 0; i < list.size(); i++) {
            DmSearchResult pro = (DmSearchResult)list.get(i);
                sb.append("<tr><td>");
                sb.append(pro.getKeyword()==null?"":URLDecoder.decode(pro.getKeyword(),"utf-8")).append("</td><td>");
                sb.append(pro.getSearchTimes()==null?"":pro.getSearchTimes()).append("</td></tr>");
        }
        
        excel(new String[]{"搜索名词","搜索次数"}, sb.toString(), "站内搜索报表");
    }
    
    public StatDmPagenator getStatDmPagenator() {
        return statDmPagenator;
    }

    public void setStatDmPagenator(StatDmPagenator statDmPagenator) {
        this.statDmPagenator = statDmPagenator;
    }
}
