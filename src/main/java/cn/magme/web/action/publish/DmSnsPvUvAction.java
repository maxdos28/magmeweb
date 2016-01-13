package cn.magme.web.action.publish;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.constants.WebConstant;
import cn.magme.pojo.Publication;
import cn.magme.result.stat.SnsPvChartListLineResult;
import cn.magme.result.stat.SnsPvTableResult;
import cn.magme.result.stat.StatPieChart;
import cn.magme.service.PublicationService;
import cn.magme.service.stat.DmSnsPvUvService;
import cn.magme.service.stat.impl.DmVisitDevicePvUvServiceImpl;
import cn.magme.util.ExpressDate;
import cn.magme.web.action.BaseAction;
@Results({@Result(name="success",location="/WEB-INF/pages/newPublisherStat/dmsnspvuv.ftl")})
public class DmSnsPvUvAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2700425007895729109L;
	
	private static final Logger log=Logger.getLogger(DmSnsPvUvAction.class);
	
	@Resource
	private DmSnsPvUvService dmSnsPvUvService;
	@Resource
	private DmVisitDevicePvUvServiceImpl dmVisitDevicePvUvServiceImpl;
	
	@Resource
	private PublicationService publicationService;
	
	public String execute(){
		Map<String,Date> dateMap=ExpressDate.judgeStatDate(startDate, endDate);
		this.startDate=dateMap.get("startDate");
		this.endDate=dateMap.get("endDate");
		pubList=publicationService.queryNormalByPublisherId(this.getSessionAdUserId());
		if(this.publicationId==null || this.publicationId<=0){
			if(pubList==null || pubList.size()<=0){
				log.error("no parameter publicationid,and we can not get the publicationid from db,method:DmSnsPvUvAction.execute");
				return SUCCESS;
			}
			this.publicationId=pubList.get(0).getId();
		}
		this.snspvuvJson();
		return SUCCESS;
	}
	
	public String snspvuvJson(){
		this.jsonResult=this.generateJsonResult(JsonResult.CODE.FAILURE, JsonResult.MESSAGE.FAILURE);
		if(this.publicationId==null || this.publicationId<=0){
			pubList=publicationService.queryNormalByPublisherId(this.getSessionAdUserId());
			if(pubList==null || pubList.size()<=0){
				log.error("no parameter publicationid,and we can not get the publicationid from db,method:DmSnsPvUvAction.execute");
				return SUCCESS;
			}
			this.publicationId=pubList.get(0).getId();
		}
		statPieChartList=dmSnsPvUvService.queryByDateAndPubId(startDate, endDate, publicationId);
		
		//表形报表
		snsPvTable = new ArrayList<SnsPvTableResult>();
		
		//计算两个时间差
		SimpleDateFormat sd =new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance(); 
		long diff;
		long nd = 1000*24*60*60;
		diff = endDate.getTime() - startDate.getTime();
		long day = diff/nd;
		
		//获取线形图数据
		statPieChartListLine = new ArrayList<SnsPvChartListLineResult>();
		for (String platform:WebConstant.DEVICE.PLATFORM) {
			if(platform.equals("360"))
				platform=WebConstant.DEVICE.PLATFORM360;
			else
				platform="'"+platform.toLowerCase()+"'";
			//线形数据
			List<StatPieChart> list = dmSnsPvUvService.queryByPlatform(startDate, endDate, publicationId, platform, "flase");
			//表形数据 (局部PV)
			List<StatPieChart> tempTabel = dmSnsPvUvService.queryByPlatform(startDate, endDate, publicationId, platform, "");
			//表形数据 (全部PV)
			List<StatPieChart> tempTabel2 = dmSnsPvUvService.queryByPlatform(null, null, publicationId, platform, "");
			
			if(list==null)list= new ArrayList<StatPieChart>();
			List<StatPieChart> spc =  new ArrayList<StatPieChart>(); 
			
			//数据填充
			for (int i = 0; i <= day; i++) {
				cal.setTime(startDate);
				cal.add(Calendar.DATE, i);
				String dtstr = sd.format(cal.getTime());
				int flag=-1;
				for (int j=0;j<list.size();j++) {
					StatPieChart stat=list.get(j);
					if(stat.getName().equals(dtstr))
						flag=j;
				}
				if(flag==-1){
					StatPieChart s= new StatPieChart();
					s.setName(dtstr);
					s.setValue(0.0);
					spc.add(s);
				}else
					spc.add(list.get(flag));
					
			}
			if(platform.equals(WebConstant.DEVICE.PLATFORM360))
				platform="360";
			
			SnsPvChartListLineResult snsPvChartListLineResult = new SnsPvChartListLineResult();
			//线形数据
			snsPvChartListLineResult.setDevice(platform.replace("'", ""));
			snsPvChartListLineResult.setList(spc);
			statPieChartListLine.add(snsPvChartListLineResult);
			
			//表形数据 装载
			SnsPvTableResult sptr = new SnsPvTableResult();
			Integer item =0;
			Integer total=0;
			String rate="0";
			if(tempTabel.get(0).getValue()!=null)
				item=tempTabel.get(0).getValue().intValue();
			if(tempTabel2.get(0).getValue()!=null)
				total=tempTabel2.get(0).getValue().intValue();
			if(item!=0)
				if(total!=0){
					String temp =String.valueOf((item.floatValue()/total.floatValue())*100);
					rate=temp.substring(0,temp.lastIndexOf(".")+2);//new DecimalFormat("#.0").format((item.floatValue()/total.floatValue())*100);
				}
					
				else
					rate=new DecimalFormat("#.0").format(item*100);
			sptr.setDevice(platform.replace("'", ""));
			sptr.setItem(item);
			sptr.setTotal(total);
			sptr.setRate(rate);
			snsPvTable.add(sptr);
			
		}
		
		//移动设备下数据
		for (String deviceName:WebConstant.DEVICE.DEVICENAME) {
			String device="";
			if(deviceName.equals("IOS"))
				device=WebConstant.DEVICE.IOS;
			else if(deviceName.equals("ANDRIOD"))
				device=WebConstant.DEVICE.ANDROID;
			
			List<StatPieChart> list = dmVisitDevicePvUvServiceImpl.queryByDateAndPubId(startDate, endDate, publicationId, deviceName, device);
			if(list!=null){
				if(list.get(0).getValue()==null)list.get(0).setValue(0.0);
				statPieChartList.add(list.get(0));
			}
			//移动设备下线形数据
			List<StatPieChart> Line = dmVisitDevicePvUvServiceImpl.queryByPlatform(startDate, endDate, publicationId, device, "flase");
			//表形数据 (局部PV)
			List<StatPieChart> tempTabel = dmVisitDevicePvUvServiceImpl.queryByPlatform(startDate, endDate, publicationId, device, "");
			//表形数据 (全部PV)
			List<StatPieChart> tempTabel2 = dmVisitDevicePvUvServiceImpl.queryByPlatform(null, null, publicationId, device, "");
			
			if(Line==null)Line= new ArrayList<StatPieChart>();
			List<StatPieChart> spc =  new ArrayList<StatPieChart>(); 
			
			//数据填充
			for (int i = 0; i <= day; i++) {
				cal.setTime(startDate);
				cal.add(Calendar.DATE, i);
				String dtstr = sd.format(cal.getTime());
				int flag=-1;
				for (int j=0;j<Line.size();j++) {
					StatPieChart stat=Line.get(j);
					if(stat.getName().equals(dtstr))
						flag=j;
				}
				if(flag==-1){
					StatPieChart s= new StatPieChart();
					s.setName(dtstr);
					s.setValue(0.0);
					spc.add(s);
				}else
					spc.add(Line.get(flag));
					
			}
			if(deviceName.equals("IOS"))
				device="IOS";
			else if(deviceName.equals("ANDRIOD"))
				device="ANDRIOD";
			SnsPvChartListLineResult snsPvChartListLineResult = new SnsPvChartListLineResult();
			snsPvChartListLineResult.setDevice(device);
			snsPvChartListLineResult.setList(spc);
			statPieChartListLine.add(snsPvChartListLineResult);
			
			//表形数据 装载
			SnsPvTableResult sptr = new SnsPvTableResult();
			Integer item =0;
			Integer total=0;
			String rate="0";
			if(tempTabel.get(0).getValue()!=null)
				item=tempTabel.get(0).getValue().intValue();
			if(tempTabel2.get(0).getValue()!=null)
				total=tempTabel2.get(0).getValue().intValue();
			if(item!=0)
				if(total!=0)
					rate=new DecimalFormat("#.0").format((item.floatValue()/total.floatValue())*100);
				else
					rate=new DecimalFormat("#.0").format(item*100);
			sptr.setDevice(device);
			sptr.setItem(item);
			sptr.setTotal(total);
			sptr.setRate(rate);
			snsPvTable.add(sptr);
		}
		
		if(statPieChartList!=null && statPieChartList.size()>0){
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			this.jsonResult.put("statPieChartList", statPieChartList);
			this.jsonResult.put("statPieChartListLine", statPieChartListLine);
			this.jsonResult.put("snsPvTable", snsPvTable);
			
		}
		return JSON;
	}
	
	private String order;
	
	private Date startDate;
	
	private Date endDate;
	
	private List<StatPieChart> statPieChartList;
	
	private List<SnsPvChartListLineResult> statPieChartListLine;
	
	private List<SnsPvTableResult> snsPvTable;

	private Long publicationId;
	
	private List<Publication> pubList;

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
	

	public Long getPublicationId() {
		return publicationId;
	}

	public void setPublicationId(Long publicationId) {
		this.publicationId = publicationId;
	}

	public List<StatPieChart> getStatPieChartList() {
		return statPieChartList;
	}

	public void setStatPieChartList(List<StatPieChart> statPieChartList) {
		this.statPieChartList = statPieChartList;
	}

	public List<Publication> getPubList() {
		return pubList;
	}

	public void setPubList(List<Publication> pubList) {
		this.pubList = pubList;
	}

	public List<SnsPvTableResult> getSnsPvTable() {
		return snsPvTable;
	}

	public void setSnsPvTable(List<SnsPvTableResult> snsPvTable) {
		this.snsPvTable = snsPvTable;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	
}
