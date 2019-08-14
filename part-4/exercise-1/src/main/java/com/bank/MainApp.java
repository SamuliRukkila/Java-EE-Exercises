package com.bank;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Bootstrap-class which is executable. It'll use bean-configuration 
 * to produce IoC, meaning the control is in xml-file and not in this class.
 */
public class MainApp {

  public static void main(String[] args) {
    
    // Get configuration attributes from this xml-file.
    try (ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("Beans.xml")) {
      
      // Using Beans.xml, get a specific bean.
      Customer c = (Customer) ctx.getBean("customer");
      
      // Use various functions to test the bean
      c.checkAccount();
      c.customerWithdrawMoney();
      c.checkAccount();
      c.customerDepositMoney();
      c.checkAccount();
      
    }
    
  }
  
}
