package org.cachos.dimon.state.logger.repo;

import org.apache.log4j.Logger;
import org.cachos.dimon.state.logger.Conf;
import org.cachos.dimon.state.logger.event.ClientEvent;
import org.cachos.dimon.state.logger.transaction.ClientEventRegistration;
import org.prevayler.Prevayler;
import org.prevayler.PrevaylerFactory;

public class RepositoryManager {
	
	static Logger logger = Logger.getLogger(RepositoryManager.class.getName());

	private static final RepositoryManager INSTANCE = new RepositoryManager();
	private Prevayler<StateRepository> prevayler = null;
	private Conf conf;
	
	private RepositoryManager(){
		this.setConf(new Conf());
		setup();
	}
	
	private void setup() {
		try {
			String prevalenceBaseDir = this.getConf().getPrevalenceBase();
			logger.debug("About to create prevayler in: " + prevalenceBaseDir);
			this.setPrevayler(PrevaylerFactory.createPrevayler(new StateRepository(), prevalenceBaseDir));
		} catch (Exception e) {
			logger.error("Unable to create prevayler, aborting...", e);
		}
	}

	public static RepositoryManager getInstance() {
		return INSTANCE;
	}
	
	public static RepositoryManager getInstance(Conf conf) {
		INSTANCE.setConf(conf);
		logger.debug("22222222222222222222: "+INSTANCE.getConf().getPrevalenceBase());
		return INSTANCE;
	}
	
	public void log(ClientEvent event) {
		this.getPrevayler().execute(new ClientEventRegistration(event));
	}

	public Prevayler<StateRepository> getPrevayler() {
		return prevayler;
	}

	public void setPrevayler(Prevayler<StateRepository> prevayler) {
		this.prevayler = prevayler;
	}

	public Conf getConf() {
		return conf;
	}

	public void setConf(Conf conf) {
		this.conf = conf;
	}
}
