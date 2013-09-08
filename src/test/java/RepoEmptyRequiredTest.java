import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.cachos.dimon.state.logger.Conf;
import org.eclipse.jetty.server.Server;
import org.junit.BeforeClass;


public abstract class RepoEmptyRequiredTest extends TestCase {
	
	static Logger logger = Logger.getLogger(RepoEmptyRequiredTest.class.getName());

	private String confTestPath = "/test-conf.properties";
	public static Server server = null;
	@BeforeClass
	public void startJetty() throws Exception {
		logger.debug("------------sfd----------------------------------------------------");
		if(server != null){
			return;
		}
		server = new Server(8080); // see notice 1

		server.start();
		int actualPort = 0;
		logger.debug("server port: " + actualPort);
	}

	public void cleanUp(Conf testConf)  {
		try {
			FileUtils.cleanDirectory(new File(testConf.getPrevalenceBase()));
			logger.debug("Dir cleanned: " + testConf.getPrevalenceBase());
		} catch (IOException e) {
			logger.error(
					"unable to clean test prevalence dir: "
							+ testConf.getPrevalenceBase(), e);
			TestCase.fail();
		}
		try {
//			startJetty();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getConfTestPath() {
		return confTestPath;
	}

	public void setConfTestPath(String confTestPath) {
		this.confTestPath = confTestPath;
	}

}
