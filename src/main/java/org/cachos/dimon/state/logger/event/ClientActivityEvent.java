package org.cachos.dimon.state.logger.event;

public class ClientActivityEvent extends ClientEvent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String planId;
	private int id;
	private int progress;
	
	public ClientActivityEvent(String ip, String port) {
		super(ip, port);
	}
	
	public ClientActivityEvent(String ip, String port, String planId, int id,
			int progress) {
		super(ip, port);
		this.planId = planId;
		this.id = id;
		this.progress = progress;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

}
