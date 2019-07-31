package com.bank;

public class Account1 implements Account {
  
  private String id;
  private double balance;
  
  public void depositMoney() {
    balance += 500;
  }
  
  public void withdrawMoney() {
    balance -= 200;
  }

  public void checkAccount() {
    System.out.println("Tilin " + id + " saldo on: " + balance + "€");
  }

  
  public String getAccountId() {
    return id;
  }
  
  /* Setters and getters for the bean -attributes */
  
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
