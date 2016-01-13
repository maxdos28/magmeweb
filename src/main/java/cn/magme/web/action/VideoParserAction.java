/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action;

import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;

import cn.magme.common.JsonResult;
import cn.magme.util.StringUtil;

/**
 * @author jacky_zhou
 * @date 2011-8-1
 * @version $id$
 */
@SuppressWarnings("serial")
public class VideoParserAction extends BaseAction{

    public String handlerAjax(){
        if(StringUtil.isNotBlank(this.vid)){
            try {
                String url="http://www.tudou.com/programs/view/"+vid+"/";    
                //创建一个parser对象;  
                NodeList list = null;
                Parser parser=new Parser(url);  
                //设置字符编码格式;  
                parser.setEncoding("gbk");     
                list = parser.parse(null);
                String str = list.toHtml();
                int indexOfIIDStr =  str.indexOf("iid");
                String iid = str.substring(indexOfIIDStr+6, indexOfIIDStr+15);
                this.generateJsonResult(JsonResult.CODE.SUCCESS, JsonResult.MESSAGE.SUCCESS, "iid", iid);                
            } catch (Exception e) { 
                  e.printStackTrace();
            }     
        }
        
        if(this.jsonResult==null){
            this.generateJsonResult(JsonResult.CODE.FAILURE, "can't find vid");
        }
        
        return JSON;
    }
    
    private String vid;

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }
}
