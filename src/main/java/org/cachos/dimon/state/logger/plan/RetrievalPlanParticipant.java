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
	private long byteCurrent;
	private long byteFrom;
	private long byteTo;
	
	public RetrievalPlanParticipant(String ip, String port) {
		super();
		this.ip = ip;
		this.port = port;
	}
	
	public RetrievalPlanParticipant(String ip, String port, int id,
			long byteFrom, long byteTo, long byteCurrent) {
		super();
		this.ip = ip;
		this.port = port;
		this.id = id;
		this.byteFrom = byteFrom;
		this.byteTo = byteTo;
		this.byteCurrent = byteCurrent;
	}
	
	public long getByteCurrent() {
		return byteCurrent;
	}

	public void setByteCurrent(long byteCurrent) {
		this.byteCurrent = byteCurrent;
	}
	
	public long getProgress() {
		return new ProgressPercentageCalculator(this).calculate();
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
	
	public long getByteFrom() {
		return byteFrom;
	}


	public void setByteFrom(long byteFrom) {
		this.byteFrom = byteFrom;
	}


	public long getByteTo() {
		return byteTo;
	}


	public void setByteTo(long byteTo) {
		this.byteTo = byteTo;
	}
}
