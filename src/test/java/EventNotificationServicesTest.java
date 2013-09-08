import org.apache.http.client.fluent.Request;
import org.cachos.dimon.state.logger.Conf;
import org.cachos.dimon.state.logger.event.AliveEvent;
import org.cachos.dimon.state.logger.event.ShutDownEvent;
import org.cachos.dimon.state.logger.event.StartUpEvent;
import org.cachos.dimon.state.logger.repo.RepositoryManager;
import org.junit.Assert;
import org.junit.Test;

public class EventNotificationServicesTest extends RepoEmptyRequiredTest {



	@Test
	public void testEventNotificationServiceWorks() throws Exception {
		Conf testConf = new Conf(this.getConfTestPath());

		cleanUp(testConf);

		String ip = "2.2.2.2";
		String port = "9999";

		RepositoryManager repo = RepositoryManager.getInstance(testConf).open();

		Assert.assertFalse(repo.isUp(ip, port));
		Assert.assertTrue(repo.isDown(ip, port));

		Request.Get(urlFor(testConf, startUp(ip, port))).execute()
				.returnContent();
		
		Assert.assertTrue(repo.isUp(ip, port));
		Assert.assertFalse(repo.isDown(ip, port));

		Request.Get(urlFor(testConf, alive(ip, port))).execute()
				.returnContent();
		Request.Get(urlFor(testConf, alive(ip, port))).execute()
				.returnContent();
		Request.Get(urlFor(testConf, alive(ip, port))).execute()
				.returnContent();

		Assert.assertTrue(repo.isUp(ip, port));
		Assert.assertFalse(repo.isDown(ip, port));

		Request.Get(urlFor(testConf, stop(ip, port))).execute().returnContent();

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

	private String urlFor(Conf conf, String suffix) {
		String url = "http://" + conf.getServicesIp() + ":"
				+ conf.getServicesPort() + "/service/logger/" + suffix;
		logger.debug("about to launch request: " + url);
		return url;
	}

	private String startUp(String ip, String port) {
		return "statusEvent/startup"
				+ this.eventLifeCycleServiceSuffix(ip, port);
	}

	private String stop(String ip, String port) {
		return "statusEvent/stop" + this.eventLifeCycleServiceSuffix(ip, port);
	}

	private String alive(String ip, String port) {
		return "statusEvent/alive" + this.eventLifeCycleServiceSuffix(ip, port);
	}

	private String eventLifeCycleServiceSuffix(String ip, String port) {
		return String.format("/%s/%s", ip, port);
	}
}
