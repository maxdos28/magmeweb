/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.publish;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import cn.magme.common.JsonResult;
import cn.magme.constants.PojoConstant;
import cn.magme.pojo.Issue;
import cn.magme.pojo.MgzProcess;
import cn.magme.pojo.Publication;
import cn.magme.pojo.Publisher;
import cn.magme.pojo.SwfRetrans;
import cn.magme.service.IssueService;
import cn.magme.service.MgzProcessService;
import cn.magme.service.PublicationService;
import cn.magme.service.SwfRetransService;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

/**
 * @author fredy.liu
 * @date 2011-8-31
 * @version $id$
 */
@Results({@Result(name="success",location="/WEB-INF/pages/publishadmin/publisherUpload.ftl")})
public class PublisherUpload extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log=Logger.getLogger(PublisherUpload.class);
	
	@Resource
	private PublicationService publicationService;
	
	@Resource
	private IssueService issueService;
	
	@Resource
	private MgzProcessService mgzProcessService;
	
	@Resource
	private SwfRetransService swfRetransService;
	
	private List<SwfRetrans> swfRetransList;
	
	private static final String JPG="jpg";
	
	private static final String SWF="swf";

	public String execute(){
		Publisher publisher = this.getSessionPublisher();
		try {
			swfRetransList = swfRetransService.queryAllByPublisherId(this.getSessionPublisherId());
			
			publicationList=this.publicationService.queryNormalByPublisherId(publisher.getId());
			//正在处理的杂志
			processIssueList=this.issueService.queryByStatusAndPublisherId(publisher.getId(), PojoConstant.ISSUE.STATUS.PROCESSING.getCode());
			//历史记录
			mgzProcessList=mgzProcessService.queryByPublisherId(publisher.getId());
		} catch (Exception e) {
			log.error("", e);
		}
		return "success";
	}
	
	public String reDealSwfJpgJson(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		try {
			Issue issue=this.issueService.queryById(this.issueId);
			MgzProcess mgzProcess=this.mgzProcessService.queryLastestByIssueId(this.issueId);
			String reDealCmd=this.systemProp.getReDealJpgSwfCmd();
			reDealCmd=reDealCmd.replace("inputFile", mgzProcess.getTargetFile());
			reDealCmd=reDealCmd.replace("issueId", String.valueOf(this.issueId));
			reDealCmd=reDealCmd.replace("missPage", String.valueOf(this.pageId));//missPage
			reDealCmd=reDealCmd.replace("totalPage", String.valueOf(issue.getTotalPages()));//totalPage
			if(StringUtil.isNotBlank(this.dealType) && this.dealType.equalsIgnoreCase(JPG)){
				reDealCmd=reDealCmd.replace("reDealType", "0");
			}else if(StringUtil.isNotBlank(this.dealType) && this.dealType.equalsIgnoreCase(SWF)){
				reDealCmd=reDealCmd.replace("reDealType", "1");
			}
			//调用远程shell处理
			try {
				Connection conn = new Connection(systemProp.getMagJobIp());
				conn.connect(); // 连接
				if(conn.authenticateWithPassword(systemProp.getMagJobUser(), systemProp.getMagJobPwd())){
					// 认证
					Session session = conn.openSession(); // 打开一个会话
				    session.execCommand(reDealCmd);
				    InputStream in = session.getStdout();
				    log.info("reDealCmd:"+reDealCmd);
				    String   result = this.processStdout(in, Charset.defaultCharset().toString());
				    log.info(result);
				    conn.close();
				}
			} catch (Exception e) {
				log.error("", e);
			}
			
			//Process process = Runtime.getRuntime().exec(reDealCmd);
			//process.waitFor();
			
		} catch (Exception e) {
			log.error("", e);
		}
		return JSON;
	}
	
	public String processStdout(InputStream in, String charset) {
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
	
	private List<Publication>  publicationList;
	
	private List<Issue> processIssueList;
	
	private List<Issue> finishIssueList;
	
	private List<MgzProcess> mgzProcessList;
	
	private String dealType;
	
	private Long issueId;
	
	private Integer pageId;
	
	
	
	
	
	public String getDealType() {
		return dealType;
	}

	public void setDealType(String dealType) {
		this.dealType = dealType;
	}

	public Long getIssueId() {
		return issueId;
	}

	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}

	public Integer getPageId() {
		return pageId;
	}

	public void setPageId(Integer pageId) {
		this.pageId = pageId;
	}

	public List<MgzProcess> getMgzProcessList() {
		return mgzProcessList;
	}

	public void setMgzProcessList(List<MgzProcess> mgzProcessList) {
		this.mgzProcessList = mgzProcessList;
	}

	public List<Issue> getFinishIssueList() {
		return finishIssueList;
	}

	public void setFinishIssueList(List<Issue> finishIssueList) {
		this.finishIssueList = finishIssueList;
	}

	public List<Issue> getProcessIssueList() {
		return processIssueList;
	}

	public void setProcessIssueList(List<Issue> processIssueList) {
		this.processIssueList = processIssueList;
	}

	public List<Publication> getPublicationList() {
		return publicationList;
	}

	public void setPublicationList(List<Publication> publicationList) {
		this.publicationList = publicationList;
	}

	public List<SwfRetrans> getSwfRetransList() {
		return swfRetransList;
	}

	public void setSwfRetransList(List<SwfRetrans> swfRetransList) {
		this.swfRetransList = swfRetransList;
	}
	
	

}
