/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.publish;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import cn.magme.common.FileOperate;
import cn.magme.common.JsonResult;
import cn.magme.pojo.Issue;
import cn.magme.pojo.MgzProcess;
import cn.magme.pojo.SwfRetrans;
import cn.magme.service.IssueService;
import cn.magme.service.MgzProcessService;
import cn.magme.service.SwfRetransService;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

/**
 * @author fredy.liu
 * @date 2011-11-22
 * @version $id$
 */
public class SwfRetransAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2217568686705941967L;
	
	private static final Logger log=Logger.getLogger(SwfRetransAction.class);
	
	@Resource
	private MgzProcessService mgzProcessService;
	@Resource
	private IssueService issueService;
	
	@Resource
	private SwfRetransService swfRetransService;
	
	private Long mgzProcessId;
	
	private String pageNos;
	
	private int pageNo;
	
	private long swfRetransId;
	
	

	
	public long getSwfRetransId() {
		return swfRetransId;
	}

	public void setSwfRetransId(long swfRetransId) {
		this.swfRetransId = swfRetransId;
	}

	public Long getMgzProcessId() {
		return mgzProcessId;
	}

	public void setMgzProcessId(Long mgzProcessId) {
		this.mgzProcessId = mgzProcessId;
	}

	public String getPageNos() {
		return pageNos;
	}

	public void setPageNos(String pageNos) {
		this.pageNos = pageNos;
	}

	

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public String reTransJson(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		
		//=========校验==============
		if(StringUtils.isBlank(this.pageNos)){
			this.jsonResult.setMessage("页码为空");
			return JSON;
		}
		if(mgzProcessId==null || mgzProcessId<=0){
			this.jsonResult.setMessage("处理id为空");
			return JSON;
		}
		MgzProcess process=mgzProcessService.queryById(this.mgzProcessId);
		if(process==null){
			this.jsonResult.setMessage("要处理的信息不存在");
			return JSON;
		}
		Issue issue = issueService.queryById(process.getIssueId());
		String [] pageNoArr=this.pageNos.trim().split(",");
		if(pageNoArr==null || pageNoArr.length>5){
			this.jsonResult.setMessage("每次处理不能超过5页");
			return JSON;
		}
		List<SwfRetrans> swfRetransList=new ArrayList<SwfRetrans>();
		try {
			for(String pageNo:pageNoArr){
				Integer page=Integer.parseInt(pageNo);
				if(page<=0){
					this.jsonResult.setMessage("页码只能是大于0的数字");
					return JSON;
				}
				SwfRetrans swfRetrans=new SwfRetrans();
				swfRetrans.setIssueId(process.getIssueId());
				swfRetrans.setPublicationName(issue.getPublicationName());
				swfRetrans.setPublicationId(issue.getPublicationId());
				swfRetrans.setMgzProcessId(process.getId());
				swfRetrans.setPageNo(page);
				swfRetrans.setIssueNum(issue.getIssueNumber());
				swfRetrans.setStatus(1);//默认为无效
				this.swfRetransService.insert(swfRetrans);
				swfRetransList.add(swfRetrans);
			}
		} catch (NumberFormatException e) {
			this.jsonResult.setMessage("页码只能是大于0的数字");
			return JSON;
		}
		String swfRetransferCmd=this.systemProp.getSwfReTransferCmd();
		swfRetransferCmd=swfRetransferCmd.replace("swflanguagedir", systemProp.getXpdfLanguageDir());
		swfRetransferCmd=swfRetransferCmd.replace("swfCmd", systemProp.getSwfCmd());
		String inputFile="";
		if(new File(process.getTargetFile()).exists()){
			inputFile=process.getTargetFile();
		}else if(new File(process.getTargetFile()+".bak").exists()){
			inputFile=process.getTargetFile()+".bak";
		}else if(new File(process.getTargetFile()+".fork.bak").exists()){
			inputFile=process.getTargetFile()+".fork.bak";
		}else if(new File(process.getTargetFile()+".fork").exists()){
			inputFile=process.getTargetFile()+".fork";
		}
		if(StringUtil.isBlank(inputFile)){
			return JSON;
		}
		swfRetransferCmd=swfRetransferCmd.replace("inputFile", inputFile);
		//=========校验==============
		//执行转换
		try {
			//使用执行远程shell方式执行这段脚本
			Connection conn = new Connection(systemProp.getMagJobIp());
			conn.connect(); // 连接
			if(conn.authenticateWithPassword(systemProp.getMagJobUser(), systemProp.getMagJobPwd())){
				// 认证
				Session session = conn.openSession(); // 打开一个会话
				String outputDir=systemProp.getTmpswfDir()+File.separator+process.getPubId()+File.separator+process.getIssueId();
				File dirFile=new File(outputDir);
				if(!dirFile.exists()){
					dirFile.mkdirs();
				}
				for(SwfRetrans swfRetrans:swfRetransList){
					String swfRetransferCmdTmp=swfRetransferCmd.replace("outputFile", 
							outputDir+File.separator+swfRetrans.getPageNo()+".swf");
					swfRetransferCmdTmp=swfRetransferCmdTmp.replace("pageNo", String.valueOf(swfRetrans.getPageNo()));
					session.execCommand(swfRetransferCmdTmp);
				    InputStream in = session.getStdout();
				    log.info("swfRetransferCmd:"+swfRetransferCmdTmp);
				    String   result = this.processStdout(in, Charset.defaultCharset().toString());
				    if(result==null || result.contains("error") || result.contains("ERROR")){
				    	this.swfRetransService.updateStatusById(2, swfRetrans.getId());
				    }else{
				    	this.swfRetransService.updateStatusById(3, swfRetrans.getId());
				    	this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				    	this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
				   }
				    log.info("执行远程swf重新转换shell信息：");
				}
			    conn.close();
			}
			//使用执行本地shell的方式执行shell				
			/*Process process = Runtime.getRuntime().exec(flvTransferCmd);
			int exitVal = process.waitFor();*/
		} catch (Exception e) {
			log.error("",e);
		}
		return JSON;
	}
	
	public String replace(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		SwfRetrans swfRetrans=this.swfRetransService.queryById(this.swfRetransId);
		String basePath=File.separator+swfRetrans.getPublicationId()+File.separator+swfRetrans.getIssueId()+File.separator+swfRetrans.getPageNo()+".swf";
		File swfFile=new File(systemProp.getTmpswfDir()+basePath);
		
		if(!swfFile.exists()){
			this.jsonResult.setMessage("要移动的文件不存在");
			return JSON;
		}
		try {
			FileOperate operate=new FileOperate();
			operate.moveFile(swfFile.getAbsolutePath(), this.systemProp.getMagLocalUrl()+basePath);
		} catch (Exception e) {
			this.jsonResult.setMessage("移动文件失败");
			return JSON;
		}
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}
	
	public String del(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		if(this.swfRetransService.updateStatusById(1,this.swfRetransId)>0){
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		}
		
		return JSON;
	}
	
	
	private String processStdout(InputStream in, String charset) {
		   byte[] buf = new byte[1024];
		   StringBuffer sb = new StringBuffer();
		   try {
		    while (in.read(buf) != -1) {
		     sb.append(new String(buf, charset));
		    }
		   } catch (IOException e) {
		    e.printStackTrace();
		   }
		   return sb.toString();
	}
	
	
	

}
