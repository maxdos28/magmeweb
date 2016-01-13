package cn.magme.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.pojo.User;
import cn.magme.service.UserService;

@Results({@Result(name="index_success",location="/WEB-INF/pages/userImageMaster/index.ftl"),
	@Result(name="introduce_success",location="/WEB-INF/pages/userImageMaster/introduce.ftl"),
	@Result(name="delivery_success",location="/WEB-INF/pages/userImageMaster/delivery.ftl")})
@SuppressWarnings("serial")
public class UserImageMasterAction extends BaseAction {

    @Resource
    private UserService userService;
    
	public String index(){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("begin", 0);
        map.put("size", 10);
        this.userImageMasterList=this.userService.getUserImageMasterList(map);	    
		return "index_success";
	}
	
	public String introduce(){
		return "introduce_success";
	}
	
	public String delivery(){
		return "delivery_success";
	}
	
	private List<User> userImageMasterList;

	public List<User> getUserImageMasterList() {
		return userImageMasterList;
	}

	public void setUserImageMasterList(List<User> userImageMasterList) {
		this.userImageMasterList = userImageMasterList;
	}
}
