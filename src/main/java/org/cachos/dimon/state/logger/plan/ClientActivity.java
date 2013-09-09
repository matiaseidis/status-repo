package org.cachos.dimon.state.logger.plan;

import java.util.List;

import org.cachos.dimon.state.logger.event.ClientEvent;

public class ClientActivity {
	
	private List<ClientEvent> events;

	public ClientActivity(List<ClientEvent> events) {
		super();
		this.events = events;
	}

	public List<ClientEvent> getEvents() {
		return events;
	}

	public void setEvents(List<ClientEvent> events) {
		this.events = events;
	}

}
