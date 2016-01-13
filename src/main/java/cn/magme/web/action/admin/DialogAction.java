/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.admin;

import javax.annotation.Resource;
import net.sf.json.JSONArray;
import cn.magme.service.DialogService;
import cn.magme.util.ToJson;

/**
 * 问答管理
 * @author shenhao
 * @date 2011-7-18
 * @version $id$
 */
@SuppressWarnings("serial")
public class DialogAction extends BaseAction{
	@Resource
	DialogService dialogService;
 
	// 页面信息
	private String info;
	// 回答内容
	private String answer;
	
	/**
	 * 分页查询
	 */
	public void page() {
		page = this.dialogService.getPage(page);
		String info = ToJson.object2json(page);
		print(info);
	}
	
	/**
	 * 删除提问问题
	 */
	public void delete(){
		String[] strs = ids.split(",");
		Long[] arr = super.strArrToLongArr(strs);
		
		this.dialogService.delete(arr);
	}
	
	/**
	 * 回答内容插入
	 */
	public void answer(){
		JSONArray jsonArray = new JSONArray();
		jsonArray = jsonArray.fromObject(info);
		if (jsonArray.size() > 0) {
			
			this.dialogService.insert(jsonArray,answer);
		}
	}

	/**
	 * 页面信息取得
	 */
	public String getInfo() {
		return info;
	}

	/**
	 * 页面信息设定
	 */
	public void setInfo(String info) {
		this.info = info;
	}
	
	/**
	 * 回答内容取得
	 * @return
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 * 回答内容设定
	 * @param answer
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
