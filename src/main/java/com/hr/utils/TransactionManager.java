package com.hr.utils;

/**
 * 和事务管理的工具类,它包含了, 开启事务,提交事务,回滚事务和释放连接
 */
public class TransactionManager {

    private ConnectionUtils connectionUtils;

    public void setConnectionUtils(ConnectionUtils connectionUtils) {
        this.connectionUtils = connectionUtils;
    }

    public void beginTransaction() {

    }

    public void commit() {

    }

    public void rollback() {

    }

    public void release() {

    }




}
