/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.publish;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;

import cn.magme.pojo.IssueStatCvTmpI;
import cn.magme.pojo.IssueStatCvTmpIII;
import cn.magme.pojo.IssueStatCvTmpIIIState;
import cn.magme.pojo.IssueStatCvTmpII_Count;
import cn.magme.pojo.IssueStatCvTmpII_Time;
import cn.magme.pojo.IssueStatCvTmpISummary;
import cn.magme.service.IssueStatCvTmpService;
import cn.magme.util.ToJson;

/**
 * @author guozheng
 * @date 2011-6-9
 * @version $id$
 */
@SuppressWarnings("serial")
public class FlowShowAction extends cn.magme.web.action.BaseAction {
	private String fromDate;
	private String toDate;
	private int opt;
	private int rd;
	private Long issueId;
	@Resource
	private IssueStatCvTmpService issueStatCvTmpService;
	
	/**
	 * 获取数据I
	 */
	public void dataI() {
		if(issueId == null){
			print("");
			return;
		}
		if (opt == 0) { // 按月
            fromDate = fromDate.substring(0, 7);
            toDate = toDate.substring(0, 7);
        }
		
		if (rd == 0) { // 图表数据
			List<IssueStatCvTmpI> list = this.issueStatCvTmpService.getTmpI(
					fromDate, toDate, issueId, opt);
			String s = ToJson.list2json(list);
			//this.jsonResult = this.generateJsonResult(111, "message", "data", list);
			print(s);
			System.out.println(s);
		} else if (rd == 1) { // 面板数据
			List<IssueStatCvTmpISummary> list = this.issueStatCvTmpService
					.getTmpISummary(fromDate, toDate, issueId, opt);
			String s = ToJson.list2json(list);
			print(s);
			System.out.println(s);
		}
	}

	/**
	 * 获取数据II_Count
	 */
	public void dataIICount() {
		if(issueId == null) {
			print("");
			return;
		}
		if (opt == 0) {
			fromDate = fromDate.substring(0, 7);
			toDate = toDate.substring(0, 7);
		}
		
		
		List<IssueStatCvTmpII_Count> list = this.issueStatCvTmpService.getTmpIICount(fromDate, toDate, issueId, opt);
		String s = ToJson.list2json(list);
		System.out.println(s);
		print(s);
	}
	
	/**
	 * 获取数据II_Time
	 */
	public void dataIITime(){
		if(issueId == null) {
			print("");
			return;
		}
		if (opt == 0) {
			fromDate = fromDate.substring(0, 7);
			toDate = toDate.substring(0, 7);
		} else if (opt == 1) {
		}
		List<IssueStatCvTmpII_Time> list = this.issueStatCvTmpService.getTmpIITime(fromDate, toDate, issueId, opt);
		String s = ToJson.list2json(list);
		System.out.println(s);
		print(s);
	}

	/**
	 * 获取数据III
	 */
	public void dataIII() {
		if(issueId == null) {
			print("");
			return;
		}
		if(opt == 0){
			fromDate = fromDate.substring(0, 7);
			toDate = toDate.substring(0, 7);
		} else if(opt == 1){
		}
		List<IssueStatCvTmpIIIState> list = this.issueStatCvTmpService.getTmpIIIState(fromDate, toDate, issueId, opt);
		List<IssueStatCvTmpIII> data = new ArrayList<IssueStatCvTmpIII>();
		Properties pro = new Properties();
		try {
			pro.load(this.getClass().getResourceAsStream("/state.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(IssueStatCvTmpIIIState is : list){
			String s = is.getState() + "：" + is.getCount();
			IssueStatCvTmpIII obj = new IssueStatCvTmpIII();
			obj.setTitle(s);
			// set mc_name,trim string,以防数据库拿出来的数据有空格
			obj.setMc_name(pro.getProperty(is.getState().trim()));
			data.add(obj);
		}
		String s = ToJson.list2json(data);
		System.out.println(s);
		print(s);
		pro.clear();
	}
	
	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public int getOpt() {
		return opt;
	}

	public void setOpt(int opt) {
		this.opt = opt;
	}

	public int getRd() {
		return rd;
	}

	public void setRd(int rd) {
		this.rd = rd;
	}

	public Long getIssueId() {
		return issueId;
	}

	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}

    private void print(String s) {
        try {
            ServletActionContext.getResponse().getWriter().print(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
