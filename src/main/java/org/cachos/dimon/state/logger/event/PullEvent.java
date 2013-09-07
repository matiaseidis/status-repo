package org.cachos.dimon.state.logger.event;

public class PullEvent extends ClientActivityEvent {
	
	public PullEvent(String ip, String port) {
		super(ip, port);
		// TODO Auto-generated constructor stub
	}

	public PullEvent(String ip, String port, String planId, int id, int progress) {
		super(ip, port, planId, id, progress);
		// TODO Auto-generated constructor stub
	}

	
}
