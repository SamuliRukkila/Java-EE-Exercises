package com.bank;

/**
 * This class is loosely coupled to the Account -interface-class.
 * The injection is defined in the class' constructor. We can change
 * what account customer uses from the configuration-file (Beans.xml).
 * 
 * @author samuli
 */
public class Customer {
  
  // Inject Account -interface
  private Account acc;
  
  /* Class' constructor where we loosely couple (soft injection)
  Account -interface to this bean. */ 
  public Customer(Account acc) {
    this.acc = acc;
  }
  
  /**
   * Deposit's fixed amount of money to the account. This function is tied
   * to AOP-bean - TimeLogger, which'll print information about this 
   * action after it has been completed.
   */
  public void customerDepositMoney() {
    this.acc.depositMoney();
  }
  
  /**
   * Withdraw's fixed amount of money from the account. This function is tied
   * to AOP-bean - TimeLogger, which'll print information about this 
   * action after it has been completed.
   */
  public void customerWithdrawMoney() {
    this.acc.withdrawMoney();
  }
  
  /**
   * Will be used to print information about the account's ID and the 
   * amount of money it holds.
   */
  public void checkAccount() {
    this.acc.checkAccount();
  }
}
