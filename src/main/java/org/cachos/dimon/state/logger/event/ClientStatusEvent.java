package org.cachos.dimon.state.logger.event;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.cachos.dimon.state.logger.event.type.ClientState;

public class ClientStatusEvent extends ClientEvent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ClientState clientState;
	
	public ClientStatusEvent(ClientState clientState, String ip, String port, String clientId,
			double bandWidth) {
		super(ip, port, clientId, bandWidth);
		this.setClientState(clientState);
	}

	public ClientState getClientState() {
		return clientState;
	}

	public void setClientState(ClientState clientState) {
		this.clientState = clientState;
	}

	@Override
	public String getEventType() {
		return this.getClientState().name();
	}
	
	@Override
	public String toString() {
		return this.getIp()+":"+this.getPort()+" - "+this.getEventType();
	}

}
