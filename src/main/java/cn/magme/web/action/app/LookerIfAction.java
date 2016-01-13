package cn.magme.web.action.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.magme.common.JsonResult;
import cn.magme.constants.PojoConstant;
import cn.magme.pojo.Issue;
import cn.magme.pojo.look.LooUser;
import cn.magme.pojo.look.LooUserEgg;
import cn.magme.pojo.look.LooUserFeedback;
import cn.magme.service.look.LookIfService;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

/**
 * LOO客接口
 * 
 * @author jasper
 * @date 2013.10.10
 * 
 */
public class LookerIfAction extends BaseAction {
	

	@Resource
	private LookIfService lookIfService;
	private Long appId;
	private String v;
	private Integer type;
	private Integer muid;
	private Long userId;
	private String param;//栏目刷新时传入的字符串参数
	private Long articleId;
	private Long itemId;
	private String itemIds;
	private String sex;
	private Integer age;
	private String mobile;
	private String invitecode;//用户输入邀请码
	private String uid;//第三方提供的ID
	private String nick;//昵称
	private String gravatar;//第三方提供的头像
	private String advice;//用户反馈
	private Long giftId;//礼品ID
	private Long categoryId;//分类ID
	private Integer trend;//文章查询趋势
	private Integer limit;//文章查询条数
	private Long contentId; //期刊id或文章id
	private Integer pageNum; //期刊的页码或文章页码
	private Long managerId; //彩蛋ID
	private Integer eggNo;//彩蛋编号
	private Long messageId;//消息ID
	private Long userMessageId;//用户回复哪条消息ID
	private int star1;//用户反馈--易用
	private int star2;//用户反馈--内容
	private int star3;//用户反馈--更新
	
	/**
	 * app启动时图片 
	 * 参数： appid 
	 * 返回： Json 只返回带链接的图片，图片发生改变图片名称要变化。
	 * 图片分PAD(pad_)和PHONE(phone_)，通过前缀区分 返回地址不带前缀
	 * 
	 * @return
	 */
	public String lookAppDownPic() {
		this.jsonResult = JsonResult.getFailure();
		
		try {
			if(appId==null||appId<=0)
			{
				this.jsonResult.setMessage("app为空");
				return JSON;
			}
			if(StringUtil.isBlank(v))
			{
				this.jsonResult.setMessage("版本号为空");
				return JSON;
			}
			this.jsonResult = lookIfService.lookAppDownPic(appId);
		} catch (Exception e) {
			this.jsonResult.setMessage(e.getMessage());
		}
		return JSON;
	}

	/**
	 * 推荐栏目 
	 * 参数
	 * appid应用id device pad或phone 
	 * 返回： Json
	 * 
	 * @return
	 */
	public String recommendItems() {
		this.jsonResult = JsonResult.getFailure();
		
		try {
			if(appId==null||appId<=0)
			{
				this.jsonResult.setMessage("app为空");
				return JSON;
			}
			if(StringUtil.isBlank(v))
			{
				this.jsonResult.setMessage("版本号为空");
				return JSON;
			}
			this.jsonResult = this.lookIfService.recommendItems(appId);
		} catch (Exception e) {
			this.jsonResult.setMessage(e.getMessage());
		}
		return JSON;
	}

	/**
	 * 分类列表,增加栏目时 
	 * 参数
	 * appid应用id 
	 * cateId
	 * 分类的编号，第一次添加，输入0，返回所有分类和第一个分类下的所有栏目。点击其他分类，就传分类id。 
	 * 返回： Json
	 * 
	 * @return
	 */
	public String getAllCateList() {
		this.jsonResult = JsonResult.getFailure();
		
		try {
			if(appId==null||appId<=0)
			{
				this.jsonResult.setMessage("app为空");
				return JSON;
			}
			if(StringUtil.isBlank(v))
			{
				this.jsonResult.setMessage("版本号为空");
				return JSON;
			}
			this.jsonResult = this.lookIfService.getAllCateList(appId);
		} catch (Exception e) {
			this.jsonResult.setMessage(e.getMessage());
		}
		return JSON;
	}
	

	/**
	 * 分类下栏目列表
	 * 参数
	 * appid应用id 
	 * cateId。 
	 * 返回： Json
	 * 
	 * @return
	 */
	public String getItemListByCate() {
		this.jsonResult = JsonResult.getFailure();
		
		try {
			if(appId==null||appId<=0)
			{
				this.jsonResult.setMessage("app为空");
				return JSON;
			}
			if(StringUtil.isBlank(v))
			{
				this.jsonResult.setMessage("版本号为空");
				return JSON;
			}
			if(categoryId==null||categoryId<=0)
			{
				this.jsonResult.setMessage("分类ID为空");
				return JSON;
			}
			this.jsonResult = this.lookIfService.getItemListByCate(appId, categoryId);
		} catch (Exception e) {
			this.jsonResult.setMessage(e.getMessage());
		}
		return JSON;
	}
	/**
	 * 二级栏目和期刊列表
	 * 参数
	 * appid应用id 
	 * itemId。 
	 * 返回： Json
	 * 
	 * @return
	 */
	public String getSubItemList() {
		this.jsonResult = JsonResult.getFailure();
		
		try {
			if(appId==null||appId<=0)
			{
				this.jsonResult.setMessage("app为空");
				return JSON;
			}
			if(StringUtil.isBlank(v))
			{
				this.jsonResult.setMessage("版本号为空");
				return JSON;
			}
			if(itemId==null||itemId<=0)
			{
				this.jsonResult.setMessage("栏目ID为空");
				return JSON;
			}
			this.jsonResult = this.lookIfService.getSubItem(appId,userId, itemId);
		} catch (Exception e) {
			this.jsonResult.setMessage(e.getMessage());
		}
		return JSON;
	}

	/**
	 * 栏目刷新 
	 * 参数： Param是一个集合，按栏目id和最后一次进入文章id依次进入 返回： Json
	 * {itemid，图片url，增量（数字），type（杂志，文章） }
	 * 
	 * @return
	 */
	public String itemsRefresh() {
		this.jsonResult = JsonResult.getFailure();
		try {
			if(appId==null||appId<=0)
			{
				this.jsonResult.setMessage("app为空");
				return JSON;
			}
			if(StringUtil.isBlank(v))
			{
				this.jsonResult.setMessage("版本号为空");
				return JSON;
			}
			if(StringUtil.isBlank(param))
			{
				this.jsonResult.setMessage("参数为空");
				return JSON;
			}
			//参数格式为item1,article1;item2,article2;
			List<Map> paramList = new ArrayList();
			String[] strs = param.split(";");
			for(String str:strs)
			{
				String[] ss = str.split(",");
				if(ss.length!=2)
					continue;
				Map m = new HashMap();
				m.put("itemId", new Long(ss[0]));
				m.put("articleId", new Long(ss[1]));
				paramList.add(m);
			}
			this.jsonResult = this.lookIfService.itemsRefresh(appId, paramList);
		} catch (Exception e) {
			this.jsonResult.setMessage(e.getMessage());
		}
		return JSON;
	}

	/**
	 * 关联登陆 
	 * 参数: Appid Nick 昵称 Type 类型微博、QQ Sex 性别
	 * Gravatar头像的url（微博profile_image_url，qq字段figureurl_1） Uid关联注册提供的唯一号码
	 * Muid我们统计生成的唯一号码 Invitationcode填邀请码 
	 * 返回： userId
	 * 
	 * @return
	 */
	public String userReg() {
		this.jsonResult = JsonResult.getFailure();
		if(appId==null||appId<=0)
		{
			this.jsonResult.setMessage("app为空");
			return JSON;
		}
		if(StringUtil.isBlank(v))
		{
			this.jsonResult.setMessage("版本号为空");
			return JSON;
		}
		if(muid==null||muid<=0)
		{
			this.jsonResult.setMessage("muid为空");
			return JSON;
		}
		if(StringUtil.isBlank(uid))
		{
			this.jsonResult.setMessage("uid为空");
			return JSON;
		}
		if(type==null||type<0)
		{
			this.jsonResult.setMessage("类型为空");
			return JSON;
		}
		LooUser user = new LooUser();
		user.setGravatar(gravatar);
		user.setUid(uid);
		user.setType(new Byte(""+type));
		if(sex != null && !sex.equals("")){
			byte temp = 1;
			if(sex.equals("女")||sex.equals("0"))
				temp = 0;
			user.setSex(temp);
		}
		user.setNickName(nick);
		user.setStatus((byte) 1);
		this.jsonResult = lookIfService.userReg(appId, muid, user);
		return JSON;
	}

	/**
	 * 补充信息 
	 * 参数： Appid Nick 昵称 Type 类型微博、QQ Sex 性别 Age 年龄（要输入）
	 * Gravatar头像的url（微博profile_image_url，qq字段figureurl_1） Uid关联注册提供的唯一号码
	 * mobile手机号（要输入） Invitationcode填邀请码
	 * 
	 * @return
	 */
	public String updateUser() {
		JsonResult r = JsonResult.getFailure();
		if(appId==null||appId<=0)
		{
			this.jsonResult.setMessage("app为空");
			return JSON;
		}
		if(StringUtil.isBlank(v))
		{
			this.jsonResult.setMessage("版本号为空");
			return JSON;
		}
		if(userId==null||userId<=0)
		{
			this.jsonResult.setMessage("userId为空");
			return JSON;
		}
		JsonResult result = lookIfService.getUser(appId, userId);
		if(result.getCode() == JsonResult.CODE.SUCCESS){
			LooUser user = (LooUser) result.get("user");
			user.setAge(age);
			if(sex != null && !sex.equals("")){
				byte temp = 1;
				if(sex.equals("女"))
					temp = 0;
				user.setSex(temp);
			}
			user.setMobile(mobile);
			JsonResult update = lookIfService.updateUser(appId, muid, user, "");
			if(update.getCode() == JsonResult.CODE.SUCCESS){
				r.setMessage("更新成功");
				r.setCode(JsonResult.CODE.SUCCESS);
				if(invitecode != null && !invitecode.equals("")){
					JsonResult invite = lookIfService.invitationFriend(appId, invitecode, userId, muid);
					if(invite.getCode() != JsonResult.CODE.SUCCESS){
						r.setMessage(invite.getMessage());
					}
				}
			}
		}
		this.jsonResult = r;
		return JSON;
	}


	/**
	 * 文章列表 
	 * 参数： appid itemid 栏目id articleid 当前文章id，如果是初始进入，赋值0 trend
	 * 文章趋势，向后加载-1，向前加载1，默认是向前 返回： Json
	 * {文章id，页数，大小，图片url，标题，描述，文章上架时间，LIST{序号，标题，链接}}
	 * 
	 * 
	 * @return
	 */
	public String articleList() {
		this.jsonResult = JsonResult.getFailure();
		
		try {
			if(appId==null||appId<=0)
			{
				this.jsonResult.setMessage("app为空");
				return JSON;
			}
			if(StringUtil.isBlank(v))
			{
				this.jsonResult.setMessage("版本号为空");
				return JSON;
			}
			if(itemId==null||itemId<=0)
			{
				this.jsonResult.setMessage("栏目为空");
				return JSON;
			}
			if(this.articleId==null)
				this.articleId = 0L;
			if(this.trend==null)
				this.trend = 1;
			if(this.limit==null)
				this.limit = 50;
			this.jsonResult = this.lookIfService.articleList(appId,userId, itemId, articleId, trend, limit,v);
		} catch (Exception e) {
			this.jsonResult.setMessage(e.getMessage());
		}
		return JSON;
	}
	
	/**
	 * 获取栏目介绍
	 * 即该栏目下最后发布的置顶文章
	 * @return
	 */
	public String itemMemo() {
		this.jsonResult = JsonResult.getFailure();
		
		try {
			if(appId==null||appId<=0)
			{
				this.jsonResult.setMessage("app为空");
				return JSON;
			}
			if(StringUtil.isBlank(v))
			{
				this.jsonResult.setMessage("版本号为空");
				return JSON;
			}
			if(itemId==null||itemId<=0)
			{
				this.jsonResult.setMessage("栏目为空");
				return JSON;
			}
			this.jsonResult  = this.lookIfService.itemMemo(appId, itemId);
		} catch (Exception e) {
			this.jsonResult.setMessage(e.getMessage());
		}
		return JSON;
	}

	/**
	 * 多看看 
	 * 参数 Appid Articleid 文章id 返回 {序号，标题，链接}
	 * 
	 * @return
	 */
	public String articleMore() {
		this.jsonResult = JsonResult.getFailure();
		
		try {
			if(appId==null||appId<=0)
			{
				this.jsonResult.setMessage("app为空");
				return JSON;
			}
			if(StringUtil.isBlank(v))
			{
				this.jsonResult.setMessage("版本号为空");
				return JSON;
			}
			if(articleId==null||articleId<=0)
			{
				this.jsonResult.setMessage("文章ID为空");
				return JSON;
			}
			this.jsonResult  = this.lookIfService.articleMore(appId, articleId);
		} catch (Exception e) {
			this.jsonResult.setMessage(e.getMessage());
		}
		return JSON;
	}

	/**
	 * 杂志最新一期
	 * 
	 * @return
	 */
	public String latestIssue() {
		this.jsonResult = JsonResult.getFailure();
		
		try {
			if(appId==null||appId<=0)
			{
				this.jsonResult.setMessage("app为空");
				return JSON;
			}
			if(StringUtil.isBlank(v))
			{
				this.jsonResult.setMessage("版本号为空");
				return JSON;
			}
			if(itemId==null||itemId<=0)
			{
				this.jsonResult.setMessage("栏目为空");
				return JSON;
			}
			this.jsonResult = this.lookIfService.latestIssue(appId, itemId);
		} catch (Exception e) {
			this.jsonResult.setMessage(e.getMessage());
		}
		return JSON;
	}
	

	/**
	 * 多个栏目的杂志最新一期
	 * 
	 * @return
	 */
	public String moreLatestIssue() {
		this.jsonResult = JsonResult.getFailure();
		
		try {
			if(appId==null||appId<=0)
			{
				this.jsonResult.setMessage("app为空");
				return JSON;
			}
			if(StringUtil.isBlank(v))
			{
				this.jsonResult.setMessage("版本号为空");
				return JSON;
			}
			if(StringUtil.isBlank(itemIds))
			{
				this.jsonResult.setMessage("栏目为空");
				return JSON;
			}
			String[] items = itemIds.split(",");
			List<Map> issueList = new ArrayList();
			for(String id:items)
			{
				JsonResult r = this.lookIfService.latestIssue(appId, Long.parseLong(id));
				Issue ie = (Issue) r.get("latestIssue");
				if(ie!=null)
				{
					Map m = new HashMap();
					m.put("latestIssue", ie);
					m.put("itemId", Long.parseLong(id));
					issueList.add(m);
				}
			}
			this.jsonResult.put("issueList", issueList);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		} catch (Exception e) {
			this.jsonResult.setMessage(e.getMessage());
		}
		return JSON;
	}

	/**
	 * 金币同步 
	 * 参数 Userid用户id 返回 金币数量
	 * 
	 * @return
	 */
	public String syncGold() {
		this.jsonResult = JsonResult.getFailure();
		
		try {
			if(appId==null||appId<=0)
			{
				this.jsonResult.setMessage("app为空");
				return JSON;
			}
			if(StringUtil.isBlank(v))
			{
				this.jsonResult.setMessage("版本号为空");
				return JSON;
			}
			if(userId==null||userId<=0)
			{
				this.jsonResult.setMessage("用户为空");
				return JSON;
			}
			this.jsonResult = this.lookIfService.syncGold(appId, userId);
		} catch (Exception e) {
			this.jsonResult.setMessage(e.getMessage());
		}
		return JSON;
	}

	/**
	 * 增加金币 
	 * type=1分享 返回 增加金币值，如果是0，说明达到今天分享上限。 当前金币值
	 * 
	 * type=2连续签到 返回 增加金币值，今天是签到的第几天。 当前金币值
	 * 
	 * type=3阅读10页加金币 返回 增加金币值。 当前金币值
	 * 
	 * type=4金币兑换 参数 Userid用户id muid Type类型，签到动作 Gifted礼品id， 返回
	 * 1，申请成功。2，金币不足。3，礼品当日发放完毕。4，兑换失败。5，用户信息不完整。
	 * 
	 * @return
	 */
	public String addGold() {
		this.jsonResult = JsonResult.getFailure();
		
		try {
			if(appId==null||appId<=0)
			{
				this.jsonResult.setMessage("app为空");
				return JSON;
			}
			if(StringUtil.isBlank(v))
			{
				this.jsonResult.setMessage("版本号为空");
				return JSON;
			}
			if(userId==null||userId<=0)
			{
				this.jsonResult.setMessage("用户ID为空");
				return JSON;
			}
			if(muid==null||muid<=0)
			{
				this.jsonResult.setMessage("muid为空");
				return JSON;
			}
			//目前只有1和3可以直接调用
			if(!(type==1||type==3||type==4))
			{
				this.jsonResult.setMessage("类型错误");
				return JSON;
			}
			this.jsonResult = this.lookIfService.addGold(appId, type, userId, muid, giftId, null);
			//记录分享次数
			if(type==1)
			{
				this.lookIfService.articleCountShare(appId, articleId);
			}
		} catch (Exception e) {
			this.jsonResult.setMessage(e.getMessage());
		}
		return JSON;
	}

	/**
	 * 用户签到
	 * @return
	 */
	public String userSignIn(){
		this.jsonResult = JsonResult.getFailure();
		try {
			if(appId==null||appId<=0)
			{
				this.jsonResult.setMessage("app为空");
				return JSON;
			}
			if(StringUtil.isBlank(v))
			{
				this.jsonResult.setMessage("版本号为空");
				return JSON;
			}
			if(userId==null||userId<=0)
			{
				this.jsonResult.setMessage("用户为空");
				return JSON;
			}
			if(muid==null||muid<=0)
			{
				this.jsonResult.setMessage("muid为空");
				return JSON;
			}
			this.jsonResult = lookIfService.userSignIn(appId, userId, muid);
		} catch (Exception e) {
			this.jsonResult.setMessage(e.getMessage());
		}
		return JSON;
	}
	
	/**
	 * 彩蛋规则 
	 * 返回： {投放开始时间，投放结束时间，投放彩蛋数量} {栏目id，文章开始日期（类型是文章时有用）}
	 * 
	 * @return
	 */
	public String getEggsRule() {
		this.jsonResult = JsonResult.getFailure();
		
		try {
			if(appId==null||appId<=0)
			{
				this.jsonResult.setMessage("app为空");
				return JSON;
			}
			if(StringUtil.isBlank(v))
			{
				this.jsonResult.setMessage("版本号为空");
				return JSON;
			}
			this.jsonResult = this.lookIfService.getEggsRule(appId,userId);
		} catch (Exception e) {
			this.jsonResult.setMessage(e.getMessage());
		}
		return JSON;
	}
	
	/**
	 * 得到用户当前彩蛋数
	 * @return
	 */
	public String getUserEggs()
	{
		this.jsonResult = JsonResult.getFailure();
		
		try {
			if(appId==null||appId<=0)
			{
				this.jsonResult.setMessage("app为空");
				return JSON;
			}
			if(StringUtil.isBlank(v))
			{
				this.jsonResult.setMessage("版本号为空");
				return JSON;
			}
			if(userId==null||userId<=0)
			{
				this.jsonResult.setMessage("用户为空");
				return JSON;
			}
			this.jsonResult = this.lookIfService.getUserEgg(appId, userId);
		} catch (Exception e) {
			this.jsonResult.setMessage(e.getMessage());
		}
		return JSON;
	}

	/**
	 * 找到彩蛋 
	 * 参数： Appid Userid用户id Itemtype栏目类型（杂志、文章） contentId期刊id或文章id
	 * Pageno期刊的页码或文章页码 
	 * 返回： 返回当前剩余数量。 彩券号码
	 * 
	 * @return
	 */
	public String findEgg() {
		this.jsonResult = JsonResult.getFailure();
		
		try {
			if(appId==null||appId<=0)
			{
				this.jsonResult.setMessage("app为空");
				return JSON;
			}
			if(StringUtil.isBlank(v))
			{
				this.jsonResult.setMessage("版本号为空");
				return JSON;
			}
			if(contentId==null||contentId<=0)
			{
				this.jsonResult.setMessage("内容ID为空");
				return JSON;
			}
			if(managerId==null||managerId<=0)
			{
				this.jsonResult.setMessage("彩蛋规则为空");
				return JSON;
			}
			if(pageNum==null||pageNum<=0)
			{
				this.jsonResult.setMessage("页号为空");
				return JSON;
			}
			if(userId==null||userId<=0)
			{
				this.jsonResult.setMessage("用户为空");
				return JSON;
			}
			if(type<0)
			{
				this.jsonResult.setMessage("类型为空");
				return JSON;
			}
			if(eggNo==null||eggNo<=0)
			{
				this.jsonResult.setMessage("彩蛋编号为空");
				return JSON;
			}
			LooUserEgg egg = new LooUserEgg();
			egg.setManagerId(managerId);
			egg.setPageNum(age);
			egg.setUserId(userId);
			egg.setType(new Byte(""+type));
			egg.setEggNo(eggNo);
			this.jsonResult = this.lookIfService.userFindEgg(appId, egg);
		} catch (Exception e) {
			this.jsonResult.setMessage(e.getMessage());
		}
		return JSON;
	}


	/**
	 * 判断是否输入过邀请码
	 * 参数 userId
	 * 返回 check 0:未输入 1:输入
	 * @return
	 */
	public String checkInvitationCode() {
		this.jsonResult = JsonResult.getFailure();
		try {
			if(appId==null||appId<=0)
			{
				this.jsonResult.setMessage("app为空");
				return JSON;
			}
			if(StringUtil.isBlank(v))
			{
				this.jsonResult.setMessage("版本号为空");
				return JSON;
			}
			if(userId==null||userId<=0)
			{
				this.jsonResult.setMessage("用户为空");
				return JSON;
			}
			if(muid==null||muid<=0)
			{
				this.jsonResult.setMessage("muid为空");
				return JSON;
			}
			if(StringUtil.isBlank(invitecode))
			{
				this.jsonResult.setMessage("邀请码为空");
				return JSON;
			}
			this.jsonResult = lookIfService.invitationFriend(appId, invitecode, userId, muid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return JSON;
	}

	/**
	 * 更新 当前用户输入的邀请码
	 * 用户ID不能为空
	 * @return
	 */
	public String updateInvitationCode() {
		this.jsonResult = JsonResult.getFailure();
		try {
			if(appId==null||appId<=0)
			{
				this.jsonResult.setMessage("app为空");
				return JSON;
			}
			if(StringUtil.isBlank(v))
			{
				this.jsonResult.setMessage("版本号为空");
				return JSON;
			}
			if(userId==null||userId<=0)
			{
				this.jsonResult.setMessage("用户为空");
				return JSON;
			}
			if(muid==null||muid<=0)
			{
				this.jsonResult.setMessage("muid为空");
				return JSON;
			}
			if(StringUtil.isBlank(invitecode))
			{
				this.jsonResult.setMessage("邀请码为空");
				return JSON;
			}
			this.jsonResult = lookIfService.invitationFriend(appId, invitecode, userId, muid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return JSON;
	}
	
	/**
	 * 用户反馈 
	 * 参数 Userid用户id Message反馈消息
	 * 
	 * @return
	 */
	public String userMessages() {
		this.jsonResult = JsonResult.getFailure();
		try {
			if(appId==null||appId<=0)
			{
				this.jsonResult.setMessage("app为空");
				return JSON;
			}
			if(StringUtil.isBlank(v))
			{
				this.jsonResult.setMessage("版本号为空");
				return JSON;
			}
			if(userId==null||userId<=0)
			{
				this.jsonResult.setMessage("用户为空");
				return JSON;
			}
			if(StringUtil.isBlank(advice))
			{
				this.jsonResult.setMessage("反馈内容为空");
				return JSON;
			}
			LooUserFeedback feedback = new LooUserFeedback();
			feedback.setFeedContent(advice);
			feedback.setUserId(userId);
			if(muid!=null&&muid>0)
				feedback.setMuid(new Long(muid));
			feedback.setUserMessageId(userMessageId);
			feedback.setCreateTime(new Date());
			feedback.setUpdateTime(new Date());
			JsonResult result = lookIfService.userMessages(appId, feedback,v,star1,star2,star3);
			this.jsonResult = result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return JSON;
	}
	
	/**
	 * 更新用户已读的消息
	 * @return
	 */
	public String updateUserReadMessage() {
		this.jsonResult = JsonResult.getFailure();
		try {
			if(appId==null||appId<=0)
			{
				this.jsonResult.setMessage("app为空");
				return JSON;
			}
			if(StringUtil.isBlank(v))
			{
				this.jsonResult.setMessage("版本号为空");
				return JSON;
			}
			if(userId==null||userId<=0)
			{
				this.jsonResult.setMessage("用户为空");
				return JSON;
			}
			if(messageId==null||messageId<=0)
			{
				this.jsonResult.setMessage("消息为空");
				return JSON;
			}
			this.jsonResult = lookIfService.updateUserReadMessage(appId, userId, messageId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return JSON;
	}
	
	/**
	 * 获得未读消息数量
	 * @return 
	 * count 未读数量
	 * messageId 消息ID
	 */
	public String getUnreadMessageCount() {
		this.jsonResult = JsonResult.getFailure();
		try {
			if(appId==null||appId<=0)
			{
				this.jsonResult.setMessage("app为空");
				return JSON;
			}
			if(StringUtil.isBlank(v))
			{
				this.jsonResult.setMessage("版本号为空");
				return JSON;
			}
			if(userId==null||userId<=0)
			{
				this.jsonResult.setMessage("用户为空");
				return JSON;
			}
			this.jsonResult = lookIfService.getUnreadMessageCount(appId, userId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return JSON;
	}
	
	/**
	 * 用户点赞
	 * 
	 * @return
	 */
	public String userPraise() {
		this.jsonResult = JsonResult.getFailure();
		try {
			if(appId==null||appId<=0)
			{
				this.jsonResult.setMessage("app为空");
				return JSON;
			}
			if(StringUtil.isBlank(v))
			{
				this.jsonResult.setMessage("版本号为空");
				return JSON;
			}
			if(userId==null||userId<=0)
			{
				this.jsonResult.setMessage("用户为空");
				return JSON;
			}
			if(articleId==null||articleId<=0)
			{
				this.jsonResult.setMessage("文章为空");
				return JSON;
			}
			if(type==null||type<=0)
			{
				this.jsonResult.setMessage("类型为空");
				return JSON;
			}
			this.jsonResult = lookIfService.userPraise(appId, userId,type,articleId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return JSON;
	}
	
	private String addPrefix(String str, int length, String prefix) {
		if (str == null)
			return null;
		if (str.trim().length() >= length)
			return str;
		StringBuffer s = new StringBuffer("");
		for (int i = 0; i < length - str.trim().length(); i++)
			s.append(prefix);
		s.append(str.trim());
		return s.toString();
	}
	
	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public String getV() {
		return v;
	}

	public void setV(String v) {
		this.v = v;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Long getContentId() {
		return contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getInvitecode() {
		return invitecode;
	}
	
	public void setInvitecode(String invitecode) {
		this.invitecode = invitecode;
	}

	public Integer getMuid() {
		return muid;
	}

	public void setMuid(Integer muid) {
		this.muid = muid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getGravatar() {
		return gravatar;
	}

	public void setGravatar(String gravatar) {
		this.gravatar = gravatar;
	}

	public String getSex() {
		return sex;
	}

	public Integer getAge() {
		return age;
	}

	public String getMobile() {
		return mobile;
	}

	public String getAdvice() {
		return advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}

	public Long getGiftId() {
		return giftId;
	}

	public void setGiftId(Long giftId) {
		this.giftId = giftId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getTrend() {
		return trend;
	}

	public void setTrend(Integer trend) {
		this.trend = trend;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Long getManagerId() {
		return managerId;
	}

	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getItemIds() {
		return itemIds;
	}

	public void setItemIds(String itemIds) {
		this.itemIds = itemIds;
	}

	public Integer getEggNo() {
		return eggNo;
	}

	public void setEggNo(Integer eggNo) {
		this.eggNo = eggNo;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public int getStar1() {
		return star1;
	}

	public void setStar1(int star1) {
		this.star1 = star1;
	}

	public int getStar2() {
		return star2;
	}

	public void setStar2(int star2) {
		this.star2 = star2;
	}

	public int getStar3() {
		return star3;
	}

	public void setStar3(int star3) {
		this.star3 = star3;
	}

	public Long getUserMessageId() {
		return userMessageId;
	}

	public void setUserMessageId(Long userMessageId) {
		this.userMessageId = userMessageId;
	}
	
}
