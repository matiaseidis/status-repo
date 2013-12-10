package org.cachos.dimon.state.logger.plan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cachos.dimon.state.logger.event.ClientActivityEvent;
import org.cachos.dimon.state.logger.event.ClientStatusEvent;

public class NetworkState implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, ClientActivityEvent> strimers = new HashMap<String, ClientActivityEvent>();
	private Map<String, ClientActivityEvent> pushers = new HashMap<String, ClientActivityEvent>();
	private Map<String, ClientActivityEvent> pullers = new HashMap<String, ClientActivityEvent>();
	private List<ClientStatusEvent> iddles = new ArrayList<ClientStatusEvent>();
	
	public Map<String, ClientActivityEvent> getStrimers() {
		return strimers;
	}
	public void setStrimers(Map<String, ClientActivityEvent> strimers) {
		this.strimers = strimers;
	}
	public Map<String, ClientActivityEvent> getPushers() {
		return pushers;
	}
	public void setPushers(Map<String, ClientActivityEvent> pushers) {
		this.pushers = pushers;
	}
	public Map<String, ClientActivityEvent> getPullers() {
		return pullers;
	}
	public void setPullers(Map<String, ClientActivityEvent> pullers) {
		this.pullers = pullers;
	}
	public List<ClientStatusEvent> getIddles() {
		return iddles;
	}
	public void setIddles(List<ClientStatusEvent> iddles) {
		this.iddles = iddles;
	}
	

}
