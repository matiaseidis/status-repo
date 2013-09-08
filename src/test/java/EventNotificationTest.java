import java.io.File;
import java.io.IOException;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.cachos.dimon.state.logger.Conf;
import org.cachos.dimon.state.logger.event.AliveEvent;
import org.cachos.dimon.state.logger.event.ClientEvent;
import org.cachos.dimon.state.logger.event.PullEvent;
import org.cachos.dimon.state.logger.event.PushEvent;
import org.cachos.dimon.state.logger.event.ShutDownEvent;
import org.cachos.dimon.state.logger.event.StartUpEvent;
import org.cachos.dimon.state.logger.plan.RetrievalPlan;
import org.cachos.dimon.state.logger.repo.RepositoryManager;
import org.junit.Assert;
import org.junit.Test;

public class EventNotificationTest extends RepoEmptyRequiredTest {

	static Logger logger = Logger.getLogger(EventNotificationTest.class
			.getName());

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
	 * 1 - prendo el dimon, lo apago durante el proceso hago alive's despues ver
	 * que en el repo esten todos los eventos
	 */
	@Test
	public void testEventNotificationWorks() throws Exception {
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

		Assert.assertTrue(repo.isUp(ip, port));
		Assert.assertFalse(repo.isDown(ip, port));

		repo.log(alive1);
		repo.log(alive2);
		repo.log(alive3);

		Assert.assertTrue(repo.isUp(ip, port));
		Assert.assertFalse(repo.isDown(ip, port));

		repo.log(shutDown);

		Assert.assertFalse(repo.isUp(ip, port));
		Assert.assertTrue(repo.isDown(ip, port));

		Assert.assertSame(
				1,
				repo.getPrevayler().prevalentSystem()
						.getEvents(StartUpEvent.class).size());
		Assert.assertSame(
				3,
				repo.getPrevayler().prevalentSystem()
						.getEvents(AliveEvent.class).size());
		Assert.assertSame(
				1,
				repo.getPrevayler().prevalentSystem()
						.getEvents(ShutDownEvent.class).size());
	}

	/**
	 * caso 2: caso 1 + plan, con update de puller y pushers. Ver que en el
	 * repo, en el planMap solo esta el update pero en el listado por evento
	 * estan todos
	 * 
	 * @throws Exception
	 */
	@Test
	public void testPlanNotificationWorks() throws Exception {
		
Conf testConf = new Conf(this.getConfTestPath());
		
		cleanUp(testConf);
		
		String ip = "2.2.2.2";
		String port = "9999";
		StartUpEvent startUp = new StartUpEvent(ip, port);
		RepositoryManager repo = RepositoryManager.getInstance(testConf).open();
		repo.log(startUp);
		Assert.assertTrue(repo.isUp(ip, port));
		
		String planId = "test-retrieval-plan";
		int clientId = 1;
		long byteCurrent = 0;
		long byteFrom = 0;
		long byteTo = 99999;
		PullEvent firstPullEvent = new PullEvent(ip, port, planId, clientId, byteFrom, byteTo, byteCurrent);
		
		repo.logPullEvent(firstPullEvent);
		byteCurrent = 1;
		PullEvent updatePullEvent = new PullEvent(ip, port, planId, clientId, byteFrom, byteTo, byteCurrent);
		repo.logPullEvent(updatePullEvent);
		
		RetrievalPlan planFromRepo = repo.getPrevayler().prevalentSystem().getPlansMap().get(planId); 
		Assert.assertNotNull(planFromRepo);
		List<ClientEvent> eventsByClient = repo.getPrevayler().prevalentSystem().getEventsByClient(ip, port);
		Assert.assertSame(3, eventsByClient.size());
		Assert.assertTrue(eventsByClient.get(0) instanceof StartUpEvent);
		Assert.assertTrue(eventsByClient.get(1) instanceof PullEvent);
		Assert.assertTrue(eventsByClient.get(2) instanceof PullEvent);
		
		PushEvent firstPusherEvent = new PushEvent("3.3.3.3", "333", planId, 2, byteFrom, byteTo, byteCurrent);
		PushEvent secondPusherEvent = new PushEvent("4.3.3.3", "333", planId, 3, byteFrom, byteTo, byteCurrent);
		PushEvent thirdPusherEvent = new PushEvent("5.3.3.3", "333", planId, 4, byteFrom, byteTo, byteCurrent);
		
		repo.logPushEvent(firstPusherEvent);
		repo.logPushEvent(secondPusherEvent);
		repo.logPushEvent(thirdPusherEvent);
		
		planFromRepo = repo.getPrevayler().prevalentSystem().getPlansMap().get(planId); 
		
		Assert.assertSame(3, planFromRepo.getPushers().size());
		int newCurrentByte = 150; 
		PushEvent thirdPusherUpdateEvent = new PushEvent("5.3.3.3", "333", planId, 4, byteFrom, byteTo, newCurrentByte);
		
		Assert.assertSame(3, planFromRepo.getPushers().size());
		Assert.assertNotSame(newCurrentByte, planFromRepo.getPusher(thirdPusherEvent.getId()).getByteCurrent());
		repo.logPushEvent(thirdPusherUpdateEvent);
		Assert.assertSame(4, thirdPusherEvent.getId());
		

	}
}
