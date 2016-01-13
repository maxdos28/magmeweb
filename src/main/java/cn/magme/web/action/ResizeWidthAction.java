/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.apache.struts2.ServletActionContext;

import cn.magme.service.UserImageService;

import com.mortennobel.imagescaling.ResampleOp;

/**
 * @author shenhao
 * @date 2011-10-14
 * @version $id$
 */
@SuppressWarnings("serial")
public class ResizeWidthAction extends BaseAction {
	@Resource
	private UserImageService userImageService;
	
	private String path;
	private List<String> fileList = new ArrayList<String>();
	private Integer width;

	public void loop() throws IOException {
		if (path == null){
			ServletActionContext.getResponse().getWriter().print("fault");
		}else{
			if(this.width==null){
				this.width=200;
			}
			List<String> arrayList = this.listFile(new File(path),"jpg",true);
			for (int i=0;i<arrayList.size();i++){
				this.width(arrayList.get(i),this.width);
				System.out.println(arrayList.get(i));
			}
			ServletActionContext.getResponse().getWriter().print("ok");
		}
	}

	public void handler() throws IOException {
		userImageService.handlerWidthAndHeight();
		ServletActionContext.getResponse().getWriter().print("ok");
	}
	
	private void width(String path,Integer imgWidth) {
	    try {
    		BufferedImage imageResize = ImageIO.read(new File(path));
    		int width = imageResize.getWidth();
    		int height = imageResize.getHeight();
    		float ratio = (float) width / (float) height;
    		if (width > imgWidth) {
    			width = imgWidth;
    			height = (int) (imgWidth / ratio);
    		}
    		ResampleOp reOp = new ResampleOp(width, height);
    		BufferedImage newImg = reOp.filter(imageResize, null);
    		File file = new File(path);
    		String parentPath = file.getParent();
    		String name = file.getName();
    		//判断整数
    		if (name.substring(0,name.indexOf(".")).matches("[\\d]+")){
                    ImageIO.write(newImg, "JPEG", new File(parentPath + "/"+imgWidth+"_" + name));
    		}
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	private List<String> listFile(File f, String suffix, boolean isdepth) {
		
		//是目录，同时需要遍历子目录
		if (f.isDirectory() && isdepth == true) {
			File[] t = f.listFiles();
			for (int i = 0; i < t.length; i++) {
				listFile(t[i], suffix, isdepth);
			}
		} else {
			String filePath = f.getAbsolutePath();
			if (suffix == "" || suffix == null) {
				//后缀名为null则为所有文件
				fileList.add(filePath);
			} else {
				//最后一个.(即后缀名前面的.)的索引
				int begIndex = filePath.lastIndexOf(".");
				String tempsuffix = "";
				//防止是文件但却没有后缀名结束的文件
				if (begIndex != -1) {
					tempsuffix = filePath.substring(begIndex + 1, filePath.length());
				}

				if (tempsuffix.equals(suffix)) {
					fileList.add(filePath);
				}
			}
		}
		return fileList;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}
	
}
