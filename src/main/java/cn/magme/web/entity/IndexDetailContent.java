package cn.magme.web.entity;

import java.io.Serializable;
import java.util.List;

import cn.magme.pojo.Tag;

public class IndexDetailContent implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4722306003542679718L;
	
	private List<Tag> tagList;
	
	private String title;
	
	private String description;

	public List<Tag> getTagList() {
		return tagList;
	}

	public void setTagList(List<Tag> tagList) {
		this.tagList = tagList;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	

}
