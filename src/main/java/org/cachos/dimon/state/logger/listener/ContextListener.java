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
	
//	private static final String DIR_PATH = "/home/meidis/Escritorio/";
//	private static final String FILE_NAME = "test.log";
//
//	private static final String DB_FILE = "DB_FILE";

	public void contextInitialized(ServletContextEvent sce) {
//
//		File dir = new File(DIR_PATH);
//		if(!dir.exists()){
//			try {
//				FileUtils.forceMkdir(dir);
//			} catch (IOException e) {
//				logger.error("Unable to create dimon status db dir. Aborting...", e);
//				System.exit(0);
//			}
//		}
//		File file = new File(dir,FILE_NAME);
//		if(!file.exists()) {
//			try {
//				file.createNewFile();
//			} catch (IOException e) {
//				logger.error("Unable to create dimon status db file. Aborting...", e);
//				System.exit(0);
//			}
//		}
//		
//		FileRepo.getInstance().setRepoFile(file);
	}

	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}


}
