package com.vmw.assignment.ng.utils;

import java.util.concurrent.TimeUnit;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

@Component
public class ThreadUtils {

	public void sleep(int seconds) {
		try {
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
			Logger.getLogger(getClass()).error("Thread interrupted");
		}
	}

}
