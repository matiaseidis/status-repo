package org.cachos.dimon.state.logger.event;

public class StartUpEvent extends ClientAvailabilityEvent {
	
	public StartUpEvent(String ip, String port) {
		super(ip, port);
	}

}
