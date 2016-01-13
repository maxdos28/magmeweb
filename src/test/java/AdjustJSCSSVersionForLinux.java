import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import cn.magme.util.FileOperate;


/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */

/**
 * @author jacky_zhou
 * @date 2011-7-20
 * @version $id$
 */
public class AdjustJSCSSVersionForLinux {   

    private static Logger log = Logger.getLogger(AdjustJSCSSVersionForLinux.class);
    
    public static String APP_PATH="/home/lfs/workspace2/magmecn_web/target/magmecnweb";
    
    private static final String sitemapProp="/usr/local/magmecn/webapp/magmecnweb71/";
    
    public static String DEPLOY_PATH="/home/lfs/deploy";
    
    public static Map<String,String> map=new HashMap<String,String>();
    
    public static Map<String,String> map1=new HashMap<String,String>();
    
    public static Map<String,String> map2=new HashMap<String,String>();
    
    public static final Pattern pattern=Pattern.compile("[\\u4e00-\\u9fa5]+",Pattern.CASE_INSENSITIVE);
    
    private static final String TAR_CMD="tar -cvzf ";
    
    private static final SimpleDateFormat sdf2=new SimpleDateFormat("yyyy_MM_dd");
    
    
    
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub 
        log.info("程序开始运行");
        log.info("步骤一:检查是否存在同名的JS或者CSS文件");
        check(new File(APP_PATH));
//        if(map2.isEmpty()){
//            log.info("检查通过,不存在同名的JS或者CSS文件,可以往下运行");
//        }else{
//            log.info("检查不通过,存在以下同名的JS或者CSS文件:");
//            for(String key:map2.keySet()){
//                //log.info(key);
//                System.out.println(key);
//            }
//            log.info("程序退出");
//            return ;
//        }
        SimpleDateFormat sf=new SimpleDateFormat("yyyyMMddHHmm");
        String version=sf.format(new Date());
        log.info("自动生成此次发布的版本号:"+version);
        //复制ipad.index.js，ipad.index.ftl两个文件，修改system.properties,jdbc.properties,log4j.propertis为线上的文件，删除一些无用的文件
        updatefile(version);
        
        renameJSCSS(new File(APP_PATH),version);
        for(String key:map.keySet()){
        	System.out.println(key+"->"+map.get(key));
        	//log.info(key+"->"+map.get(key));
        }

        adjustJSCSSVersion(new File(APP_PATH));
        //removeFiles(new File(APP_PATH),"html");
        removeChineseFile(new File(APP_PATH));
        archive();
        
    }
    
    /**
     * 删除指定扩展名的所有文件，但是包含index的除外
     * @param root
     * @param extend
     */
    private static void removeFiles(File root,String extend){
    	for(File rootDir:root.listFiles()){
    		if(rootDir.isDirectory()){
    			removeFiles(rootDir,extend);
    		}else if(rootDir.getName().toLowerCase().endsWith(extend) 
    				&& !rootDir.getParentFile().getAbsolutePath().equalsIgnoreCase(APP_PATH)){
    				/*&& !rootDir.getName().toLowerCase().contains("index")
    				&& !rootDir.getName().toLowerCase().contains("baidu")
    				&& !rootDir.getName().toLowerCase().contains("admin_login")
    				&& !rootDir.getName().toLowerCase().contains("static")
    				&& !rootDir.getName().toLowerCase().contains("error")
    				&& !rootDir.getName().toLowerCase().contains("qq")
    				&& !rootDir.getName().toLowerCase().contains("main")*/
    			rootDir.delete();
    		}
    	}
    }
    
    private static void removeChineseFile(File root){
    	for(File rootDir:root.listFiles()){
    		Matcher m=pattern.matcher(rootDir.getName());
    		if(rootDir.isDirectory()){
    			removeChineseFile(rootDir);
    		}else if(m.lookingAt()){
    			rootDir.delete();
    		}
    	}
    }
    
    public static void main2(String[] args) {
    	removeChineseFile(new File(APP_PATH));
	}
    
    
    /**
     * 压缩文件
     */
    private static void archive(){
    	String parentPath=new File(APP_PATH).getParentFile().getAbsolutePath();
    	//FileOperate.delFile(warFile.getAbsolutePath());
    	
    	File zipStaticPath=new File(parentPath+File.separator+"static");
    	if(!zipStaticPath.exists()){
    		zipStaticPath.mkdir();
    	}
    	//删除v3下所有的html文件
    	/*File v3File=new File(APP_PATH+File.separator+"v3");
    	Pattern pattern=Pattern.compile(".*\\.html(\\.temp)?$");
    	File [] v3FileList=v3File.listFiles();
    	for(File f:v3FileList){
    		if(f.isFile()){
    			if(pattern.matcher(f.getName()).matches()){
    				f.delete();
    			}
    		}
    	}*/
    	
    	//删除所有的temp文件
    	deleteAllTempFile(new File(APP_PATH));
    	File deployStaticFile=new File(DEPLOY_PATH+File.separator+"static");
    	if(!deployStaticFile.exists()){
    		deployStaticFile.mkdirs();
    	}
    	
    	//zip static files
    	FileOperate.copyFolder(APP_PATH+File.separator+"js", deployStaticFile.getAbsolutePath()+File.separator+"js");
    	FileOperate.copyFolder(APP_PATH+File.separator+"images", deployStaticFile.getAbsolutePath()+File.separator+"images");
    	FileOperate.copyFolder(APP_PATH+File.separator+"reader", deployStaticFile.getAbsolutePath()+File.separator+"reader");
    	FileOperate.copyFolder(APP_PATH+File.separator+"style", deployStaticFile.getAbsolutePath()+File.separator+"style");
    	FileOperate.copyFolder(APP_PATH+File.separator+"v3", deployStaticFile.getAbsolutePath()+File.separator+"v3");
    	FileOperate.copyFolder(APP_PATH+File.separator+"widget", deployStaticFile.getAbsolutePath()+File.separator+"widget");
    	FileOperate.copyFolder(APP_PATH+File.separator+"kindeditor", deployStaticFile.getAbsolutePath()+File.separator+"kindeditor");
    	
    	
    	if(!new File(DEPLOY_PATH+File.separator+sdf2.format(new Date())).exists()){
    		new File(DEPLOY_PATH+File.separator+sdf2.format(new Date())).mkdirs();
    	}
    	
    	File staticTarFile=new File(DEPLOY_PATH+File.separator+sdf2.format(new Date())+File.separator+"static.tar.gz ");
    	//if(staticTarFile.exists()){
    	staticTarFile.delete();
    	//}
    	
    	File appTarFile=new File(DEPLOY_PATH+File.separator+sdf2.format(new Date())+File.separator+"magmecnweb.tar.gz ");
    	//if(appTarFile.exists()){
    	appTarFile.delete();
    	//}
    	
    	//
    	String staticCmd=TAR_CMD+staticTarFile.getAbsolutePath()+" "+DEPLOY_PATH+File.separator+"static";
    	try {
    		Process pr1=Runtime.getRuntime().exec(staticCmd);
    		//pr1.waitFor();
    		String s="";
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(pr1.getInputStream()));
			   String line = null;
		   while ((line = in.readLine()) != null) {
		    s += line + "\n";    
		   }
		   System.out.println(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	String appCmd=TAR_CMD+appTarFile.getAbsolutePath()+" "+APP_PATH;
    	try {
			Process pr2=Runtime.getRuntime().exec(appCmd);
			//pr2.waitFor();
			String s="";
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(pr2.getInputStream()));
			   String line = null;
		   while ((line = in.readLine()) != null) {
		    s += line + "\n";    
		   }
		   System.out.println(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	/*File zipFile=new File(parentPath+File.separator+"static.zip");
    	File srcdir = new File(APP_PATH+File.separator+"static");  
        if (!srcdir.exists())  
            throw new RuntimeException(srcdir + "不存在！");  
        
        Project prj = new Project();  
        Zip zip = new Zip();  
        zip.setProject(prj);  
        zip.setDestFile(zipFile);  
        FileSet fileSet = new FileSet();  
        fileSet.setProject(prj);  
        fileSet.setDir(srcdir);  
        zip.addFileset(fileSet);  
        zip.execute();  
        
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        File deploy=new File(DEPLOY_PATH+File.separator+sdf.format(new Date()));
        if(deploy.exists()){
        	FileOperate.delAllFile(deploy.getAbsolutePath());
        }
        deploy.mkdirs();
        //File warFile=new File(parentPath+File.separator+"magmecnweb.war");
    	//FileOperate.copyFile(warFile.getAbsolutePath(), deploy+File.separator+"magmecnweb.zip");
        File staticFile=new File(deploy+File.separator+"static.zip");
        if(staticFile.exists()){
        	staticFile.delete();
        }
    	FileOperate.copyFile(zipFile.getAbsolutePath(), staticFile.getAbsolutePath());
    	
        
        File zipFile2=new File(parentPath+File.separator+"magmecnweb.zip");
        File srcdir2 = new File(APP_PATH); 
        Project prj2 = new Project();  
        Zip zip2 = new Zip();  
        zip2.setProject(prj);  
        zip2.setDestFile(zipFile2);  
        FileSet fileSet2 = new FileSet();  
        fileSet2.setProject(prj2);  
        fileSet2.setDir(srcdir2);  
        zip2.addFileset(fileSet2);  
        zip2.execute(); 
        
        File webzipFile=new File(deploy+File.separator+"magmecnweb.zip");
        if(webzipFile.exists()){
        	webzipFile.delete();
        }
        FileOperate.copyFile(zipFile2.getAbsolutePath(), webzipFile.getAbsolutePath());*/
    	
    }
    
    public static void deleteAllTempFile(File file){
    	if(!file.exists()){
    		return ;
    	}
    	Pattern pattern=Pattern.compile(".*\\.temp$");
    	for(File f:file.listFiles()){
    		if(f.isDirectory()){
    			deleteAllTempFile(f);
    		}else{
    			if(pattern.matcher(f.getName()).matches()){
    				f.delete();
    			}
    		}
    	}
    }
    
    private static void updatefile(String version){
    	 //修改属性文件
        try {
        	//system.properties
			/*Properties prop=new Properties();
			prop.load(new FileReader(APP_PATH+"\\WEB-INF\\classes\\system.properties.online"));
			prop.setProperty("systemVersion", version);
			prop.store(new FileWriter(APP_PATH+"\\WEB-INF\\classes\\system.properties"), "system configuration");*/
			
			//system properties
			/*FileWriter writer=new FileWriter(APP_PATH+"\\WEB-INF\\classes\\system.properties.online");
			writer.append("systemVersion="+version);
			writer.flush();
			writer.close();*/
			
			String systemprop=APP_PATH+"/WEB-INF/classes/system.properties";
			FileOperate.delFile(systemprop);
			InputStreamReader is = new InputStreamReader(new FileInputStream(APP_PATH+"/WEB-INF/classes/system.properties.online"));
			BufferedReader br = new BufferedReader(is);
			File f = new File(systemprop);
			String s;
			BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));
			while ((s = br.readLine()) != null) {
				bw.write(s);
				bw.newLine();
			}
			bw.write("systemVersion="+version);
			bw.newLine();
			bw.write("siteMapPath="+sitemapProp);
			bw.newLine();
			bw.close();
			//oper.copyFile(APP_PATH+"\\WEB-INF\\classes\\system.properties.online", systemprop);
			
			//jdbc
			String jdbcprop=APP_PATH+"/WEB-INF/classes/jdbc.properties";
			FileOperate.delFile(jdbcprop);
			FileOperate.copyFile(APP_PATH+"/WEB-INF/classes/jdbc.properties.online", jdbcprop);
			
			//log4j
			String log4jprop=APP_PATH+"/WEB-INF/classes/log4j.properties";
			FileOperate.delFile(log4jprop);
			FileOperate.copyFile(APP_PATH+"/WEB-INF/classes/log4j.properties.online", log4jprop);
			
			//datasource
			String ds=APP_PATH+"/WEB-INF/classes/spring/ctx-datasource.xml";
			FileOperate.delFile(ds);
			FileOperate.copyFile(APP_PATH+"/WEB-INF/classes/spring/ctx-datasource.xml.online", ds);
			
			//ipad首页
			String ipadIndex=APP_PATH+"/WEB-INF/pages/mobile/ipad.ftl";
			FileOperate.delFile(ipadIndex);
			FileOperate.copyFile(APP_PATH+"/WEB-INF/pages/mobile/ipad.ftl.online", ipadIndex);
			
			//ipadjs
			String ipadJsIndex=APP_PATH+"/v3/mobile/js/ipad.index.js";
			FileOperate.delFile(ipadJsIndex);
			FileOperate.copyFile(APP_PATH+"/v3/mobile/js/ipad.index.js.online", ipadJsIndex);
			
			//删除目录
			FileOperate.delAllFile(APP_PATH+"/_ai");
			FileOperate.delAllFile(APP_PATH+"/_bak");
			FileOperate.delAllFile(APP_PATH+"/_files");
			FileOperate.delAllFile(APP_PATH+"/_fxg");
			FileOperate.delAllFile(APP_PATH+"/_png");
			FileOperate.delAllFile(APP_PATH+"/v3/_files");
			FileOperate.delAllFile(APP_PATH+"/v3/mobile/_files");
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    /**
     * 检查是否存在同名的文件
     * @param f
     */
    public static void check(File f){
        if(!f.exists()){
            return;
        }else{
            if(f.isFile()){
                if(f.getName().toLowerCase().endsWith(".css")
                		||f.getName().toLowerCase().endsWith(".js")
                		||f.getName().toLowerCase().endsWith(".swf")){
                   if(f.getAbsolutePath().contains("/magmecnweb/event/")
            		||f.getAbsolutePath().contains("/magmecnweb/js/")
            		||f.getAbsolutePath().contains("/magmecnweb/v3/")
            		||f.getAbsolutePath().contains("/magmecnweb/style/")
            		||f.getAbsolutePath().contains("/magmecnweb/widget/")
            		||f.getAbsolutePath().contains("/magmecnweb/reader/")){
                	   //System.out.println(f.getName()+","+f.getAbsolutePath());
                        if(map1.containsKey(f.getName())){
                            map2.put(f.getAbsolutePath(), f.getName());
                            map2.put(map1.get(f.getName()), f.getName());
                        }else{
                            map1.put(f.getName(), f.getAbsolutePath());
                        }
                    }
                }
            }else{
                File[] fs=f.listFiles();
                for(File file:fs){
                    check(file);
                }
            }
        }
    }
    
    public static void adjustJSCSSVersion(File f){
        if(!f.exists()){
            return;
        }else{
            //System.out.println(f.getAbsolutePath());
            if(f.isFile()){
                if(f.getName().toLowerCase().endsWith(".html")
                        ||f.getName().toLowerCase().endsWith(".htm")
                        ||f.getName().toLowerCase().endsWith(".jsp")
                        ||f.getName().toLowerCase().endsWith(".css")
                        ||f.getName().toLowerCase().endsWith(".ftl")){
                    try {
                        BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(f),"utf-8"));
                        PrintWriter pw=new PrintWriter(f.getAbsolutePath()+".temp");
                        String line=null;
                        do{
                            line=br.readLine();
                            if(line!=null){
                                Set<String> keys=map.keySet();
                                for(String key:keys){
                                    if(line.contains(key)&&!line.contains("extjs")&&!line.contains("admin_js")){
                                        line=line.replaceAll(key, map.get(key));
                                    }
                                }
                                pw.println(line);
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
                    adjustJSCSSVersion(file);
                }
            }
        }
    }
    /**
     * 给JS和CSS文件加上版本号
     */
    public static void renameJSCSS(File f,String version){
        if(!f.exists()){
            return;
        }else{
            if(f.isFile()){
                if(f.getName().toLowerCase().endsWith(".css")
                		||f.getName().toLowerCase().endsWith(".js")
                		||f.getName().toLowerCase().endsWith(".swf")){
                    if(f.getAbsolutePath().contains("/magmecnweb/event/")
                    		||f.getAbsolutePath().contains("/magmecnweb/js/")
                    		||f.getAbsolutePath().contains("/magmecnweb/kindeditor/")
                    		||f.getAbsolutePath().contains("/magmecnweb/v3/")
                    		||f.getAbsolutePath().contains("/magmecnweb/style/")
                    		||f.getAbsolutePath().contains("/magmecnweb/widget/")
                    		||f.getAbsolutePath().contains("/magmecnweb/reader/")){
                        String oldfilename=f.getName();
                        int index=oldfilename.lastIndexOf(".");
                        String newfilename=oldfilename.substring(0,index)+"."+version+oldfilename.substring(index);
                        if(!map.containsValue(oldfilename)){
                            if(cp(f.getParentFile().getAbsolutePath()+File.separator+oldfilename,f.getParentFile().getAbsolutePath()+File.separator+newfilename)){
                                map.put(oldfilename, newfilename);
                            }
                        }
                    }
                }
            }else{
                File[] fs=f.listFiles();
                for(File file:fs){
                    renameJSCSS(file,version);
                }
            }
        }
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
    
    public static boolean cp(String src,String dest){
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
                    //sf.deleteOnExit();
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
