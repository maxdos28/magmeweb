package cn.magme.web.util;

import java.io.File;

public class CopyWebXmlDsConfigure {
	
	private String appPath="";
		
	public CopyWebXmlDsConfigure(String appPath){
		this.appPath=appPath;
	}
	
	public void copyRes(){
		System.out.println("start copy web.xml");
		//copy web.xml
    	File webxmlFile=new File(appPath+File.separator+"WEB-INF"+File.separator+"web.xml");
    	File webxmlbonlineFile=new File(webxmlFile.getParentFile().getAbsolutePath()+File.separator+"web.xml.online");
    	webxmlFile.delete();
    	webxmlbonlineFile.renameTo(webxmlFile);
    	System.out.println("end copy web.xml");
    	//copy ctx-ds.xml
    	System.out.println("start copy ctx-datasource.xml");
    	File dsxmlFile=new File(appPath+File.separator+"WEB-INF"+File.separator+"classes"
    	                +File.separator+"spring"+File.separator+"ctx-datasource.xml");
    	File dsxmlbonlineFile=new File(dsxmlFile.getParentFile().getAbsolutePath()+File.separator+"ctx-datasource.xml.online");
    	dsxmlFile.delete();
    	dsxmlbonlineFile.renameTo(dsxmlFile);
    	System.out.println("end copy ctx-datasource.xml");
    }

}
