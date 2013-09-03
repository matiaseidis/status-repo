package org.cachos.dimon.state.logger.repo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.apache.log4j.Logger;

public class FileRepo {
	
	static Logger logger = Logger.getLogger(FileRepo.class.getName());

	private File repoFile = null;
	
	private static final FileRepo INSTANCE = new FileRepo();
	
	private FileRepo(){}

	public File getRepoFile() {
		return repoFile;
	}

	public void setRepoFile(File repoFile) {
		this.repoFile = repoFile;
	}

	public static FileRepo getInstance() {
		return INSTANCE;
	}

	public FileRepo append(String newLine) {
		Writer output;
		try {
		output = new BufferedWriter(new FileWriter(this.getRepoFile()));
		output.append(newLine);
			output.close();
			System.out.println("new line appended to db file: "+output+" - "+ this.getRepoFile().getAbsolutePath());
			logger.info("new line appended to db file: "+output+" - "+ this.getRepoFile().getAbsolutePath());
		} catch (IOException e) {
			System.out.println("Unable to append new line to repo file");
			logger.error("Unable to append new line to repo file", e);
		}
		return this;
	}
}
