package com.hyn.redis.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

//用来获取springboot创建的工厂
@Component
public class ApplicationContextUtils implements ApplicationContextAware {
    //将创建好的工厂以参数形式传递给这个类
    //保留下的工厂
    private  static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    //提供在工厂中获取对象的方法
    public static Object getBean(String beanName){
        return applicationContext.getBean(beanName);
    }
}
