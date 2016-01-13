package cn.magme.web.action.admin;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionContext;
import cn.magme.constants.WebConstant;
import cn.magme.pojo.Admin;
import cn.magme.service.AdminService;
import cn.magme.util.ToJson;

/**
 * 主要3个功能：<br/>
 * 1. 用户登陆<br/>
 * 2. 目录菜单管理<br/>
 * 3. 管理员账号管理<br/>
 * 
 * @author guozheng
 * @date 2011-6-3
 * @version $id$
 */
@SuppressWarnings("serial")
public class AdminAction extends BaseAction {

	@Resource
	private AdminService adminService;
	/** 用户信息 */
	private Admin admin;
	/** 验证码 */
	private String code;
	/** 返回页面信息 */
	private String info;

	/**
	 * 后台登陆
	 */
	public void login() {
	    // 生成的验证码
		String c = (String) ServletActionContext.getRequest().getSession()
				.getAttribute("code");
		// 验证码正确则进行身份验证
		if (code == null) {
			print("{failure:true,message:'验证码错误'}");
		}else if (c.equalsIgnoreCase(code)) {
			boolean b = this.adminService.login(admin, code, ActionContext
					.getContext());
			if (b) {
				// ServletActionContext.getRequest().getSession().setAttribute(WebConstant.SESSION.ADMIN,
				// admin);
			    //用户身份认证正确则返回正确信息
				print("{success:true}");
			} else {
			    //用户身份认证失败则返回错误信息
				print("{failure:true,message:'用户名或密码错误!'}");
			}
		} else {
			print("{failure:true,message:'验证码错误'}");
		}
	}

	/**
	 * 管理员管理
	 */
	public void page() {
		page = this.adminService.getPage(page);
		String info = ToJson.object2json(page);
		print(info);
	}

	/**
	 * 管理员信息更新
	 */
	public void commit() {
	    // 被变更所有用户信息
		Object[] arr = super.toJavaArr(info, Admin.class);
		Admin[] admins = this.castToAdmin(arr);
		this.adminService.commit(admins);
	}

	/**
	 * 管理员信息删除
	 */
	public void delete() {
	    // 被选中所有用户id数组
		String[] strs = info.split(",");
		Long[] arr = super.strArrToLongArr(strs);
		this.adminService.delete(arr);
	}

	/**
	 * 数组类型转换object[] => Admin[]
	 * @param arr
	 * @return
	 */
	private Admin[] castToAdmin(Object[] arr) {
		Admin[] admins = new Admin[arr.length];
		for (int i = 0; i < arr.length; i++) {
			admins[i] = (Admin) arr[i];
		}
		return admins;
	}

	/**
	 * 后台管理目录菜单
	 */
	public void tree() {
	    // 当前用户信息
		Admin logon = (Admin) ActionContext.getContext().getSession().get(
				WebConstant.SESSION.ADMIN);
		String[] strs = null;
		if (logon != null) {
			if (logon.getLevel() > 1) {
				strs = new String[] { 
						"首页管理",
						"杂志分类管理", "出版商管理","通用评论管理","广告商管理", "用户管理","编辑认证管理",
						"期刊管理", "看米管理", "杂志管理", "切米管理", "广告管理", "问答管理","统计","标签评论管理","反馈内容管理","反馈种类管理","消息群发","统计系统","用户行为统计" };
			} else {
				strs = new String[] { 
						"首页管理",
						"杂志分类管理", "出版商管理","通用评论管理", "广告商管理","用户管理","编辑认证管理",
						"期刊管理", "看米管理", "杂志管理", "切米管理", "广告管理", "问答管理","统计","标签评论管理","反馈内容管理","反馈种类管理","消息群发","管理员管理","统计系统","用户行为统计" };
			}
		} else {
			strs = new String[] { 
					"首页管理",
					"杂志分类管理", "出版商管理","通用评论管理","广告商管理", "用户管理","编辑认证管理",
					"期刊管理", "看米管理", "杂志管理", "切米管理", "广告管理", "问答管理","统计","标签评论管理","反馈内容管理","反馈种类管理","消息群发","管理员管理","统计系统","用户行为统计" };
		}

//		print("{text:'01',children:[ {text:'01-01',leaf:true}, {text:'01-02',children:[ {text:'01-02-01',leaf:true}, {text:'01-02-02',leaf:true} ]}, {text:'01-03',leaf:true} ]}, {text:'02',leaf:true}]");
//		print("[{id:1,text:\"node1\",children:\"[]\",leaf:true},{id:2,text:\"node2\",children:[{id:3,text:\"node3\",leaf:true}],leaf:false}]");
		// 输出所有目录对应数据
		print(resultStr(strs,logon.getLevel()));
	}

	/**
	 * 后台验证码生成
	 */
	public void checkCode() {
		OutputStream os;
		try {
			os = ServletActionContext.getResponse().getOutputStream();
			// 验证码
			String code = this.outputImage(os);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 验证码对应图片的输出
	 * 
	 * @param os
	 * @return
	 */
	private String outputImage(OutputStream os) {
		BufferedImage img = new BufferedImage(68, 22,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		Random r = new Random();
		Color c = new Color(200, 150, 255);
		g.setColor(c);
		g.fillRect(0, 0, 68, 22);
		StringBuffer sb = new StringBuffer();
		char[] ch = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
		int index, len = ch.length;
		for (int i = 0; i < 4; i++) {
			index = r.nextInt(len);
			// 随机生成图片颜色
			g.setColor(new Color(r.nextInt(88), r.nextInt(188), r.nextInt(255)));
			g.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 22));
			// 随机生成字符
			g.drawString("" + ch[index], (i * 15) + 3, 18);
			sb.append(ch[index]);
		}

		try {
			ServletActionContext.getRequest().getSession().setAttribute("code",
					sb.toString());
			ImageIO.write(img, "JPG", os);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 *  菜单数据：json格式
	 *  递归输出子目录
	 * @param strs
	 * @return
	 */
	private String resultStr(String[] strs,int level) {
		StringBuffer returnStr = new StringBuffer();
		returnStr.append("[");
		if (strs == null){
		    return "";
		}
		for (String str : strs) {
			returnStr.append("{text:" +"\""+ str+"\"");
			returnStr.append(",cls:\"file\"");
			//returnStr.append(",icon:\"/images/extFold.gif\""); //图标对应图片
			// 首页管理： 子目录
			if (str.equals("首页管理")){
			    returnStr.append(",leaf:false");
				returnStr.append(",children:");
//				returnStr.append(resultStr(new String[] {"热点新闻","点击排行","麦米号外","一线地带",
//				        "滚动杂志","热点聚焦(图)","热点聚焦(字)","编辑图片","本周推荐","切米","话题","云标签"},level));
				returnStr.append(resultStr(new String[] {"首页模板管理","首页元素管理","首页事件管理","首页插页事件","切米","推荐标签","推荐杂志","推荐热门杂志","首页榜单管理"},level));
			// 看米管理： 子目录
			}else if(str.equals("看米管理")){
			    returnStr.append(",leaf:false");
			    returnStr.append(",children:");
			    returnStr.append(resultStr(new String[] {"familyissue","familycategory"},level));
			// 杂志分类管理： 子目录
			}else if(str.equals("杂志分类管理")){
			    returnStr.append(",leaf:false");
			    returnStr.append(",children:");
			    returnStr.append(resultStr(new String[] {"原始分类","展示分类"},level));			    
			// 统计： 子目录
			}else if(str.equals("统计")){
			    returnStr.append(",leaf:false");
                returnStr.append(",children:");
                // 根据不同权限显示不同菜单
                if (level > 1) {
                    returnStr.append(resultStr(new String[] {"杂志周排名"},level));
                } else {
                    returnStr.append(resultStr(new String[] {"期刊流量","杂志流量","杂志周排名（制作）"},level));
                }
            // 统计系统： 子目录
            }else if(str.equals("统计系统")){
                returnStr.append(",leaf:false");
                returnStr.append(",children:");
                returnStr.append(resultStr(new String[] {"杂志阅读排行","杂志阅读周排行","期刊阅读报表","期刊页码报表","统计明细图表","事件排行报表","站内搜索报表"},level));
            // 用户行为统计： 子目录
            }else if(str.equals("用户行为统计")){
                returnStr.append(",leaf:false");
                returnStr.append(",children:");
                returnStr.append(resultStr(new String[] {"黏着访问量","黏着度"},level));
			}else{
			    returnStr.append(",leaf:true");
			}
			returnStr.append("}");
		}
		returnStr.append("]");
		// 以标准json格式返回
		return returnStr.toString().replace("}{", "},{");
	}

	/**
	 * 用户信息取得
	 * @return
	 */
	public Admin getAdmin() {
		return admin;
	}

	/**
	 * 用户信息设定
	 * @param admin
	 */
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	/**
	 * 验证码取得
	 * @return
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 验证码设定
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 返回页面信息取得
	 */
	public String getInfo() {
		return info;
	}

	/**
	 * 返回页面信息设定
	 */
	public void setInfo(String info) {
		this.info = info;
	}

}
