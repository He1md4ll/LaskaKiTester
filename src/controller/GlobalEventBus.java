package controller;

import com.google.common.eventbus.EventBus;

public class GlobalEventBus {
	
	private static EventBus bus = new EventBus();
	
	public static synchronized EventBus getEventBus() {
		return bus;
	}
}