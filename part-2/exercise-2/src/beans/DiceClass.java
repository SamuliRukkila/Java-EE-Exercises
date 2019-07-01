package beans;

import java.io.Serializable;
import java.util.Random;

import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Named;


@Named(value = "DiceBean")
@RequestScoped
public class DiceClass implements Serializable {

  private static final long serialVersionUID = 1L;
 
  private int sum = 0;
  private int result = 5;
  private int throwAmount = 0;
  private double average = 0;
  
  private Random rand = new Random();
  
  /**
   * Returns sum of all the throws
   * @return the sum of all the throws
   */
  public int getSum() {
    return this.sum;
  }
  
  /**
   * Returns the amount of throws
   * @return the amount of throws
   */
  public int getThrowAmount() {
    return this.throwAmount;
  }
  
  /**
   * Returns the latest dice result
   * @return the latest dice result
   */
  public int getResult() {
    return this.result;
  }
  
  /**
   * Returns the average value of dices 
   * @return the average value of dices
   */
  public double getAverage() {
    return this.average;
  }
  
  
  /**
   * Gets a random number between 1-6. After the number
   * has been fetched, the amount of throws will be increased
   * by one, new throw result will be added to total sum and lastly
   * the average will be re-calculated.
   */
  public void throwDice(ActionEvent ae) {
    this.result = rand.nextInt(6) + 1;
    this.throwAmount++;
    this.sum += this.result;
    this.average = this.sum * 1.0 / this.throwAmount;
  }
  
  /**
   * Resets the total sum of throw-values
   */
  public void resetSum() {
    this.sum = 0;
  }
  
  /**
   * Resets the amount of throws
   */
  public void resetThrows() {
    this.throwAmount = 0;
  }
  
  /**
   * Resets the average value of dices
   */
  public void resetAverage() {
    this.average = 0;
  }
  
}
