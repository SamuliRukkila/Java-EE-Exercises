package com.bank;

public class Customer {
  
  private Account acc;
  
  public Customer(Account acc) {
    this.acc = acc;
  }
  
  public void customerDepositMoney() {
    this.acc.depositMoney();
  }
  
  public void customerWithdrawMoney() {
    this.acc.withdrawMoney();
  }
  
  public void checkAccount() {
    this.acc.checkAccount();
  }
}
