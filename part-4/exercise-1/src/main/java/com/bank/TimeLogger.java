package com.bank;

import java.util.Date;

/**
 * This class is AOP-bean. In this application this bean is made as an
 * aspect. Aspect is an independent module, which can be called in other
 * functions like an interceptor.
 * 
 * In which functions below methods will be called is defined in the Beans.xml
 * -configuration file.
 * 
 * @author samuli
 */
public class TimeLogger {
  
  // Loosely inject Account -bean
  private Account acc;
  private Date date;

  /**
   * Constructor for the bean
   * @param acc - account -bean
   */
  public TimeLogger(Account acc) {
    this.acc = acc;
    this.date = new Date();
  }
  
  
  /**
   * This function will only be called when customer deposits money 
   * into the bank. It'll fetch the account's ID from the loosely injected
   * Account -bean.
   */
  public void moneyDeposited() {
    System.out.println("Tilille " + acc.getAccountId() + " tallennettu rahaa | Aika: "
        + "[" + date.toString() + "]");
  }
  
  /**
   * This function will only be called when customer withdraws money 
   * from the bank. It'll fetch the account's ID from the loosely injected
   * Account -bean.
   */
  public void moneyWithdrawed() {
    System.out.println("Tililtä " + acc.getAccountId() + " nostettu rahaa | Aika: "
        + "[" + date.toString() + "]");

  }
  
}
