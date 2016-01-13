/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.admin;

import java.util.List;

import javax.annotation.Resource;

import cn.magme.pojo.sns.UserEx;
import cn.magme.service.sns.UserExService;
import cn.magme.util.ToJson;

/**
 * 用户扩展信息（编辑认证、推荐用户）管理
 * @author billy
 * @date 2012-06-04
 */
@SuppressWarnings("serial")
public class UserExAction extends BaseAction {
	@Resource
	private UserExService userExService;
	
	private UserEx userEx;
	
	/**
	 * 分页查询
	 */
	public void page() {
		List<UserEx> list = this.userExService.getByCondition(page, userEx);
		page.setData(list);
		String info = ToJson.object2json(page);
		print(info);
	}

	/**
	 * 添加或更新
	 */
	public void commit() {
		Object[] arr = super.toJavaArr(info, UserEx.class);
		UserEx[] infos = castToUserEx(arr);
		for (UserEx userEx : infos)
			if(userEx.getUserId() != null)
				this.userExService.updateUserEx(userEx);
	}
	
    /**
     * 删除
     */
    public void delete() {
        String[] strArr = ids.split(",");
        Long[] arr = this.strArrToLongArr(strArr);
        for (Long userId : arr) {
			userExService.deleteUserEx(userId);
		}
    }
	
	/**
	 * 数组类型转换 Object[] => UserEx[]
	 * @param arr
	 * @return
	 */
	private UserEx[] castToUserEx(Object[] arr) {
		UserEx[] infos = new UserEx[arr.length];
		for (int i = 0; i < infos.length; i++) {
			infos[i] = (UserEx) arr[i];
		}
		return infos;
	}

	public void setUserEx(UserEx userEx) {
		this.userEx = userEx;
	}

	public UserEx getUserEx() {
		return userEx;
	}

}
