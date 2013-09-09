package org.cachos.dimon.state.logger.event;

import org.cachos.dimon.state.logger.event.type.ClientState;

public class ClientStatusEvent extends ClientEvent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ClientState clientState;
	
	public ClientStatusEvent(ClientState clientState, String ip, String port, String clientId,
			long bandWidth) {
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

}
