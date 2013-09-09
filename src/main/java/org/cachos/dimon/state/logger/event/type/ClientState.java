package org.cachos.dimon.state.logger.event.type;

public enum ClientState {
	UP, ALIVE, DOWN;

	public static ClientState forEvent(String event) {
		for(ClientState clientState : ClientState.values()) {
			if(clientState.name().equalsIgnoreCase(event)) {
				return clientState;
			}
		}
		return null;
	}
}
