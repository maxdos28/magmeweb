/**
 * Alipay.com Inc.
 * Copyright (c) 2005-2008 All Rights Reserved.
 */
package cn.magme.web.action.alipay.notify;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.dom4j.io.SAXReader;
import org.springframework.context.ApplicationContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import cn.magme.constants.PojoConstant.SALE;
import cn.magme.pojo.charge.PublicationProduct;
import cn.magme.pojo.charge.Sale;
import cn.magme.pojo.charge.SaleContent;
import cn.magme.service.charge.PublicationProductService;
import cn.magme.service.charge.SaleContentService;
import cn.magme.service.charge.SaleService;
import cn.magme.util.ApplicationContextComm;
import cn.magme.web.action.alipay.base.PartnerConfig;
import cn.magme.web.action.alipay.security.RSASignature;

/**
 * 接收通知并处理
 * 
 * @author devin.song
 * @date 2013-04-01
 */
public class RSANotifyReceiver extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6620727864412464990L;

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    	System.out.println("接收到通知!");
        //获得通知参数
        Map map = request.getParameterMap();
        //获得通知签名
        String sign = (String) ((Object[]) map.get("sign"))[0];
        //获得待验签名的数据
        String verifyData = getVerifyData(map);
        boolean verified = false;
        PrintWriter out = response.getWriter();
        //使用支付宝公钥验签名
        String notify_data = "";
        Map<String,String> vMap = null;
        try {
        	notify_data = (String) ((Object[]) map.get("notify_data"))[0];
            verified = RSASignature.doCheck(verifyData, sign, PartnerConfig.RSA_ALIPAY_PUBLIC);
            if(notify_data.isEmpty()){
            	out.print("fail==notify_data.isEmpty()");
            }
            vMap = getNotifyData(notify_data);
        } catch (Exception e) {
            e.printStackTrace();
            out.print("fail==catch");
        }
       
        //验证签名通过
        if (verified) {
        	//根据交易状态处理业务逻辑
        	//当交易状态成功，处理业务逻辑成功。回写success
        	if(vMap.get("trade_status").equals("TRADE_FINISHED")){//交易成功
        		String uuid = vMap.get("out_trade_no");
        		String platformuuid = vMap.get("trade_no"); 
        		ApplicationContext ctx = ApplicationContextComm.getApplicationContext();
        		SaleService saleService = ctx.getBean(SaleService.class);
        		SaleContentService  saleContentService = ctx.getBean(SaleContentService.class);
        		PublicationProductService publicationProductService =ctx.getBean(PublicationProductService.class);
        		Sale sale = null;
        		List<Sale> tmpSale = saleService.getByCondition(uuid, null);
        		if(tmpSale!=null && !tmpSale.isEmpty()){
        			for (Sale s : tmpSale) {
        				sale = s;
	        			sale.setPlatformuuid(platformuuid);
	        			saleService.updateSale(sale);
	        			
						if(s.getStatus()==0){
		        			PublicationProduct product = publicationProductService.getById(sale.getProductId());
		            		//订阅当前期,生成对应的订单详情,赋予用户对当前期刊的阅读权限
		            		if (product.getNumber() == 1 && sale!=null) {
		            			SaleContent saleContent = new SaleContent();
		            			saleContent.setIssueCode(sale.getIssueCode());
		            			saleContent.setSaleId(sale.getId());
		            			saleContentService.insert(saleContent);
		            			sale.setStatus(SALE.STATUS_FINISHED);
		            			saleService.updateSale(sale);
		            		}
						}else if(s.getStatus()==1){//当有多期订阅时需要查询对应的明细表SaleContent的status=1的总数是否已经购买完毕，如果完毕更改sale表的status为2 (钱货两清)
							//node
						}
					}
        			
        		}
        		out.print("success");
        	}
        } else {
        	System.out.println("接收支付宝系统通知验证签名失败，请检查！");
            out.print("fail");
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    /**
     * 获得验签名的数据
     * @param map
     * @return
     * @throws Exception 
     */
    @SuppressWarnings("unchecked")
	private String getVerifyData(Map map) {
        String notify_data = (String) ((Object[]) map.get("notify_data"))[0];
        return "notify_data="+notify_data;
    }
    
	private Map<String,String> getNotifyData(String notify_data){
    	StringReader sr = new StringReader(notify_data);
    	InputSource is = new InputSource(sr);
		Map<String,String> backMap = new HashMap<String, String>();
		 try {
			 DocumentBuilderFactory factory =  DocumentBuilderFactory.newInstance();
			 DocumentBuilder builder = factory.newDocumentBuilder();
			 Document doc = builder.parse(is);
			 Element e = doc.getDocumentElement();
			 NodeList notifyList = e.getChildNodes();
			 if(notifyList!=null){
				 for (int i = 0; i < notifyList.getLength(); i++) {
					Node notify = notifyList.item(i);
					String nodeName = notify.getNodeName();
					String nodeValue = notify.getFirstChild().getNodeValue();
					backMap.put(nodeName, nodeValue);
				}
			 }
			 return backMap;
		} catch (Exception e) {
			//e.printStackTrace();
			return null;
		}
    }
}
