package cn.magme.web.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 合并ftl文件中，对于css或js的多条连续引用合并为一条
 * @author billy.qi
 *
 */
public class MergeJsCssRef {
    public static String APP_PATH="E:\\work\\ee\\magmecn_web\\target\\magmecnweb";		//在192.168.1.100上的目录
//	public static String APP_PATH="E:\\java\\workspace\\magmecn_web\\target\\magmecnweb";//在192.168.1.30 上的目录
//    public static String URL_PATH="E:\\java\\workspace\\magmecn_web\\target\\magmecnweb\\WEB-INF\\pages\\decorators\\widget_decorator_qplus.ftl";
    //public static String APP_PATH="/home/lfs/workspace2/magmecn_web/target/magmecnweb";		//在192.168.1.13上的目录
    public static final File ROOT_FILE = new File(APP_PATH);
    public static String VERSION = "";
    public static final String JS = "js";
    public static final String CSS = "css";
    private static final String[] SKIP_JS = { "/v3/kindeditor/kindeditor.js", "/kindeditor/kindeditor.js","/v3/js/useFunction.js","/v3/style/global.js" };//忽略的js
    private static final String[] SKIP_CSS = {"/v3/style/global.css" }; //忽略的css
    
    
	public static void main(String[] args) {
		System.out.println("start......\n");
		long time = System.currentTimeMillis();
        SimpleDateFormat sf=new SimpleDateFormat("yyyyMMddHHmm");
        VERSION = "";
//        mergeJSCSSRef(new File(URL_PATH));
        mergeJSCSSRef(new File(APP_PATH));
//        testDir();
        System.out.println("\n\nend!!!!!!!!!!!!");
        CopyWebXmlDsConfigure dealxmlds=new CopyWebXmlDsConfigure(APP_PATH);
        dealxmlds.copyRes();
        time = System.currentTimeMillis() - time;
        System.out.println("cost time : " + time + " ms");
	}

	/**
	 * 将目录下的所有ftl中js/css引用归并起来
	 * @param f
	 */
    public static void mergeJSCSSRef(File f){
    	if(!f.exists()){
    		return;
    	}else{
    		if(f.isFile()){
    			if(f.getName().toLowerCase().endsWith(".ftl")){
    				System.out.println("processing file:" + f.getAbsolutePath());
    				try {
    					BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(f),"utf-8"));
    					PrintWriter pw=new PrintWriter(f.getAbsolutePath()+".temp");
    					String line=null;
    					List<String> jsRefs = new LinkedList<String>();
    					List<String> cssRefs = new LinkedList<String>();
    					boolean jsStart = false;
    					boolean cssStart = false;
    					int jsIndex = 1;
    					int cssIndex = 1;
    					do{
    						line=br.readLine();
    						if(line!=null && !"".equals(line.trim())){
    							if(isJsRefLine(line)){
    								jsStart = true;
    								jsRefs.add(getRefUrl(line, JS));
    							} else if(jsStart){//js 结束，输出组合引用
									jsStart = false;
									String mergeRef = getMergeRef(jsRefs, jsIndex++, JS, f);
									if(mergeRef != null){
										pw.println(mergeRef);
									}
    							}
								if(isCssRefLine(line)){
									cssRefs.add(getRefUrl(line, CSS));
									cssStart = true;
								} else if(cssStart){//css 结束，输出组合引用
									cssStart = false;
									String mergeRef = getMergeRef(cssRefs, cssIndex++, CSS, f);
									if(mergeRef != null){
										pw.println(mergeRef);
									}
								}
								//普通行
								if(!jsStart && !cssStart){
									pw.println(line);
								}
    						}
    					}while(line!=null);
    					br.close();
    					pw.close();
    					mv(f.getAbsolutePath()+".temp",f.getAbsolutePath());
    				} catch (Exception e) {
    					e.printStackTrace();
    				}
    			}
    		}else{
    			File[] fs=f.listFiles();
    			for(File file:fs){
    				mergeJSCSSRef(file);
    			}
    		}
    	}
    }

    private static boolean isJsRefLine(String line){
    	Pattern p = Pattern.compile("\\s*<script.*src=\"\\$\\{systemProp\\.staticServerUrl\\}.*\\.js");
    	Matcher m = p.matcher(line);
    	boolean match = m.find();
    	if(match && SKIP_JS.length > 0)
    		for (int i = 0; i < SKIP_JS.length; i++)
    			if(line.indexOf(SKIP_JS[i]) > -1)
    				return false;
    	return match;
    }
    
    private static boolean isCssRefLine(String line){
    	Pattern p = Pattern.compile("\\s*<link.*href=\"\\$\\{systemProp\\.staticServerUrl\\}.*\\.css");
    	Matcher m = p.matcher(line);
    	boolean match = m.find();
    	if(match && SKIP_CSS.length > 0)
    		for (int i = 0; i < SKIP_CSS.length; i++)
    			if(line.indexOf(SKIP_CSS[i]) > -1)
    				return false;
    	return match;
    }
    
    private static String getRefUrl(String line, String suffix){
    	Pattern p = Pattern.compile("\\$\\{systemProp\\.staticServerUrl\\}.*\\." + suffix);
    	Matcher m = p.matcher(line);
    	String url = null;
    	if(m.find()){
			url = m.group();
			url = url.replace("${systemProp.staticServerUrl}", "");
		}
    	return url;
    }
    
    private static String getMergeRef(List<String> urls, int index, String suffix, File file){
    	if(urls == null || urls.isEmpty()) return null;
    	String url = "";
    	if(urls.size() > 1) {
    		url = "/v3/" + suffix + "/";//js
    		if(CSS.equals(suffix)){
    			url = "/v3/style/";
    		}
    		File f = new File(APP_PATH + url);
    		if(!f.exists())
    			f.mkdirs();
    		//兼容window环境和linux环境
    		String fileName = file.getAbsolutePath().replace(APP_PATH, "").replaceAll("\\\\", ".").replaceAll("/", ".");
    		url += VERSION + "_merge_for_" + fileName + "_" + index + "." + suffix;
    		copyFile(urls, APP_PATH + url, suffix);
    	} else {
    		url = urls.get(0);
    	}
    	urls.clear();
    	String ref = "<script src='${systemProp.staticServerUrl}" + url + "'></script>";
    	if(CSS.equalsIgnoreCase(suffix)){
    		ref = "<link href='${systemProp.staticServerUrl}" + url + "' rel='stylesheet' type='text/css'/>";
    	}
    	return ref;
    }

    /**    
    *  组合文件    
     * @param suffix 
    *  @return  boolean    
    */     
    public static void copyFile(List<String> urls, String newPath, String suffix) {   
	    try {
	    	StringBuffer content = new StringBuffer();
	    	//读取内容，在组合多个css文件时将@import和@charset等以@符号开头的行移动到文件头
		    for (String oldPath : urls) {
			    oldPath = APP_PATH + oldPath;
			    StringBuffer fileStr = readFile(oldPath);
			    //在每个js文件前加一行分号，以避免文件之间关联
			    if(JS.equals(suffix)){
			    	content.append(";\r\n");
			    }
				content.append(fileStr);
		    }
		    BufferedWriter bw = new BufferedWriter(new FileWriter(newPath));
		    bw.write(content.toString());
		    bw.flush();
		    bw.close();
	    } catch  (Exception  e)  {
		    System.out.println("复制文件操作出错");     
		    e.printStackTrace();     
	    }     
    }
    
    /**
     * 测试获取js/css文件中用来替换多个'../'的路径是否正确
     */
    @SuppressWarnings("unused")
	private static void testDir(){
    	String[] files = {"E:\\java\\workspace\\magmecn_web\\target\\magmecnweb\\v3\\widget20120529\\style\\style.css",
    			"E:\\java\\workspace\\magmecn_web\\target\\magmecnweb\\v3\\style\\pop.css"
    	};
    	for (String path : files) {
			File file = new File(path);
			System.out.println("path=" + path);
			System.out.println("dir1=" + getAbsolutePathFromRoot(file, 1));
			System.out.println("dir2=" + getAbsolutePathFromRoot(file, 2));
			System.out.println("dir3=" + getAbsolutePathFromRoot(file, 3));
			System.out.println("\n\n");
		}
    }
    /**
     * ../的种类
     */
    private static String[] UP_DIR_TYPES = { "\\.\\./", "\\.\\./\\.\\./", "\\.\\./\\.\\./\\.\\./", "\\.\\./\\.\\./\\.\\./\\.\\./"};
    /**
     * 字符串中含有../的相对路径改为绝对路径
     * @param str
     * @param replacement
     * @return
     */
    private static String replaceDirString(String str, String[] replacement){
    	for (int i = UP_DIR_TYPES.length; i > 0; i--) {
			String temp = UP_DIR_TYPES[i - 1];
			String replaceString = replacement[i - 1];
			str = str.replaceAll(temp, replaceString);
		}
    	return str;
    }
    /**
     * 获取文件中，多级../相对路径对应的绝对路径
     * @param file 当前文件
     * @param upNum ../数量：如 ../../ 对应的数字为 2
     * @return
     */
    private static String getAbsolutePathFromRoot(File file, int upNum){
    	file = file.getParentFile();
    	String url = "";
    	for(int i = 0; i < upNum; i++){
    		file = file.getParentFile();
    		if(ROOT_FILE.equals(file)){
    			return "/";
    		} else {
    			url = file.getName() + "/";
    		}
    	}
    	file = file.getParentFile();
    	while(!ROOT_FILE.equals(file)){
    		url = file.getName() + "/" + url;
    		file = file.getParentFile();
    	}
    	url = "/" + url;
    	return url;
    }

	private static StringBuffer readFile(String path) {
		StringBuffer content = new StringBuffer();
		File oldfile = new File(path);   
		if (oldfile.exists()) { //文件存在时   
			BufferedReader br = null;
			try {
				String[] replacement = new String[UP_DIR_TYPES.length];
				for (int i = 0; i < replacement.length; i++) {
					replacement[i] = getAbsolutePathFromRoot(oldfile, i + 1);
				}
				br = new BufferedReader(new FileReader(oldfile));
				String line = "";
				while((line = br.readLine()) != null){
					while(line.length() > 0 && line.charAt(0) == 65279){
						line = line.substring(1);
					}
					// @import url("jquery.jscrollpane.css");
					if(line.trim().indexOf("@import") == 0){//导入文件
						int firstIdx = line.indexOf('\"') + 1;
						int lastIdx = line.indexOf('\"', firstIdx);
						String name = line.substring(firstIdx, lastIdx);
						String url = getImportUrl(path, name);
						line = readFile(url).toString();//递归读取import文件
					} else if(line.trim().indexOf("../") > -1){
						line = replaceDirString(line, replacement);
					}
					line += "\r\n";
					content.append(line);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return content;
	}
	/**
	 * 获取导入文件的绝对路径
	 * @param url 当前文件绝对路径
	 * @param name 导入文件相对地址
	 * @return 导入文件的绝对地址
	 */
	private static String getImportUrl(String url, String name) {
		boolean first = true;
		do {
    		//兼容window环境和linux环境
			int lastDirLeft = url.lastIndexOf('/');
			int lastDirRight = url.lastIndexOf('\\');
			int lastDir = Math.max(lastDirLeft, lastDirRight);//最后一个路径分隔符
			url = url.substring(0, lastDir);//路径中去掉最后一个分隔符
			if(first) {
				first = false;
			} else {
				name = name.replace("../", "");//名称中去掉第一个../
			}
		} while (name.contains("../"));//需要退回上一级目录
		url += "/" + name;
		return url;
	}
    
    public static boolean mv(String src,String dest){
        File sf=new File(src);
        if(!sf.exists()){
            return false;
        }else{
            if(sf.isFile()){
                try {
                    FileInputStream fis=new FileInputStream(sf);
                    FileOutputStream fos=new FileOutputStream(dest);
                    byte[] bs=new byte[10240];
                    int cnt=0;
                    do{
                        cnt=fis.read(bs);
                        if(cnt>0){
                            fos.write(bs, 0, cnt);
                        }
                    }while(cnt>0);
                    fis.close();
                    fos.close();
                    sf.deleteOnExit();
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }else{
                return false;
            }
        }
    }
}
