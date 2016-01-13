/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.upload;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.ProgressListener;
import org.apache.log4j.Logger;

import cn.magme.constants.WebConstant;
import cn.magme.pojo.Publisher;
import cn.magme.web.entity.UploadInfo;

/**
 * @author lqwang
 * @date 2010-10-29
 * @version $id$
 */
public class MagProgcessListener  implements ProgressListener{

    static final Logger log = Logger.getLogger(MagProgcessListener.class);
    private UploadInfo info;
    private HttpServletRequest request;
    /* (non-Javadoc)
     * @see org.apache.commons.fileupload.ProgressListener#update(long, long, int)
     */
    @Override
    public void update(long pBytesRead, long pContentLength, int pItems) {
        log.debug(pBytesRead+":"+pContentLength+":");
        Publisher pub=(Publisher)request.getSession().getAttribute(WebConstant.SESSION.PUBLISHER);
        String infoStr="info"+pub.getId();
        info = (UploadInfo)request.getSession().getAttribute(infoStr);
        if(info==null){
            info =new UploadInfo();
        }
        info.setTotalLong(pContentLength);
        info.setUploaded(pBytesRead);
        //info.setPercent((int) (pBytesRead * 40 / pContentLength));
        info.setPercent((int) (pBytesRead * 100 / pContentLength));
        request.getSession().setAttribute(infoStr, info);
    }
    public HttpServletRequest getRequest() {
        return request;
    }
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
    
    

}
