package cn.magme.web.util;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JudgeStatDate {
	
	private static final Long MAX_DAY=1000L*60*60*24*31;//31天
	
	/**
	 * stat used valid date,if this date is not valid,we convert it to valid date.
	 * @param startDate
	 * @param endDate
	 * @return valid date after convert
	 */
	public static Map<String,Date> convertDateToValidDate(Date startDate,Date endDate){
		Map<String,Date> validDate=new HashMap<String,Date>();
		
		//判断日期，只能查看MAX_DAY天内数据，并且不能查看5月1日之前的数据
		Calendar cal=Calendar.getInstance();
		cal.set(Calendar.YEAR, 2012);
		cal.set(Calendar.MONTH, 4);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		Date today=new Date();
		//结束日期不能小于2012-05-01
		if(endDate==null || endDate.getTime()<cal.getTimeInMillis()){
			endDate=today;
		}
		//时间间隔不能超过MAX_DAY天
		if(startDate==null || endDate.getTime()-startDate.getTime()>MAX_DAY){
			startDate=new Date(endDate.getTime()-MAX_DAY);
		}
		//查看统计的最早时间不能小于2012-05-01
		if(startDate.getTime()<cal.getTime().getTime()){
			startDate=cal.getTime();
		}
		validDate.put("startDate", startDate);
		validDate.put("endDate", endDate);
		return validDate;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
