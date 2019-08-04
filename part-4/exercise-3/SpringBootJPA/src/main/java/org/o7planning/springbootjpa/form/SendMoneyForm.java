package org.o7planning.springbootjpa.form;

/**
 * Normaali bean, jota käytetään varastoimaan tietoa kun
 * halutaan siirtää rahaa pankkitililtä toiselle. Beanin ansiosta
 * tietojen väliaikainen tallentaminen on helppoa.
 */
public class SendMoneyForm {
  
  private Long fromAccountId;
  private Long toAccountId;
  private Double amount;
  
  public SendMoneyForm() {}
  
  public SendMoneyForm(Long fromAccountId, Long toAccountId, Double amount) {
    this.fromAccountId = fromAccountId;
    this.toAccountId = toAccountId;
    this.amount = amount;
  }
  
  
  /* SETTERIT JA GETTERIT LUOKAN ATTRIBUUTEILLE */
  
  public Long getFromAccountId() {
    return fromAccountId;
  }

  public void setFromAccountId(Long fromAccountId) {
    this.fromAccountId = fromAccountId;
  }

  public Long getToAccountId() {
    return toAccountId;
  }

  public void setToAccountId(Long toAccountId) {
    this.toAccountId = toAccountId;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }
  
}
