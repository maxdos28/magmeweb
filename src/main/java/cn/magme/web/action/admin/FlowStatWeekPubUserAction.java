/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.admin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import cn.magme.service.FlowStatService;
import cn.magme.util.ToJson;

/**
 * @author shenhao
 * @date 2011-9-22
 * @version $id$
 * 用户可见
 */
@SuppressWarnings("serial")
public class FlowStatWeekPubUserAction extends BaseAction {
    @Resource
    private FlowStatService flowStatService;
    // 选中日期
    private String date;

    /**
     * 杂志周排行流量: 分页查询
     */
    public void page() {
        page = this.flowStatService.getPageByConditionPubWeek(page,getBetweenDate(),1);
        String info = ToJson.object2json(page);
        print(info);
    }
    
    // 以周为时间段
    private String getBetweenDate(){
        java.util.Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date tmpDate = null;
        try {
            tmpDate = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.setTime(tmpDate);
        int week=cal.get(Calendar.DAY_OF_WEEK);
        // 倒推到星期4的变量
        int[] intArr = new int[]{3,4,5,6,7,1,2};
        
        // 本周/上周4
        String endTime = modityDate(date,intArr[week-1]);
        // 上周/上上周4
        String beginTime = modityDate(endTime,6);
        return beginTime.replaceAll("-", "") + "-" + endTime.replaceAll("-", "");
    }
    
    // 日期减法
    private String modityDate(String time,int minus) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date tmpDate = null;
        try {
            tmpDate = df.parse(time);
        } catch(Exception e) {
            // 日期型字符串格式错误  
        }
        // 倒推到本周或上周四
        long nDay=(tmpDate.getTime()/(24*60*60*1000)+1-minus)*(24*60*60*1000);
        tmpDate.setTime(nDay);
        return df.format(tmpDate);
    }
    
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
