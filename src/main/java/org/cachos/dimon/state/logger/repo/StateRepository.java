package org.cachos.dimon.state.logger.repo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.cachos.dimon.state.logger.event.ClientEvent;
import org.cachos.dimon.state.logger.event.ClientStatusEvent;
import org.cachos.dimon.state.logger.event.type.ClientState;
import org.cachos.dimon.state.logger.plan.RetrievalPlan;

public class StateRepository implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static Logger logger = Logger.getLogger(StateRepository.class.getName());

	private Map<String, RetrievalPlan> plansMap = null;
	
	private Map<String, List<ClientEvent>> eventsByTypeMap = null;

	private Map<String, List<ClientEvent>> eventsByClientMap = null;

	public StateRepository() {
		this.setPlansMap(new HashMap<String, RetrievalPlan>());
		this.setEventsByTypeMap(new HashMap<String, List<ClientEvent>>());
		this.setEventsByClientMap(new HashMap<String, List<ClientEvent>>()); 
	}
	
	public List<ClientEvent> getEvents(String eventKey) {
		List<ClientEvent> result = this.getEventsByTypeMap().get(eventKey);
		if(result == null) {
			result = new ArrayList<ClientEvent>();
			this.getEventsByTypeMap().put(eventKey, result);
		}
		return result;
	}
	
	public Map<String, List<ClientEvent>> getEventsByTypeMap() {
		return eventsByTypeMap;
	}
	
	public void setEventsByTypeMap(Map<String, List<ClientEvent>> eventsByTypeMap) {
		this.eventsByTypeMap = eventsByTypeMap;
	}

	public Map<String, RetrievalPlan> getPlansMap() {
		return plansMap;
	}
	
	public void setPlansMap(Map<String, RetrievalPlan> plansMap) {
		this.plansMap = plansMap;
	}

	public List<ClientEvent> getEventsByClient(String ip, String port) {
		List<ClientEvent> result = this.getEventsByClientMap().get(addressKey(ip, port));
		if(result == null) {
			result = new ArrayList<ClientEvent>();
			this.getEventsByClientMap().put(addressKey(ip, port), result);
		}
		return result;
	}

	private String addressKey(String ip, String port) {
		return ip+":"+port;
	}

	public Map<String, List<ClientEvent>> getEventsByClientMap() {
		return eventsByClientMap;
	}

	public void setEventsByClientMap(Map<String, List<ClientEvent>> eventsByClientMap) {
		this.eventsByClientMap = eventsByClientMap;
	}
}
