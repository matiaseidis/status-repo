package org.cachos.dimon.state.logger.listener;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.cachos.dimon.state.logger.repo.FileRepo;

public class ContextListener implements ServletContextListener {
	
	static Logger logger = Logger.getLogger(ContextListener.class.getName());

	public void contextInitialized(ServletContextEvent sce) {
	}

	public void contextDestroyed(ServletContextEvent sce) {
	}


}
