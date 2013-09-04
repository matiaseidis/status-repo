package org.cachos.dimon.state.logger.repo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.apache.log4j.Logger;
import org.cachos.dimon.state.logger.event.DimonEvent;

public class FileRepo {

	static Logger logger = Logger.getLogger(FileRepo.class.getName());

	private File repoFile = null;

	private static final FileRepo INSTANCE = new FileRepo();

	private FileRepo() {
	}

	public File getRepoFile() {
		return repoFile;
	}

	public void setRepoFile(File repoFile) {
		this.repoFile = repoFile;
	}

	public static FileRepo getInstance() {
		return INSTANCE;
	}

	public FileRepo log(DimonEvent event) {
		Writer output;
		try {
			String plainEvent = event.toString();
			output = new BufferedWriter(
					new FileWriter(this.getRepoFile(), true));
			output.append(plainEvent);
			output.append(System.getProperty("line.separator"));
			output.close();
			logger.info("new line appended to db file: " + plainEvent+ " - "
					+ this.getRepoFile().getAbsolutePath());
		} catch (IOException e) {
			logger.error("Unable to append new line to repo file", e);
		}
		return this;
	}
}
