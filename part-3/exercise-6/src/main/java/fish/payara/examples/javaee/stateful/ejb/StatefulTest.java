package fish.payara.examples.javaee.stateful.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;

@Stateful
@SessionScoped
public class StatefulTest {

	public StatefulTest() {
	}

	private int i;

	@PostConstruct
	public void initialize() {
		i = 0;
	}

	public int getI() {
		return i++;
	}
}
