import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.cachos.dimon.state.logger.Conf;
import org.cachos.dimon.state.logger.event.ClientEvent;
import org.cachos.dimon.state.logger.event.ClientStatusEvent;
import org.cachos.dimon.state.logger.event.type.ClientState;
import org.cachos.dimon.state.logger.repo.RepositoryManager;
import org.junit.Test;

public class PrevaylerConfTest extends RepoEmptyRequiredTest{

	static Logger logger = Logger.getLogger(PrevaylerConfTest.class.getName());
	

	@Test
	public void testPrevalenceConfigurationWorks() {

		Conf testConf = new Conf(this.getConfTestPath());
		cleanUp(testConf);

		RepositoryManager repo = null;

		try {
			repo = RepositoryManager.getInstance(testConf).open();
		} catch (Exception e) {
			e.printStackTrace();
			TestCase.fail();
		}
		long bandWidth = 1000;
		ClientStatusEvent event = new ClientStatusEvent(ClientState.UP, "test-ip", "test-port", "test-client-id", bandWidth);
		
		int size = repo.getPrevayler().prevalentSystem().getEvents(event.getEventType())
				.size();

		assertSame(repo.getPrevayler().prevalentSystem().getEvents(event.getEventType()).toString(), 0, size);
		
		repo.logClientStatusEvent(event);

		size = repo.getPrevayler().prevalentSystem().getEvents(event.getEventType())
				.size();
		
		assertSame(1, size);

	}

}
