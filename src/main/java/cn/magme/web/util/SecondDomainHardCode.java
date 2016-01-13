package cn.magme.web.util;

import java.util.HashMap;
import java.util.Map;

import cn.magme.util.StringUtil;

public class SecondDomainHardCode {
	/**
	 * 由于页面需要大量的硬编码，统一移动到这里，用于二级域名使用,这个需求是seo部门的需求，硬编码是无奈的，不要找我哈，需求的时候说明过了这是硬编码
	 */
	public static Map<String,String> hardCodePageInfo(String sortName){
	    if(StringUtil.isBlank(sortName)){
	    	return null;
	    }
	    String secondTitle=null;
	    String secondKeyword=null;
	    String secondDesc=null;
	    String altval=null;
	    Map<String,String> map=new HashMap<String,String>();
	    if(sortName.equalsIgnoreCase("caijing")){
	       secondTitle="财经杂志电子版免费在线阅读-麦米网财经杂志频道";
	       secondKeyword="财经杂志电子版,世界知名的财经杂志,著名财经杂志,最好的财经杂志";
	       secondDesc="麦米网财经杂志频道为您提供世界知名的财经杂志电子版免费在线阅读.其中包括创业邦,东亚企业家,中国投资等国内外著名财经杂志.找最好的财经杂志,上麦米网财经杂志频道.";
	       altval="财经杂志电子版在线阅读";
	    }else if(  sortName.equalsIgnoreCase("jiaju")){
	       secondTitle="家居杂志电子版免费在线阅读-麦米网家居杂志频道";
	       secondKeyword="家居杂志,时尚家居杂志,精品家居杂志,瑞丽家居杂志";
	       secondDesc="麦米网家居杂志频道为您提供全球主流的时尚家居杂志,国际杂志的等精品家居杂志的订阅及下载.免费阅读国内外知名家居杂志：《瑞丽家居杂志》,《宜家家居杂志》,《缤纷家居杂志》,更有家居杂志图片供您参考.";
	       altval="家居杂志电子版在线阅读";
	    }else if(  sortName.equalsIgnoreCase("lvyou")){
	       secondTitle="旅游杂志电子版免费在线阅读_麦米网旅游杂志频道";
	       secondKeyword="旅游杂志,时尚旅游杂志,最好的旅游杂志,中国旅游杂志,麦米网旅游杂志频道";
	       secondDesc="麦米网旅游杂志频道为您提供最好,最时尚旅游杂志,以及世界著名景区,中国城市旅游景点最全面的旅游信息.经典的旅游杂志供您免费阅读:《中国城市旅游杂志》《世界旅游杂志》.全方位绿色的生态旅游资讯。";
	       altval="旅游杂志电子版在线阅读";
	    }else if(  sortName.equalsIgnoreCase("nanxing")){
	       secondTitle="男性杂志电子版免费在线阅读-麦米网男性杂志频道";
	       secondKeyword="男性杂志,时尚男性杂志,全球男性杂志,最好的男性杂志";
	       secondDesc="麦米网男性杂志频道为您提供世界各国时尚男性杂志免费在线阅读和下载,以及高端成功人士必备的知名杂志:《全球精英》《高尔夫玩家》《纽扣》.给喜欢男性杂志的读者提供最好的阅读享受。";
	       altval="男性杂志电子版在线阅读";
	    }else if(  sortName.equalsIgnoreCase("nvxing")){
	       secondTitle="女性杂志电子版免费在线阅读_麦米网女性杂志频道";
	       secondKeyword="女性杂志排行榜,健康女性杂志,中国女性杂志,瑞丽女性杂志";
	       secondDesc="麦米网女性杂志频道给万千女性创造时尚健康的曼妙都市生活,这里有健康女性杂志,中国女性杂志等电子版杂志免费阅读体验,《都市丽人》《爱丽杂志》《女友hi》等系列知名女性杂志期刊,有了您的阅读,生活将会加精彩";
	       altval="女性杂志电子版在线阅读";
	    }else if(  sortName.equalsIgnoreCase("qiche")){
	       secondTitle="汽车杂志电子版免费在线阅读-麦米网汽车杂志频道";
	       secondKeyword="汽车杂志推荐,汽车杂志订阅,汽车杂志有哪些";
	       secondDesc="麦米网汽车杂志频道为您推荐最新的汽车杂志,其中包括《动感驾驭》《购车情报》等国内外知名的汽车杂志.并提供汽车杂志订阅与下载.想了解汽车杂志有哪些,上麦米网汽车杂志频道!";
	       altval="汽车杂志电子版在线阅读";
	    }else if(  sortName.equalsIgnoreCase("qinggan")){
	       secondTitle="情感杂志电子版免费在线阅读-麦米网情感杂志频道";
	       secondKeyword="女性情感杂志,情感杂志在线阅读,都市情感杂志";
	       secondDesc="麦米网情感杂志频道让你融入都市情感杂志的曼妙生活.免费提供《都市丽人》《品味》《风尚志》《女友hi》等众多女性情感杂志在线阅读与下载.你可以随时随地享受都市情感给你带来的生活中的炫彩！";
	       altval="情感杂志电子版在线阅读";
	    }else if(  sortName.equalsIgnoreCase("shishang")){
	       secondTitle="时尚杂志电子版免费在线阅读_麦米网时尚杂志频道";
	       secondKeyword="男士时尚杂志,女性时尚杂志,电子时尚杂志,时尚杂志在线阅读";
	       secondDesc="麦米网时尚杂志频道为杂志迷们提供男性时尚,女性时尚以及电子时尚杂志的在线阅读与下载,其中包括品味,格调以及瑞丽时尚杂志.想知道时尚杂志有哪些？来麦米网时尚杂志频道";
	       altval="时尚杂志电子版在线阅读";
	    }else if(  sortName.equalsIgnoreCase("tech")){
	       secondTitle="IT杂志电子版免费在线阅读-麦米网IT杂志频道";
	       secondKeyword="it杂志排行,it杂志下载,国外it杂志";
	       secondDesc="麦米网it杂志频道为你推荐更多的it杂志排行,it杂志下载以及国外it杂志的信息,其中包括《软件和信息服务》《计算机光盘软件与应用》等专业的it杂志免费在线阅读,让你的it杂志体验无处不在！";
	       altval="IT杂志电子版在线阅读";
	    }else if(  sortName.equalsIgnoreCase("wenhua")){
	       secondTitle="文化杂志电子版免费在线阅读_麦米网文化杂志频道";
	       secondKeyword="商业文化杂志,企业文化杂志,建筑与文化杂志";
	       secondDesc="麦米网文化杂志频道为你提供商业文化杂志,建筑与文化杂志,现代企业文化杂志等各领域知名杂志的在线阅读与下载.主要有风尚北京,风尚上海,澳门报告众多知名文化杂志期刊.";
	       altval="文化杂志电子版在线阅读";
	    }else if(  sortName.equalsIgnoreCase("yishu")){
	       secondTitle="艺术杂志电子版免费在线阅读-麦米网艺术杂志频道";
	       secondKeyword="艺术杂志,电影艺术杂志,人体艺术杂志,钢琴艺术杂志";
	       secondDesc="麦米网艺术杂志频道为你提供更专业的艺术杂志信息,并专注于电影艺术杂志.人体艺术杂志、钢琴艺术杂志,数码艺术杂志等领域.独家提供《品味》《音乐周刊》等系列知名艺术杂志在线阅读与下载.";
	       altval="艺术杂志电子版在线阅读";
	    }else if(  sortName.equalsIgnoreCase("waiwen")){
	       secondTitle="国外英文杂志免费在线阅读_麦米网外文杂志频道";
	       secondKeyword="好的英文杂志,英文杂志网站,国外杂志,外文杂志";
	       secondDesc="麦米网外文杂志频道为您提供国外好的英文杂志在线阅读与下载,包括City Weekend,ShanghaiFamily,that's prd,Home&Office,Parents and kids等众多知名外文杂志.";
	       altval="国外英文杂志电子版在线阅读";
	    }else if(  sortName.equalsIgnoreCase("shenghuo")){
	       secondTitle="生活杂志电子版免费在线阅读_麦米网生活杂志频道";
	       secondKeyword="时尚生活杂志,保险生活杂志,妇女生活杂志,文艺生活杂志";
	       secondDesc="麦米网生活杂志频道为您提供健康,保险,母婴,美食等众多生活类杂志的在线阅读与下载,独家提供美食堂,时尚育儿,时尚孕妇,母婴世界,北京宝宝,健康大视野等著名的生活杂志在线阅读.";
	       altval="生活杂志电子版在线阅读";
	    }else if(  sortName.equalsIgnoreCase("xueshu")){
	       secondTitle="学术杂志电子版免费在线阅读_麦米网学术杂志频道";
	       secondKeyword="省cn级权威性学术杂志,西医内科学学术杂志,土木工程类学术杂志,学术杂志免费在线阅读";
	       secondDesc="麦米网学术杂志频道为您提供土木工程,西医内科学,软件开发,兵工科技等众多省级权威性学术杂志在线阅读和下载.获得了亚洲新能源,开发月刊,模具工程,中国照明等众多学术杂志的授权.";
	       altval="学术杂志电子版在线阅读";  
	    }else if(  sortName.equalsIgnoreCase("yule")){
	       secondTitle="娱乐杂志电子版免费在线阅读_麦米网娱乐杂志频道";
	       secondKeyword="国外娱乐杂志,香港娱乐杂志,台湾娱乐杂志,美国娱乐杂志,娱乐杂志免费在线阅读";
	       secondDesc="麦米网娱乐杂志频道为您提供香港,台湾,美国以及国内最新的时尚娱乐杂志在线阅读和下载.包括音乐时空,影视圈,音乐周刊,现代娱乐,富周刊,星工场,南都娱乐周刊等知名娱乐杂志";
	       altval="娱乐杂志电子版在线阅读";  
		}
	    map.put("secondTitle", secondTitle);
	    map.put("secondKeyword", secondKeyword);
	    map.put("secondDesc", secondDesc);
	    map.put("altval", altval);
	    
		return map;
	}

}
