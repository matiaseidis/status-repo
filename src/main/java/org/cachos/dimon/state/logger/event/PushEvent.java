package org.cachos.dimon.state.logger.event;

public class PushEvent extends ClientActivityEvent {

	public PushEvent(String ip, String port) {
		super(ip, port);
		// TODO Auto-generated constructor stub
	}

	public PushEvent(String ip, String port, String planId, int id,
			long byteCurrent, long byteFrom, long byteTo) {
		super(ip, port, planId, id, byteCurrent, byteFrom, byteTo);
		// TODO Auto-generated constructor stub
	}

}
