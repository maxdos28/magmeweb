package cn.magme.web.freemarker;

import java.util.List;

import cn.magme.util.StringUtil;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

public class StringSubFreeMarker implements TemplateMethodModel{

	@Override
	public Object exec(List arg) throws TemplateModelException {
		String str=(String) arg.get(0);//需要更改参数
		Integer len= Integer.valueOf((String) arg.get(1));//修改长度  1中文2长度
		return StringUtil.subString(str, len);
	}

}
