/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.admin;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.processors.JsonBeanProcessor;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.magme.common.FileOperate;
import cn.magme.common.JsonResult;
import cn.magme.common.SystemProp;
import cn.magme.constants.PojoConstant;
import cn.magme.constants.WebConstant;
import cn.magme.pojo.Admin;
import cn.magme.pojo.FpageEvent;
import cn.magme.pojo.Tag;
import cn.magme.service.FpageEventService;
import cn.magme.service.TagService;
import cn.magme.util.DateUtil;
import cn.magme.util.ImageUtil;
import cn.magme.util.StringUtil;
import cn.magme.util.ToJson;

import com.opensymphony.xwork2.ActionContext;

/**
 * 首页模板管理
 * 
 * @author guozheng
 * @date 2011-5-20
 * @version $id$
 */
@SuppressWarnings("serial")
public class FPageEventAction extends BaseAction {

    private Logger log = Logger.getLogger(this.getClass());
    private String type;
    private File img;
    private String imgFileName;
    private String newImgFileName;
    private String uploadFileId;
    private String ids;
    private FpageEvent fpageEvent;
    /**
     * 标签 可以使集合
     */
    private String tagStr;

    @Resource
    private FpageEventService fpageEventService;
    @Resource
    private SystemProp systemProp;
    
    

    /**
     * 图片上传
     */
    public void upload() {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=UTF-8");

        //目前path临时用fpageimage
        FileOperate fileOp = new FileOperate();
        String rootPath = systemProp.getFpageLocalUrl() + File.separator + "event";
        if (!new File(rootPath).exists()) {
            fileOp.newFolder(rootPath);
        }

        String currentDate = DateUtil.format(new Date(), "yyyyMMdd");
        String path = rootPath + File.separator + currentDate;
        if (!new File(path).exists()) {
            fileOp.newFolder(path);
        }
        String newImgFileName = System.currentTimeMillis()
                + imgFileName.substring(imgFileName.lastIndexOf(".")).toLowerCase();
        String newImgFilePath = path + File.separator + newImgFileName;

        fileOp.moveFile(img.getAbsolutePath(), newImgFilePath);

        ImageUtil.smallerByRatio(newImgFilePath, path + File.separator + "0.7_" + newImgFileName, 0.7f);
        ImageUtil.smallerTag(newImgFilePath,path + File.separator + "68_" + newImgFileName, 68, 68, 3);


        Integer imgWidth = ImageUtil.getWidth(newImgFilePath);
        Integer imgHeight = ImageUtil.getHeight(newImgFilePath);
//
//        //        FpageInfo fpageInfo = new FpageInfo();
//        //        fpageInfo.setId(Long.parseLong(uploadFileId));
//        //        fpageInfo.setImgFile(newImgFileName);
//        //        this.fpageInfoService.update(fpageInfo);
//
        //改版后，图片尺寸不限制 20120704
        try {
//            if ((imgWidth != 200 && imgWidth != 436) || (imgHeight != 200 && imgHeight != 436)) {
//                response.getWriter().print("{success:'false',msg:'您上传的图片大小不符合规格,请重新上传'}");
//            } else {
	            fpageEvent.setWidth((float)imgWidth);
	            fpageEvent.setHeight((float)imgHeight);
                response.getWriter().print(
                        "{success:'true',newImgFileName:'" + ("/" + currentDate + "/" + newImgFileName) + "', width: "
                                + imgWidth + ", height: " + imgHeight + "}");
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 首页管理：分页查询
     */
    public void page() {
        log.info("searching page:" + page.getCurPage());
        page = this.fpageEventService.getByPage(page, fpageEvent);
        String info = ToJson.object2json(page);
        print(info);
    }

    /**
     * 首页管理：删除
     */
    public void delete() {
        log.info("delete ids:" + ids);
        /*
         * if(ids == null || ids.isEmpty()){
         * print("{success:true,message:ids is empty}"); return; }
         */
        String[] strArr = ids.split(",");
        this.fpageEventService.delete(super.strArrToLongArr(strArr));
    }

    /**
     * 首页管理：插入或更新
     */
    public void commit() {
        //Object[] arr = super.toJavaArr(info, FpageEvent.class);
        //FpageEvent[] events = castToFpageEvent(arr);
        
        JSONArray jsonArray = new JSONArray();
        jsonArray = jsonArray.fromObject(info);
        FpageEvent[] events = new FpageEvent[jsonArray.size()];
        if (jsonArray.size() > 0) {
            for (int i = 0;i < jsonArray.size();i++) {
                events[i] = new FpageEvent();
                JSONObject jsonObject =  jsonArray.getJSONObject(i);
                events[i].setId(Long.parseLong(jsonObject.getString("id")));
                events[i].setWeight(jsonObject.getInt("weight"));
                events[i].setTitle(jsonObject.getString("title"));
                events[i].setDescription(jsonObject.getString("description"));
                events[i].setPublicationId(jsonObject.getLong("publicationId"));
                events[i].setIssueId(jsonObject.getLong("issueId"));
                events[i].setPageNo(jsonObject.getInt("pageNo"));
                events[i].setEndPageNo(jsonObject.getInt("endPageNo"));
                events[i].setImgFile(jsonObject.getString("imgFile"));
                events[i].setWidth(Float.parseFloat(jsonObject.getString("width")));
                events[i].setHeight(Float.parseFloat(jsonObject.getString("height")));
                events[i].setStatus(jsonObject.getInt("status"));
                events[i].setIsPubCover(jsonObject.getInt("isPubCover"));
                events[i].setIsRecommend(jsonObject.getInt("isRecommend"));
                events[i].setIsSuitMobile(jsonObject.getInt("isSuitMobile"));
            }
            this.fpageEventService.commit(events);
        }
    }

    /**
     * 数组类型转换 Object[] => FpageInfo[]
     * 
     * @param arr
     * @return
     */
    private FpageEvent[] castToFpageEvent(Object[] arr) {
        FpageEvent[] events = new FpageEvent[arr.length];
        for (int i = 0; i < events.length; i++) {
            events[i] = (FpageEvent) arr[i];
        }
        return events;
    }

    /**
     * 首页管理：生效
     */
    public void valid() {
        String[] ids_arr = ids.split(",");
        for (String id : ids_arr) {
            FpageEvent fpageEvent = new FpageEvent();
            fpageEvent.setId(Long.parseLong(id.toString()));
            fpageEvent.setStatus(PojoConstant.FPAGE.STATUS_VALID);
            this.fpageEventService.update(fpageEvent);
        }
    }

    /**
     * 首页管理：失效
     */
    public void invalid() {
        String[] ids_arr = ids.split(",");
        for (String id : ids_arr) {
            FpageEvent fpageEvent = new FpageEvent();
            fpageEvent.setId(Long.parseLong(id.toString()));
            fpageEvent.setStatus(PojoConstant.FPAGE.STATUS_INVALID);
            this.fpageEventService.update(fpageEvent);
        }
    }

    private Long issueId;
    private Integer pageNo;
    private Integer start;
    private Integer end;
    private String description;
    private String title;
    private Float width;
    private Float height;
    private Long adId;

    public String addJson() {
        try {
            ActionContext ctx = ActionContext.getContext();
            HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
            BufferedInputStream inputStream = new BufferedInputStream(request.getInputStream());

            FpageEvent fpageEvent = new FpageEvent();
            fpageEvent.setIssueId(issueId);
            //fpageEvent.setPublicationId(issueService.queryById(issueId).getPublicationId());
            fpageEvent.setPageNo(start);
            fpageEvent.setEndPageNo(end);
            fpageEvent.setDescription(description);
            fpageEvent.setTitle(title);
            if(description!=null){
            	fpageEvent.setDescription(java.net.URLDecoder.decode(description));
            }
            if(title!=null){
            	fpageEvent.setTitle(java.net.URLDecoder.decode(title));
            }
            fpageEvent.setWidth(width);
            fpageEvent.setHeight(height);
            fpageEvent.setAdId(adId);
            System.out.println("--->:"+tagStr);
            String[] arr =null;
            if(StringUtil.isNotBlank(tagStr)){//添加标签(事件)
            	tagStr = java.net.URLDecoder.decode(tagStr);
            	arr= tagStr.split(";");
            }

            this.jsonResult = fpageEventService.addJson(fpageEvent, inputStream,
                    (Admin) ctx.getSession().get(WebConstant.SESSION.ADMIN),arr);

        } catch (Exception e) {
            e.printStackTrace();
            this.generateJsonResult(JsonResult.CODE.EXCEPTION, "服务器内部错误");
        }
        return JSON;
    }

    public String findEventsJson() {
        try {
            List<FpageEvent> fpageEventlist = fpageEventService.getFpageEventListByIssueIdAndPageNo(issueId, pageNo);

            this.jsonResult = new JsonResult();
            this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
            this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
            this.jsonResult.put("fpageEventlist", fpageEventlist);

        } catch (Exception e) {
            e.printStackTrace();
            this.generateJsonResult(JsonResult.CODE.EXCEPTION, "服务器内部错误");
        }
        return JSON;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getUploadFileId() {
        return uploadFileId;
    }

    public void setUploadFileId(String uploadFileId) {
        this.uploadFileId = uploadFileId;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public FpageEvent getFpageEvent() {
        return fpageEvent;
    }

    public void setFpageEvent(FpageEvent fpageEvent) {
        this.fpageEvent = fpageEvent;
    }

	public Long getAdId() {
		return adId;
	}

	public void setAdId(Long adId) {
		this.adId = adId;
	}

	public String getTagStr() {
		return tagStr;
	}

	public void setTagStr(String tagStr) {
		this.tagStr = tagStr;
	}
	
}
