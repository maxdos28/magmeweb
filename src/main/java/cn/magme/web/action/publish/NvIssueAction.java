package cn.magme.web.action.publish;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionContext;

import cn.magme.common.JsonResult;
import cn.magme.constants.PojoConstant;
import cn.magme.constants.WebConstant;
import cn.magme.pojo.ActionLog;
import cn.magme.pojo.AdvertiseVideo;
import cn.magme.pojo.Issue;
import cn.magme.pojo.IssueContents;
import cn.magme.pojo.User;
import cn.magme.service.ActionLogService;
import cn.magme.service.AdvertiseVideoService;
import cn.magme.service.IssueContentsService;
import cn.magme.service.IssueService;
import cn.magme.service.SysParameterService;
import cn.magme.service.UserService;
import cn.magme.util.StringUtil;
import cn.magme.util.Struts2Utils;
import cn.magme.web.action.BaseAction;

/**
 * 
 * @author Administrator
 *
 */
@Results({@Result(name="success",location="/WEB-INF/pages/publish/nvmagazine.ftl")})
public class NvIssueAction extends BaseAction {
	private static final Logger log=Logger.getLogger(NvIssueAction.class);
	
	@Resource
	private IssueService issueService;
	@Resource
	private IssueContentsService issueContentsService;
	@Resource
	private AdvertiseVideoService advertiseVideoService;
	@Resource
    private ActionLogService actionLogService;
	
	@Resource
	private SysParameterService sysParameterService;
	@Resource
	private UserService userService;
	
	private static final String NVYOU_PUBLISHER_ID="NVYOU_PUBLISHER_ID";
	
	private static final int DEFAULT_SIZE=15;
	
	/**
	 * 最新
	 */
	private static final int TYPE_LASTEST=1;
	
	/**
	 * 最热
	 */
	private static final int TYPE_HOTEST=2;

	/**
	 * 
	 */
	private static final long serialVersionUID = -3454275301760376773L;
	
	
	public String execute(){
		this.issueJson();
		this.rigtJson();
		return "success";
	}
	
	public String issueJson(){
		this.jsonResult=new JsonResult();
		//默认失败
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		
		try {
			Long superId=Long.parseLong(this.sysParameterService.queryByParamKey(NVYOU_PUBLISHER_ID).getParamValue());
			if(this.type!=TYPE_HOTEST){
				//不是最热一律认为为最新
				this.issueList=this.issueService.queryBySuperId(superId, begin, size);
			}else{
				this.issueList=this.issueService.queryBySuperIdOrderByClickNum(superId, begin, size);
			}
			
			if(this.issueList!=null && this.issueList.size()>=0){
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
				this.jsonResult.put("issueList", this.issueList);
				this.adVideo = advertiseVideoService.queryByNvIndexVideo();
				this.jsonResult.put("adVideo",this.adVideo);
			}
		} catch (Exception e) {
			log.error("", e);
		}
		if(StringUtil.isNotBlank(callbackparam)){
			String jsonString =this.callbackparam+"(["+ JSONObject.fromObject(this.jsonResult).toString()+"])";
			//String [] headers=new String[]{"encoding:UTF-8","no-cache:true"};
			Struts2Utils.renderText(jsonString);		
			return null;
		}
		return JSON;
		
	}
	
	/**
	 * 女友右侧数据查询
	 */
	public void rigtJson(){
		this.jsonResult=new JsonResult();
		//默认失败
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		try {
			Long superId=Long.parseLong(this.sysParameterService.queryByParamKey(NVYOU_PUBLISHER_ID).getParamValue());
			this.sortIssueList=this.issueService.queryBySuperIdOrderByClickNum(superId, 0, 5);
			this.goodArticleList=this.issueContentsService.nvGoodArticleByWeek();
		}catch (Exception e) {
		}
	}
	
	
	/**
	 * 女友注册用户
	 * @return
	 */
	public String registerJson(){
		 User user=new User();
	        user.setUserName(userName);
	        user.setPassword(password);
	        user.setPassword2(password2);
	        user.setEmail(email);
	        user.setType(PojoConstant.USER.USER_TYPE_NV);
	        
	        this.jsonResult=userService.register(user);
	        if(StringUtil.isNotBlank(callbackparam)){
				String jsonString =this.callbackparam+"(["+ JSONObject.fromObject(this.jsonResult).toString()+"])";
				//String [] headers=new String[]{"encoding:UTF-8","no-cache:true"};
				Struts2Utils.renderText(jsonString);		
				return null;
			}
		return JSON;
	}
	
	/**
     * 用户登陆
     * @return
     */
    public String loginJson(){
        this.jsonResult=userService.login(userName,password);
        if(this.jsonResult.getCode()==JsonResult.CODE.SUCCESS){
            //校验通过,将登陆用户信息存放在SESSION
            ActionContext.getContext().getSession().put(WebConstant.SESSION.USER, this.jsonResult.getData().get("user"));
            //纪录用户的操作日志
            ActionLog actionLog=this.generateActionLog(
                    PojoConstant.ACTIONLOG.ACTIONTYPEID_LOGIN,
                    ((User)this.jsonResult.getData().get("user")).getId());
            actionLogService.insertActionLog(actionLog);
            if(StringUtil.isNotBlank(callbackparam)){
				String jsonString =this.callbackparam+"(["+ JSONObject.fromObject(this.jsonResult).toString()+"])";
				//String [] headers=new String[]{"encoding:UTF-8","no-cache:true"};
				Struts2Utils.renderText(jsonString);		
				return null;
			}
        }
        return JSON;
    }
    
    /**
     * 用户退出登陆
     * @return
     */
    public String logoutJson(){
    	//米客登出
    	ActionContext.getContext().getSession().remove(WebConstant.SESSION.USER);
        
    	//米商登出
        ActionContext.getContext().getSession().remove(WebConstant.SESSION.PUBLISHER);
        this.generateJsonResult(JsonResult.CODE.SUCCESS, JsonResult.MESSAGE.SUCCESS);
        if(StringUtil.isNotBlank(callbackparam)){
			String jsonString =this.callbackparam+"(["+ JSONObject.fromObject(this.jsonResult).toString()+"])";
			//String [] headers=new String[]{"encoding:UTF-8","no-cache:true"};
			Struts2Utils.renderText(jsonString);		
			return null;
		}
        return JSON;
    }
	
	
	private int begin=0;
	
	private int size=DEFAULT_SIZE;
	
	private String userName;
	private String password;
	private String password2;
	private String email;
	
	
	
	
	public int getSize() {
		return size;
	}


	public void setSize(int size) {
		this.size = size;
	}


	public int getBegin() {
		return begin;
	}


	public void setBegin(int begin) {
		this.begin = begin;
	}

	private List<Issue> issueList;
	private List<AdvertiseVideo> adVideo;
	
	private List<Issue> sortIssueList;
	
	private List<IssueContents> goodArticleList;
	
	private String callbackparam;
	
	
	
	
	
	public String getCallbackparam() {
		return callbackparam;
	}


	public void setCallbackparam(String callbackparam) {
		this.callbackparam = callbackparam;
	}


	public List<Issue> getIssueList() {
		return issueList;
	}


	public void setIssueList(List<Issue> issueList) {
		this.issueList = issueList;
	}
	

	public List<AdvertiseVideo> getAdVideo() {
		return adVideo;
	}


	public void setAdVideo(List<AdvertiseVideo> adVideo) {
		this.adVideo = adVideo;
	}

	/**
	 * 1最新，2最热
	 */
	private Integer type=TYPE_LASTEST;


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getPassword2() {
		return password2;
	}


	public void setPassword2(String password2) {
		this.password2 = password2;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}

	public List<Issue> getSortIssueList() {
		return sortIssueList;
	}

	public void setSortIssueList(List<Issue> sortIssueList) {
		this.sortIssueList = sortIssueList;
	}

	public List<IssueContents> getGoodArticleList() {
		return goodArticleList;
	}

	public void setGoodArticleList(List<IssueContents> goodArticleList) {
		this.goodArticleList = goodArticleList;
	}
	
	
	
	
	
	

}
