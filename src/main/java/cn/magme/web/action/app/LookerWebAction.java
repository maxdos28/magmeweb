package cn.magme.web.action.app;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.pojo.look.LooGift;
import cn.magme.pojo.look.LooGoldManager;
import cn.magme.pojo.look.LooGoldTop;
import cn.magme.pojo.look.LooReadTop;
import cn.magme.pojo.look.LooUser;
import cn.magme.pojo.look.LooUserMessages;
import cn.magme.pojo.look.LooUserStar;
import cn.magme.service.look.LookIfService;
import cn.magme.util.DateUtil;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;


/**
 * LOO客接口
 * 
 * @author jasper
 * @date 2013.10.10
 * 
 */
@Results({
		@Result(name = "getGiftsList", location = "/WEB-INF/pages/looker/convertHistory.ftl"),
		@Result(name = "checkday", location = "/WEB-INF/pages/looker/signIn.ftl"),
		@Result(name = "InvitationCode", location = "/WEB-INF/pages/looker/inviteFriends.ftl"),
		@Result(name = "Board", location = "/WEB-INF/pages/looker/sort.ftl"),
		@Result(name = "Messages", location = "/WEB-INF/pages/looker/messageCenter.ftl"),
		@Result(name = "MessagesItem", location = "/WEB-INF/pages/looker/messageCenterItem.ftl"),
		@Result(name = "giftList", location = "/WEB-INF/pages/looker/convert.ftl"),
		@Result(name = "giftListNew", location = "/WEB-INF/pages/looker/convertNew.ftl"),
		@Result(name = "getUser", location = "/WEB-INF/pages/looker/userDetial.ftl"),
		@Result(name = "userSettings", location = "/WEB-INF/pages/looker/userSettings.ftl"),
		@Result(name = "activity", location = "/WEB-INF/pages/looker/activity.ftl"),
		@Result(name = "activityIntroduction", location = "/WEB-INF/pages/looker/activityIntroduction.ftl"),
		@Result(name = "about", location = "/WEB-INF/pages/looker/about.ftl"),
		@Result(name = "advice", location = "/WEB-INF/pages/looker/advice.ftl"),
		@Result(name = "buyLimit", location = "/WEB-INF/pages/looker/buyLimit.ftl"),
		@Result(name = "luck", location = "/WEB-INF/pages/looker/luck.ftl")})
public class LookerWebAction extends BaseAction {
	private static final long serialVersionUID = 3778157278699901468L;
	@Resource
	private LookIfService lookIfService;
	private Long appId;
	private String v;
	private Long contentId;
	private Long userId;
	private String os;
	private Integer muid;
	
	private String invitecode;//用户输入邀请码
	private String userInviteCode;//用户自身邀请码
	private LooUser looUser;
	private List<LooGift> gifts;//兑换礼品
	@SuppressWarnings("rawtypes")
	private List<Map> goldGifts;//兑换历史
	private List<LooUserMessages> userMessages;//用户消息
	private List<LooGoldManager> goldManagers;
	private List<LooGoldTop> goldTops;
	private List<LooReadTop> readTops;
	private Integer rate;//完善率
	private Integer day;
	private Integer gold;
	private Integer sign;//是否已经签到
	private boolean activity;//是否存在活动
	private boolean notice;//活动通知
	private String activityName;//活动名称
	private Integer eggNum;//拥有彩蛋数量
	private Integer mustEgg;//需要彩蛋数量
	private String ticket;//奖券
	private Integer mesRead;
	private Long messageId;//消息ID
	private Long awardId;//刮刮卡ID
	private int goldRank;//金币排名
	private int readRank;//阅读排名
	private List<LooGift> buyLimitList;//抢购商品列表
	private Long giftId;//礼品ID
	private List<LooGift> luckGiftList;//刮刮卡大奖列表
	private LooUserStar looUserStar;//用户的评星
	/**
	 * 阅读排行榜 金币排行榜 参数 Userid用户id os操作系统 返回 {昵称，排名，金币数量}
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String goldBoard() {
		// TODO 用户排行榜 阅读榜单 金币榜单
		if(checkAppIDAndUid(appId)){
			JsonResult goldResult = lookIfService.goldBoard(appId);
			//金币排行榜
			if(goldResult.getCode() == JsonResult.CODE.SUCCESS){
				 goldTops = (List<LooGoldTop>) goldResult.get("goldList");
			}
			//阅读排行榜
			JsonResult readResult = lookIfService.readingBoard(appId);
			if(readResult.getCode() == JsonResult.CODE.SUCCESS){
				readTops = (List<LooReadTop>) readResult.get("readingList");
			}	
			//用户排名
			if(userId!=null&&userId>0)
			{
				Map r = this.lookIfService.getGoldAndReadRank(appId, userId);
				if(r!=null)
				{
					goldRank = (Integer)r.get("goldRank");
					readRank = (Integer)r.get("readRank");
				}
			}
			else
				userId = null;
			
		}
		return "Board";
	}

	/**
	 * 消息提示 参数 Userid用户id os操作系统
	 * 
	 * @return
	 */
	public String getMessages() {
		// TODO 用户消息列表 分页模式
		getMessageItem();
		return "Messages";
	}
	
	@SuppressWarnings("unchecked")
	public String getMessageItem(){
		if(checkAppIDAndUid(appId) ){
			if(userId==null)
				userId = 0L;
			JsonResult result = lookIfService.getMessages(appId, userId,messageId);
			if(result.getCode() == JsonResult.CODE.SUCCESS){
				userMessages = (List<LooUserMessages>) result.get("userMessageList");
			}
			JsonResult read = lookIfService.getUnreadMessageCount(appId, userId);
			if(read.getCode() == JsonResult.CODE.SUCCESS){
				mesRead = Integer.valueOf(read.get("messageId").toString());
			}
			else
				mesRead = 0;
		}
		
		return "MessagesItem";
	}

	/**
	 * 礼品列表 参数 Userid用户id os操作系统
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String giftList() {
		// TODO 礼品列表
		if(checkAppIDAndUid(appId)){
			JsonResult result = lookIfService.giftList(appId);
			if(result.getCode() == JsonResult.CODE.SUCCESS){
				gifts = (List<LooGift>) result.get("giftList");
			}
			JsonResult goldResult = lookIfService.syncGold(appId, userId);
			if(goldResult.getCode() == JsonResult.CODE.SUCCESS){
				gold = (Integer) goldResult.get("gold");
			}
			
			JsonResult userResult = lookIfService.getUser(appId, userId);
			if(userResult.getCode() == JsonResult.CODE.SUCCESS){
				LooUser reg = (LooUser) userResult.get("user");
				if(reg.getAge() == null||reg.getAge()<=0 || reg.getMobile() == null||reg.getMobile().trim().length()==0){
					notice = true;
				}else if(reg.getAge().intValue() > 0 && reg.getMobile().length() >0){
					notice = false;
				}
			}
		}
		return "giftList";
//		return "giftListNew";
	}

	/**
	 * 兑换历史记录 参数 Userid用户id os操作系统 返回 {giftid，礼品名称，礼品图片，花费金币数，兑换日期}
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getGiftsList() {
		// TODO 用户兑换历史
		if(checkAppIDAndUid(appId) && checkAppIDAndUid(userId)){
			JsonResult result = lookIfService.getUserGiftsList(appId, userId);
			if(result.getCode() == JsonResult.CODE.SUCCESS){
				goldGifts = (List<Map>) result.get("userGiftList");
			}
		}
		return "getGiftsList";
	}

	/**
	 * 当前签到天数 参数 Userid用户id os操作系统
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getCheckDay() {
		// TODO 当前用户签到天数 用户签到规则 比如第一天多少 第二天多少
		if(checkAppIDAndUid(appId) && checkAppIDAndUid(userId)){
			JsonResult result = lookIfService.getCheckDay(appId, userId);
			if(result.getCode() == JsonResult.CODE.SUCCESS){
				if(result.get("day") != null)
					day = (Integer) result.get("day");
//				if(result.get("gold") != null)
//					gold = (Integer) result.get("gold");
				if(result.get("isSign") != null)
					sign = (Integer) result.get("isSign");
			}
			JsonResult goldRuleList = lookIfService.getGoldRule(appId, 2);
			if(goldRuleList.getCode() == JsonResult.CODE.SUCCESS){
				goldManagers = (List<LooGoldManager>) goldRuleList.get("goldRuleList");
			}
			JsonResult goldResult = lookIfService.syncGold(appId, userId);
			if(goldResult.getCode() == JsonResult.CODE.SUCCESS){
				gold = (Integer) goldResult.get("gold");
			}
		}
		return "checkday";
	}

	/**
	 * 彩蛋页面 显示我的彩蛋数量 显示彩蛋活动 显示彩蛋抽奖券号 参数 Userid用户id os操作系统
	 * 
	 * @return
	 */
	public String eggActivity() {
		// TODO 彩蛋页面
		activity = false;
		if(checkAppIDAndUid(appId) ){
			JsonResult result = lookIfService.getEggsRule(appId,userId);
			if(result.getCode() == JsonResult.CODE.SUCCESS){
				activity = true;
				if(result.getData() == null)
					activity = false;
				else{
					Date now = new Date();
					Date begin = (Date) result.get("beginTime");
					Date end = (Date) result.get("endTime");
					//开始时间距离当前日计算
					int sd = DateUtil.getDaysBetween(DateUtil.parse(DateUtil.format(now, "yyyy-MM-dd"), "yyyy-MM-dd"), DateUtil.parse(DateUtil.format(begin, "yyyy-MM-dd"), "yyyy-MM-dd"));
					//结束时间距离当前日期计算
					int ed = DateUtil.getDaysBetween(DateUtil.parse(DateUtil.format(now, "yyyy-MM-dd"), "yyyy-MM-dd"), DateUtil.parse(DateUtil.format(end, "yyyy-MM-dd"), "yyyy-MM-dd"));
					if(sd > 0){
						notice = true;
					}else if(sd <= 0){
						notice = false;
					}
					//活动已经结束 
					if(ed < 0)
						activity = false;
					
					activityName = result.get("eggName").toString();
					mustEgg =  (Integer) result.get("eggNums");
					JsonResult eggResult = lookIfService.getUserEgg(appId, userId);
					if(eggResult.getCode() == JsonResult.CODE.SUCCESS){
						eggNum = (Integer) eggResult.get("eggs");
					}
				}
			}
		}
		return "activity";
	}

	/**
	 * 彩蛋活动详细信息 参数 Userid用户id os操作系统
	 * 
	 * @return
	 */
	public String activityDetail() {
		// TODO 获取彩蛋活动信息
		return "activityIntroduction";
	}

	/**
	 * 用户设置页面 参数 Userid用户id os操作系统
	 * 
	 * @return
	 */
	public String userSettings() {
		// TODO 个人信息完善度
		if(checkAppIDAndUid(appId) && checkAppIDAndUid(userId)){
			JsonResult result = lookIfService.getUser(appId, userId);
			if(result.getCode() == JsonResult.CODE.SUCCESS){
				LooUser reg = (LooUser) result.get("user");
				int i = 0;
				if(reg.getAge() != null && reg.getAge() > 0){
					i++;
				}else if(reg.getSex() != null){
					i++;
				}else if(reg.getMobile() != null && !reg.getMobile().equals("")){
					i++;
				}
				
				//检查用户是否输入过好友的邀请码
				JsonResult checkInvitationCode =  lookIfService.checkInvitationCode(appId, userId);
				if(checkInvitationCode.getCode() == JsonResult.CODE.SUCCESS){
					if(checkInvitationCode.get("invitationCode") != null)
						invitecode = checkInvitationCode.get("invitationCode").toString();
				}
				if(invitecode != null && !invitecode.equals("")){
					i++;
				}
				rate = (int) (((float)(i/4.0)) * 100);
			}
		}
		return "userSettings";
	}

	/**
	 * 得到用户信息 参数 Userid用户id os操作系统 Appid 返回： Nick 昵称 Type 类型微博、QQ Sex 性别 Age
	 * 年龄（要输入） Gravatar头像的url（微博profile_image_url，qq字段figureurl_1）
	 * Uid关联注册提供的唯一号码 mobile手机号（要输入） Invitationcode填邀请码
	 * 
	 * @return
	 */
	public String getUser() {
		// TODO 用户个人信息
		if(checkAppIDAndUid(appId) && checkAppIDAndUid(userId)){
			JsonResult result = lookIfService.getUser(appId, userId);
			if(result.getCode() == JsonResult.CODE.SUCCESS){
				LooUser reg = (LooUser) result.get("user");
				looUser = reg;
			}
		}
		getInvitationCode();
		return "getUser";
	}

	/**
	 * 添加邀请码和获取邀请码 参数 Userid用户id os操作系统
	 * 
	 * @return
	 */
	public String getInvitationCode() {
		// TODO 用户邀请码 用户是否已经输入邀请码 并且显示已输入的邀请码
		if(checkAppIDAndUid(appId) && checkAppIDAndUid(userId)){
			JsonResult getInvitationCode =  lookIfService.getInvitationCode(appId, userId);
			if(getInvitationCode.getCode() == JsonResult.CODE.SUCCESS){
				if(getInvitationCode.get("invitationCode") != null)
					userInviteCode = getInvitationCode.get("invitationCode").toString();
			}
			JsonResult checkInvitationCode =  lookIfService.checkInvitationCode(appId, userId);
			if(checkInvitationCode.getCode() == JsonResult.CODE.SUCCESS){
				if(checkInvitationCode.get("invitationCode") != null)
					invitecode = checkInvitationCode.get("invitationCode").toString();
			}
		}
		return "InvitationCode";
	}
	
	/**
	 * 关于我们
	 * @return
	 */
	public String about(){
		
		return "about";
	}
	
	/**
	 * 限时抢购
	 * @return
	 */
	public String buyLimit()
	{
		if(checkAppIDAndUid(appId) && checkAppIDAndUid(userId)){
			this.buyLimitList = this.lookIfService.getBuyLimitList(appId);
			//用status字段表示是否已到抢购时间,1表示已到,0表示未到
			if(buyLimitList!=null&&buyLimitList.size()>0)
			{
				Date d = new Date();
				for(LooGift g:buyLimitList)
				{
					if(g.getEndDate()!=null&&g.getEndDate().before(d))
					{
						g.setStatus(new Byte("1"));
					}
					else
						g.setStatus(new Byte("0"));
				}
			}
			JsonResult goldResult = lookIfService.syncGold(appId, userId);
			if(goldResult.getCode() == JsonResult.CODE.SUCCESS){
				gold = (Integer) goldResult.get("gold");
			}
		}
		return "buyLimit";
	}
	
	/**
	 * 提交抢购信息
	 * @return
	 */
	public String buyLimitOrder()
	{
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
		if(userId==null||userId<=0)
		{
			this.jsonResult.setMessage("用户ID为空");
			return JSON;
		}
		if(giftId==null||giftId<=0)
		{
			this.jsonResult.setMessage("礼品ID为空");
			return JSON;
		}
		this.jsonResult = lookIfService.createBuyLimitOrder(appId, userId, giftId, muid);
		return JSON;
	}
	
	/**
	 * 刮刮卡
	 * @return
	 */
	public String luck()
	{
		// 礼品列表
		if(checkAppIDAndUid(appId)){
			//大奖礼品
			luckGiftList = this.lookIfService.luckGiftList(appId);
			//用户金币
			JsonResult goldResult = lookIfService.syncGold(appId, userId);
			if(goldResult.getCode() == JsonResult.CODE.SUCCESS){
				gold = (Integer) goldResult.get("gold");
			}
		}
		return "luck";
	}
	
	/**
	 * 设备端得到刮刮卡
	 * @return
	 */
	public String getAward(){
		
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
		if(userId==null||userId<=0)
		{
			this.jsonResult.setMessage("用户ID为空");
			return JSON;
		}
		this.jsonResult = this.lookIfService.requestLuckCard(userId);
		return JSON;
	}
	
	/**
	 * 提交中奖结果
	 * @return
	 */
	public String commitAward(){
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
		if(userId==null||userId<=0)
		{
			this.jsonResult.setMessage("用户ID为空");
			return JSON;
		}
		if(awardId==null||awardId<=0)
		{
			this.jsonResult.setMessage("刮刮卡ID为空");
			return JSON;
		}
		this.jsonResult = this.lookIfService.confirmLuckCard(userId, muid, awardId);
		return JSON;
	}

	private boolean checkAppIDAndUid(Long val){
		if(val != null && val >0 )
			return true;
		return false;
	}
	
	/**
	 * 用户反馈页面
	 * 
	 * @return
	 */
	public String advice() {
		looUserStar = this.lookIfService.userStar(appId, userId, v);
		return "advice";
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

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getInvitecode() {
		return invitecode;
	}

	public void setInvitecode(String invitecode) {
		this.invitecode = invitecode;
	}


	public LooUser getLooUser() {
		return looUser;
	}

	public List<LooGift> getGifts() {
		return gifts;
	}

	@SuppressWarnings("rawtypes")
	public List<Map> getGoldGifts() {
		return goldGifts;
	}

	public List<LooUserMessages> getUserMessages() {
		return userMessages;
	}

	public List<LooGoldManager> getGoldManagers() {
		return goldManagers;
	}

	public Integer getRate() {
		return rate;
	}

	public String getUserInviteCode() {
		return userInviteCode;
	}

	public void setUserInviteCode(String userInviteCode) {
		this.userInviteCode = userInviteCode;
	}

	public Integer getDay() {
		return day;
	}

	public Integer getGold() {
		return gold;
	}

	public Integer getMuid() {
		return muid;
	}

	public void setMuid(Integer muid) {
		this.muid = muid;
	}

	public List<LooGoldTop> getGoldTops() {
		return goldTops;
	}

	public List<LooReadTop> getReadTops() {
		return readTops;
	}

	public Integer getSign() {
		return sign;
	}
	
	public boolean getActivity(){
		return activity;
	}

	public boolean getNotice() {
		return notice;
	}

	public String getActivityName() {
		return activityName;
	}

	public Integer getEggNum() {
		return eggNum;
	}

	public Integer getMustEgg() {
		return mustEgg;
	}

	public String getTicket() {
		return ticket;
	}

	public Integer getMesRead() {
		return mesRead;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public Long getAwardId() {
		return awardId;
	}

	public void setAwardId(Long awardId) {
		this.awardId = awardId;
	}

	public int getGoldRank() {
		return goldRank;
	}

	public void setGoldRank(int goldRank) {
		this.goldRank = goldRank;
	}

	public int getReadRank() {
		return readRank;
	}

	public void setReadRank(int readRank) {
		this.readRank = readRank;
	}

	public List<LooGift> getBuyLimitList() {
		return buyLimitList;
	}

	public void setBuyLimitList(List<LooGift> buyLimitList) {
		this.buyLimitList = buyLimitList;
	}

	public Long getGiftId() {
		return giftId;
	}

	public void setGiftId(Long giftId) {
		this.giftId = giftId;
	}

	public List<LooGift> getLuckGiftList() {
		return luckGiftList;
	}

	public void setLuckGiftList(List<LooGift> luckGiftList) {
		this.luckGiftList = luckGiftList;
	}

	public LooUserStar getLooUserStar() {
		return looUserStar;
	}

	public void setLooUserStar(LooUserStar looUserStar) {
		this.looUserStar = looUserStar;
	}

}
