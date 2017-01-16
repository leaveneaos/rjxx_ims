package com.rjxx.taxeasy.filter;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;    
import org.aspectj.lang.annotation.*;    
import org.slf4j.Logger;    
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;    
import org.springframework.web.context.request.RequestContextHolder;    
import org.springframework.web.context.request.ServletRequestAttributes;

import com.rjxx.taxeasy.domains.Privileges;
//import com.rjxx.taxeasy.domains.XtLog;
import com.rjxx.taxeasy.service.PrivilegesService;
//import com.rjxx.taxeasy.service.XtLogService;
import com.rjxx.taxeasy.web.BaseController;

import freemarker.template.utility.StringUtil;

import javax.annotation.Resource;    
import javax.servlet.http.HttpServletRequest;    
import javax.servlet.http.HttpSession;    
import java.lang.reflect.Method;
import java.util.*;    
    
/**  
 * 切点类  
 * @author kk 
 * @since 2017-01-11 Pm 13:35  
 * @version 1.0  
 */    
@Aspect    
@Component    
public  class SystemLogAspect extends BaseController {    
    //注入Service用于把日志保存数据库    
    //@Resource    
     //private LogService logService;    
    //本地异常日志记录对象    
     private  static  final Logger logger = LoggerFactory.getLogger(SystemLogAspect. class);    
    
     @Autowired
     private PrivilegesService privilegesService;
     
//     @Autowired
//     private XtLogService xtLogService;
    //Service层切点    
    @Pointcut("@annotation(com.rjxx.taxeasy.filter.SystemServiceLog)")    
     public  void serviceAspect() {    
    }    
    
    //Controller层切点    
    @Pointcut("@annotation(com.rjxx.taxeasy.filter.SystemControllerLog)")    
     public  void controllerAspect() {    
    }    
    
    /**  
     * 前置通知 用于拦截Controller层记录用户的操作  
     *  
     * @param joinPoint 切点  
     */    
    @Before("controllerAspect()")    
     public  void doBefore(JoinPoint joinPoint) {    
    
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();    
        HttpSession session = request.getSession();    
        //读取session中的用户    
        String url = StringUtils.substringAfter(request.getRequestURI(), request.getContextPath());
        url = StringUtils.substringBeforeLast(url, "/");
        Map params = new HashMap();
        params.put("urls",url);
        List<Privileges> priList = privilegesService.findOneByParams(params);
        //请求的IP    
        String ip = "";    
        if (request.getHeader("x-forwarded-for") == null) {
        	ip = request.getRemoteAddr();// 访问者IP
		} else {
			ip = request.getHeader("x-forwarded-for");
		}
       
         try {    
        	 String key =(String) getControllerMethodDescription(joinPoint).get("key");
        	 String value = request.getParameter(key);
            //*========控制台输出=========*//    
            System.out.println("=====前置通知开始=====");    
            System.out.println("请求方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));    
            System.out.println("操作类型:" + getControllerMethodDescription(joinPoint).get("description")); 
            System.out.println("详细数据:" + value); 
            System.out.println("操作对象:" + url.toString());    
            System.out.println("请求IP:" + ip);    
            //*========数据库日志=========*//    
/*            XtLog log = new XtLog();    
            log.setAnctionobj(priList.get(0).getName());   
            log.setDescription( getControllerMethodDescription(joinPoint).get("description").toString());     
            log.setDetails(value);
            log.setMethod((joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));;    
            log.setRequestip(ip);   
            log.setOperator(this.getYhid()); 
            log.setTime(new Date());
            //保存数据库    
            xtLogService.save(log);*/
            System.out.println("=====前置通知结束=====");    
        }  catch (Exception e) {    
            //记录本地异常日志    
            logger.error("==前置通知异常==");    
            logger.error("异常信息:{}", e.getMessage());    
        }    
    }    
    
    /**  
     * 异常通知 用于拦截service层记录异常日志  
     *  
     * @param joinPoint  
     * @param e  
     */    
    @AfterThrowing(pointcut = "serviceAspect()", throwing = "e")    
     public  void doAfterThrowing(JoinPoint joinPoint, Throwable e) {    
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();    
        HttpSession session = request.getSession();    
        //读取session中的用户    
        //User user = (User) session.getAttribute(WebConstants.CURRENT_USER);    
        //获取请求ip    
        String ip = request.getRemoteAddr();    
        //获取用户请求方法的参数并序列化为JSON格式字符串    
        String params = "";    
         if (joinPoint.getArgs() !=  null && joinPoint.getArgs().length > 0) {    
             for ( int i = 0; i < joinPoint.getArgs().length; i++) {    
                //params += JSONUtil.toJsonString(joinPoint.getArgs()[i]) + ";";    
            }    
        }    
         try {    
              /*========控制台输出=========*/    
            System.out.println("=====异常通知开始=====");    
            System.out.println("异常代码:" + e.getClass().getName());    
            System.out.println("异常信息:" + e.getMessage());    
            System.out.println("异常方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));    
            System.out.println("方法描述:" + getServiceMthodDescription(joinPoint));    
            //System.out.println("请求人:" + user.getName());    
            System.out.println("请求IP:" + ip);    
            System.out.println("请求参数:" + params);    
               /*==========数据库日志=========*/    
            /*Log log = SpringContextHolder.getBean("logxx");    
            log.setDescription(getServiceMthodDescription(joinPoint));    
            log.setExceptionCode(e.getClass().getName());    
            log.setType("1");    
            log.setExceptionDetail(e.getMessage());    
            log.setMethod((joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));    
            log.setParams(params);    
            log.setCreateBy(user);    
            log.setCreateDate(DateUtil.getCurrentDate());    
            log.setRequestIp(ip);    
            //保存数据库    
            logService.add(log);    */
            System.out.println("=====异常通知结束=====");    
        }  catch (Exception ex) {    
            //记录本地异常日志    
            logger.error("==异常通知异常==");    
            logger.error("异常信息:{}", ex.getMessage());    
        }    
         /*==========记录本地异常日志==========*/    
        logger.error("异常方法:{}异常代码:{}异常信息:{}参数:{}", joinPoint.getTarget().getClass().getName() + joinPoint.getSignature().getName(), e.getClass().getName(), e.getMessage(), params);    
    
    }    
    
    
    /**  
     * 获取注解中对方法的描述信息 用于service层注解  
     *  
     * @param joinPoint 切点  
     * @return 方法描述  
     * @throws Exception  
     */    
     public  static String getServiceMthodDescription(JoinPoint joinPoint)    
             throws Exception {    
        String targetName = joinPoint.getTarget().getClass().getName();    
        String methodName = joinPoint.getSignature().getName();    
        Object[] arguments = joinPoint.getArgs();    
        Class targetClass = Class.forName(targetName);    
        Method[] methods = targetClass.getMethods();    
        String description = "";    
         for (Method method : methods) {    
             if (method.getName().equals(methodName)) {    
                Class[] clazzs = method.getParameterTypes();    
                 if (clazzs.length == arguments.length) {    
                    description = method.getAnnotation(SystemServiceLog. class).description();    
                     break;    
                }    
            }    
        }    
         return description;    
    }    
    
    /**  
     * 获取注解中对方法的描述信息 用于Controller层注解  
     *  
     * @param joinPoint 切点  
     * @return 方法描述  
     * @throws Exception  
     */    
     public  static Map getControllerMethodDescription(JoinPoint joinPoint)  throws Exception {    
        String targetName = joinPoint.getTarget().getClass().getName();    
        String methodName = joinPoint.getSignature().getName();    
        Object[] arguments = joinPoint.getArgs();    
        Class targetClass = Class.forName(targetName);    
        Method[] methods = targetClass.getMethods();    
        String description = "";
        String key = ""; 
        Map resultMap = new HashMap();
         for (Method method : methods) {    
             if (method.getName().equals(methodName)) {    
                Class[] clazzs = method.getParameterTypes();    
                 if (clazzs.length == arguments.length) {    
                    description = method.getAnnotation(SystemControllerLog. class).description();    
                    key = method.getAnnotation(SystemControllerLog. class).key();  
                     break;    
                }    
            }    
        }  
         resultMap.put("description", description);
         resultMap.put("key", key);
         return resultMap;    
    }    
}    