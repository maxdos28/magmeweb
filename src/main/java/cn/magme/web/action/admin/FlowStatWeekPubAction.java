/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.admin;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import cn.magme.pojo.Category;
import cn.magme.pojo.FeedBack;
import cn.magme.pojo.ext.ExtPubWeekStat;
import cn.magme.service.FlowStatService;
import cn.magme.util.ToJson;

/**
 * @author shenhao
 * @date 2011-9-20
 * @version $id$
 */
@SuppressWarnings("serial")
public class FlowStatWeekPubAction extends BaseAction {

	@Resource
	private FlowStatService flowStatService;

	// 选中日期
	private String date;
	// 一键设定系数
	private String parameter;
	
    /**
	 * 杂志周排行流量: 分页查询
	 */
	public void page() {
		page = this.flowStatService.getPageByConditionPubWeek(page,getBetweenDate());
		String info = ToJson.object2json(page);
		print(info);
	}
	
	/**
	 * 修改数据
	 */
	public void inputData() {
	    if (parameter != null) {
	        this.flowStatService.updateAllByPara(getBetweenDate(), parameter);
	    }else{
	        Object[] arr = super.toJavaArr(info, ExtPubWeekStat.class);
	        ExtPubWeekStat[] lst = castToPubWeekStat(arr);
	        this.flowStatService.update(lst);
	    }
	}
	
	/**
	 * 发布
	 */
	public void release() {
	    this.flowStatService.updateStatus(getBetweenDate());
	}
	
	public void outputExcel() throws IOException {
	    List<ExtPubWeekStat> list = this.flowStatService.output(getBetweenDate());
	    
        HttpServletResponse response = ServletActionContext.getResponse();
        String[] titles=new String[]{
                "杂志编号","杂志名","独立访问数(调整)","pv量(调整)",
                "排名(调整)","独立访问者","pv量","排名","周数"};
        StringBuilder sb = new StringBuilder();
        //设置Excel头部信息
        sb.append(setExcelHeader());
        //第一行标题信息
        sb.append("<table><tr>").append(setTitle(titles)).append("</tr>" );
            //获得需要导出的数据
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        
        System.out.println("===========要导出的数据条数============"+(list.equals("")?0:list.size()));
        //展示数据的拼装
        for (int i = 0; i < list.size(); i++) {
            ExtPubWeekStat extPubWeekStat=(ExtPubWeekStat)list.get(i);
                sb.append("<tr><td>");
                sb.append(extPubWeekStat.getPubId()==null?"":extPubWeekStat.getPubId()).append("</td><td>");
                sb.append(extPubWeekStat.getPubName()==null?"":extPubWeekStat.getPubName()).append("</td><td>");
                sb.append(extPubWeekStat.getMuCountM()).append("</td><td>");
                sb.append(extPubWeekStat.getPvCountM()).append("</td><td>");
                sb.append(extPubWeekStat.getRankM()).append("</td><td>");
                sb.append(extPubWeekStat.getMuCount()).append("</td><td>");
                sb.append(extPubWeekStat.getPvCount()).append("</td><td>");
                sb.append(extPubWeekStat.getRank()).append("</td><td>");
                sb.append(extPubWeekStat.getBetweenWeek()).append("</td></tr>");
        }
        sb.append("</table>");
        // 设置Excel尾部信息
        sb.append(setExcelFoot());
        // 导出的Excel
        String time = sf.format(System.currentTimeMillis());
        response.addHeader("Content-Disposition","attachment;filename=statWeek" + time +".xls");

//         response.addHeader("Content-Disposition","inline;filename=test.xls");
        // response.setContentType("application/octet-stream");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(sb.toString());
        response.flushBuffer();
	}
	
	
	   /*
     * 设置Excel内容中的标题(第一行)
     * @return 返回多个单元格的数据(包括<td>标签和</td>标签)
     */
    public String setTitle(String[] titles){
        StringBuilder sb = new StringBuilder("<td>");
        for (int i = 0; i < titles.length; i++) {
            String title = titles[i];
            sb.append(title);
            sb.append("</td><td>");
        }
        return sb.toString();
    }

    /*
     * 设置Excel头部信息
     */
    public final String setExcelHeader(){
        StringBuilder sb = new StringBuilder();
        sb.append("<html xmlns:x=\"urn:schemas-microsoft-com:office:excel\">");
        sb.append(" <head>");
        sb.append(" <!--[if gte mso 9]><xml>");
        sb.append("<x:ExcelWorkbook>");
        sb.append("<x:ExcelWorksheets>");
        sb.append("<x:ExcelWorksheet>");
        sb.append("<x:Name>排行</x:Name>");
        sb.append("<x:WorksheetOptions>");
        sb.append("<x:Print>");         
        sb.append("<x:ValidPrinterInfo />");
        sb.append(" </x:Print>");
        sb.append("</x:WorksheetOptions>");
        sb.append("</x:ExcelWorksheet>");
        sb.append("</x:ExcelWorksheets>");
        sb.append("</x:ExcelWorkbook>");
        sb.append("</xml>");
        sb.append("<![endif]-->");
        sb.append(" </head>");
        sb.append("<body>");
        return sb.toString();
    }

    /*
     * 设置Excel尾部信息
     */
    public final String setExcelFoot(){
        StringBuilder sb = new StringBuilder();
        sb.append("</body>");
        sb.append("</html>");
        return sb.toString();
    }
	
    /**
     * 转换数据类型Object[] => ExtPubWeekStat[]
     * @param arr
     * @return
     */
    private ExtPubWeekStat[] castToPubWeekStat(Object[] arr) {
        ExtPubWeekStat[] infos = new ExtPubWeekStat[arr.length];
        for (int i = 0; i < infos.length; i++) {
            infos[i] = (ExtPubWeekStat) arr[i];
        }
        return infos;
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
	
    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
}
