/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.third;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import cn.magme.common.ThirdConfig;
import cn.magme.constants.PojoConstant;
import cn.magme.constants.WebConstant;
import cn.magme.pojo.User;
import cn.magme.service.UserService;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

import com.opensymphony.xwork2.ActionContext;
import com.renren.api.client.RenrenApiClient;
import com.renren.api.client.utils.HttpURLUtils;

/**
 * @author qiaowei
 * @date 2011-8-19
 * @version $id$
 */
@SuppressWarnings("serial")
@Results({ @Result(name = "returnUri", type = "redirect", location = "/qqlogin.html?flag=${flag}"),
            @Result(name = "index", type = "redirect", location = "/index.action")  })
public class ThirdLoginAction extends BaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Resource
    private ThirdConfig thirdConfig;

    @Resource
    private UserService userService;

    private String code;

    private String id;

    private String name;

    private String imageUrl;

    private String gender;
    
    private String address;

    private String flag = "false";

    public String sina() {
        if (StringUtil.isBlank(id) || StringUtil.isBlank(name)) {
            //缺乏有效参数，跳转到登录页去
            return "index";
        }

        String userName = id + "@weibo.com";
        User user = this.userService.getUserByName(userName);
        if (user == null || user.getId() == null) {
            flag = "true";
            user = new User();
            user.setUserName(userName);
            user.setNickName(name);
            user.setType(PojoConstant.USER.USER_TYPE_WB);
            user.setEmail(userName);
            user.setPassword("");
            user.setGender(gender.equals("m") ? 1 : 2);
            user.setAddress(address);
            user.setStatus(PojoConstant.USER.STATUS_ACTIVE);
            //user.setReserve1(accessToken); //临时字段1,用来暂存access_token //每个人人账号唯一
            //添加用户
            user = this.userService.addUser(user, imageUrl);
        }
        this.userService.updateUserLastLoginTime(user.getId());

        //查询用户的统计信息
        if (user != null) {
            user.setStatsMap(this.userService.getUserStatsMapByUserId(user.getId()));
            userService.setUserEnjoyList(user);
        }
        //写入当前session
        ServletActionContext.getRequest().getSession().setAttribute(WebConstant.SESSION.USER, user);

        //已登录，跳转到个人主页
        return "returnUri";
    }

    public String baidu() {
        if (StringUtil.isBlank(id) || StringUtil.isBlank(name)) {
            //缺乏有效参数，跳转到登录页去
            return "index";
        }

        String userName = id + "@baidu.com";
        User user = this.userService.getUserByName(userName);
        if (user == null || user.getId() == null) {
            flag = "true";
            user = new User();
            user.setUserName(userName);
            user.setNickName(name);
            user.setType(PojoConstant.USER.USER_TYPE_BD);
            user.setEmail(userName);
            user.setPassword("");
            //user.setGender(gender.equals("m") ? 1 : 2);
            //user.setAddress(address);
            user.setStatus(PojoConstant.USER.STATUS_ACTIVE);
            //user.setReserve1(accessToken); //临时字段1,用来暂存access_token //每个人人账号唯一
            //添加用户
            user = this.userService.addUser(user, imageUrl);
        }
        this.userService.updateUserLastLoginTime(user.getId());

        //查询用户的统计信息
        if (user != null) {
            user.setStatsMap(this.userService.getUserStatsMapByUserId(user.getId()));
            userService.setUserEnjoyList(user);
        }
        //写入当前session
        ServletActionContext.getRequest().getSession().setAttribute(WebConstant.SESSION.USER, user);

        //已登录，跳转到个人主页
        return "returnUri";
    }    
    
    public String renren() {
        if (StringUtil.isBlank(code)) {
            //缺乏有效参数，跳转到登录页去
            return "index";
        }

        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("client_id", thirdConfig.getRrApiKey());
        parameters.put("client_secret", thirdConfig.getRrSecret());
        parameters.put("redirect_uri", systemProp.getDomain() + thirdConfig.getRrRedirectUri());//这个redirect_uri要和之前传给authorization endpoint的值一样
        parameters.put("grant_type", thirdConfig.getRrGrantType());
        parameters.put("code", code);
        String tokenResult = HttpURLUtils.doPost(thirdConfig.getRrOauthTokenEndpoint(), parameters);
        JSONObject tokenJson = (JSONObject) JSONValue.parse(tokenResult);
        if (tokenJson != null) {
            String accessToken = (String) tokenJson.get("access_token");
            Long expiresIn = (Long) tokenJson.get("expires_in");//距离过期时的时间段（秒数）
            long currentTime = System.currentTimeMillis() / 1000;
            long expiresTime = currentTime + expiresIn;//即将过期的时间点（秒数）
            ActionContext.getContext().getSession().put("expiresTime", expiresTime);
            //调用人人网API获得用户信息
            RenrenApiClient apiClient = new RenrenApiClient(accessToken, true);
            int rrUid = apiClient.getUserService().getLoggedInUser();
            JSONArray userInfo = apiClient.getUserService().getInfo(String.valueOf(rrUid), "name,headurl");
            if (userInfo != null && userInfo.size() > 0) {
                JSONObject currentUser = (JSONObject) userInfo.get(0);
                if (currentUser != null) {
                    String name = (String) currentUser.get("name");
                    String headurl = (String) currentUser.get("headurl");
                    String userName = rrUid + "@renren.com";
                    User user = this.userService.getUserByName(userName);
                    if (user == null || user.getId() == null) {
                        flag = "true";
                        user = new User();
                        user.setUserName(userName);
                        user.setNickName(name);
                        user.setType(PojoConstant.USER.USER_TYPE_RR);
                        user.setEmail(userName);
                        user.setPassword("");
                        user.setStatus(PojoConstant.USER.STATUS_ACTIVE);
                        user.setReserve1(accessToken); //临时字段1,用来暂存access_token //每个人人账号唯一
                        //添加用户
                        user = this.userService.addUser(user, headurl);
                    }
                    this.userService.updateUserLastLoginTime(user.getId());

                    //查询用户的统计信息
                    if (user != null) {
                        user.setStatsMap(this.userService.getUserStatsMapByUserId(user.getId()));
                        userService.setUserEnjoyList(user);
                    }
                    //写入当前session
                    ServletActionContext.getRequest().getSession().setAttribute(WebConstant.SESSION.USER, user);

                    //已登录，跳转到个人主页
                    return "returnUri";
                }
            }
        }
        return "returnUri";

    }

    public String kaixin() {
        if (StringUtil.isBlank(code)) {
            //缺乏有效参数，跳转到登录页去
            return "index";
        }

        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("client_id", thirdConfig.getKxApiKey());
        parameters.put("client_secret", thirdConfig.getKxSecret());
        parameters.put("redirect_uri", systemProp.getDomain() + thirdConfig.getKxRedirectUri());//这个redirect_uri要和之前传给authorization endpoint的值一样
        parameters.put("grant_type", thirdConfig.getKxGrantType());
        parameters.put("code", code);
        String tokenResult = HttpURLUtils.doPost(thirdConfig.getKxOauthTokenEndpoint(), parameters);
        JSONObject tokenJson = (JSONObject) JSONValue.parse(tokenResult);
        if (tokenJson != null) {
            String accessToken = (String) tokenJson.get("access_token");
            Long expiresIn = (Long) tokenJson.get("expires_in");//距离过期时的时间段（秒数）
            long currentTime = System.currentTimeMillis() / 1000;
            long expiresTime = currentTime + expiresIn;//即将过期的时间点（秒数）
            ActionContext.getContext().getSession().put("expiresTime", expiresTime);
            //调用开心网API获得用户信息
            parameters = new HashMap<String, String>();
            parameters.put("access_token", accessToken);
            String userResult = HttpURLUtils.doGet(thirdConfig.getKxUserJsonUri() + "?access_token=" + accessToken);

            if (userResult != null && userResult.length() > 0) {
                JSONObject userJson = (JSONObject) JSONValue.parse(userResult);
                if (userJson != null && userJson.size() > 0) {
                    String uid = (String) userJson.get("uid");
                    String name = (String) userJson.get("name");
                    String gender = (String) userJson.get("gender");
                    int i_gender = 0;
                    if (gender.equals("0")) {
                        i_gender = 1;
                    } else if (gender.equals("1")) {
                        i_gender = 2;
                    }
                    String logo50 = (String) userJson.get("logo50");

                    String userName = uid + "@kaixin.com";
                    User user = this.userService.getUserByName(userName);
                    if (user == null || user.getId() == null) {
                        flag = "true";
                        user = new User();
                        user.setUserName(userName);
                        user.setNickName(name);
                        user.setType(PojoConstant.USER.USER_TYPE_KX);
                        user.setEmail(userName);
                        user.setPassword("");
                        user.setGender(i_gender);
                        user.setStatus(PojoConstant.USER.STATUS_ACTIVE);
                        user.setReserve1(accessToken); //临时字段1,用来暂存access_token //每个人人账号唯一
                        //添加用户
                        user = this.userService.addUser(user, logo50);
                    }
                    this.userService.updateUserLastLoginTime(user.getId());

                    //查询用户的统计信息
                    if (user != null) {
                        user.setStatsMap(this.userService.getUserStatsMapByUserId(user.getId()));
                        userService.setUserEnjoyList(user);
                    }
                    //写入当前session
                    ServletActionContext.getRequest().getSession().setAttribute(WebConstant.SESSION.USER, user);

                    //已登录，跳转到个人主页
                    return "returnUri";
                }
            }
        }
        return "returnUri";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
