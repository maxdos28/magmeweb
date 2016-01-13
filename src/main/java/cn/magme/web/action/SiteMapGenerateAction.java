package cn.magme.web.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cn.magme.common.JsonResult;
import cn.magme.constants.PojoConstant;
import cn.magme.pojo.SysParameter;
import cn.magme.pojo.sns.Creative;
import cn.magme.service.SitemapGenerateService;
import cn.magme.service.SysParameterService;
import cn.magme.service.sns.SnsCreativeService;

/**
 * 
 * @author fredy
 * @since 2012-11-26
 */
public class SiteMapGenerateAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4356998982776227552L;
	@Resource
	private SnsCreativeService snsCreativeService;
	
	@Resource
	private SysParameterService sysParameterService;	
	
	@Resource
	private SitemapGenerateService sitemapGenerateService;
	
	private static final SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
	
	private static final Logger log=Logger.getLogger(SiteMapGenerateAction.class);
	
	public String execute(){
		this.jsonResult=JsonResult.getFailure();
		try {
			sitemapGenerateService.init();
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		} catch (Exception e) {
			log.error("", e);
		}
		return JSON;
	}
	
	/**
	 * 增量式的增加sitemap文件大小
	 * @return
	 */
	public String siteJson(){
		this.jsonResult=JsonResult.getFailure();
		SysParameter minCreativeParam=sysParameterService.queryByParamKey(PojoConstant.SYS_PARAMETER.SITEMAP_MAX_CREATIVE_ID);
		SysParameter currentPageParam=sysParameterService.queryByParamKey(PojoConstant.SYS_PARAMETER.SITEMAP_CURRENT_PAGE);
		int currentPage=Integer.valueOf(currentPageParam.getParamValue());
		List<Creative> cList=snsCreativeService.queryByMinId(Integer.valueOf(minCreativeParam.getParamValue()));
		
		//先写sitemap
		try {
			if(cList!=null && cList.size()>0){
				File f=new File(this.systemProp.getSiteMapPath()+File.separator+"/sitemap/sitemap_"+(++currentPage)+".xml");
				if(!f.exists()){
					f.createNewFile();
				}
				BufferedWriter bw=new BufferedWriter(new FileWriter(f));
				bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?><urlset>");
				for(int j=0;j<cList.size();j++){
					StringBuilder sb=new StringBuilder("");
					Creative c=cList.get(j);
					sb.append("<url><loc>http://www.magme.cn/sns/c");
					sb.append(c.getId()).append("/</loc><lastmod>");
					sb.append(df.format(c.getUpdateTime()));
					sb.append("</lastmod><changefreq>always</changefreq><priority>0.8</priority></url>");
					bw.write(sb.toString());
					bw.flush();
					if(j==cList.size()-1){
						bw.write("</urlset>\n");
						bw.close();
					}
				}
				bw.close();
			}
			
		} catch (Exception e) {
			log.error("", e);
		}
		
		//写入根
		try {
			if(cList!=null && cList.size()>0){
				DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
				DocumentBuilder builder=factory.newDocumentBuilder();
				Document doc=builder.parse(this.systemProp.getSiteMapPath()+File.separator+"sitemap.xml");
				doc.normalize();
				Element sitemapElement=doc.createElement("sitemap");
				
				Element locElement=doc.createElement("loc");
				Element lastmodElement=doc.createElement("lastmod");
				locElement.appendChild(doc.createTextNode(this.systemProp.getAppServerUrl()+"/sitemap/"+"sitemap_"+currentPage+".xml"));
				lastmodElement.appendChild(doc.createTextNode(df.format(new Date())));
				
				sitemapElement.appendChild(locElement);
				sitemapElement.appendChild(lastmodElement);
				
				doc.getDocumentElement().appendChild(sitemapElement);
				TransformerFactory tf = TransformerFactory.newInstance(); 
				Transformer transformer = tf.newTransformer(); 
				DOMSource source = new DOMSource(doc); 
				transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8"); 
				transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
				PrintWriter pw = new PrintWriter(new FileOutputStream(this.systemProp.getSiteMapPath()+File.separator+"sitemap.xml")); 
				StreamResult result = new StreamResult(pw); 
				transformer.transform(source, result); 
				
				//回写数据
				currentPageParam.setParamValue(String.valueOf(currentPage));
				minCreativeParam.setParamValue(String.valueOf(cList.get(0).getId()));
				
				
				sysParameterService.update(currentPageParam);
				sysParameterService.update(minCreativeParam);
			}
		} catch (Exception e) {
			log.error("", e);
		}
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}
	
	/*public String execute(){
		HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/xml;charset=UTF-8");
        File f=new File(this.systemProp.getSiteMapPath()+File.separator+"sitemap_"+siteIndex+".xml");
        try {
			PrintWriter out =response.getWriter();
			
			
			
			out.flush();  
	        out.close();
		} catch (Exception e) {
			log.error("", e);
		}
        
        return SUCCESS;
	}
	
	private String siteIndex="1";

	public String getSiteIndex() {
		return siteIndex;
	}

	public void setSiteIndex(String siteIndex) {
		this.siteIndex = siteIndex;
	}*/
	
	
	
	

}
