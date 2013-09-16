package org.cachos.dimon.state.logger.plan;

import java.io.Serializable;

import org.cachos.dimon.state.logger.event.ClientActivityEvent;

public class RetrievalPlanParticipant implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String clientId;
	private String ip;
	private String port;
	private long byteCurrent;
	private long byteFrom;
	private long byteTo;
	private double bandWidth;
	
	public RetrievalPlanParticipant(String ip, String port) {
		super();
		this.ip = ip;
		this.port = port;
	}
	
	public RetrievalPlanParticipant(ClientActivityEvent event) {
		this(event.getIp(), 
		event.getPort(), 
		event.getClientId(), 
		event.getByteFrom(), 
		event.getByteTo(), 
		event.getByteCurrent(), 
		event.getBandWidth());
		
	}
//	
	public RetrievalPlanParticipant(String ip, String port, String clientId,
			long byteFrom, long byteTo, long byteCurrent, double bandWidth) {
		super();
		this.setIp(ip);
		this.setPort(port);
		this.setClientId(clientId);
		this.setByteFrom(byteFrom);
		this.setByteTo(byteTo);
		this.setByteCurrent(byteCurrent);
		this.setBandWidth(bandWidth);
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

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public double getBandWidth() {
		return bandWidth;
	}

	public void setBandWidth(double bandWidth) {
		this.bandWidth = bandWidth;
	}
}
