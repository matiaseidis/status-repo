package org.cachos.dimon.state.logger.repo;

import java.util.List;

import org.apache.log4j.Logger;
import org.cachos.dimon.state.logger.Conf;
import org.cachos.dimon.state.logger.event.ClientActivityEvent;
import org.cachos.dimon.state.logger.event.ClientEvent;
import org.cachos.dimon.state.logger.event.ClientStatusEvent;
import org.cachos.dimon.state.logger.event.type.ClientState;
import org.cachos.dimon.state.logger.transaction.ClientActivityEventRegistration;
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
		if(prevayler == null) {
			synchronized (instance) {
				if(prevayler == null) {
					prevayler = PrevaylerFactory.createPrevayler(new StateRepository(),
							this.getConf().getPrevalenceBase());
				}
			}
		}
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

	public void logClientActivityEvent(ClientActivityEvent event) {
		System.out.println("RepositoryManager.logPullEvent()");
		
			this.getPrevayler().execute(
					new ClientActivityEventRegistration(event));
	}
	
	public void logClientStatusEvent(ClientStatusEvent event) {
			this.getPrevayler().execute(
					new ClientEventRegistration<ClientStatusEvent>(event));
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
		
		for(int i = eventsByClient.size(); i >= 0; i--) {
			ClientEvent e = eventsByClient.get(i-1);
			if(e instanceof ClientStatusEvent) {
				ClientState state = ((ClientStatusEvent)e).getClientState();
				return ClientState.ALIVE.equals(state) || 
						ClientState.UP.equals(state);
			}
		}
		return false;
	}

	public boolean isDown(String ip, String port) {
		return !isUp(ip, port);
	}
}
