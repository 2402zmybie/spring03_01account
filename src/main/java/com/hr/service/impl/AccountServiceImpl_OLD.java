package com.hr.service.impl;

import com.hr.dao.IAccountDao;
import com.hr.domain.Account;
import com.hr.service.IAccountService;
import com.hr.utils.TransactionManager;

import java.util.List;

public class AccountServiceImpl_OLD implements IAccountService {

    private IAccountDao accountDao;
    private TransactionManager txManager;

    public void setTxManager(TransactionManager txManager) {
        this.txManager = txManager;
    }

    public void setAccountDao(IAccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public List<Account> findAllAccount() {
        try {
            //开启事务
            txManager.beginTransaction();
            //执行操作
            List<Account> accounts = accountDao.findAllAccount();
            //提交事务
            txManager.commit();
            //返回结果
            return accounts;
        }catch (Exception e) {
            //回滚操作
            txManager.rollback();
            throw new RuntimeException(e);
        }finally {
            //释放连接
            txManager.release();
        }

    }

    public Account findAccountById(Integer accountId) {
        try {
            //开启事务
            txManager.beginTransaction();
            //执行操作
            Account account = accountDao.findAccountById(accountId);
            //提交事务
            txManager.commit();
            //返回结果
            return account;
        }catch (Exception e) {
            //回滚操作
            txManager.rollback();
            throw new RuntimeException(e);
        }finally {
            //释放连接
            txManager.release();
        }
    }

    public void saveAccount(Account account) {
        try {
            //开启事务
            txManager.beginTransaction();
            //执行操作
            accountDao.saveAccount(account);
            //提交事务
            txManager.commit();
            //返回结果
        }catch (Exception e) {
            //回滚操作
            txManager.rollback();
            throw new RuntimeException(e);
        }finally {
            //释放连接
            txManager.release();
        }

    }

    public void updateAccount(Account account) {
        try {
            //开启事务
            txManager.beginTransaction();
            //执行操作
            accountDao.updateAccount(account);
            //提交事务
            txManager.commit();
            //返回结果
        }catch (Exception e) {
            //回滚操作
            txManager.rollback();
            throw new RuntimeException(e);
        }finally {
            //释放连接
            txManager.release();
        }

    }

    public void deleteAccount(Integer accountId) {
        try {
            //开启事务
            txManager.beginTransaction();
            //执行操作
            accountDao.deleteAccount(accountId);
            //提交事务
            txManager.commit();
            //返回结果
        }catch (Exception e) {
            //回滚操作
            txManager.rollback();
            throw new RuntimeException(e);
        }finally {
            //释放连接
            txManager.release();
        }

    }


    //需要使用ThreadLocal对象把Connection和当前线程邦定,从而使一个线程中只有一个能控制事务的对象
    //转账
    public void transfer(String sourceName, String targetName, Float money) {
        try {
            //开启事务
            txManager.beginTransaction();
            //执行操作

            //查询转出账户
            Account source = accountDao.findAccountByName(sourceName);
            //查询转入账户
            Account target = accountDao.findAccountByName(targetName);
            //转出账户减钱
            source.setMoney(source.getMoney() - money);
            //转入账户加钱
            target.setMoney(target.getMoney() + money);
            //更新转出账户
            accountDao.updateAccount(source);
        int i = 1 / 0;
            //更新转入账户
            accountDao.updateAccount(target);

            //提交事务
            txManager.commit();
            //返回结果

        }catch (Exception e) {
            //回滚操作
            txManager.rollback();
            e.printStackTrace();
        }finally {
            //释放连接
            txManager.release();
        }


    }
}
