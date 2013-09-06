package org.cachos.dimon.state.logger.event;

public class AliveEvent extends ClientAvailabilityEvent {
	
	public AliveEvent(String ip, String port) {
		super(ip, port);
	}

}
