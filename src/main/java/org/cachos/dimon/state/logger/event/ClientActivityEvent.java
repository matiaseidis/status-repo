package org.cachos.dimon.state.logger.event;

import org.cachos.dimon.state.logger.event.type.CachoDirection;
import org.cachos.dimon.state.logger.plan.ProgressPercentageCalculator;

public class ClientActivityEvent extends ClientEvent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String planId;
	
	private long byteCurrent;
	private long byteFrom;
	private long byteTo;
	private CachoDirection cachoDirection;
	
	public ClientActivityEvent(CachoDirection cachoDirection, String ip, String port, String planId, String clientId,
			long byteFrom, long byteTo, long byteCurrent, long bandWidth) {
		super(ip, port, clientId, bandWidth);
		this.setPlanId(planId);
		this.setByteCurrent(byteCurrent);
		this.setByteFrom(byteFrom);
		this.setByteTo(byteTo);
		this.setCachoDirection(cachoDirection);
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public long getProgress() {
		return new ProgressPercentageCalculator(this.getByteFrom(), this.getByteTo(), this.getByteCurrent()).calculate();
	}

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

	public CachoDirection getCachoDirection() {
		return cachoDirection;
	}

	public void setCachoDirection(CachoDirection cachoDirection) {
		this.cachoDirection = cachoDirection;
	}

	@Override
	public String getEventType() {
		return this.getCachoDirection().name();
	}
}
