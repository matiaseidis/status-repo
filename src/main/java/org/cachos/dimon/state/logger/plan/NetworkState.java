package org.cachos.dimon.state.logger.plan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cachos.dimon.state.logger.event.ClientEvent;

public class NetworkState implements Serializable {
	
	private Map<String, ClientEvent> strimers = new HashMap<String, ClientEvent>();
	private Map<String, ClientEvent> pushers = new HashMap<String, ClientEvent>();
	private Map<String, ClientEvent> pullers = new HashMap<String, ClientEvent>();
	private List<String> iddles = new ArrayList<String>();
	
	public Map<String, ClientEvent> getStrimers() {
		return strimers;
	}
	public void setStrimers(Map<String, ClientEvent> strimers) {
		this.strimers = strimers;
	}
	public Map<String, ClientEvent> getPushers() {
		return pushers;
	}
	public void setPushers(Map<String, ClientEvent> pushers) {
		this.pushers = pushers;
	}
	public Map<String, ClientEvent> getPullers() {
		return pullers;
	}
	public void setPullers(Map<String, ClientEvent> pullers) {
		this.pullers = pullers;
	}
	public List<String> getIddles() {
		return iddles;
	}
	public void setIddles(List<String> iddles) {
		this.iddles = iddles;
	}
	

}
