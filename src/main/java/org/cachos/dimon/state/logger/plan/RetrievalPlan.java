package org.cachos.dimon.state.logger.plan;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class RetrievalPlan {

	private long id;
	private List<Pusher> pushers = new ArrayList<Pusher>();
	private Puller puller;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public List<Pusher> getPushers() {
		return pushers;
	}

	public void setPushers(List<Pusher> pushers) {
		this.pushers = pushers;
	}

	public Puller getPuller() {
		return puller;
	}

	public void setPuller(Puller puller) {
		this.puller = puller;
	}

	public Pusher getPusher(String clientId) {
		for(Pusher pusher : this.getPushers()){
			if(StringUtils.equals(pusher.getClientId(), clientId)) {
				return pusher;
			}
		}
		return null;
	}

}
