package fish.payara.examples.javaee.facade.ejb;

import javax.ejb.Stateless;

@Stateless
public class CustomerService {
	public long getCustomer(int sessionID) {
		// get logged in customer id
		return 100005L;
	}

	public boolean checkId(long x) {
		// check if customer id is valid
		return true;
	}
}
