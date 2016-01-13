package cn.magme.web.entity;

import java.io.Serializable;

public class AdposAndAdDO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7272843950089280285L;
	
	/**
	 * 映射id
	 */
	private Long admappingId;
	/**
	 * 映射状态
	 */
	private Integer status;
	
	/**
	 * 广告位的信息
	 */
	private Long adposId;
	/**
	 * 页码
	 */
	private Integer pageNo;
	/**
	 * 期刊id
	 */
	private Long issueId;
	/**
	 * 位置信息,对应广告位的posx
	 */
	private Integer xOffset;
	/**
	 * 位置信息,对应广告位的posy
	 */
	private Integer yOffset;
	/**
	 * 位置信息,对应广告位的posx
	 */
	private Float width;
	/**
	 * 位置信息,对应广告位的posx
	 */
	private Float height;
	
	/**
	 * 广告id
	 */
	private Long adId;
	/**
	 * 对应广告的title
	 */
	private String name;
	/**
	 * 广告的描述
	 */
	private String description;
	/**
	 * 广告类型adType
	 */
	private int iaType;
	/**
	 * 广告链接
	 */
	private String linkurl;
	private String imgurl;
	private String mediaurl;
	
	
	
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getAdmappingId() {
		return admappingId;
	}
	public void setAdmappingId(Long admappingId) {
		this.admappingId = admappingId;
	}
	public Long getAdposId() {
		return adposId;
	}
	public void setAdposId(Long adposId) {
		this.adposId = adposId;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Long getIssueId() {
		return issueId;
	}
	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}
	public Integer getxOffset() {
		return xOffset;
	}
	public void setxOffset(Integer xOffset) {
		this.xOffset = xOffset;
	}
	public Integer getyOffset() {
		return yOffset;
	}
	public void setyOffset(Integer yOffset) {
		this.yOffset = yOffset;
	}
	public Float getWidth() {
		return width;
	}
	public void setWidth(Float width) {
		this.width = width;
	}
	public Float getHeight() {
		return height;
	}
	public void setHeight(Float height) {
		this.height = height;
	}
	public Long getAdId() {
		return adId;
	}
	public void setAdId(Long adId) {
		this.adId = adId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getIaType() {
		return iaType;
	}
	public void setIaType(int iaType) {
		this.iaType = iaType;
	}
	public String getLinkurl() {
		return linkurl;
	}
	public void setLinkurl(String linkurl) {
		this.linkurl = linkurl;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public String getMediaurl() {
		return mediaurl;
	}
	public void setMediaurl(String mediaurl) {
		this.mediaurl = mediaurl;
	}
	
	
	
	

}
