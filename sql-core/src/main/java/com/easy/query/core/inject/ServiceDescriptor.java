package com.easy.query.core.inject;

import com.easy.query.core.exception.EasyQueryInjectCurrentlyInCreationException;
import com.easy.query.core.util.ClassUtil;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.function.Function;

/**
 * create time 2023/5/5 21:32
 * 文件说明
 *
 * @author xuejiaming
 */
public class ServiceDescriptor {
    private final Class<?> serviceType;
    private final Class<?> implementationType;
    private final Object implementationInstance;
    private final Function<ServiceProvider,Object> implementationFactory;
    /**
     * 使用ThreadLocal主要是考虑到多线程getService创建服务所以要单独对当前线程进行检查
     */
    private final Map<Long,Object> THREAD_VERSION= new ConcurrentHashMap<>();
    public ServiceDescriptor(Class<?> serviceType, Class<?> implementationType) {
        if(serviceType==null){
            throw new IllegalArgumentException(ClassUtil.getSimpleName(serviceType));
        }
        this.serviceType = serviceType;
        if(implementationType==null){
            throw new IllegalArgumentException(ClassUtil.getSimpleName(implementationType));
        }
        this.implementationType = implementationType;
        this.implementationInstance = null;
        this.implementationFactory = null;
    }
    public ServiceDescriptor(Class<?> serviceType, Object implementationInstance) {

        if(serviceType==null){
            throw new IllegalArgumentException(ClassUtil.getSimpleName(serviceType));
        }
        this.serviceType = serviceType;
        if(implementationInstance==null){
            throw new IllegalArgumentException(ClassUtil.getInstanceSimpleName(implementationInstance));
        }
        this.implementationInstance = implementationInstance;
        this.implementationType = null;
        this.implementationFactory = null;
    }
    public ServiceDescriptor(Class<?> serviceType, Function<ServiceProvider,Object> implementationFactory) {

        if(serviceType==null){
            throw new IllegalArgumentException(ClassUtil.getSimpleName(serviceType));
        }
        this.serviceType = serviceType;
        if(implementationFactory==null){
            throw new IllegalArgumentException(ClassUtil.getInstanceSimpleName(implementationFactory));
        }
        this.implementationFactory = implementationFactory;
        this.implementationType = null;
        this.implementationInstance = null;
    }

    public Class<?> getServiceType() {
        return serviceType;
    }

    public Class<?> getImplementationType() {
        return implementationType;
    }

    public Object getImplementationInstance() {
        return implementationInstance;
    }

    public Function<ServiceProvider,Object> getImplementationFactory() {
        return implementationFactory;
    }

    /**
     * 对当前线程下进行循环依赖检查
     */
    public void checkBeanCurrently(){
        long currentThreadId = Thread.currentThread().getId();
        Object o = THREAD_VERSION.putIfAbsent(currentThreadId, currentThreadId);
        if(o!=null){
            throw new EasyQueryInjectCurrentlyInCreationException("bean currently creation:"+ClassUtil.getSimpleName(serviceType));
        }
    }

    /**
     * 重置检查点
     */
    public void resetBeanCurrently(){
        long currentThreadId = Thread.currentThread().getId();
        THREAD_VERSION.remove(currentThreadId);
    }
}