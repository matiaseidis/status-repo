package org.cachos.dimon.state.logger.event;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public abstract class ClientEvent implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
	static Logger logger = Logger.getLogger(ClientEvent.class.getName());

	private String ip;
	private String port;
	private String clientId;
	private String date;
	private long bandWidth;

	public ClientEvent(String ip, String port, String clientId, long bandWidth) {
		super();
		this.ip = ip;
		this.port = port;
		this.clientId = clientId;
		this.bandWidth = bandWidth;
		this.setDate(new SimpleDateFormat(DATE_PATTERN).format(new Date()));
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
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public long getBandWidth() {
		return bandWidth;
	}

	public void setBandWidth(long bandWidth) {
		this.bandWidth = bandWidth;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	public abstract String getEventType();
}
