/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.admin;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import cn.magme.pojo.UserImageComment;
import cn.magme.service.UserImageCommentService;
import cn.magme.util.ToJson;

/**
 * 标签评论
 * @author shenhao
 * @date 2011-8-10
 * @version $id$
 */
@SuppressWarnings("serial")
public class TagCommentAction extends BaseAction {

    // 页面信息(查询条件:用户昵称，主题，内容)
    private UserImageComment userImageComment;

    @Resource
    private UserImageCommentService userImageCommentService;
    
    /**
     * 分页查询
     */
    public void page() {
        page = this.userImageCommentService.getPageByCondition(page,userImageComment);
        String info = ToJson.object2json(page);
        print(info);
    }

    /**
     * 添加或更新
     */
    @SuppressWarnings("static-access")
    public void commit() {
        JSONArray jsonArray = new JSONArray();
        jsonArray = jsonArray.fromObject(info);
        UserImageComment[] lst = castToUserImageComment(jsonArray);
        this.userImageCommentService.commit(lst);
    }

//    /**
//     * 删除tag评论
//     */
//    public void delete() {
//        String[] strArr = ids.split(",");
//        this.tagCommentService.delete(super.strArrToLongArr(strArr));
//    }
//    
//    /**
//     * 过滤关键字
//     */
//    public void filterAll() {
//        
//        KeywordFilter kf = new KeywordFilter();
//        kf.loadKeyword(new File(systemProp.getFileFilterPath()));
//        // 记录总数，每次取1000条过滤
//        long count = this.tagCommentService.getTagCommentCount(null,null,null);
//        int tmp = (int) ((count / 1000) + 1);
//        for (int i = 0; i < tmp; i++) {
//            page = new ExtPageInfo(); 
//            page.setStart((long)(i * 1000+1));
//            page.setLimit((i + 1) * 1000);
//            // 取出数据
//            page = this.tagCommentService.getPageByCondition(page,null);
//            // 过滤数据
//            List<TagComment> data = page.getData();
//            for(TagComment tagComment : data){
//                String s = tagComment.getContentInfo().getContent();
//                s = kf.filter(s);
//                // 将数据写回database
//                tagComment.getContentInfo().setContent(s);
//                this.tagCommentService.commit(new ContentInfo[]{tagComment.getContentInfo()});
//            }   
//        }
//    }
//    
    /**
     * 数组类型转换 
     * @param arr
     * @return
     */
    private UserImageComment[] castToUserImageComment(JSONArray ja) {
        UserImageComment[] infos = new UserImageComment[ja.size()];
        UserImageComment info;
          for (int i = 0; i < infos.length; i++) {
              JSONObject jsonObject =  ja.getJSONObject(i);
              info = new UserImageComment();
              info.setId(jsonObject.getLong("id"));
              info.setStatus(jsonObject.getInt("status"));
              infos[i] = info;
          }
          return infos;
    }
    
    public UserImageComment getUserImageComment() {
        return userImageComment;
    }

    public void setUserImageComment(UserImageComment userImageComment) {
        this.userImageComment = userImageComment;
    }
}
