package org.cachos.dimon.state.logger.event;

public class ClientAvailabilityEvent extends ClientEvent {
	
	public ClientAvailabilityEvent(String ip, String port) {
		super(ip, port);
	}

}
