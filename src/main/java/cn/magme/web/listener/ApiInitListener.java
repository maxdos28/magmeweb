/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.listener;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import cn.magme.util.TaskEvent;
import cn.magme.util.TimeMagage;

import com.renren.api.client.RenrenApiConfig;

public class ApiInitListener implements ServletContextListener{

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        //Nothing to do
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        RenrenApiConfig.renrenApiKey = "fe16782316da45b6add489d51ccb9150";
        RenrenApiConfig.renrenApiSecret = "9efd58f1f66f41bda62e572cde823542";
        new TimeMagage();//推送消息给ios
    }
}