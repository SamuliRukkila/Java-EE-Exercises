package com.bank;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {

  public static void main(String[] args) {
  
    try (ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("Beans.xml")) {
      
      Customer c = (Customer) ctx.getBean("customer");
      
      c.checkAccount();
      c.customerWithdrawMoney();
      c.checkAccount();
      c.customerDepositMoney();
      c.checkAccount();
      
    }
    
  }
  
}
