package com.hr.service.impl;

import com.hr.dao.IAccountDao;
import com.hr.domain.Account;
import com.hr.service.IAccountService;

import java.util.List;

public class AccountServiceImpl implements IAccountService {

    private IAccountDao accountDao;

    public void setAccountDao(IAccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public List<Account> findAllAccount() {
        return accountDao.findAllAccount();
    }

    public Account findAccountById(Integer accountId) {
        return accountDao.findAccountById(accountId);
    }

    public void saveAccount(Account account) {
        accountDao.saveAccount(account);
    }

    public void updateAccount(Account account) {
        accountDao.updateAccount(account);
    }

    public void deleteAccount(Integer accountId) {
        accountDao.deleteAccount(accountId);
    }
    //转账
    public void transfer(String sourceName, String targetName, Float money) {
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
    }
}
