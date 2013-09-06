package org.cachos.dimon.state.logger.event;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.common.collect.Lists;

public class ClientEvent implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
	static Logger logger = Logger.getLogger(ClientEvent.class.getName());
	private String separator = ",";

	private String ip;
	private String port;
	private Date date = new Date();

	public ClientEvent(String ip, String port) {
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
				this.getIp(),
				this.getPort()
				);
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
