package cn.magme.web.action.publish;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cn.magme.common.JsonResult;
import cn.magme.constants.PojoConstant;
import cn.magme.constants.WebConstant;
import cn.magme.pojo.Issue;
import cn.magme.pojo.IssueContents;
import cn.magme.pojo.Publication;
import cn.magme.pojo.Publisher;
import cn.magme.pojo.ma.MaAdLocation;
import cn.magme.service.IssueContentsService;
import cn.magme.service.IssueService;
import cn.magme.service.PublicationService;
import cn.magme.service.ma.MaAdLocationService;
import cn.magme.service.xiaozi.XiaoziService;
import cn.magme.util.FileOperate;
import cn.magme.util.ImageUtil;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

import com.opensymphony.xwork2.ActionContext;

/**
 * 采编系统上传zip包
 * 
 * @author fredy
 * @since 2013-4-23
 */
@Results({@Result(name="issueUploadJson",type="json",params={"root","jsonResult","contentType","text/html"})})
public class MagicEditorUploadAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7382593617525511564L;
	
	private static final Logger log=Logger.getLogger(MagicEditorUploadAction.class);
	
	@Resource
	private PublicationService publicationService;
	@Resource
	private XiaoziService xiaoziService;
	
	@Resource
	private IssueContentsService issueContentsService;

	@Resource
	private IssueService issueService;
	
	@Resource
	private MaAdLocationService maAdLocationService;
	
	private static final String ISSUE_NUM_SEP="/";
	
	/**
	 * 文件类型的个数
	 */
	private static final int FILE_TYPE=3;
	/**
	 * 图片的高度
	 */
	private static final int JPG_HEIGHT=120;
	
	/**
	 * 图片的宽度
	 */
	private static final int JPG_WIDTH=200;
	
		
	/**
	 * 图片后缀
	 */
	private static final String PICTURE_SUFFIX=".jpg";
	
	/**
	 * jpg图片后缀
	 */
	private static final String JPG_SUFFIX=".jpg";
	/**
	 * pad图片前缀
	 */
	private static final String PAD_PICTURE_PREFIX="pad_";
	
	/**
	 * phone图片前缀
	 */
	private static final String PHONE_PICTURE_PREFIX="phone_";
	
	/**
	 * 配置文件，总页数
	 */
	private static final String PAGE_COUNT="pageCount";
	/**
	 * 配置文件，方向横竖屏
	 */
	private static final String ORIENTATION="orientation";
	/**
	 * 配置文件，页
	 */
	private static final String PAGE="page";
	/**
	 * 配置文件，页码
	 */
	private static final String PAGE_NO="pageNo";
	/**
	 * 配置文件，页标题
	 */
	private static final String TITLE="title";
	/**
	 * 配置文件，页描述
	 */
	private static final String DESCRIPTION="description";
	
	/**
	 * 配置文件，页节点全描述
	 */
	private static final String ISSUE_CONTENTS_LIST="issueContentsList";
	/** 
	 * 下划线分隔符
	 */
	private static final String SLASH_SEPERATOR="_";

	
	
	public String execute() {
		this.jsonResult = JsonResult.getFailure();
		if (StringUtil.isBlank(issueFileFileName)
				|| !(issueFileFileName.toLowerCase().endsWith(".zip")||issueFileFileName.toLowerCase().endsWith(".mpres"))) {
			jsonResult.setMessage("上传文件类型必需为zip或mpres文件");
			return "issueUploadJson";
		}
		
		if (issueFile == null) {
        	jsonResult.setMessage("上传文件为空");
            return "issueUploadJson";
        }
		if ((this.smallPic1 != null&&this.smallPic2 == null)||(this.smallPic1 == null&&this.smallPic2 != null))
		{
			jsonResult.setMessage("两张图片要同时上传");
            return "issueUploadJson";
		}
		Long appId = getSessionAppId();
		if(appId==null)
		{
			jsonResult.setMessage("无有效APPID");
            return "issueUploadJson";
		}
		Publication pub=this.publicationService.queryById(publicationId);
		//新建期刊信息
		Issue issue = new Issue();
        issue.setIssueType(PojoConstant.ISSUE.ISSUETYPE.INTERACTIVEISSUE.getCode());
        issue.setDescription(description);
        issue.setFileName(issueFile.getName());
        String issueNumber=year.substring(2,4)+ISSUE_NUM_SEP+month;
        if(StringUtil.isNotBlank(day)){
        	issueNumber+=ISSUE_NUM_SEP+day;
        }
        issue.setIssueNumber(issueNumber);
        issue.setKeyword(keyword);
        issue.setIsFree(isFree);
        issue.setPublicationId(publicationId);
        issue.setPublicationName(pub.getName());
        issue.setPublishDate(publishDate);
        issue.setStatus(PojoConstant.ISSUE.STATUS.WAIT_PROCESS.getCode());
        issue.setIssueCode(0L);
        Long issueId=issueService.insert(issue);
        //处理ZIP
        this.jsonResult.setMessage(null);
        String issuePath = uploadIssueZip(issue, issueId,this.jsonResult);
        if(this.jsonResult.getMessage()!=null)
        	return "issueUploadJson";
        //处理图片
        String staticPath = systemProp.getStaticLocalUrl();
		String relativePath = File.separator +"appprofile"+File.separator+publicationId+File.separator+issueId+File.separator;
		if (this.smallPic1 != null&&this.smallPic2 != null) {
			// 图片尺寸检查
			// FileInputStream fis = new FileInputStream(smallPic);
			try {
				BufferedImage buff = ImageIO.read(smallPic1);
				if (buff.getHeight() < 472 || buff.getWidth() < 472) {
					log.warn("图片尺寸为472*472");
					this.jsonResult.setMessage("图片尺寸为472*472");
					return JSON;
				}
				BufferedImage buff2 = ImageIO.read(smallPic2);
				if (buff2.getHeight() < 360 || buff2.getWidth() < 360) {
					log.warn("图片尺寸为360*360");
					this.jsonResult.setMessage("图片尺寸为360*360");
					return JSON;
				}

				// 图片尺寸转换 pad 470*470 phone 360*360
				// 文章图片ID，文章ID+－+时间
				String fileName = relativePath + "pub_" + issueId + "-"
						+ System.nanoTime();

				FileOperate.copyFile(smallPic1.getAbsolutePath(), staticPath+fileName+".jpg");
				if(buff.getHeight() == 472 && buff.getWidth() == 472)
				{
					FileOperate.copyFile(smallPic1.getAbsolutePath(), staticPath+fileName+ "_pad.jpg");
				}
				else
				{
					ImageUtil.smallerByWidthAndHeight(smallPic1.getAbsolutePath(),
							staticPath + fileName + "_pad.jpg", 472, 472);
				}
				if(buff2.getHeight() == 360 && buff2.getWidth() == 360)
				{
					FileOperate.copyFile(smallPic2.getAbsolutePath(), staticPath+fileName+ "_phone.jpg");
				}
				else
				{
					ImageUtil.smallerByWidthAndHeight(smallPic2.getAbsolutePath(),
							staticPath + fileName + "_phone.jpg", 360, 360);
				}
				issue.setSmallPic(fileName.replaceAll("\\\\", "/") + ".jpg");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//更新期刊
		issueService.updateById(issue);
		
		ChangeJpgThread t=new ChangeJpgThread(issuePath,issueId);
		t.start();
		//图片缩放
        this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
        this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return "issueUploadJson";
	}

	private String uploadIssueZip(Issue issue, Long issueId,JsonResult r) {
		//jasper 2013.10.24 去掉杂志的appId路径
        String issuePath=this.systemProp.getMagicEditorPath()+File.separator+publicationId+File.separator+issueId+File.separator;
        File srcIssueFile=new File(issuePath);
        if(!srcIssueFile.exists()){
        	srcIssueFile.mkdirs();
        }
        File destFile=new File(issuePath+issueId+issueFileFileName.toLowerCase().substring(issueFileFileName.toLowerCase().lastIndexOf("."), issueFileFileName.toLowerCase().length()));
        FileOperate.copyFile(issueFile.getAbsolutePath(),destFile.getAbsolutePath());
        //保存的文件名称
        issue.setFileName(destFile.getName());
        try {
        	//解压根zip包
			FileOperate.unzipFile(issueFile.getAbsolutePath(), issuePath,false);
			//解压解压后的资源文件
			File [] fList=srcIssueFile.listFiles();
		    for(File ff:fList){
				if(ff.isDirectory()||!ff.getName().toLowerCase().endsWith(".zip"))
					continue;
			   if(ff.getName().indexOf("assets")>0){
				   FileOperate.unzipFile(ff.getAbsolutePath(),issuePath,true);
			   }
				if (ff.getName().toLowerCase().indexOf("phone") > 0) {
					FileOperate.unzipFile(ff.getAbsolutePath(), issuePath, true);
				}
				if (ff.getName().toLowerCase().indexOf("pad") > 0) {
					FileOperate.unzipFile(ff.getAbsolutePath(), issuePath, true);
				}
		    }
		    //重命名解压后的资源文件
		    File [] assetsFolderList=srcIssueFile.listFiles();
		    int assetsIndex=0;
		    for(File ff:assetsFolderList){
		    	assetsIndex=ff.getName().indexOf("assets");
		    	if(ff.isDirectory() && assetsIndex>0){
		    		FileOperate.moveFolder(ff.getAbsolutePath(), issuePath+ff.getName().substring(0,assetsIndex));
		    	}
		    }
		    //上传的压缩包保留
			//destFile.delete();
		} catch (IOException e) {
			log.error("", e);
			r.setMessage("unzip file error");
			//return JSON;
		}
		//
		File configFile=new File(issuePath+"config.xml");
		if(!configFile.exists()){
			r.setMessage("no config file");
			//return JSON;
		}
		Map<String,Object> config=this.parseConfig(configFile,issueId);
		int totalPages=(Integer)config.get(PAGE_COUNT);
		issue.setTotalPages(totalPages);
		issue.setOrientation((Integer)config.get(ORIENTATION));
		issue.setStatus(PojoConstant.ISSUE.STATUS.WAIT_AUDIT.getCode());
		issue.setIssueCode(issueId);
		//jasper 2013.10.24 去掉杂志的appId路径
		issue.setSwfPath(publicationId+"/"+issueId);
		issue.setJpgPath(issue.getSwfPath());
		//杂志封面图
		String coverPad = (String) config.get("coverPad");
		String coverPhone = (String) config.get("coverPhone");
		if(StringUtil.isNotBlank(coverPad)&&StringUtil.isNotBlank(coverPhone))
		{
			File coverPadFile = new File(issuePath+coverPad);
			File coverPhoneFile = new File(issuePath+coverPhone);
			//文件需要重新命名,以_pad和_phone结尾
			if(coverPadFile.exists()&&coverPhoneFile.exists())
			{
				FileOperate.copyFile(coverPadFile.getAbsolutePath(), issuePath+"pub_" + issueId+coverPad.substring(coverPad.lastIndexOf(".")));
				FileOperate.copyFile(coverPadFile.getAbsolutePath(), issuePath+"pub_" + issueId+"_pad"+coverPad.substring(coverPad.lastIndexOf(".")));
				FileOperate.copyFile(coverPhoneFile.getAbsolutePath(), issuePath+"pub_" + issueId+"_phone"+coverPhone.substring(coverPhone.lastIndexOf(".")));
				String fileName = File.separator +"appprofile"+File.separator+publicationId+File.separator+issueId+File.separator+"pub_" + issueId+coverPad.substring(coverPad.lastIndexOf("."));
				issue.setSmallPic(fileName.replaceAll("\\\\", "/"));
			}
		}
		
		//更新目录
		List<IssueContents> issueContentsList=(List<IssueContents>)config.get(ISSUE_CONTENTS_LIST);
		if(issueContentsList!=null && issueContentsList.size()>0){
			for(IssueContents issueContent:issueContentsList){
				issueContentsService.insert(issueContent);
			}
		}
		return issuePath;
	}
	
	/**
	 * 解析xml配置文件
	 * @return
	 */
	private Map<String,Object> parseConfig(File configFile,Long issueId){
		Map<String,Object> map =new HashMap<String,Object>();
		List<IssueContents> issueContentList=new ArrayList<IssueContents>();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = dbf.newDocumentBuilder();
            InputStream in = new FileInputStream(configFile);
            Document doc = builder.parse(in);
            
            Element root = doc.getDocumentElement();
            if (root == null) return null;
            
            NodeList collegeNodes = root.getChildNodes();
            if (collegeNodes == null) return null;
            
            Node pageCountNode=root.getElementsByTagName(PAGE_COUNT).item(0);
            System.out.println(pageCountNode.getFirstChild().getNodeName());
            map.put(PAGE_COUNT, Integer.parseInt(pageCountNode.getFirstChild().getNodeValue()));
            
            //方向，横竖屏
            Node orientationNode=root.getElementsByTagName(ORIENTATION).item(0);
            map.put(ORIENTATION, Integer.parseInt(orientationNode.getFirstChild().getNodeValue()));
            //封面图片
            Node coverPadNode=root.getElementsByTagName("coverPad").item(0);
            if(coverPadNode!=null)
            map.put("coverPad", coverPadNode.getFirstChild().getNodeValue());
            Node coverPhoneNode=root.getElementsByTagName("coverPhone").item(0);
            if(coverPhoneNode!=null)
            map.put("coverPhone", coverPhoneNode.getFirstChild().getNodeValue());
            //页码,标题，描述
            NodeList pageNodeList=root.getElementsByTagName(PAGE);
            for(int i = 0; i < pageNodeList.getLength(); i++) {
                Node page = pageNodeList.item(i);
                NodeList pageDetailNodeList=page.getChildNodes();
                IssueContents issueContent=new IssueContents();
                for(int j=0; j<pageDetailNodeList.getLength();j++){
                	Node pageDetail = pageDetailNodeList.item(j);
                	if(pageDetail.getNodeName().equalsIgnoreCase(PAGE_NO)){
                		issueContent.setPageno(Integer.parseInt(pageDetail.getFirstChild().getNodeValue()));
                	}
					if(pageDetail.getNodeName().equalsIgnoreCase(TITLE)){
						issueContent.setTitle(pageDetail.getFirstChild().getNodeValue());            		
					}
					if(pageDetail.getNodeName().equalsIgnoreCase(DESCRIPTION)){
						issueContent.setDescription(pageDetail.getFirstChild().getNodeValue());
					}
					//判断是否为目录
					if(pageDetail.getNodeName().equalsIgnoreCase("belongToCatalog")){
						if(pageDetail.getFirstChild().getNodeValue()!=null&&pageDetail.getFirstChild().getNodeValue().equals("1"))
							issueContent.setIsCatalog(1);
						else
							issueContent.setIsCatalog(0);
					}
					//默认无精品页
					issueContent.setIsBoutique(0);
                }
                issueContent.setIssueid(issueId);
                issueContent.setStatus(PojoConstant.ISSUECONTENTS.STATUS.SHOW.getCode());
                issueContentList.add(issueContent);
            }
            //广告信息
            NodeList adNodeList=root.getElementsByTagName("ad");
            if(adNodeList!=null&&adNodeList.getLength()>0)
            {
            	Date date = new Date();
            	for(int i = 0; i < adNodeList.getLength(); i++) {
                	MaAdLocation mal = new MaAdLocation();
                	mal.setType(new Byte("2"));
                	mal.setPublicationId(publicationId);
                	mal.setIssueId(issueId);
                	String keywords = null;
                    Node ad = adNodeList.item(i);
                    NodeList adDetailNodeList=ad.getChildNodes();
                    for(int j=0; j<adDetailNodeList.getLength();j++){
                    	Node adDetail = adDetailNodeList.item(j);
                    	
                    	//广告ID
                    	if(adDetail.getNodeName().equalsIgnoreCase("id")){
                    		mal.setAdId(adDetail.getFirstChild().getNodeValue());                    		
                    	}
                    	//页码
    					if(adDetail.getNodeName().equalsIgnoreCase("pageNo")){      
    						mal.setPageNo(new Integer(adDetail.getFirstChild().getNodeValue()));  
    					}
                    	//广告尺码
    					if(adDetail.getNodeName().equalsIgnoreCase("size")){
    						mal.setAdSize(adDetail.getFirstChild().getNodeValue());  
    					}
    					//广告关键字
    					if(adDetail.getNodeName().equalsIgnoreCase("keyword")){
    						keywords = adDetail.getFirstChild().getNodeValue();  
    					}
    					//设备区分
    					if(adDetail.getNodeName().equalsIgnoreCase("device")){
    						if(adDetail.getFirstChild().getNodeValue().equals("pad"))
    							mal.setDevice(new Byte("1"));  
    						else
    							mal.setDevice(new Byte("2")); 
    					}
                    }
                    this.maAdLocationService.saveAdLocation(mal, keywords);
                }
            }
            
        } catch (Exception e) {
          log.error("parse config file error", e);
        }
        
        map.put(ISSUE_CONTENTS_LIST, issueContentList);
        return map;
		
	}
	
	private Long getSessionAppId()
	{
		Publisher p = (Publisher) ActionContext.getContext().getSession().get(WebConstant.SESSION.PUBLISHER);
		List<Long> appIdList = xiaoziService.queryAppIdByPublisherId(p.getId());
		if(appIdList!=null)
			return appIdList.get(0);
		else
			return null;
	}
	
	/**
	 * 测试
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception{
		/*MagicEditorUploadAction a=new MagicEditorUploadAction();
		File f=new File("d:\\config2.xml");
		Map<String,Object> map=a.parseConfig(f,1000L);
		System.out.println(map.get(PAGE_COUNT));
		System.out.println(map.get(ORIENTATION));
		List<IssueContents> issueContentList=(List<IssueContents>)map.get(ISSUE_CONTENTS_LIST);
		for(IssueContents i:issueContentList){
			System.out.println(i.getTitle());
			System.out.println(i.getDescription());
			System.out.println(i.getPageno());
		}*/
		
		MagicEditorUploadAction a= new MagicEditorUploadAction();
		ChangeJpgThread b=a.new ChangeJpgThread("E:\\work\\ee\\magmecn_web\\src\\main\\webapp\\pdfprofile\\750\\6376",6376L);
		b.run();
	}
	
	/**
	 * 修改图片
	 * @author fredy
	 * @since 2013-4-23
	 */
	private class ChangeJpgThread extends Thread{
		private String issuePath;
		private Long issueId;
		
		public ChangeJpgThread(String issuePath,Long issueId){
			this.issuePath=issuePath;
			this.issueId=issueId;
		}

		@Override
		public void run() {
			File issueFile=new File(this.issuePath);
			if(!issueFile.exists() || !issueFile.isDirectory() || issueFile.listFiles().length<=0){
				return;
			}
			File []listFile=issueFile.listFiles();
			for(File f:listFile){
				if(f.isDirectory())
				{
					File[] subFileList = f.listFiles(new FilenameFilter() {
						
						@Override
						public boolean accept(File dir, String name) {
							if(name.toLowerCase().endsWith(PICTURE_SUFFIX)&&(name.indexOf(PAD_PICTURE_PREFIX)==0 || name.indexOf(PHONE_PICTURE_PREFIX)==0))
								return true;
							return false;
						}
					});
					if(subFileList!=null&&subFileList.length>0)
					{
						for(File sf:subFileList)
						{
							//期刊内页缩放成120
							ImageUtil.smallerByWidthWithSameRatio(sf.getAbsolutePath(),
									sf.getParentFile().getAbsolutePath()+File.separator+"120_"+sf.getName(), JPG_WIDTH);
							//第一张图片需要转封面
							if(sf.getName().toLowerCase().equalsIgnoreCase("pad_q1"+PICTURE_SUFFIX)){
								String pubPath=sf.getParentFile().getParentFile().getAbsolutePath()+File.separator;
								ImageUtil.smallerByWidthAndHeight(sf.getAbsolutePath(),pubPath+"100_"+issueId+".jpg", 100, 132);
								ImageUtil.smallerByWidthAndHeight(sf.getAbsolutePath(),pubPath+"110_"+issueId+".jpg", 110, 145);
								ImageUtil.smallerByWidthAndHeight(sf.getAbsolutePath(),pubPath+"172_"+issueId+".jpg", 172, 230);
								ImageUtil.smallerByWidthAndHeight(sf.getAbsolutePath(),pubPath+"248_"+issueId+".jpg", 248, 330);
								ImageUtil.smallerByWidthAndHeight(sf.getAbsolutePath(),pubPath+"200_"+issueId+".jpg", 200, 268);
								//320图片是等比例
								ImageUtil.smallerByWidthWithSameRatio(sf.getAbsolutePath(),pubPath+"320_"+issueId+".jpg", 320);
							}
						}
					}
				}
			}
			
		}
		
		
	}
	
	/**
	 * 描述
	 */
	private String description;
	
	/**
	 * 年
	 */
	private String year;
    
	/**
	 * 月
	 */
    private String month;
    
    /**
     * 日
     */
    private String day;
	
    /**
     * 关键词
     */
    private String keyword;
    
    /**
     * 杂志id
     */
	private Long publicationId;
	
	/**
	 * 发布日期
	 */
	private Date publishDate;
	
	/**
	 * 刊物属性，收费，免费
	 */
	private Integer isFree=1;
	
	/**
	 * 上传的文件
	 */
	private File issueFile;
	/**
	 *  上传文件名.
	 */
	private String issueFileFileName;
	

	private File smallPic1;

	private File smallPic2;
	private String smallPic1FileName;
	private String smallPic2FileName;



	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Long getPublicationId() {
		return publicationId;
	}
	public void setPublicationId(Long publicationId) {
		this.publicationId = publicationId;
	}
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public Integer getIsFree() {
		return isFree;
	}
	public void setIsFree(Integer isFree) {
		this.isFree = isFree;
	}
	public File getIssueFile() {
		return issueFile;
	}
	public void setIssueFile(File issueFile) {
		this.issueFile = issueFile;
	}

	public String getIssueFileFileName() {
		return issueFileFileName;
	}

	public void setIssueFileFileName(String issueFileFileName) {
		this.issueFileFileName = issueFileFileName;
	}

	public File getSmallPic1() {
		return smallPic1;
	}

	public void setSmallPic1(File smallPic1) {
		this.smallPic1 = smallPic1;
	}

	public File getSmallPic2() {
		return smallPic2;
	}

	public void setSmallPic2(File smallPic2) {
		this.smallPic2 = smallPic2;
	}

	public String getSmallPic1FileName() {
		return smallPic1FileName;
	}

	public void setSmallPic1FileName(String smallPic1FileName) {
		this.smallPic1FileName = smallPic1FileName;
	}

	public String getSmallPic2FileName() {
		return smallPic2FileName;
	}

	public void setSmallPic2FileName(String smallPic2FileName) {
		this.smallPic2FileName = smallPic2FileName;
	}
}
