package edu.haut.greenhouse.server;

import java.util.Timer;
import java.util.TimerTask;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public abstract class AfterSpringBegin extends TimerTask implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
			Timer timer = new Timer();
			timer.schedule(this, 0);
		}
	}

	

}
