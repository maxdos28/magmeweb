package cn.magme.web.freemarker;

import java.util.List;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

public class CreativeResizeFreeMarker implements TemplateMethodModel {

	@Override
	public Object exec(List arg) throws TemplateModelException {
		if(arg.get(1).equals("") || arg.get(2).equals(""))
			return "";
		String tp=(String) arg.get(0);//反回类型 w:宽 h:高
		Integer w= Integer.valueOf((String) arg.get(1));
		Integer h= Integer.valueOf((String) arg.get(2));
		Integer s= Integer.valueOf((String) arg.get(3));
		return calculate(tp, w, h, s);
	}
	
	/**
	 * 
	 * @param tp 返回操作类型
	 * @param imgWidth 图片宽度
	 * @param imgHeight 图片高度
	 * @param size 操作图片大小 如 100 ,500
	 * @return
	 */
	private Integer calculate(String tp,Integer imgWidth,Integer imgHeight,Integer size){
		int height=0;
		int width=0;
		if(imgWidth<imgHeight){
				double wf=(double)imgWidth/(double)size;
    		height=(int) (imgHeight/wf);
    		width=(int) (imgWidth/wf);
   }else{
    		double wf=(double)imgWidth/(double)size;
    		width=size;
    		height=(int) (imgHeight/wf);
    	}
		if(tp.equals("h"))
			return height;
		else
			return width;
	}

}
