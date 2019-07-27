package fish.payara.examples.javaee.async.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
@Singleton
public class TestLogging {
	@EJB
	MyLoggingBean logBean;

	// Look execution order from server console

	@PostConstruct
	public void testLoggers() {
		System.out.println("testLoggers() started");
		System.out.println("call logSync()");
		logBean.logSync("logSync() logging");
		System.out.println("testLoggers() continues after waiting");
		System.out.println("call logAsync()");
		logBean.logAsync("logAsync() logging");
		System.out.println("testLoggers() continues immediately");
		System.out.println("testLoggers() finished");
	}
}