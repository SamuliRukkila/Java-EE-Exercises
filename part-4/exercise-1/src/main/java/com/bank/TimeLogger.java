package com.bank;

import java.util.Date;

public class TimeLogger {
  
  private Account acc;
  private Date date;

  public TimeLogger(Account acc) {
    this.acc = acc;
    this.date = new Date();
  }
  
  
  public void moneyDeposited() {
    System.out.println("Tilille " + acc.getAccountId() + " tallennettu rahaa | Aika: "
        + "[" + date.toString() + "]");
  }
  
  public void moneyWithdrawed() {
    System.out.println("Tililtä " + acc.getAccountId() + " nostettu rahaa | Aika: "
        + "[" + date.toString() + "]");

  }
  
}
