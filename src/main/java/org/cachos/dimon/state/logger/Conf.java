package org.cachos.dimon.state.logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Conf {
	
	static Logger logger = Logger.getLogger(Conf.class.getName());

	private String confPath = "/conf.properties";
	private String baseDir = "prevalence.base";
	private String servicesIp = "services.ip";
	private String servicesPort = "services.port";
	private Properties properties;
	
	public Conf() {
		this.setUp();
	}
	
	public Conf(String confPath) {
		this.confPath = confPath;
		this.setUp();
	}

	protected void setUp() {
		Properties props = new Properties();
		try {
			logger.debug("about to try to open resource: "+confPath);
			InputStream is = this.getClass().getResourceAsStream(confPath);
			props.load(is);
		} catch (IOException e) {
			logger.fatal("Unable to load configuration values form " + confPath + " - context shutdown.", e);
			System.exit(1);
		}
		this.properties = props;

		createDirOrShutDown(new File(this.getPrevalenceBase()));
	}
	
	private void createDirOrShutDown(File dir) {
		if (!dir.exists()) {
			if (!dir.mkdirs()) {
				logger.fatal("Unable to create directory " + dir.getAbsolutePath() + " - context shutdown.");
				System.exit(1);
			}
		}
	}


	public String getPrevalenceBase() {
		return System.getProperty("user.home") + System.getProperty("file.separator") + this.get(baseDir);
	}
	
	public String get(String propertyKey) {
		String result = properties.getProperty(propertyKey);
		if (result == null) {
			throw new IllegalArgumentException("property not found: " + propertyKey);
		}
		return result;
	}

	public String getServicesIp() {
		return get(this.servicesIp);
	}

	public String getServicesPort() {
		return get(this.servicesPort);
	}

}
