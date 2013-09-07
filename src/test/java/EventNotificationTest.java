import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.cachos.dimon.state.logger.Conf;
import org.cachos.dimon.state.logger.event.AliveEvent;
import org.cachos.dimon.state.logger.event.ShutDownEvent;
import org.cachos.dimon.state.logger.event.StartUpEvent;
import org.cachos.dimon.state.logger.repo.RepositoryManager;
import org.junit.Assert;
import org.junit.Test;


public class EventNotificationTest extends RepoEmptyRequiredTest {

	static Logger logger = Logger.getLogger(EventNotificationTest.class.getName());
	
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

	
	/**
	 * 1 - prendo el dimon, lo apago
	 * durante el proceso hago alive's
	 * despues ver que en el repo esten todos los eventos
	 * 
	 * 
	 * 2 - caso 1 + plan, con update de puller y pushers. 
	 * Ver que en el repo, en el planMap solo esta el update
	 * pero en el listado por evento estan todos
	 * @throws Exception 
	 */
	@Test
	public void testEventNotificationWorks() throws Exception{
		Conf testConf = new Conf(this.getConfTestPath());
		
		cleanUp(testConf);
		
		String ip = "2.2.2.2";
		String port = "9999";
		StartUpEvent startUp = new StartUpEvent(ip, port);
		AliveEvent alive1 = new AliveEvent(ip, port);
		AliveEvent alive2 = new AliveEvent(ip, port);
		AliveEvent alive3 = new AliveEvent(ip, port);
		ShutDownEvent shutDown = new ShutDownEvent(ip, port);
		
		
		RepositoryManager repo = RepositoryManager.getInstance(testConf).open();
		Assert.assertFalse(repo.isUp(ip, port));
		Assert.assertTrue(repo.isDown(ip, port));
		repo.log(startUp);
		repo.log(alive1);
		repo.log(alive2);
		repo.log(alive3);
		Assert.assertTrue(repo.isUp(ip, port));
		Assert.assertFalse(repo.isDown(ip, port));
		repo.log(shutDown);
		Assert.assertFalse(repo.isUp(ip, port));
		Assert.assertTrue(repo.isDown(ip, port));
		
		Assert.assertSame(1, repo.getPrevayler().prevalentSystem().getEvents(StartUpEvent.class).size());
		Assert.assertSame(3, repo.getPrevayler().prevalentSystem().getEvents(AliveEvent.class).size());
		Assert.assertSame(1, repo.getPrevayler().prevalentSystem().getEvents(ShutDownEvent.class).size());
	}
}
