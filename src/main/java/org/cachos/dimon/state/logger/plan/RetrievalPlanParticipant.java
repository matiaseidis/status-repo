package org.cachos.dimon.state.logger.plan;

import java.io.Serializable;

public abstract class RetrievalPlanParticipant implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String ip;
	private String port;
	private int progress;
	
	
	public RetrievalPlanParticipant(String ip, String port) {
		super();
		this.ip = ip;
		this.port = port;
	}
	
	
	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getPort() {
		return port;
	}
	
	public void setPort(String port) {
		this.port = port;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
}
