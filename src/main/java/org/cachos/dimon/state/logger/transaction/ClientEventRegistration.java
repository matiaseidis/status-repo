package org.cachos.dimon.state.logger.transaction;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.cachos.dimon.state.logger.event.ClientEvent;
import org.cachos.dimon.state.logger.repo.StateRepository;
import org.prevayler.Transaction;

public class ClientEventRegistration implements
		Transaction<StateRepository> {
	
	static Logger logger = Logger.getLogger(ClientEventRegistration.class.getName());

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ClientEvent event;
	
	public ClientEventRegistration(ClientEvent event) {
		this.event = event;
	}
	
	public void executeOn(StateRepository repo, Date date) {
		List<ClientEvent> events = repo.getEvents(event);
		events.add(event);
		logger.debug("Succesfully logged event of type "+event.getClass().getSimpleName()+" by client "+event.getIp()+":"+event.getPort());
	}
}
