/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.sns;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionContext;

import cn.magme.common.FileOperate;
import cn.magme.common.JsonResult;
import cn.magme.constants.WebConstant;
import cn.magme.pojo.Publisher;
import cn.magme.pojo.User;
import cn.magme.pojo.sns.UserEx;
import cn.magme.service.PublisherService;
import cn.magme.service.UserService;
import cn.magme.service.sns.SnsUserIndexService;
import cn.magme.service.sns.UserExService;
import cn.magme.util.DateUtil;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

/**
 * @author billy
 * @date 2012-06-06
 * @version $id$
 */
@Results({ 
	@Result(name = "apply_json", type = "json", params = { "root", "jsonResult", "contentType", "text/html" }),
	@Result(name = "json", type = "json", params = { "root", "jsonResult"}),
	@Result(name = "success", location = "/WEB-INF/pages/sns/sns_config.ftl")
	})
public class InfoUserAction extends BaseAction {
	private static final long serialVersionUID = -2460942123888842000L;
	@Resource
	private UserExService userExService;
	@Resource
	private UserService userService;
	@Resource
	private PublisherService publisherService;
	
	private UserEx userEx;
	
	private String nameZh;
	private String phone;
	private String publisher;
	private String office;
	private String imgFileName;
	private File imgFile;
	

	private Integer attention;
	private Integer creative;
	private Integer fans;

	@Resource
	private SnsUserIndexService snsUserIndexService;

	@Override
	public String execute() {
		User user = userService.getUserById(getSessionUserId());
        ActionContext.getContext().getSession().put(WebConstant.SESSION.USER, user);
		statsMap();
		userEx = userExService.getByUserId(getSessionUserId());
		return "success";
	}

	/**
	 * 查询用户的统计信息
	 */
	private void statsMap() {
		attention = snsUserIndexService.getAttention(this.getSessionUserId());
		creative = snsUserIndexService.getCreativeCount(this.getSessionUserId());
		fans = snsUserIndexService.getFans(this.getSessionUserId());
	}
	
	public String validateDetail(){
		this.jsonResult = JsonResult.getFailure();
		if(office == null || publisher == null || phone == null || nameZh == null){
			this.jsonResult.setMessage("请输入完整信息！");
			return JSON;
		} else {
			Publisher p = publisherService.queryByName(publisher);
			if(p == null){
				this.jsonResult.setMessage("出版商不存在");
				return JSON;
			}
		}
		this.jsonResult = JsonResult.getSuccess();
		return JSON;
	}
	
	//模糊查询出版商名称
	public String pname(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		List<String> list = publisherService.getPublisherName("%"+publisher.replace("%", "")+"%");
		
		
		this.jsonResult.put("name", list);
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		return JSON;
	}
	
	
	public String apply(){
		this.jsonResult = JsonResult.getFailure();
		if(imgFile == null || office == null || publisher == null
				|| phone == null || nameZh == null || imgFileName == null){
			this.jsonResult.setMessage("请输入完整信息！");
		} else {
			//queryByName方法查询的是username或publishname
			Publisher p = publisherService.queryByName(publisher);
			if(p != null){
				Long userId = this.getSessionUserId();
				this.userEx = userExService.getByUserId(userId);
				boolean exists = true;
				if(userEx == null) {
					exists = false;
					userEx = new UserEx();
					userEx.setUserId(userId);
				}
				boolean success = upload();
				if(success){
					userEx.setPublisher(p.getId());
					userEx.setPhone(phone);
					userEx.setAudit(0);
					userEx.setOffice(office);
					userEx.setNameZh(nameZh);
					userEx.setIsRecommend(0);
					if(exists){
						this.jsonResult = userExService.updateUserEx(userEx);
					} else {
						this.jsonResult = userExService.insertUserEx(userEx);
					}
				} else {
					this.jsonResult.setMessage("请选择正确的图片文件格式上传，仅支持png,jpg,jpeg,gif格式");
				}
			} else {
				this.jsonResult.setMessage("出版商不存在");
			}
		}
		return "apply_json";
	}

    /**
     * 图片上传
     * @return 
     */
    public boolean upload() {
    	String format = (imgFileName.substring(imgFileName.lastIndexOf("."))).toLowerCase();
    	String[] formats = {".png",".jpg",".jpeg",".gif"};
    	boolean valide = false;
    	for (String f : formats) {
			if(f.equals(format)){
				valide = true;
				break;
			}
		}
    	if(!valide) return false;
        FileOperate fileOp = new FileOperate();
        //放在用户头像目录下的ex子文件夹中
        Long userId = this.getSessionUserId();
		String rootPath = systemProp.getProfileLocalUrlTmp() + File.separator + userId + File.separator + "ex";
        if (!new File(rootPath).exists()) {
            fileOp.newFolder(rootPath);
        }

        String currentDate = DateUtil.format(new Date(), "yyyyMMdd");
        String path = rootPath + File.separator + currentDate;
        if (!new File(path).exists()) {
            fileOp.newFolder(path);
        }
        String newImgFileName = userId + "_" + new Date().getTime() + format;
        String newImgFilePath = path + File.separator + newImgFileName;

        fileOp.moveFile(imgFile.getAbsolutePath(), newImgFilePath);

        userEx.setImgPath("/" + currentDate + "/" + newImgFileName);
        return true;
    }
	public void setUserEx(UserEx userEx) {
		this.userEx = userEx;
	}

	public UserEx getUserEx() {
		return userEx;
	}

	public void setImgFileName(String imgFileName) {
		this.imgFileName = imgFileName;
	}

	public String getImgFileName() {
		return imgFileName;
	}

	public void setImgFile(File imgFile) {
		this.imgFile = imgFile;
	}

	public File getImgFile() {
		return imgFile;
	}

	public String getNameZh() {
		return nameZh;
	}

	public void setNameZh(String nameZh) {
		this.nameZh = nameZh;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	public Integer getAttention() {
		return attention;
	}

	public void setAttention(Integer attention) {
		this.attention = attention;
	}

	public Integer getCreative() {
		return creative;
	}

	public void setCreative(Integer creative) {
		this.creative = creative;
	}

	public Integer getFans() {
		return fans;
	}

	public void setFans(Integer fans) {
		this.fans = fans;
	}
}
