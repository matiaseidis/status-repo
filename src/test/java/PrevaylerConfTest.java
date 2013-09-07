import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.cachos.dimon.state.logger.Conf;
import org.cachos.dimon.state.logger.event.ClientEvent;
import org.cachos.dimon.state.logger.event.StartUpEvent;
import org.cachos.dimon.state.logger.repo.RepositoryManager;
import org.junit.Test;

public class PrevaylerConfTest extends TestCase {

	static Logger logger = Logger.getLogger(PrevaylerConfTest.class.getName());
	private String confTestPath = "/test-conf.properties";

	public void cleanUp(Conf testConf) {
		try {
			FileUtils.cleanDirectory(new File(testConf.getPrevalenceBase()));
			logger.debug("Dir cleanned: " + testConf.getPrevalenceBase());
		} catch (IOException e) {
			logger.error(
					"unable to clean test prevalence dir: "
							+ testConf.getPrevalenceBase(), e);
			TestCase.fail();
		}
	}

	@Test
	public void testPrevalenceConfigurationWorks() {

		Conf testConf = new Conf(confTestPath);
		cleanUp(testConf);

		RepositoryManager repo = null;

		try {
			repo = RepositoryManager.getInstance(testConf).open();
		} catch (Exception e) {
			e.printStackTrace();
			TestCase.fail();
		}

		ClientEvent event = new StartUpEvent("test-ip", "test-port");
		
		int size = repo.getPrevayler().prevalentSystem().getEvents(event)
				.size();

		assertSame(0, size);
		
		repo.log(event);

		size = repo.getPrevayler().prevalentSystem().getEvents(event)
				.size();
		
		assertSame(1, size);

	}

}
