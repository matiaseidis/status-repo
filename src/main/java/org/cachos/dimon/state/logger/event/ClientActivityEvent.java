package org.cachos.dimon.state.logger.event;

import org.cachos.dimon.state.logger.plan.ProgressPercentageCalculator;

public abstract class ClientActivityEvent extends ClientEvent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ClientActivityEvent(String ip, String port, String planId, int id,
			long byteFrom, long byteTo, long byteCurrent) {
		super(ip, port);
		this.planId = planId;
		this.id = id;
		this.byteCurrent = byteCurrent;
		this.byteFrom = byteFrom;
		this.byteTo = byteTo;
	}

	private String planId;
	private int id;
//	private int progress;
	private long byteCurrent;
	private long byteFrom;
	private long byteTo;
	
	public ClientActivityEvent(String ip, String port) {
		super(ip, port);
	}
	
//	public ClientActivityEvent(String ip, String port, String planId, int id,
//			int progress) {
//		super(ip, port);
//		this.planId = planId;
//		this.id = id;
////		this.progress = progress;
//	}

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

	public long getProgress() {
		return new ProgressPercentageCalculator(this.getByteFrom(), this.getByteTo(), this.getByteCurrent()).calculate();
//		return new ProgressPercentageCalculator(this).calculate();
	}

//	public void setProgress(int progress) {
//		this.progress = progress;
//	}

	public long getByteCurrent() {
		return byteCurrent;
	}

	public void setByteCurrent(long byteCurrent) {
		this.byteCurrent = byteCurrent;
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
