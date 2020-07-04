package com.hr.utils;

import java.sql.SQLException;

/**
 * 和事务管理的工具类,它包含了, 开启事务,提交事务,回滚事务和释放连接
 */
public class TransactionManager {

    private ConnectionUtils connectionUtils;

    public void setConnectionUtils(ConnectionUtils connectionUtils) {
        this.connectionUtils = connectionUtils;
    }

    public void beginTransaction() {
        try {
            connectionUtils.getThreadConnection().setAutoCommit(false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void commit() {
        try {
            connectionUtils.getThreadConnection().commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void rollback() {
        try {
            connectionUtils.getThreadConnection().rollback();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void release() {
        try {
            connectionUtils.getThreadConnection().close();  //吧连接关闭,还回线程池中
            connectionUtils.removeConnection();  //把连接和线程解绑
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }




}
