package fish.payara.examples.javaee.facade.ejb;

import javax.ejb.Stateless;

@Stateless
public class LoanService {
	public boolean checkCreditRating(long id, double amount) {
		// check if customer is eligible for the amount
		return true;
	}
}