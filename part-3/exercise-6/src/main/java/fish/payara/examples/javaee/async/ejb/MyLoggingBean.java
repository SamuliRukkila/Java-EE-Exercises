package fish.payara.examples.javaee.async.ejb;

import java.util.logging.FileHandler;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Asynchronous;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
@Singleton
public class MyLoggingBean {
	private Logger logger;
	FileHandler fh;

	@PostConstruct
	public void start() {
		logger = Logger.getLogger("TestLogger");
		logger.info("start() logging");
	}

	public void logSync(String msg) {

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}

		logger.info(msg);
	}

	@Asynchronous
	public void logAsync(String msg) {

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}

		logger.info(msg);
	}
}
