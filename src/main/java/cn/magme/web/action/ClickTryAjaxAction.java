/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.constants.PojoConstant;
import cn.magme.pojo.FeedBack;
import cn.magme.pojo.FeedBackCategory;
import cn.magme.pojo.Tag;
import cn.magme.pojo.User;
import cn.magme.service.TagService;

/**
 * 意见反馈
 * 
 * @author jacky_zhou
 * @date 2011-8-4
 * @version $id$
 */
@Results({ @Result(name = "edit_success", location = "/WEB-INF/pages/static/feedback.ftl"),
        @Result(name = "editAjax", location = "/WEB-INF/pages/dialog/feedback.ftl") })
@SuppressWarnings("serial")
public class ClickTryAjaxAction extends BaseAction {

    @Resource
    private TagService tagService;

    /**
     * 添加标签
     * 
     * @return
     */
    public String addTagJson() {
        User user = this.getSessionUser();

        Tag tag = new Tag();
        tag.setCreatedBy(user.getId());
        tag.setType(tagType);
        tag.setName(tagName);
        tag.setObjectId(objectId);
        this.jsonResult = tagService.insert(tag);
        this.tagList = tagService.getTagListByTypeAndObjectId(tagType, objectId, 0, 20);
        this.jsonResult.put("tagList", tagList);

        return JSON;
    }

    private List<Tag> tagList;
    private Integer tagType;
    private String tagName;
    private Long objectId;

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    public Integer getTagType() {
        return tagType;
    }

    public void setTagType(Integer tagType) {
        this.tagType = tagType;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

}
