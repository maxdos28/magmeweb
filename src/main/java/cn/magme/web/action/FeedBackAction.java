/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.constants.PojoConstant;
import cn.magme.pojo.FeedBack;
import cn.magme.pojo.FeedBackCategory;
import cn.magme.service.FeedBackCategoryService;
import cn.magme.service.FeedBackService;

/**
 * 意见反馈
 * @author jacky_zhou
 * @date 2011-8-4
 * @version $id$
 */
@Results({@Result(name="editAjax",location="/WEB-INF/pages/dialog/feedback.ftl")})
@SuppressWarnings("serial")
public class FeedBackAction extends BaseAction {

    @Resource
    private FeedBackService feedBackService;
    
    @Resource
    private FeedBackCategoryService feedBackCategoryService;
    
    /**
     * 获取意见反馈弹出框里的内容
     * @return
     */
    public String editAjax(){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("status", PojoConstant.FEEDBACKCATEGORY.STATUS_OK);
        this.feedBackCategoryList=feedBackCategoryService.getFeedBackCategoryList(map);
        return "editAjax";
    }    
    
    /**
     * 保存用户的意见反馈
     * @return
     */
    public String saveJson(){
        FeedBack feedBack=new FeedBack();
        feedBack.setCategoryId(this.categoryId);
        feedBack.setContent(this.content);
        feedBack.setUserId(this.getSessionUserId());
        feedBack.setIpAddress(this.getRequestIpLong());
        feedBack.setProvince(this.getRequestProvince());
        feedBack.setCity(this.getRequestCity());
        this.jsonResult=feedBackService.insertFeedBack(feedBack);
        return JSON;
    }
    
    private List<FeedBackCategory> feedBackCategoryList;
    private Long categoryId;
    private String content;
    private String authcode;
    
    public List<FeedBackCategory> getFeedBackCategoryList() {
        return feedBackCategoryList;
    }

    public void setFeedBackCategoryList(List<FeedBackCategory> feedBackCategoryList) {
        this.feedBackCategoryList = feedBackCategoryList;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthcode() {
        return authcode;
    }

    public void setAuthcode(String authcode) {
        this.authcode = authcode;
    }
}
