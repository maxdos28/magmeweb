/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import cn.magme.common.JsonResult;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.config.entities.ExceptionMappingConfig;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.ExceptionHolder;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

/**
 * 在com.opensymphony.xwork2.interceptor.ExceptionMappingInterceptor
 * 的基础上加入了将异常封装为JSON信息的异常处理拦截器
 * @author jacky_zhou
 * @date 2011-5-19
 * @version $id$
 */
@SuppressWarnings("serial")
public class ExceptionInterceptor extends AbstractInterceptor {
    
    //以下代码部分来源于com.opensymphony.xwork2.interceptor.ExceptionMappingInterceptor
    protected static final Logger LOG = LoggerFactory.getLogger(ExceptionInterceptor.class);

    protected Logger categoryLogger;
    protected boolean logEnabled = false;
    protected String logCategory;
    protected String logLevel;
    

    public boolean isLogEnabled() {
        return logEnabled;
    }

    public void setLogEnabled(boolean logEnabled) {
        this.logEnabled = logEnabled;
    }

    public String getLogCategory() {
        return logCategory;
    }

    public void setLogCategory(String logCatgory) {
        this.logCategory = logCatgory;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }
    
    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        String result=null;
        try {
            result = invocation.invoke();
        } catch (Exception e) {
            e.printStackTrace();
            if (isLogEnabled()) {
                handleLogging(e);
            }
            List<ExceptionMappingConfig> exceptionMappings = invocation.getProxy().getConfig().getExceptionMappings();
            String mappedResult = this.findResultFromExceptions(exceptionMappings, e);
            //对于JSON请求,返回封装好的JSON格式的异常信息
            String method=invocation.getProxy().getMethod();
            if(method.toUpperCase().endsWith("JSON")){
                JsonResult jsonResult=new JsonResult(JsonResult.CODE.EXCEPTION,"服务器内部错误",null,null);
                String jsonStr=JSONObject.fromObject(jsonResult).toString();
                HttpServletResponse response=ServletActionContext.getResponse();
                response.setContentType("text/html;charset=UTF-8"); 
                response.getWriter().print(jsonStr);
                result = null;
            //非JSON请求,正常处理
            }else{
                if (mappedResult != null) {
                    result = mappedResult;
                    publishException(invocation, new ExceptionHolder(e));
                } else {
                    throw e;
                }
            }
        }
        return result;
    }
    
    /**
     * Handles the logging of the exception.
     * 
     * @param e  the exception to log.
     */
    protected void handleLogging(Exception e) {
        if (logCategory != null) {
            if (categoryLogger == null) {
                // init category logger
                categoryLogger = LoggerFactory.getLogger(logCategory);
            }
            doLog(categoryLogger, e);
        } else {
            doLog(LOG, e);
        }
    }
    
    /**
     * Performs the actual logging.
     * 
     * @param logger  the provided logger to use.
     * @param e  the exception to log.
     */
    protected void doLog(Logger logger, Exception e) {
        if (logLevel == null) {
            logger.debug(e.getMessage(), e);
            return;
        }
        
        if ("trace".equalsIgnoreCase(logLevel)) {
            logger.trace(e.getMessage(), e);
        } else if ("debug".equalsIgnoreCase(logLevel)) {
            logger.debug(e.getMessage(), e);
        } else if ("info".equalsIgnoreCase(logLevel)) {
            logger.info(e.getMessage(), e);
        } else if ("warn".equalsIgnoreCase(logLevel)) {
            logger.warn(e.getMessage(), e);
        } else if ("error".equalsIgnoreCase(logLevel)) {
            logger.error(e.getMessage(), e);
        } else if ("fatal".equalsIgnoreCase(logLevel)) {
            logger.fatal(e.getMessage(), e);
        } else {
            throw new IllegalArgumentException("LogLevel [" + logLevel + "] is not supported");
        }
    }

    protected String findResultFromExceptions(List<ExceptionMappingConfig> exceptionMappings, Throwable t) {
        String result = null;

        // Check for specific exception mappings.
        if (exceptionMappings != null) {
            int deepest = Integer.MAX_VALUE;
            for (Object exceptionMapping : exceptionMappings) {
                ExceptionMappingConfig exceptionMappingConfig = (ExceptionMappingConfig) exceptionMapping;
                int depth = getDepth(exceptionMappingConfig.getExceptionClassName(), t);
                if (depth >= 0 && depth < deepest) {
                    deepest = depth;
                    result = exceptionMappingConfig.getResult();
                }
            }
        }

        return result;
    }

    /**
     * Return the depth to the superclass matching. 0 means ex matches exactly. Returns -1 if there's no match.
     * Otherwise, returns depth. Lowest depth wins.
     *
     * @param exceptionMapping  the mapping classname
     * @param t  the cause
     * @return the depth, if not found -1 is returned.
     */
    public int getDepth(String exceptionMapping, Throwable t) {
        return getDepth(exceptionMapping, t.getClass(), 0);
    }

    @SuppressWarnings("unchecked")
    private int getDepth(String exceptionMapping, Class exceptionClass, int depth) {
        if (exceptionClass.getName().contains(exceptionMapping)) {
            // Found it!
            return depth;
        }
        // If we've gone as far as we can go and haven't found it...
        if (exceptionClass.equals(Throwable.class)) {
            return -1;
        }
        return getDepth(exceptionMapping, exceptionClass.getSuperclass(), depth + 1);
    }

    /**
     * Default implementation to handle ExceptionHolder publishing. Pushes given ExceptionHolder on the stack.
     * Subclasses may override this to customize publishing.
     *
     * @param invocation The invocation to publish Exception for.
     * @param exceptionHolder The exceptionHolder wrapping the Exception to publish.
     */
    protected void publishException(ActionInvocation invocation, ExceptionHolder exceptionHolder) {
        invocation.getStack().push(exceptionHolder);
    }
}
