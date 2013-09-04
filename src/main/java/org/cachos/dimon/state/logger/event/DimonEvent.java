package org.cachos.dimon.state.logger.event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.common.collect.Lists;

public class DimonEvent {
	
	private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
	static Logger logger = Logger.getLogger(DimonEvent.class.getName());
	private String separator = ",";

	private String eventType; 
	private String ip;
	private String port;
	private String comment;
	private Date date;

	public DimonEvent(String eventType, String ip, String port, String comment) {
		this.date = new Date();
		this.comment = comment;
		this.eventType = eventType;
		this.ip = ip;
		this.port = port;
	}
	

	private String asPlainText() {
		
		List<String> columns = getEventProperties();
		
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < columns.size(); i++) {
			sb.append(columns.get(i));
			if(i != columns.size()-1) {
				sb.append(separator);
			}
		}
		
		return sb.toString();
	}

	private ArrayList<String> getEventProperties() {
		return Lists.newArrayList(
				new SimpleDateFormat(DATE_PATTERN).format(this.getDate()),
				this.getEventType(),
				this.getIp(),
				this.getPort(),
				this.getComment()
				);
	}
	
	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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
	
	@Override
	public String toString() {
		return this.asPlainText();
	}
}
