/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.freemarker;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * 对中文参数进行编码
 * @author jacky_zhou
 * @date 2011-10-10
 * @version $id$
 */
public class EncodeMethodFreeMarker implements TemplateMethodModel {
    @SuppressWarnings({ "unchecked"})
    public Object exec(List args) throws TemplateModelException {
        if(args!=null&&args.size()>0){
            try {
                return URLEncoder.encode(args.get(0)+"","UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return args.get(0)+"";
            }  
        }else{
            return "";
        }
    }
    
    public static void main(String[] args) throws Exception{
        System.out.println(URLEncoder.encode("我的标签","UTF-8"));
    }
}
