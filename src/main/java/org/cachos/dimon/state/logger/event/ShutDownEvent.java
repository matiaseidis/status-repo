package org.cachos.dimon.state.logger.event;

public class ShutDownEvent extends ClientAvailabilityEvent {
	
	public ShutDownEvent(String ip, String port) {
		super(ip, port);
	}

}
