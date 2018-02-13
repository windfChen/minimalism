package com.windf.core.frame;

import java.util.ArrayList;
import java.util.List;

public class InitializationControler {

	private static InitializationControler initializationControler = new InitializationControler();

	public static InitializationControler getInstance() {
		return initializationControler;
	}

	private List<Initializationable> initializationables = new ArrayList<Initializationable>();

	private InitializationControler() {

	}

	public void addInitializationable(Initializationable initializationable) {
		initializationables.add(initializationable);
	}
	
	public void doInit() {
		for (Initializationable initializationable : initializationables) {
			initializationable.init();
		}
	}

}
