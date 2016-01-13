/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.freemarker;

import java.util.List;

import cn.magme.util.StringUtil;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * 对头像进行缩放的文件名
 * @author fredy.liu
 * @date 2011-10-19
 * @version $id$
 */
public class AvatarResizeFreeMarker implements TemplateMethodModel{
	

	@Override
	public Object exec(List arguments) throws TemplateModelException {
		  if(arguments!=null&&arguments.size()>1){
	            try {
	                return StringUtil.prefix(String.valueOf(arguments.get(0)), arguments.get(1)+"_");
	            } catch (Exception e) {
	                e.printStackTrace();
	                return arguments.get(0)+"";
	            }  
	        }else{
	            return "";
	        }
	}

	
}
