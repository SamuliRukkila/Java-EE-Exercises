package com.bank;

/**
 * Interface for the account. Defines what methods
 * needs to be in according beans.
 * 
 * @author samuli
 */
public interface Account {

  public void depositMoney();
  public void withdrawMoney();
  public void checkAccount();
  public String getAccountId();
  
}
