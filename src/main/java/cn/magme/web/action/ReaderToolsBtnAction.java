/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @author shenhao
 * @date 2011-8-22
 * @version $id$
 */
@SuppressWarnings("serial")
public class ReaderToolsBtnAction extends BaseAction {

	private static final Logger log=Logger.getLogger(ReaderToolsBtnAction.class);
	// 点击按钮
	private int btNum;

	public void clickStatic(){
	    String path = "/home/statlog/clickStatic.txt";
//		String path = "C:\\file\\clickStatic.txt";
		String[] btNm = new String[]{"缩小","步进条","放大","上一页","下一页",
		            "预览","单页模式","双页模式","标签","全屏","下方左翻","下方右翻",
		            "互动广告","我的切米","分享","杂志分类","页码按钮"};
		File file = new File(path);
		log.info(btNum);
		if (file.exists()){
			BufferedReader reader = null;
			List<String> lstList = new ArrayList<String>();
			try {
				reader = new BufferedReader(new FileReader(file));
				String tempString = null;
				int line = 1;
				// 一次读入一行，直到读入null为文件结束
				
				while ((tempString = reader.readLine()) != null) {
					// 请求按钮对应点击数加 1
					if (line == btNum){
						String str = tempString;
						if (str.indexOf(",") !=-1){
    						String newStr = str.split(",")[0]+","
    						                + String.valueOf(Integer.parseInt(str.split(",")[1]) + 1);
    						lstList.add(newStr);
						}
					}else{
						lstList.add(tempString);
					}
					line++;
				}
				log.info(lstList.toString());
				reader.close();
			} catch (IOException e) {
			    log.info("ReaderToolsBtnAction expIO!");
				e.printStackTrace();
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e1) {
					    log.info("ReaderToolsBtnAction exp final!");
					}
				}
				try {
					FileWriter writer = new FileWriter(path, false);
					for (String string:lstList){
						writer.write(string+"\n");
					}
					writer.close();
				} catch (IOException e) {
				    log.info("ReaderToolsBtnAction exp write!");
					e.printStackTrace();
				}
			}
		}else{
			try {
				FileWriter writer = new FileWriter(path, true);
				for (int i = 1;i<=17;i++){
					if (i == btNum){
						writer.write(btNm[i-1]+",1\n");
					}else{
						writer.write(btNm[i-1]+",0\n");
					}
				}
				writer.close();
				log.info("ReaderToolsBtnAction file new!");
			} catch (IOException e) {
			    log.info("ReaderToolsBtnAction exp file new!");
				e.printStackTrace();
			}
		}
	}
	
	public int getBtNum() {
		return btNum;
	}

	public void setBtNum(int btNum) {
		this.btNum = btNum;
	}

}
