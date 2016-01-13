/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.admin;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import cn.magme.common.FileOperate;
import cn.magme.common.SystemProp;
import cn.magme.constants.PojoConstant;
import cn.magme.pojo.FpageInfo;
import cn.magme.pojo.Issue;
import cn.magme.service.FpageInfoService;
import cn.magme.service.IssueService;
import cn.magme.util.ToJson;

/**
 * 首页管理: "热点新闻","点击排行","麦米号外","一线地带","滚动杂志","热点聚焦(图)",
 *          "热点聚焦(字)","编辑图片","本周推荐","切米","话题","云标签"
 * 
 * @author guozheng
 * @date 2011-5-20
 * @version $id$
 */
@SuppressWarnings("serial")
public class FPageInfoAction extends BaseAction {

    private Logger log = Logger.getLogger(this.getClass());
    private String type;
    private File img;
    private String imgFileName;
    private String newImgFileName;
    private String uploadFileId;
    private String ids;
    @Resource
    private FpageInfoService fpageInfoService;
    @Resource
    private SystemProp systemProp;
    @Resource
    IssueService issueService;
    /**
     * 图片上传
     */
    public void upload() {
        //目前path临时用fpageimage
        String path = systemProp.getFpageLocalUrl() + File.separator + type;

        String newImgFileName = System.currentTimeMillis() + imgFileName.substring(imgFileName.lastIndexOf(".")).toLowerCase();
        String newImgFilePath = path + File.separator + newImgFileName;
        FileOperate fileOp = new FileOperate();
        fileOp.moveFile(img.getAbsolutePath(), newImgFilePath);

//        FpageInfo fpageInfo = new FpageInfo();
//        fpageInfo.setId(Long.parseLong(uploadFileId));
//        fpageInfo.setImgFile(newImgFileName);
//        this.fpageInfoService.update(fpageInfo);
        
        try {
            ServletActionContext.getResponse().getWriter().print("{success:true,newImgFileName:'" + newImgFileName + "'}");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //    /**
    //     * 实现上传方法
    //     * 
    //     * @param file
    //     * @param fileName
    //     * @param filePath
    //     */
    //    public void uploadFile(File file, String fileName, String filePath) {
    //        FileInputStream fis = null;
    //        FileOutputStream fos = null;
    //        try {
    //            fis = new FileInputStream(file);
    //            File f = new File(filePath);
    //            if (!f.exists()) {
    //                f.mkdirs();
    //            }
    //            fos = new FileOutputStream(filePath + fileName);
    //            byte[] bytes = new byte[1024];
    //            while (fis.read(bytes) != -1) {
    //                fos.write(bytes);
    //            }
    //        } catch (FileNotFoundException e) {
    //            e.printStackTrace();
    //        } catch (IOException e) {
    //            e.printStackTrace();
    //        } finally {
    //            try {
    //                fis.close();
    //                fos.close();
    //            } catch (IOException e) {
    //                e.printStackTrace();
    //            }
    //        }
    //    }

    /**
     * 首页管理：分页查询
     */
    public void page() {
        log.info("searching page:" + page.getCurPage());
        page = this.fpageInfoService.getByPage(type, page);
        String info = ToJson.object2json(page);
        print(info);
    }
    
    /**
     * 期刊下拉框: 上架期刊
     */
    public void issue() {
        List<Issue> list = this.issueService.queryAllNormalIssues();
        String info = ToJson.list2json(list);
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
        this.fpageInfoService.delete(super.strArrToLongArr(strArr));
    }

    /**
     * 首页管理：插入或更新
     */
    public void commit() {
        Object[] arr = super.toJavaArr(info, FpageInfo.class);
        FpageInfo[] infos = castToFpageInfo(arr);
        this.fpageInfoService.commit(type, infos);
    }

    /**
     * 数组类型转换 Object[] => FpageInfo[]
     * @param arr
     * @return
     */
    private FpageInfo[] castToFpageInfo(Object[] arr) {
        FpageInfo[] infos = new FpageInfo[arr.length];
        for (int i = 0; i < infos.length; i++) {
            infos[i] = (FpageInfo) arr[i];
        }
        return infos;
    }

    /**
     * 首页管理：生效
     */
    public void valid() {
        String[] ids_arr = ids.split(",");
        for(String id : ids_arr){
            FpageInfo fPageInfo = new FpageInfo();
            fPageInfo.setId(Long.parseLong(id.toString()));
            fPageInfo.setStatus(PojoConstant.FPAGE.STATUS_VALID);
            this.fpageInfoService.update(fPageInfo);
        }
    }

    /**
     * 首页管理：失效
     */
    public void invalid() {
        String[] ids_arr = ids.split(",");
        for(String id : ids_arr){
            FpageInfo fPageInfo = new FpageInfo();
            fPageInfo.setId(Long.parseLong(id.toString()));
            fPageInfo.setStatus(PojoConstant.FPAGE.STATUS_INVALID);
            this.fpageInfoService.update(fPageInfo);
        }
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

    public String getUploadFileId() {
        return uploadFileId;
    }

    public void setUploadFileId(String uploadFileId) {
        this.uploadFileId = uploadFileId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNewImgFileName() {
        return newImgFileName;
    }

    public void setNewImgFileName(String newImgFileName) {
        this.newImgFileName = newImgFileName;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
}
