import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.cachos.dimon.state.logger.Conf;

import junit.framework.TestCase;


public abstract class RepoEmptyRequiredTest extends TestCase {
	
	static Logger logger = Logger.getLogger(RepoEmptyRequiredTest.class.getName());

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

	public String getConfTestPath() {
		return confTestPath;
	}

	public void setConfTestPath(String confTestPath) {
		this.confTestPath = confTestPath;
	}

}
