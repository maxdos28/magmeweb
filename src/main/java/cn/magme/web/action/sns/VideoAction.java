package cn.magme.web.action.sns;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import cn.magme.common.JsonResult;
import cn.magme.web.action.BaseAction;

public class VideoAction extends BaseAction{
	private static final long serialVersionUID = 773199919377802112L;
	private static final Logger log=Logger.getLogger(VideoAction.class);
	
	@Override
	public String execute() throws Exception {
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		try{
			if(null!=video){
				String tudouapi="http://api.tudou.com/v3/gw?method=item.info.get&appKey=3aeaf723c84fb5ce&format=json&itemCodes=";
				String tudou="^.*tudou.com.*$";
				String youku="^.*youku.com.*$";
				String ku6="^.*ku6.com.*$";
				Pattern p = Pattern.compile(tudou);
				Matcher m = p.matcher(video);
				if(m.matches()){
					String codeRex=".*/programs/view/.*/";
					String condRex1=".*/playlist/p/le/.*/.*/play.html";
					p=Pattern.compile(codeRex);
					m = p.matcher(video);
					
					Pattern p1 = Pattern.compile(condRex1);
					Matcher m1 = p1.matcher(video);
					
					if(m.matches() || m1.matches()){
						String code="";
						if(m.matches())
							code=video.substring(video.indexOf("/view/")+6,video.lastIndexOf("/"));
						if(m1.matches()){
							String temp=video.replace("/play.html", "");
							code=video.substring(temp.lastIndexOf("/")+1,video.lastIndexOf("/play.html"));
						}
						if(code!=""){
							tudouApi(tudouapi, code);
						}
						 return JSON;
						 
					}
					
					String idsRex=".*/playlist/p/a\\d+.html";
					String idsRex1=".*/playlist/p/a\\d+i.*.html";
					
					p=Pattern.compile(idsRex);
					m=p.matcher(video);
					p1=Pattern.compile(idsRex1);
					m1=p1.matcher(video);
					if(m.matches() || m1.matches()){
						HttpURLConnection htc = (HttpURLConnection) new URL(video).openConnection();
						htc.connect();
						BufferedReader in = new BufferedReader(new InputStreamReader(htc.getInputStream()));
						 
						InputStream inputStream = htc.getInputStream();
						byte bytes[] = new byte[1024*100]; 
						int index = 0;
						int count = inputStream.read(bytes, index, 1024*100);
						while (count != -1) {
						  index += count;
						  count = inputStream.read(bytes, index, 1);
						  
						}
						inputStream.close();
						in.close();
						
						String htmlContent = new String(bytes, "gb2312");
						htmlContent=htmlContent.substring(htmlContent.indexOf("<meta name=\"description\" content=\"")+34);
						//内容描述
					 	String description=htmlContent.substring(0,htmlContent.indexOf("/>")-1);
						this.jsonResult.put("title", description);
						
					 	htmlContent=htmlContent.substring(htmlContent.indexOf("listData = [{")+12);
					    htmlContent=htmlContent.substring(0,htmlContent.indexOf("}")+1);
					   	String[] listData = htmlContent.split(",");
					   	htmlContent=null;
					    for (int i = 0; i < listData.length; i++) {
							if(listData[i].indexOf("iid:")!=-1){
								 String iid=listData[i].substring(listData[i].indexOf("iid")+4);
								 this.jsonResult.put("playerUrl", "http://www.tudou.com/v/"+iid+"/v.swf");
							 }else if(listData[i].indexOf("pic:")!=-1){
								 String picUrl=listData[i].substring(listData[i].indexOf("pic:")+4).replace("\"", "");
								 this.jsonResult.put("picUrl", picUrl);
							 }
						}
						if(listData.length>1)
							this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
						return JSON;
					}
					
					String iidRex=".*/playlist/p/l.*.html";
					p=Pattern.compile(iidRex);
					m=p.matcher(video);
					if(m.matches()){
						HttpURLConnection htc = (HttpURLConnection) new URL(video).openConnection();
						htc.connect();
						BufferedReader in = new BufferedReader(new InputStreamReader(htc.getInputStream()));
						 
						InputStream inputStream = htc.getInputStream();
						byte bytes[] = new byte[1024*100]; 
						int index = 0;
						int count = inputStream.read(bytes, index, 1024*100);
						while (count != -1) {
						  index += count;
						  count = inputStream.read(bytes, index, 1);
						  
						}
						inputStream.close();
						in.close();
						
						String icode="";
						String htmlContent = new String(bytes, "gb2312");
						htmlContent=htmlContent.substring(htmlContent.indexOf("listData = [{")+12);
					    htmlContent=htmlContent.substring(0,htmlContent.indexOf("}")+1);
					   	String[] listData = htmlContent.split(",");
					   	htmlContent=null;
					    for (int i = 0; i < listData.length; i++) {
							if(listData[i].indexOf("icode:")!=-1){
								icode=listData[i].substring(listData[i].indexOf("icode")+7).replace("\"", "");
								 break;
							 }
						}
					    if(icode!=""){
					    	tudouApi(tudouapi, icode);
					    }
					    
						return JSON;
					}
					
				}
				
				p=Pattern.compile(youku);
				m = p.matcher(video);
				if(m.find()){
					HttpURLConnection htc = (HttpURLConnection) new URL(video).openConnection();
					htc.connect();
					BufferedReader in = new BufferedReader(new InputStreamReader(htc.getInputStream()));
					 
					InputStream inputStream = htc.getInputStream();
					byte bytes[] = new byte[1024*100]; 
					int index = 0;
					int count = inputStream.read(bytes, index, 1024*100);
					while (count != -1) {
					  index += count;
					  count = inputStream.read(bytes, index, 1);
					  
					}
					inputStream.close();
					in.close();
					
					String htmlContent = new String(bytes, "utf-8");
					htmlContent=htmlContent.substring(htmlContent.indexOf("videoId"));
					String playerUrl = "http://player.youku.com/player.php/sid/"+htmlContent.substring(10,htmlContent.indexOf(";")).replace("'", "")+"/v.swf";
					this.jsonResult.put("playerUrl",playerUrl);
					htmlContent=htmlContent.substring(htmlContent.indexOf("转发到MSN"));
					htmlContent=htmlContent.substring(0,htmlContent.indexOf("</a>"));
					String[] yklist = htmlContent.split("&");
					for (int i = 0; i < yklist.length; i++) {
						if(yklist[i].indexOf("Title")!=-1){
							String title = URLDecoder.decode(yklist[i].substring(6),"utf-8");
							this.jsonResult.put("title", title);
						}else if(yklist[i].indexOf("screenshot")!=-1){
							String picurl = yklist[i].substring(11, yklist[i].indexOf("\" target"));
							this.jsonResult.put("picUrl", picurl);
						}
					}
					if(yklist.length>1)
						this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
					
					return JSON;
				}
				
				p=Pattern.compile(ku6);
				m = p.matcher(video);
				if(m.find()){
					String htmlContent = "";
				    java.io.InputStream inputStream;
				    java.net.URL url = new java.net.URL(video);

				    java.net.HttpURLConnection connection = (java.net.HttpURLConnection) url.openConnection();
				    connection.connect();
				    inputStream = connection.getInputStream();
				    byte bytes[] = new byte[1024*100]; 
				    int index = 0;
				    int count = inputStream.read(bytes, index, 1024*100);
				    while (count != -1) {
				      index += count;
				      count = inputStream.read(bytes, index, 1);
				      
				    }
				    inputStream.close();
				    htmlContent = new String(bytes,0,index,"gb2312");//
				    htmlContent=htmlContent.substring(htmlContent.indexOf("VideoInfo"));
					htmlContent=htmlContent.substring(htmlContent.indexOf("VideoInfo"),htmlContent.indexOf("};"));
					htmlContent=htmlContent.substring(htmlContent.indexOf("{")+1,htmlContent.indexOf(", data:"));
					String[] yklist = htmlContent.split(",");
					for (int i = 0; i < yklist.length; i++) {
						String temp=yklist[i];
						if(temp.indexOf("title")!=-1){
							String title=temp.trim().substring(6).replace("\"", "").trim();
							this.jsonResult.put("title", title);
						}else if(temp.trim().indexOf("cover")!=-1){
							String picurl=temp.trim().substring(7).replace("\"", "").trim();
							this.jsonResult.put("picUrl", picurl);
						}else if(temp.indexOf("id:")!=-1 && temp.indexOf("uid:")==-1){
							String playUrl=temp.trim().substring(3).replace("\"", "").trim();
							
							this.jsonResult.put("playerUrl","http://player.ku6.com/refer/"+playUrl+"/v.swf");
						}
						
					}
					if(yklist.length>1)
						this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
					
					return JSON;
				}
			}
		}catch (Exception e) {
			log.error("", e);
		}
		return JSON;
	}
	
	private void tudouApi(String tudouapi,String code) throws Exception{
		HttpURLConnection htc;
		htc = (HttpURLConnection) new URL(tudouapi+code).openConnection();
		htc.connect();
		 BufferedReader in = new BufferedReader(new InputStreamReader(htc.getInputStream()));
		 String inputLine;
		 StringBuffer sbf=new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 sbf.append(new String(inputLine.getBytes(),"utf-8"));
	      }
		 in.close();
		 String[] resJson = sbf.toString().split("\",\"");
		 for(int i=0; i<resJson.length;i++){
			 if(resJson[i].indexOf("title\":\"")!=-1){
				 String title=resJson[i].substring(resJson[i].indexOf("title")+8);
				 this.jsonResult.put("title", title);
			 }else if(resJson[i].indexOf("picUrl\":\"")!=-1){
				 String picUrl=resJson[i].substring(resJson[i].indexOf("picUrl")+9);
				 this.jsonResult.put("picUrl", picUrl);
			 }else if(resJson[i].indexOf("outerPlayerUrl\":\"")!=-1){
				 String playerUrl=resJson[i].substring(resJson[i].indexOf("outerPlayerUrl")+17);
				 this.jsonResult.put("playerUrl", playerUrl);
			 }
		 }
		 if(resJson.length>1)
			 this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
	}
	
	private String video;

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

}
