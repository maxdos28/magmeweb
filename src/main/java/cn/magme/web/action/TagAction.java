/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action;

import javax.annotation.Resource;

import cn.magme.common.JsonResult;
import cn.magme.pojo.Tag;
import cn.magme.service.TagService;

/**
 * @author jacky_zhou
 * @date 2011-10-12
 * @version $id$
 */
@SuppressWarnings("serial")
public class TagAction extends BaseAction {
    @Resource
    private TagService tagService;
    
    /**
     * 添加标签
     */
    public String addJson(){
        Tag tag=new Tag();
        tag.setName(this.name);
        tag.setObjectId(this.objectId);
        tag.setType(this.type);
        tag.setCreatedBy(this.getSessionUserId());
        tag.setPageNum(this.pageNum);
        this.jsonResult=tagService.insert(tag);
        return JSON;
    }
    
    /**
     * 显示对应的tag集合
     * @return
     */
    public String listTagJson(){
    	Tag tag=new Tag();
        tag.setObjectId(this.objectId);
        tag.setType(this.type);
        tag.setPageNum(this.pageNum);
        this.jsonResult= new JsonResult();
        this.jsonResult.put("tagList", tagService.getByTag(tag));
    	return JSON;
    }
    
    private String name;
    private Long objectId;
    private Integer type;
    private Integer pageNum;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
    
    
}
