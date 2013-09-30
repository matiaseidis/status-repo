package org.cachos.dimon.state.logger.event.type;

public enum CachoDirection {
	PULL, PUSH;
	
	public static CachoDirection forEvent(String event) {
		for(CachoDirection cachoDirection : CachoDirection.values()) {
			if(cachoDirection.name().equalsIgnoreCase(event)) {
				return cachoDirection;
			}
		}
		return null;
	}

}
