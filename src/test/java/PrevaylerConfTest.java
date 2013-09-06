import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.cachos.dimon.state.logger.Conf;
import org.cachos.dimon.state.logger.event.ClientEvent;
import org.cachos.dimon.state.logger.event.StartUpEvent;
import org.cachos.dimon.state.logger.repo.RepositoryManager;
import org.junit.Before;
import org.junit.Test;


public class PrevaylerConfTest extends TestCase {
	
	static Logger logger = Logger.getLogger(PrevaylerConfTest.class.getName());
	private String confTestPath = "/test-conf.properties";

	public void cleanUp(){
		Conf testConf = new Conf(confTestPath);
		try {
			FileUtils.cleanDirectory(new File(testConf.getPrevalenceBase()));
			logger.debug("Dir cleanned: "+testConf.getPrevalenceBase());
		} catch (IOException e) {
			logger.error("unable to clean test prevalence dir: "+testConf.getPrevalenceBase(), e);
			TestCase.fail();
		}
	}
	
	@Test
	public void testPrevalenceConfigurationWorks(){

		cleanUp();
		
		Conf testConf = new Conf(confTestPath);
		RepositoryManager repo = RepositoryManager.getInstance(testConf);
//		repo.setConf(testConf);
		ClientEvent event = new StartUpEvent("test-ip", "test-port");
		logger.debug("a verrrrrr: "+repo.getConf().getPrevalenceBase());
		
		assertSame(
				1, 
				repo.getPrevayler().prevalentSystem().getEvents().get(
						event.getClass().getSimpleName()
						).size());
//		repo.log(event);
//		assertSame(
//				1, 
//				repo.getPrevayler().prevalentSystem().getEvents().get(
//						event.getClass().getSimpleName()
//						).size());
		
		
		
		
	}
	

}
