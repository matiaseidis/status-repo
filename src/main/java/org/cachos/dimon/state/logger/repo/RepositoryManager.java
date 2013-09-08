package org.cachos.dimon.state.logger.repo;

import java.util.List;

import org.apache.log4j.Logger;
import org.cachos.dimon.state.logger.Conf;
import org.cachos.dimon.state.logger.event.AliveEvent;
import org.cachos.dimon.state.logger.event.ClientEvent;
import org.cachos.dimon.state.logger.event.PullEvent;
import org.cachos.dimon.state.logger.event.PushEvent;
import org.cachos.dimon.state.logger.event.StartUpEvent;
import org.cachos.dimon.state.logger.transaction.ClientActivityPullEventRegistration;
import org.cachos.dimon.state.logger.transaction.ClientActivityPushEventRegistration;
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
		if (instance.getConf() == null) {
			synchronized (instance) {
				if (instance.getConf() == null) {
					instance.setConf(conf);
				}
			}
		}
		return instance;
	}

	public void logPullEvent(PullEvent event) {
		System.out.println("RepositoryManager.logPullEvent()");
		this.getPrevayler().execute(
				new ClientActivityPullEventRegistration(event));
	}

	public void logPushEvent(PushEvent event) {
		this.getPrevayler().execute(
				new ClientActivityPushEventRegistration(event));
	}

	public void log(ClientEvent event) {
		System.out.println("RepositoryManager.log()");
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

	public boolean isUp(String ip, String port) {
		List<ClientEvent> eventsByClient = getPrevayler().prevalentSystem()
				.getEventsByClient(ip, port);

		if (eventsByClient.isEmpty()) {
			return false;
		}

		ClientEvent lastClientEvent = eventsByClient
				.get(eventsByClient.size() - 1);
		return lastClientEvent instanceof StartUpEvent
				|| lastClientEvent instanceof AliveEvent;
	}

	public boolean isDown(String ip, String port) {
		return !isUp(ip, port);
	}
}
