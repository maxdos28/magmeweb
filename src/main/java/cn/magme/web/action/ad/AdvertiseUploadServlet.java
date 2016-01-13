/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.ad;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.magme.common.SystemProp;
import cn.magme.constants.PojoConstant;
import cn.magme.util.StringUtil;

public class AdvertiseUploadServlet extends HttpServlet {
	
	private static final Logger log=Logger.getLogger(AdvertiseUploadServlet.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 9087483307302248082L;

	/**
	 * Constructor of the object.
	 */
	public AdvertiseUploadServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}
	private String adLocalUrl="/mnt/static/ad";
	private String interactiveContentLocalUrl="/mnt/static/interactive";
	private static final String JPG_PATH="jpg";
	private static final String VIDEO_PATH="video";
	
	public void init(ServletConfig config) throws ServletException {
		ApplicationContext appctx = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
		SystemProp systemProp=appctx.getBeansOfType(SystemProp.class).get("systemProp");
		this.adLocalUrl=systemProp.getAdLocalUrl();
		this.interactiveContentLocalUrl=systemProp.getInteractiveContentLocalUrl();
	}
	 
	@SuppressWarnings("rawtypes")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//AdUser adUser=(AdUser)request.getSession().getAttribute(WebConstant.SESSION.ADUSER);
		String isInteractive=request.getParameter("isInteractive");// 1表示互动广告
		log.info("上传广告文件开始");
		String fileName=request.getParameter("fileName");
		int adType=Integer.valueOf(request.getParameter("adType"));
		long userId=Long.parseLong(request.getParameter("userId"));
		String filePath=null;
		File adFileDir=null;
		String serverPath="";
		//互动内容
		if(StringUtil.isNotBlank(isInteractive) && isInteractive.equalsIgnoreCase("1")){
			filePath=interactiveContentLocalUrl+File.separator;
			serverPath=filePath+userId;
			adFileDir=new File(filePath+userId);
			
		}else{
		//广告
			if(adType==PojoConstant.ADVERTISE.ADTYPE.IMAGE.getCode()){
				filePath=adLocalUrl+File.separator+JPG_PATH+File.separator;
				serverPath=JPG_PATH+File.separator+userId;
				adFileDir=new File(filePath+userId);
			}else if(adType==PojoConstant.ADVERTISE.ADTYPE.VEDIO.getCode() || adType==PojoConstant.ADVERTISE.ADTYPE.INSERT.getCode()){
				filePath=adLocalUrl+File.separator+VIDEO_PATH+File.separator;
				serverPath=VIDEO_PATH+File.separator+userId;
				adFileDir=new File(filePath+userId);
			}
		}
		
		String tmpFileName=System.currentTimeMillis()+fileName.substring(fileName.lastIndexOf("."));
		serverPath+=File.separator+"tmp"+File.separator+tmpFileName;
        File jpgFile=new File(adFileDir.getAbsolutePath()+File.separator+"tmp"+File.separator+tmpFileName);
        File tmpFile=new File(adFileDir.getAbsolutePath()+File.separator+"tmp");
        if(!tmpFile.exists()){
         	tmpFile.mkdirs();
		}
         
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		DiskFileItemFactory factory  = new DiskFileItemFactory();
		factory.setSizeThreshold(4096);
		ServletFileUpload sfu = new ServletFileUpload(factory);
		try
		{
			List fileItems = sfu.parseRequest(request);
			Iterator iter = fileItems.iterator();
			while( iter.hasNext() )
			{
				FileItem item = (FileItem) iter.next();
				
				if( !item.isFormField() )
				{
					String name = item.getName();
					System.out.println( name );
					try
					{
						item.write( jpgFile ) ;
					}catch( Exception e){
						log.error("", e);
						e.printStackTrace();
					}
					
				 } 
			} 
			log.info("上传广告文件结束");
			PrintWriter writer=response.getWriter();
			writer.print(serverPath);
		}catch(FileUploadException e){
			log.error("", e);
			e.printStackTrace();
		}
	}
 

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		 this.doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
