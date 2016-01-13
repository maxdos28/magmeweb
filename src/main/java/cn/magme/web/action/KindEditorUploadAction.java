/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action;

import java.io.File;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.service.EventOpusService;

/**
 * 处理文本编辑器的图片上传
 * @author jacky_zhou
 * @date 2011-7-8
 * @version $id$
 */
@SuppressWarnings("serial")
@Results({@Result(name="upload_json",type="json",params={"root","eventUploadJsonResult","contentType","text/html"})})
public class KindEditorUploadAction extends BaseAction {
    @Resource
    private EventOpusService eventOpusService;    

    /**
     * 保存从KindEditor上传的图片
     * @return
     */
    public String imageUploadJson(){
        this.eventUploadJsonResult=eventOpusService.saveEventUpload(imgFile,
                imgFileFileName,imgFileContentType,this.getSessionUserId());
        return "upload_json";
    }

    private File imgFile;
    private String imgFileFileName;
    private String imgFileContentType;
    private Map<String,Object> eventUploadJsonResult;

    public File getImgFile() {
        return imgFile;
    }
    public void setImgFile(File imgFile) {
        this.imgFile = imgFile;
    }
    public String getImgFileFileName() {
        return imgFileFileName;
    }
    public void setImgFileFileName(String imgFileFileName) {
        this.imgFileFileName = imgFileFileName;
    }
    public String getImgFileContentType() {
        return imgFileContentType;
    }
    public void setImgFileContentType(String imgFileContentType) {
        this.imgFileContentType = imgFileContentType;
    }
    public Map<String, Object> getEventUploadJsonResult() {
        return eventUploadJsonResult;
    }
    public void setEventUploadJsonResult(Map<String, Object> eventUploadJsonResult) {
        this.eventUploadJsonResult = eventUploadJsonResult;
    }
}
