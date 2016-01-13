package cn.magme.web.action.admin;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;

import cn.magme.common.FileOperate;
import cn.magme.common.JsonResult;
import cn.magme.pojo.Sort;
import cn.magme.service.SortService;

public class SortAction extends BaseAction {

	private static final long serialVersionUID = -7870872641067870322L;
	
	@Resource
	private SortService sortService;
	
	public String addJson(){
		Sort sort=new Sort();
		sort.setName(this.name);
		sort.setGroup(this.group);
		sort.setPath(this.path);
		this.jsonResult=sortService.insert(sort);
		return JSON;
	}
	
	public String deleteJson(){
		sortService.deleteById(this.id);
		this.jsonResult=this.generateJsonResult(JsonResult.CODE.SUCCESS, JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}
	
	public String updateJson(){
		Sort sort=new Sort();
		sort.setId(this.id);
		sort.setName(this.name);
		sort.setGroup(this.group);
		sort.setPath(this.path);
		this.jsonResult=sortService.update(sort);
		return JSON;
	}
	
	public String getListJson(){
		List<Sort> sortList=sortService.getAllList();
		this.jsonResult=this.generateJsonResult(JsonResult.CODE.SUCCESS, JsonResult.MESSAGE.SUCCESS);
		jsonResult.put("sortList", sortList);
		return JSON;
	}
	
    /**
     * 图片上传
     */
    public String uploadJson() {
        //目前path临时用fpageimage
        FileOperate fileOp = new FileOperate();
        String rootPath = systemProp.getStaticLocalUrl() + File.separator + "sort";
        if (!new File(rootPath).exists()) {
            fileOp.newFolder(rootPath);
        }

        String newImgFileName = System.currentTimeMillis()
                + imgFileName.substring(imgFileName.lastIndexOf(".")).toLowerCase();
        String newImgFilePath = rootPath + File.separator + newImgFileName;

        fileOp.moveFile(img.getAbsolutePath(), newImgFilePath);

    	ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8"); 
        try {
			ServletActionContext
			        .getResponse()
			        .getWriter()
			        .print("{success:true,newImgFileName:'/sort/"+newImgFileName+ "'}");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}        
       return null;
    }	
	
	private String name;
	private Long id;
	private String group;
	private String path;
    private File img;
    private String imgFileName;
    private String newImgFileName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public File getImg() {
		return img;
	}

	public void setImg(File img) {
		this.img = img;
	}

	public String getImgFileName() {
		return imgFileName;
	}

	public void setImgFileName(String imgFileName) {
		this.imgFileName = imgFileName;
	}

	public String getNewImgFileName() {
		return newImgFileName;
	}

	public void setNewImgFileName(String newImgFileName) {
		this.newImgFileName = newImgFileName;
	}
}