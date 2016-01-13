/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.admin;

import javax.annotation.Resource;
import cn.magme.pojo.UserImage;
import cn.magme.service.UserImageService;
import cn.magme.util.ToJson;
import cn.magme.util.keyword.KeywordFilter;

/**
 * 标签管理
 * 
 * @author guozheng
 * @date 2011-5-20
 * @version $id$
 */
public class TagAction extends BaseAction {

	@Resource
	private UserImageService userImageService;
	
    // 页面信息(查询条件:用户编号，标题，描述)
    private UserImage userImage;

    /**
	 * 分页查询
	 */
	public void page() {
		page = this.userImageService.getPageByCondition(page,userImage);
		String info = ToJson.object2json(page);
		print(info);
	}

	/**
	 * 过滤关键字
	 */
	public void filterAll() {
//		KeywordFilter kf = new KeywordFilter();
//		kf.loadKeyword(new File(systemProp.getFileFilterPath()));
//		// 记录总数，每次取1000条过滤
//		long count = this.tagService.getCountByCondition(null);
//		int tmp = (int) ((count / 1000) + 1);
//		for (int i = 0; i < tmp; i++) {
//			page = new ExtPageInfo(); 
//			page.setStart((long)(i * 1000+1));
//			page.setLimit((i + 1) * 1000);
//			// 取出数据
//			page = this.tagService.getPageByCondition(page,null);
//			// 过滤数据
//			List<Tag> data = page.getData();
//			for(Tag tag : data){
//				String s = tag.getDescription();
//				s = kf.filter(s);
//				// 将数据写回database
//				// update one by one...?batch
//				tag.setDescription(s);
//				this.tagService.updateTag(tag);
//			}	
//		}
	}

	/**
	 * 添加或更新
	 */
	public void commit() {
		Object[] arr = super.toJavaArr(info, UserImage.class);
		UserImage[] lst = castToUserImage(arr);
		this.userImageService.commit(lst,userImage);
	}
	
	/**
	 * 数组类型转换 Object => Tag[]
	 * @param arr
	 * @return
	 */
	private UserImage[] castToUserImage(Object[] arr) {
	    UserImage[] infos = new UserImage[arr.length];
		for (int i = 0; i < infos.length; i++) {
			infos[i] = (UserImage) arr[i];
		}
		return infos;
	}

    public UserImage getUserImage() {
        return userImage;
    }

    public void setUserImage(UserImage userImage) {
        this.userImage = userImage;
    }
}
