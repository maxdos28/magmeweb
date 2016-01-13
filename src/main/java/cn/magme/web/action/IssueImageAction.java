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
import cn.magme.pojo.FpageEvent;
import cn.magme.pojo.Tag;
import cn.magme.service.FpageEventService;
import cn.magme.service.PopularService;
import cn.magme.service.TagService;
import cn.magme.service.UserEnjoyService;

/**
 * 事件图片
 * @author jacky_zhou
 * @date 2011-10-8
 * @version $id$
 */
@Results({@Result(name="show",location="/WEB-INF/pages/issueImage/show.ftl")})
@SuppressWarnings("serial")
public class IssueImageAction  extends BaseAction {
    @Resource
    private FpageEventService fpageEventService;
    
    @Resource
    private TagService tagService;
    
    @Resource
    private UserEnjoyService userEnjoyService;
    
    @Resource
    private PopularService popularService;
    
    /**
     * 事件图片最终页
     * @return
     */
    public String show(){
        //事件图片的信息
        this.issueImage=fpageEventService.getFpageEventById(this.id);
        if(this.issueImage!=null&&!PojoConstant.FPAGEEVENT.STATUS_DELETE.equals(this.issueImage.getStatus())){
            //前6条相关图片
            this.preIssueImageList=fpageEventService.getPreFpageEventList(this.id,0,6);
            //后6条相关图片
            this.sufIssueImageList=fpageEventService.getSufFpageEventList(this.id,0,6);
            //事件图片的标签,按热度排列
            this.tagList=tagService.getTagListByTypeAndObjectId(PojoConstant.TAG.TYPE_EVENT, this.id, null, null);
            this.enjoyIssueImageList=fpageEventService.getAlsoEnjoyFpageEventListById(this.id, 0, 8);
            //是否喜欢:1-是,0-否
            this.isEnjoy=userEnjoyService.isEnjoy(this.getSessionUserId(),this.id,PojoConstant.USERENJOY.TYPE_EVENT);
            
            //对应的点击数+1
            popularService.click(this.id, PojoConstant.POPULAR.TYPE_EVENT);
        }
        return "show";
    }
    
    private Long id;
    private FpageEvent issueImage;
    private List<FpageEvent> issueImageList;
    private List<FpageEvent> preIssueImageList;
    private List<FpageEvent> sufIssueImageList;
    private List<Tag> tagList;
    private List<FpageEvent> enjoyIssueImageList;
    private Integer isEnjoy;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public FpageEvent getIssueImage() {
		return issueImage;
	}
	public void setIssueImage(FpageEvent issueImage) {
		this.issueImage = issueImage;
	}
	public List<FpageEvent> getIssueImageList() {
		return issueImageList;
	}
	public void setIssueImageList(List<FpageEvent> issueImageList) {
		this.issueImageList = issueImageList;
	}
	public List<FpageEvent> getPreIssueImageList() {
		return preIssueImageList;
	}
	public void setPreIssueImageList(List<FpageEvent> preIssueImageList) {
		this.preIssueImageList = preIssueImageList;
	}
	public List<FpageEvent> getSufIssueImageList() {
		return sufIssueImageList;
	}
	public void setSufIssueImageList(List<FpageEvent> sufIssueImageList) {
		this.sufIssueImageList = sufIssueImageList;
	}
	public List<Tag> getTagList() {
		return tagList;
	}
	public void setTagList(List<Tag> tagList) {
		this.tagList = tagList;
	}
	public List<FpageEvent> getEnjoyIssueImageList() {
		return enjoyIssueImageList;
	}
	public void setEnjoyIssueImageList(List<FpageEvent> enjoyIssueImageList) {
		this.enjoyIssueImageList = enjoyIssueImageList;
	}
	public Integer getIsEnjoy() {
		return isEnjoy;
	}
	public void setIsEnjoy(Integer isEnjoy) {
		this.isEnjoy = isEnjoy;
	}
}
