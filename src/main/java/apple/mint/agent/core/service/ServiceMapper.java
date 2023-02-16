package apple.mint.agent.core.service;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import apple.mint.agent.core.channel.SendChannelWrapper;
import apple.mint.agent.core.config.ServiceConfig;

public class ServiceMapper {

    Logger logger = LoggerFactory.getLogger(ServiceMapper.class);

    LoginService loginService;

    LogoutService logoutService;

    List<RequestService> requestServices = new ArrayList<RequestService>();

    List<PushService> pushServices = new ArrayList<PushService>();

    List<ResponseService> responseServices = new ArrayList<ResponseService>();

    Map<String, Service> map = new LinkedHashMap<String, Service>();

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
        map.put(loginService.getCd(), loginService);
    }

    public void setLogoutService(LogoutService logoutService) {
        this.logoutService = logoutService;
        map.put(logoutService.getCd(), logoutService);
    }

    public LoginService getLoginService() {
        return loginService;
    }

    public LogoutService getLogoutService() {
        return logoutService;
    }

    public void addService(Service service) {
        if (service instanceof RequestService) {
            requestServices.add((RequestService) service);
        } else if (service instanceof PushService) {
            pushServices.add((PushService) service);
        } else if (service instanceof ResponseService) {
            responseServices.add((ResponseService) service);
        }

        map.put(service.getCd(), service);
    }

    public Service getService(String cd) {
        return map.get(cd);
    }

    public List<RequestService> getRequestServices() {
        return this.requestServices;
    }

    public List<PushService> getPushServices() {
        return this.pushServices;
    }

    public List<ResponseService> getResponseServices() {
        return this.responseServices;
    }

    /**
     * 
     * @param uriList
     * @param serviceConfigs
     * @param sendChannelWrapper
     * @param exceptionSkipMode
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws IOException
     */
    public void addService(
            String[] uriList,
            ServiceConfig[] serviceConfigs,
            ServiceContext serviceContext,
            SendChannelWrapper sendChannelWrapper,
            boolean exceptionSkipMode)
            throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {

        if (serviceConfigs == null || serviceConfigs.length == 0)
            return;

        if (uriList == null || uriList.length == 0) {
            throw new IllegalArgumentException("class uriList must be not null.");
        }

        // URL[] urls = new URL[uriList.length];
        // for (int i = 0; i < uriList.length; i++) {
        // urls[i] = new URL(uriList[i]);
        // }
        // URLClassLoader classLoader = new URLClassLoader(urls);

        Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
        if (!method.isAccessible()) {
            method.setAccessible(true);
        }
        // URLClassLoader classLoader = new URLClassLoader(new URL[] {},
        // ClassLoader.getSystemClassLoader());

        logger.info("123456-this.getClass().getClassLoader():"
                + this.getClass().getClassLoader().getClass().getCanonicalName());
        logger.info("123457-this.getClass().getClassLoader().getSystemClassLoader():"
                + this.getClass().getClassLoader().getSystemClassLoader().getClass().getCanonicalName());
        logger.info("123458-ClassLoader.getSystemClassLoader():"
                + ClassLoader.getSystemClassLoader().getClass().getCanonicalName());

        URLClassLoader classLoader = new URLClassLoader(new URL[] {},
                getClass().getClassLoader().getSystemClassLoader());
        for (int i = 0; i < uriList.length; i++) {
            method.invoke(classLoader, new URL(uriList[i]));
        }

        try {
            for (ServiceConfig serviceConfig : serviceConfigs) {
                try {
                    String cd = serviceConfig.getCd();
                    String name = serviceConfig.getName();
                    Map<?, ?> params = serviceConfig.getParams();
                    String className = serviceConfig.getClassName();
                    Boolean disabled = serviceConfig.isDisabled();
                    Class<?> clazz = classLoader.loadClass(className);
                    Class<?>[] parameterTypes = { String.class, String.class, ServiceContext.class,
                            SendChannelWrapper.class, Map.class, Boolean.class };
                    Constructor<?> constructor = clazz.getConstructor(parameterTypes);
                    Service service = (Service) constructor.newInstance(cd, name, serviceContext, sendChannelWrapper,
                            params, disabled);
                    addService(service);
                } catch (Exception e) {
                    if (exceptionSkipMode) {
                        logger.error("", e);
                        continue;
                    } else {
                        throw e;
                    }
                }
            }
        } finally {
            if (classLoader != null)
                classLoader.close();
        }

    }

    public void addService(
            ServiceConfig[] serviceConfigs,
            ServiceContext serviceContext,
            SendChannelWrapper sendChannelWrapper,
            boolean exceptionSkipMode, URLClassLoader classLoader)
            throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {

        if (serviceConfigs == null || serviceConfigs.length == 0)
            return;
 

        try {
            for (ServiceConfig serviceConfig : serviceConfigs) {
                try {
                    String cd = serviceConfig.getCd();
                    String name = serviceConfig.getName();
                    Map<?, ?> params = serviceConfig.getParams();
                    String className = serviceConfig.getClassName();
                    Boolean disabled = serviceConfig.isDisabled();

                    Class<?> clazz = classLoader.loadClass(className);
                    //Class<?> clazz = Class.forName(className, false, classLoader);

                    Class<?>[] parameterTypes = { String.class, String.class, ServiceContext.class,
                            SendChannelWrapper.class, Map.class, Boolean.class };
                    Constructor<?> constructor = clazz.getConstructor(parameterTypes);
                    Service service = (Service) constructor.newInstance(cd, name, serviceContext, sendChannelWrapper,
                            params, disabled);
                    addService(service);
                } catch (Exception e) {
                    if (exceptionSkipMode) {
                        logger.error("", e);
                        continue;
                    } else {
                        throw e;
                    }
                }
            }
        } finally {
            // if (classLoader != null) classLoader.close();
            // logger.info("classLoader 닫지 않는다.");
        }

    }

}
