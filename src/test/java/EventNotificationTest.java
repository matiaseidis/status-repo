import java.io.File;
import java.io.IOException;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.apache.http.entity.ContentLengthStrategy;
import org.apache.log4j.Logger;
import org.cachos.dimon.state.logger.Conf;
import org.cachos.dimon.state.logger.event.ClientActivityEvent;
import org.cachos.dimon.state.logger.event.ClientEvent;
import org.cachos.dimon.state.logger.event.ClientStatusEvent;
import org.cachos.dimon.state.logger.event.type.CachoDirection;
import org.cachos.dimon.state.logger.event.type.ClientState;
import org.cachos.dimon.state.logger.plan.RetrievalPlan;
import org.cachos.dimon.state.logger.repo.RepositoryManager;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Collections2;

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
		RepositoryManager.getInstance().reset();
	}

	/**
	 * 1 - prendo el dimon, lo apago durante el proceso hago alive's despues ver
	 * que en el repo esten todos los eventos
	 */
	@Test
	public void testEventNotificationWorks() throws Exception {
		Conf testConf = new Conf(this.getConfTestPath());

		cleanUp(testConf);

		String ip = "26.2.2.2";
		String port = "9999";
		long bandWidth = 122;
		ClientStatusEvent startUp = new ClientStatusEvent(ClientState.UP, ip,
				port, "client-id", bandWidth);
		ClientStatusEvent alive1 = new ClientStatusEvent(ClientState.ALIVE, ip,
				port, "client-id", bandWidth);
		ClientStatusEvent alive2 = new ClientStatusEvent(ClientState.ALIVE, ip,
				port, "client-id", bandWidth);
		ClientStatusEvent alive3 = new ClientStatusEvent(ClientState.ALIVE, ip,
				port, "client-id", bandWidth);
		ClientStatusEvent shutDown = new ClientStatusEvent(ClientState.DOWN,
				ip, port, "client-id", bandWidth);

		RepositoryManager repo = RepositoryManager.getInstance(testConf).open();

		Assert.assertFalse(repo.isUp(ip, port));
		Assert.assertTrue(repo.isDown(ip, port));

		repo.logClientStatusEvent(startUp);
		
		Assert.assertSame(repo.getPrevayler().prevalentSystem()
				.getEvents(ClientState.UP.name()).toString(),
				1,
				repo.getPrevayler().prevalentSystem()
						.getEvents(ClientState.UP.name()).size());
		
		Assert.assertSame(repo.getPrevayler().prevalentSystem()
				.getEventsByClient(ip, port).toString(),
				1,
				repo.getPrevayler().prevalentSystem()
				.getEventsByClient(ip, port).size());

		Assert.assertTrue(repo.isUp(ip, port));
		Assert.assertFalse(repo.isDown(ip, port));

		repo.logClientStatusEvent(alive1);
		
		Assert.assertSame(repo.getPrevayler().prevalentSystem()
				.getEvents(ClientState.UP.name()).toString(),
				1,
				repo.getPrevayler().prevalentSystem()
						.getEvents(ClientState.UP.name()).size());
		
		
		repo.logClientStatusEvent(alive2);
		repo.logClientStatusEvent(alive3);

		Assert.assertTrue(repo.isUp(ip, port));
		Assert.assertFalse(repo.isDown(ip, port));

		repo.logClientStatusEvent(shutDown);

		Assert.assertFalse(repo.isUp(ip, port));
		Assert.assertTrue(repo.isDown(ip, port));

		Assert.assertSame(repo.getPrevayler().prevalentSystem()
				.getEvents(ClientState.UP.name()).toString(),
				1,
				repo.getPrevayler().prevalentSystem()
						.getEvents(ClientState.UP.name()).size());
		Assert.assertSame(
				3,
				repo.getPrevayler().prevalentSystem()
						.getEvents(ClientState.ALIVE.name()).size());
		Assert.assertSame(
				1,
				repo.getPrevayler().prevalentSystem()
						.getEvents(ClientState.DOWN.name()).size());
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
		String clientId = "1";
		long bandWidth = 99;
		ClientStatusEvent startUp = new ClientStatusEvent(ClientState.UP, ip,
				port, clientId, bandWidth);
		RepositoryManager repo = RepositoryManager.getInstance(testConf).open();
		repo.logClientStatusEvent(startUp);
		Assert.assertTrue(repo.isUp(ip, port));

		String planId = "test-retrieval-plan";
		long byteCurrent = 0;
		long byteFrom1 = 0;
		long byteFrom2 = 10;
		long byteFrom3 = 20;
		long byteTo1 = 9;
		long byteTo2 = 19;
		long byteTo3 = 29;
		ClientActivityEvent firstPullEvent = new ClientActivityEvent(
				CachoDirection.PULL, ip, port, planId, clientId, byteFrom1,
				byteTo1, byteCurrent, bandWidth);

		repo.logClientActivityEvent(firstPullEvent);
		byteCurrent = 1;
		ClientActivityEvent updatePullEvent = new ClientActivityEvent(
				CachoDirection.PULL, ip, port, planId, clientId, byteFrom1,
				byteTo1, byteCurrent, bandWidth);
		repo.logClientActivityEvent(updatePullEvent);

		RetrievalPlan planFromRepo = repo.getPrevayler().prevalentSystem()
				.getPlansMap().get(planId);
		Assert.assertNotNull(planFromRepo);
		List<ClientEvent> eventsByClient = repo.getPrevayler()
				.prevalentSystem().getEventsByClient(ip, port);
		Assert.assertSame(3, eventsByClient.size());
		Assert.assertTrue(((ClientStatusEvent) eventsByClient.get(0))
				.getClientState().equals(ClientState.UP));
		Assert.assertTrue(((ClientActivityEvent) eventsByClient.get(1))
				.getCachoDirection().equals(CachoDirection.PULL));
		Assert.assertTrue(((ClientActivityEvent) eventsByClient.get(2))
				.getCachoDirection().equals(CachoDirection.PULL));
		ClientActivityEvent firstPusherEvent = new ClientActivityEvent(
				CachoDirection.PUSH, "3.3.3.3", "333", planId, "2", byteFrom1,
				byteTo1, byteCurrent, bandWidth);
		ClientActivityEvent secondPusherEvent = new ClientActivityEvent(
				CachoDirection.PUSH, "4.3.3.3", "333", planId, "3", byteFrom2,
				byteTo2, byteCurrent, bandWidth);
		ClientActivityEvent thirdPusherEvent = new ClientActivityEvent(
				CachoDirection.PUSH, "5.3.3.3", "333", planId, "4", byteFrom3,
				byteTo3, byteCurrent, bandWidth);

		repo.logClientActivityEvent(firstPusherEvent);
		repo.logClientActivityEvent(secondPusherEvent);
		repo.logClientActivityEvent(thirdPusherEvent);

		planFromRepo = repo.getPrevayler().prevalentSystem().getPlansMap()
				.get(planId);

		Assert.assertSame(3, planFromRepo.getPulls().size());
		int newCurrentByte = 150;
		ClientActivityEvent thirdPusherUpdateEvent = new ClientActivityEvent(
				CachoDirection.PUSH, "5.3.3.3", "333", planId, "4", byteFrom3,
				byteTo3, newCurrentByte, bandWidth);

		Assert.assertSame(3, planFromRepo.getPulls().size());
		Assert.assertNotSame(newCurrentByte,
				planFromRepo.getPusher(thirdPusherEvent.getClientId())
						.getByteCurrent());
		repo.logClientActivityEvent(thirdPusherUpdateEvent);
		Assert.assertEquals("4", thirdPusherEvent.getClientId());

	}
}
