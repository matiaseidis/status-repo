package org.cachos.dimon.state.logger.plan;

import org.apache.log4j.Logger;

public class ProgressPercentageCalculator {
	
	static Logger logger = Logger.getLogger(ProgressPercentageCalculator.class.getName());
	
	private long byteFrom;
	private long byteTo;
	private long byteCurrent;

	private String name;
	
	
	public ProgressPercentageCalculator(RetrievalPlanParticipant p) {
		super();
		this.byteFrom = p.getByteFrom();
		this.byteTo = p.getByteTo();
		this.byteCurrent = p.getByteCurrent();
		this.setName(p.getClass().getSimpleName());
	}
	
	public ProgressPercentageCalculator(long byteFrom, long byteTo,
			long byteCurrent) {
		super();
		this.byteFrom = byteFrom;
		this.byteTo = byteTo;
		this.byteCurrent = byteCurrent;
	}

	public long calculate() {
		long totalBytes = byteTo - byteFrom;
		long transferredBytes = byteCurrent - byteFrom; 
		long progressPercentage = (transferredBytes * 100) / totalBytes;
//		logger.debug("progress for "+this.getName()+": ["+progressPercentage+"] -> from:["+this.getByteFrom()+"] to:["+this.getByteTo()+"] current:["+this.getByteCurrent()+"]");
		return progressPercentage;
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

	public long getByteCurrent() {
		return byteCurrent;
	}

	public void setByteCurrent(long byteCurrent) {
		this.byteCurrent = byteCurrent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
