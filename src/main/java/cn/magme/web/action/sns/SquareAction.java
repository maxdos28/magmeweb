package cn.magme.web.action.sns;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.pojo.User;
import cn.magme.pojo.sns.M1Square;
import cn.magme.result.sns.UserInfoResult;
import cn.magme.service.sns.M1SquareService;
import cn.magme.service.sns.SnsUserIndexService;
import cn.magme.web.action.BaseAction;

/**
 * M1广场
 * @author devin.song
 * 
 */
@SuppressWarnings("serial")
@Results({@Result(name="success",location="/WEB-INF/pages/sns/sns_square.ftl")})
public class SquareAction extends BaseAction {
    @Resource
    private M1SquareService m1SquareService;
    @Resource
    private SnsUserIndexService snsUserIndexService;
   
    private String tagName;
    private Integer begin=0;
    private Integer size = 30;
    Integer attentionNum = 0;
	Integer fansNum = 0;
    
    private List<M1Square> m1List;
    private List<UserInfoResult> userInfoList;
	
	@Override
	public String execute() throws Exception {
		User u = this.getSessionUser();
		if(u!=null){
			loginToUserList();
		}else{
			notLoginToUserList();
		}
		squareList();
		return SUCCESS;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String squareList(){
		this.jsonResult = JsonResult.getFailure();
		Map map = new HashMap();
		if(begin!=null && begin >=0){
			map.put("begin", begin);
		}else{
			map.put("begin", 0);
		}
		map.put("size", size);
		map.put("tagName", tagName);
		m1List = m1SquareService.queryM1SquareList(map);
		this.jsonResult.put("m1List",m1List);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		return JSON;
	}
	
	/**
	 * 换一批作者
	 * @return
	 */
	public String changeUserList(){
		this.jsonResult = JsonResult.getFailure();
		User u = this.getSessionUser();
		if(u!=null){
			loginToUserList();
		}else{
			notLoginToUserList();
		}
		if(userInfoList==null || userInfoList.size()<=0){
			begin = 0;
			if(u!=null){
				loginToUserList();
			}else{
				notLoginToUserList();
			}
		}
		this.jsonResult.put("begin", begin);
		this.jsonResult.put("userInfoList", userInfoList);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		return JSON;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void notLoginToUserList() {
		Map map = new HashMap();
		map.put("begin", begin);
		map.put("size", 5);
		userInfoList = snsUserIndexService.getNotLoginSquareUserList(map);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void loginToUserList() {
		Long userId = this.getSessionUserId();
		Map map = new HashMap();
		map.put("begin", begin);
		map.put("size", 5);
		map.put("userid", userId);
		userInfoList = snsUserIndexService.getLoginSquareUserList(map);
		attentionNum = snsUserIndexService.getAttention(userId);
		fansNum = snsUserIndexService.getFans(userId);
	}
	

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public Integer getBegin() {
		return begin;
	}

	public void setBegin(Integer begin) {
		this.begin = begin;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public List<M1Square> getM1List() {
		return m1List;
	}

	public void setM1List(List<M1Square> m1List) {
		this.m1List = m1List;
	}

	public List<UserInfoResult> getUserInfoList() {
		return userInfoList;
	}

	public void setUserInfoList(List<UserInfoResult> userInfoList) {
		this.userInfoList = userInfoList;
	}

	public Integer getAttentionNum() {
		return attentionNum;
	}

	public void setAttentionNum(Integer attentionNum) {
		this.attentionNum = attentionNum;
	}

	public Integer getFansNum() {
		return fansNum;
	}

	public void setFansNum(Integer fansNum) {
		this.fansNum = fansNum;
	}
	
  
}
