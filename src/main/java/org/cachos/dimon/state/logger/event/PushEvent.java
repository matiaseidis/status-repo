package org.cachos.dimon.state.logger.event;

public class PushEvent extends ClientActivityEvent {

	public PushEvent(String ip, String port) {
		super(ip, port);
		// TODO Auto-generated constructor stub
	}

	public PushEvent(String ip, String port, String planId, String clientId,
			long byteCurrent, long byteFrom, long byteTo) {
		super(ip, port, planId, clientId, byteFrom, byteTo, byteCurrent);
		// TODO Auto-generated constructor stub
	}

}
