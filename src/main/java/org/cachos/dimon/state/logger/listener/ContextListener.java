package org.cachos.dimon.state.logger.listener;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.cachos.dimon.state.logger.repo.FileRepo;
import org.cachos.dimon.state.logger.repo.RepositoryManager;

public class ContextListener implements ServletContextListener {
	
	static Logger logger = Logger.getLogger(ContextListener.class.getName());

	public void contextInitialized(ServletContextEvent sce) {
	}

	public void contextDestroyed(ServletContextEvent sce) {
		try {
			RepositoryManager.getInstance().close();
		} catch (Exception e) {
			logger.error("unable to close RepositoryManager", e);
		}
		try {
			FileUtils.cleanDirectory(new File(RepositoryManager.getInstance().getConf().getPrevalenceBase()));
		} catch (IOException e) {
			logger.error("unable to clean prevalence base", e);
		}
		
	}


}
