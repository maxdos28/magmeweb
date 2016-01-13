package cn.magme.web.action.admin;

import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletResponse;
import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.apache.struts2.ServletActionContext;
import cn.magme.util.ExtPageInfo;

/**
 * 基类
 * 继承自cn.magme.web.action.BaseAction
 * @author guozheng
 * @date 2011-6-3
 * @version $id$
 */
public class BaseAction extends cn.magme.web.action.BaseAction {

	private static final long serialVersionUID = 9124702645190525702L;
	// 分页参数类
	protected ExtPageInfo page;
	// 界面被变更或被选中所有id
	protected String ids;
	// json string,contain items add or update from client
	// 页面返回信息
	protected String info;
	
	/**
	 * servlet response print
	 * @param s
	 */
	protected void print(String s) {
		try {
			ServletActionContext.getResponse().getWriter().print(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * convert string array to long array
	 * 
	 * @param strArr
	 * @return
	 */
	protected Long[] strArrToLongArr(String[] strArr) {
		Long[] idArr = new Long[strArr.length];
		for (int i = 0; i < strArr.length; i++) {
			idArr[i] = Long.valueOf(strArr[i]);
		}
		return idArr;
	}

	/**
	 * convert json string to java array
	 * 
	 * @param jsonString
	 * @param clazz
	 * @return
	 */
	protected Object[] toJavaArr(String jsonString, Class clazz) {
		//设置日期的格式
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[]{"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd"}));
		JSONArray array = JSONArray.fromObject(jsonString);
		Object[] obj = new Object[array.size()];
		for (int i = 0; i < array.size(); i++) {
			JSONObject jsonObject = array.getJSONObject(i);			
			obj[i] = JSONObject.toBean(jsonObject, clazz);
		}
		return obj;
	}
	
    protected void excel(String[] titles,String text,String fileNm) throws IOException {
        HttpServletResponse response = ServletActionContext.getResponse();
        StringBuilder sb = new StringBuilder();
        //设置Excel头部信息
        sb.append(setExcelHeader());
        //第一行标题信息
        sb.append("<table><tr>").append(setTitle(titles)).append("</tr>" );
        
        //获得需要导出的数据
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        
        //展示数据的拼装
        sb.append(text);
        sb.append("</table>");
        
        // 设置Excel尾部信息
        sb.append(setExcelFoot());
        // 导出的Excel
        String time = sf.format(System.currentTimeMillis());
        response.addHeader("Content-Disposition","attachment;filename="+ 
   //             java.net.URLEncoder.encode(fileNm+time, "utf-8") +".xls");
        new String ((fileNm+time).getBytes("gb2312"),"iso-8859-1")+".xls");

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(sb.toString());
        response.flushBuffer();
    }
    
    /*
     * 设置Excel内容中的标题(第一行)
     * @return 返回多个单元格的数据(包括<td>标签和</td>标签)
     */
    private String setTitle(String[] titles){
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
    private final String setExcelHeader(){
        StringBuilder sb = new StringBuilder();
        sb.append("<html xmlns:x=\"urn:schemas-microsoft-com:office:excel\">");
        sb.append(" <head>");
        sb.append(" <!--[if gte mso 9]><xml>");
        sb.append("<x:ExcelWorkbook>");
        sb.append("<x:ExcelWorksheets>");
        sb.append("<x:ExcelWorksheet>");
        sb.append("<x:Name>报表</x:Name>");
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
    private final String setExcelFoot(){
        StringBuilder sb = new StringBuilder();
        sb.append("</body>");
        sb.append("</html>");
        return sb.toString();
    }
    
	/*public static void main(String[] args) {
		String info = "[{\"id\":\"13\",\"createdTime\":\"2011-05-23 15:48:57\",\"updatedTime\":\"2011-05-23 15:48:57\",\"lastLoginTime\":null,\"userName\":\"jacky5\",\"password\":\"96E79218965EB72C92A549DD5A330112\",\"nickName\":\"jacky5\",\"email\":\"hehe@hehe.com\",\"status\":\"0\",\"gender\":\"0\",\"birthdate\":\"2011-05-26T00:00:00\",\"occupation\":\"\",\"education\":\"\",\"hobbies\":\"\",\"phone\":\"\",\"astro\":\"\",\"bloodType\":\"\",\"address\":\"\",\"province\":\"\",\"city\":\"\",\"recuserId\":\"\",\"avatar\":\"\",\"reserve1\":\"\",\"reserve2\":\"\",\"reserve3\":\"\"}]";
		new BaseAction().toJavaArr(info, User.class);
	}*/

	/**
	 * 分页参数类取得
	 */
	public ExtPageInfo getPage() {
		return page;
	}

	/**
	 * 分页参数类设定
	 * @param page
	 */
	public void setPage(ExtPageInfo page) {
		this.page = page;
	}

	/**
	 * 界面被变更或被选中所有id取得
	 * @return
	 */
	public String getIds() {
		return ids;
	}
	
	/**
	 * 界面被变更或被选中所有id设定
	 * @param ids
	 */
	public void setIds(String ids) {
		this.ids = ids;
	}
	/**
	 * 页面返回信息取得
	 * @return
	 */
	public String getInfo() {
		return info;
	}

	/**
	 * 页面返回信息设定
	 * @param info
	 */
	public void setInfo(String info) {
		this.info = info;
	}

}
