package com.hr.factory;

import com.hr.domain.Account;
import com.hr.service.IAccountService;
import com.hr.utils.TransactionManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 用于创建Service的代理对象的工厂
 * ,目的: 在不改变IAccountService代码的基础上, 对IAccountService的方法增强(加上事务)
 */
public class BeanFactory {

    private IAccountService accountService;

    public void setAccountService(IAccountService accountService) {
        this.accountService = accountService;
    }

    private TransactionManager txManager;

    public void setTxManager(TransactionManager txManager) {
        this.txManager = txManager;
    }

    /**
     * 获取Service的代理对象
     * @return
     */
    public IAccountService getAccountService() {
        return (IAccountService) Proxy.newProxyInstance(accountService.getClass().getClassLoader(), accountService.getClass().getInterfaces(),
                new InvocationHandler() {
                    /**
                     * 为业务类IAccountService 添加事务的支持
                     *
                     * @param proxy
                     * @param method
                     * @param args
                     * @return
                     * @throws Throwable
                     */
                    //整个invoke方法 在执行就是环绕通知
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Object rtValue = null;
                        try {
                            //开启事务 (前置通知)
                            txManager.beginTransaction();
                            //执行操作
                            rtValue = method.invoke(accountService, args);  //在环绕通知中有明确的切入点方法调用
                            //提交事务 (后置通知)
                            txManager.commit();
                            //返回结果
                            return rtValue;
                        } catch (Exception e) {
                            //回滚操作 (异常通知)
                            txManager.rollback();
                            throw new RuntimeException(e);
                        } finally {
                            //释放连接 (最终通知)
                            txManager.release();
                        }
                    }
                }
        );
    }
}
