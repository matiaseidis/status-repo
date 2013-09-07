package org.cachos.dimon.state.logger.event;

public class PullEvent extends ClientActivityEvent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PullEvent(String ip, String port) {
		super(ip, port);
		// TODO Auto-generated constructor stub
	}

	public PullEvent(String ip, String port, String planId, int id,
			long byteCurrent, long byteFrom, long byteTo) {
		super(ip, port, planId, id, byteCurrent, byteFrom, byteTo);
		// TODO Auto-generated constructor stub
	}

	
}
