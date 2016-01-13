/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.newpublisherstat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.pojo.Publication;
import cn.magme.pojo.stat.CommonDmChart;
import cn.magme.pojo.stat.CommonDmDotObj;
import cn.magme.pojo.stat.CommonDmLineObj;
import cn.magme.pojo.stat.DmStayedUser;
import cn.magme.service.PublicationService;
import cn.magme.service.stat.DmStayedUserService;
import cn.magme.util.xsl.XslUtil;
import cn.magme.web.action.BaseAction;

/**
 * @author billy.qi
 * @date 2012-9-4
 * @version $id$
 */
@SuppressWarnings("serial")
@Results({ @Result(name = "success", location = "/WEB-INF/pages/publish/dmstayeduser.ftl"),
	@Result(name="downloadsuccess",type="stream",params={"contentType","application/vnd.ms-excel","bufferSize","2048",
			"inputName","inputStream","contentDisposition","attachment;filename=\"exportData.xls\""}) })
public class DmStayedUserAction extends BaseAction {

	@Resource
	private DmStayedUserService dmStayedUserService;

	private static final Long SEARCH_DAY_LENGTH = 1000L * 60 * 60 * 24 * 31;

	private static final Logger log = Logger .getLogger(DmStayedUserAction.class);

	private Date startDate;
	private Date endDate;
	private Integer selectType;
	private Integer chartType;//图表类型（1：新用户/留存用户，2：留存率,3：表格）
	private List<Publication> pubList;
	private ByteArrayInputStream inputStream;

	private String publicationName;
	@Resource
	private PublicationService publicationService;

	public String editorExecute() {
		return execute();
	}
	public String execute() {
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String exportData(){
		this.pubJson();
		List<DmStayedUser> dataList = (List<DmStayedUser>) this.jsonResult.get("dataList");
		List<String> headers = new ArrayList<String>();
		headers.add("日期");
		headers.add("留存用户 ");
		headers.add("新用户");
		headers.add("留存率");

		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		List<List<String>> contents = new ArrayList<List<String>>();
		for(DmStayedUser data : dataList){
			List<String> rowContent = new ArrayList<String>();
			rowContent.add(f.format(data.getDate()));
			rowContent.add("" + data.getStayedUserNum());
			rowContent.add("" + data.getNewUserNum());
			rowContent.add("" + data.getRate() * 100);
			contents.add(rowContent);
		}

		HSSFWorkbook wb = XslUtil.exportToExcel(headers, contents);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			wb.write(baos);
		} catch (IOException e) {
			log.error("",e);
		}
		byte[] ba = baos.toByteArray();
		this.inputStream = new ByteArrayInputStream(ba);
		return "downloadsuccess";
	}
	public String pubJson() {
		this.jsonResult = JsonResult.getFailure();
		try {
			Long publisherId = this.getSessionPublisherId();
			if (publicationName != null) {
				Publication publication = publicationService.queryByPublicationName(publicationName);
				if (publication != null) {
					publisherId = publication.getPublisherId();
				}
			}
			if (publisherId == null || publisherId <= 0) {
				this.jsonResult.setMessage("没有publisherId");
				return JSON;
			}
			// 判断日期，只能查看15天内数据，并且不能查看5月1日之前的数据
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, 2012);
			cal.set(Calendar.MONTH, 4);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			Date today = new Date();
			// 结束日期不能小于2012-05-01
			if (endDate == null || this.endDate.getTime() < cal.getTimeInMillis()) {
				this.endDate = today;
			}
			// 时间间隔不能超过15天
			if (this.startDate == null
					|| this.endDate.getTime() - this.startDate.getTime() > SEARCH_DAY_LENGTH) {
				this.startDate = new Date(this.endDate.getTime() - SEARCH_DAY_LENGTH);
			}
			// 查看统计的最早时间不能小于2012-05-01
			if (this.startDate.getTime() < cal.getTime().getTime()) {
				this.startDate = cal.getTime();
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("startDate", startDate);
			param.put("endDate", endDate);
			param.put("selectType", selectType);
			param.put("publisherId", publisherId);
			List<DmStayedUser> list = this.dmStayedUserService .queryByCondition(param);
			
			if (list != null && list.size() >= 0) {
				this.jsonResult = JsonResult.getSuccess();
				if(chartType == 3){
					this.jsonResult.put("dataList", list);
				} else {
					this.jsonResult.put("dataList", buildChartObj(list));
				}
			}
		} catch (Exception e) {
			log.error("", e);
		}
		return JSON;
	}
	
	private static final Integer BUILD_TYPE_NEW_USER = 1;
	private static final Integer BUILD_TYPE_STAYED_USER = 2;
	private static final Integer BUILD_TYPE_RATE = 3;
	/**
	 * 构建图表
	 * @param list
	 * @return
	 */
	private CommonDmChart buildChartObj (List<DmStayedUser> list){
		if(list == null || list.isEmpty()) return null;
		CommonDmChart c = new CommonDmChart();
		List<CommonDmLineObj> lines = new ArrayList<CommonDmLineObj>();
		c.sethName("日期");
		if(chartType == 1){
			c.setvName("人");
			c.setTitle("新用户/留存用户");
			lines.add(buildLineObj(list, "新用户", BUILD_TYPE_NEW_USER));
			lines.add(buildLineObj(list, "留存用户", BUILD_TYPE_STAYED_USER));
		} else {
			c.setvName("%");
			c.setTitle("留存率");
			lines.add(buildLineObj(list, "留存率", BUILD_TYPE_RATE));
		}
		c.setLineList(lines);
		return c;
	}
	/**
	 * 构建一条曲线图
	 * @param list
	 * @param lineName
	 * @param type
	 * @return
	 */
	private CommonDmLineObj buildLineObj(List<DmStayedUser> list, String lineName, Integer type) {
		CommonDmLineObj line = new CommonDmLineObj();
		line.setLineName(lineName);
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		List<CommonDmDotObj> objs = new ArrayList<CommonDmDotObj>();
		Calendar c = Calendar.getInstance();
		c.setTime(startDate);
		Calendar end = Calendar.getInstance();
		end.setTime(endDate);
		int index = 0;
		while(c.before(end) || c.equals(end)){//遍历日期，构建曲线的数据点
			CommonDmDotObj dot = new CommonDmDotObj();
			//x轴
			dot.setDotName(f.format(c.getTime()));
			//x轴index
			dot.setIndex(index++);
			//是否周末
			int weekDay = c.get(Calendar.DAY_OF_WEEK);
			dot.setSp(weekDay == Calendar.SUNDAY || weekDay == Calendar.SATURDAY ? 1 : 0 );
			//value
			boolean hasValue = false;
			for (DmStayedUser user : list) {
				Date date = user.getDate();
				Calendar ca = Calendar.getInstance();
				ca.setTime(date);
				if(c.equals(ca)){
					//y轴的值
					if(type.equals(BUILD_TYPE_NEW_USER))//新用户
						dot.setValue(1.0 * user.getNewUserNum());
					else if(type.equals(BUILD_TYPE_STAYED_USER))//留存用户
						dot.setValue(1.0 * user.getStayedUserNum());
					else if(type.equals(BUILD_TYPE_RATE)){//留存率
						int rate = (int)(user.getRate() * 10000);
						dot.setValue(rate / 100.0);
					}
					hasValue = true;
					break;
				}
			}
			if(!hasValue){
				dot.setValue(-1.0);
			}
			objs.add(dot);
			c.add(Calendar.DAY_OF_MONTH, 1);
		}
		line.setDotList(objs);
		return line;
	}

	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}
	public List<Publication> getPubList() {
		return pubList;
	}

	public void setPubList(List<Publication> pubList) {
		this.pubList = pubList;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setSelectType(Integer selectType) {
		this.selectType = selectType;
	}

	public Integer getSelectType() {
		return selectType;
	}

	public void setChartType(Integer chartType) {
		this.chartType = chartType;
	}

	public Integer getChartType() {
		return chartType;
	}
	/**
	 * @param publicationName the publicationName to set
	 */
	public void setPublicationName(String publicationName) {
		this.publicationName = publicationName;
	}
	/**
	 * @return the publicationName
	 */
	public String getPublicationName() {
		return publicationName;
	}

}
