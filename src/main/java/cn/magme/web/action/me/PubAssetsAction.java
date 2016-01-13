package cn.magme.web.action.me;

import java.io.File;

import cn.magme.common.JsonResult;
import cn.magme.web.action.BaseAction;

/**
 * 
 * @author fredy
 * @since 2013-4-18
 */
public class PubAssetsAction extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5875458333282402539L;
	
	public static final String PUB_ASSETS_FOLDER="pubassets";
	
	private String ROOT_PATH;

	public String execute(){
		this.jsonResult=JsonResult.getFailure();
		ROOT_PATH=this.systemProp.getMagLocalUrl()+File.separator+PUB_ASSETS_FOLDER;
		File f=new File(ROOT_PATH);
		if(f==null || !f.exists() || !f.isDirectory()){
			this.jsonResult.setMessage("folder is not exist,folder:"+PUB_ASSETS_FOLDER);
			return JSON;
		}
		
		File [] files=f.listFiles();
		if(files==null || files.length<=0){
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage("no file in this folder");
			return JSON;
		}
		this.iterateFile(f);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}
	
	/**
	 * 遍历文件夹，记录文件名称和最后修改是假你
	 * @param f
	 */
	private void iterateFile(File f){
		if(!f.exists()){
			//文件错误，不进行遍历
			return;
		}else if(f.isFile()){
			//已经到了文件不再遍历
			this.jsonResult.put(f.getAbsolutePath().replace(ROOT_PATH, ""),f.lastModified());
			return;
		}else if(f.isDirectory()){
			//到了文件夹，继续遍历,如果文件夹没文件，停止遍历
			File [] files=f.listFiles();
			if(files==null || files.length<=0){
				return ;
			}
			for(File ff:files){
				iterateFile(ff);
			}
		}
	}

}
