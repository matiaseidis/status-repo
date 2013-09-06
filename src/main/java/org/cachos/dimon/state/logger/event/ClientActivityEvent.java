package org.cachos.dimon.state.logger.event;

public class ClientActivityEvent extends ClientEvent {
	
	public ClientActivityEvent(String ip, String port) {
		super(ip, port);
	}

}
