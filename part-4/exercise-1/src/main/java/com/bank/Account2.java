package com.bank;

/**
 * Bean -class which implements Account -interface's 
 * functions. 
 * 
 * @author samuli
 */
public class Account2 implements Account {
  
  private String id;
  private double balance;
  
  /**
   * Deposits fixed amount of money to the account.
   */
  public void depositMoney() {
    balance += 1100;
  }
  
  /**
   * Withdraws fixed amount of money from the account.
   */
  public void withdrawMoney() {
    balance -= 160;
  }
  
  /**
   * Prints account's ID and the amount of money it holds.
   */
  public void checkAccount() {
    System.out.println("Tilin " + id + " saldo on: " + balance + "€");
  }

  
  /* Setters and getters for the bean -attributes */
  
  public String getAccountId() {
    return getId();
  }
  
  
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public double getBalance() {
    return balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

}
