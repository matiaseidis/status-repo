package org.cachos.dimon.state.logger.repo;

import org.apache.log4j.Logger;
import org.cachos.dimon.state.logger.Conf;
import org.cachos.dimon.state.logger.event.ClientEvent;
import org.cachos.dimon.state.logger.transaction.ClientEventRegistration;
import org.prevayler.Prevayler;
import org.prevayler.PrevaylerFactory;

public class RepositoryManager {

	static Logger logger = Logger.getLogger(RepositoryManager.class.getName());

	private static RepositoryManager instance = new RepositoryManager();
	private Prevayler<StateRepository> prevayler = null;
	private Conf conf;

	private RepositoryManager() {
	}

	public RepositoryManager open() throws Exception {
		prevayler = PrevaylerFactory.createPrevayler(new StateRepository(),
				this.getConf().getPrevalenceBase());
		return instance;
	}

	public RepositoryManager close() throws Exception {
		prevayler.close();
		return instance;
	}

	public static RepositoryManager getInstance() {
		return instance;
	}

	public static RepositoryManager getInstance(Conf conf) {
		instance.setConf(conf);
		return instance;
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
