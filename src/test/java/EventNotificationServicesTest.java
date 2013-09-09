import java.util.concurrent.CountDownLatch;

import junit.framework.TestCase;

import org.cachos.dimon.state.logger.Conf;
import org.cachos.dimon.state.logger.repo.RepositoryManager;
import org.junit.Test;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;

import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.spi.container.servlet.ServletContainer;
//import org.eclipse.jetty.server.Server;
//import org.eclipse.jetty.servlet.ServletContextHandler.Context;
//import org.eclipse.jetty.servlet.ServletHolder;
//import org.mortbay.jetty.handler.ContextHandler.Context;

/*
 * 
 * curl http://localhost:8080/service/logger/check/2.2.2.2/9999 DOWN
 * curl http://localhost:8080/service/logger/statusEvent/startup/2.2.2.2/9999 OK
 * curl http://localhost:8080/service/logger/check/2.2.2.2/9999 UP
 * curl http://localhost:8080/service/logger/statusEvent/alive/2.2.2.2/9999 OK
 * curl http://localhost:8080/service/logger/statusEvent/alive/2.2.2.2/9999 OK
 * curl http://localhost:8080/service/logger/statusEvent/alive/2.2.2.2/9999 OK
 * curl http://localhost:8080/service/logger/check/2.2.2.2/9999 UP
 * curl http://localhost:8080/service/logger/statusEvent/stop/2.2.2.2/9999 OK
 * curl http://localhost:8080/service/logger/check/2.2.2.2/9999 DOWN
 */
public class EventNotificationServicesTest extends RepoEmptyRequiredTest {

	static Server server;
	@Test
	public void testEventNotificationServiceWorks() throws Exception {
		Conf testConf = new Conf(this.getConfTestPath());

		cleanUp(testConf);

//		this.startServer();
		
//		final CountDownLatch c = new CountDownLatch(1);
//		new Thread(new Runnable() {
//
//			public void run() {
//				try {
//					server = new Server(8080);
//					Context root = new Context(server, "/", Context.SESSIONS);
//					root.addServlet(new ServletHolder(new ServletContainer(
//							new PackagesResourceConfig(
//									"org.cachos.dimon.state.logger.service"))),
//							"/");
//					try {
//						server.start();
//					} catch (Exception e) {
//						logger.error("unable to start server. Aborting", e);
//						TestCase.fail();
//					}
//				} catch (Exception e) {
//					logger.error("unable to start server. Aborting", e);
//					TestCase.fail();
//				} finally {
//					c.countDown();
//				}
//			}
//
//		}).start();

		String ip = "2.2.2.2";
		String port = "9999";

		RepositoryManager repo = RepositoryManager.getInstance(testConf).open();

//		Assert.assertFalse(repo.isUp(ip, port));
//		Assert.assertTrue(repo.isDown(ip, port));
		urlFor(testConf, startUp(ip, port));
//		Request.Get(urlFor(testConf, startUp(ip, port))).execute()
//				.returnContent();

//		Assert.assertTrue(repo.isUp(ip, port));
//		Assert.assertFalse(repo.isDown(ip, port));

		urlFor(testConf, alive(ip, port));
		urlFor(testConf, alive(ip, port));
		urlFor(testConf, alive(ip, port));
//		Request.Get(urlFor(testConf, alive(ip, port))).execute()
//				.returnContent();
//		Request.Get(urlFor(testConf, alive(ip, port))).execute()
//				.returnContent();
//		Request.Get(urlFor(testConf, alive(ip, port))).execute()
//				.returnContent();

//		Assert.assertTrue(repo.isUp(ip, port));
//		Assert.assertFalse(repo.isDown(ip, port));
		urlFor(testConf, stop(ip, port));
//		Request.Get(urlFor(testConf, stop(ip, port))).execute().returnContent();

//		Assert.assertFalse(repo.isUp(ip, port));
//		Assert.assertTrue(repo.isDown(ip, port));

//		Assert.assertSame(
//				1,
//				repo.getPrevayler().prevalentSystem()
//						.getEvents(StartUpEvent.class).size());
//		Assert.assertSame(
//				3,
//				repo.getPrevayler().prevalentSystem()
//						.getEvents(AliveEvent.class).size());
//		Assert.assertSame(
//				1,
//				repo.getPrevayler().prevalentSystem()
//						.getEvents(ShutDownEvent.class).size());

	}

	private void startServer() {

		final CountDownLatch c = new CountDownLatch(1);
		new Thread(new Runnable() {

			public void run() {
				try {
					Server server = new Server(8080);
					Context root = new Context(server, "/", Context.SESSIONS);
					root.addServlet(new ServletHolder(new ServletContainer(
							new PackagesResourceConfig(
									"org.cachos.dimon.state.logger.service"))),
							"/");
					// root.addServlet(new ServletHolder(new
					// ServletContainer(new WebAppResourceConfig(new String[0],
					// null))), "/");
					try {
						server.start();
					} catch (Exception e) {
						logger.error("unable to start server. Aborting", e);
						TestCase.fail();
					}
				} catch (Exception e) {
					logger.error("unable to start server. Aborting", e);
					TestCase.fail();
				} finally {
					c.countDown();
				}
			}

		}).start();

		// try {
		// c.await();
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
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
