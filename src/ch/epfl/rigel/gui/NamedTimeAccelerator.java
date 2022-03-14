package ch.epfl.rigel.gui;

import java.time.Duration;

/**
 * @author Paul Doucet (316442)
 *
 */
public enum NamedTimeAccelerator {
	
	
	TIMES_1("1×", TimeAccelerator.continuous(1)),
	TIMES_30("30×", TimeAccelerator.continuous(30)),
	TIMES_300("300×", TimeAccelerator.continuous(300)),
	TIMES_3000("3000×", TimeAccelerator.continuous(3000)),
	DAY("jour", TimeAccelerator.discrete(60, Duration.ofHours(24))),
	SIDERAL_DAY("jour sidéral", TimeAccelerator.discrete(60, TimeAccelerator.SIDERAL_DAY_DURATION));

	private String name;
	private TimeAccelerator accelerator;
	
	private NamedTimeAccelerator(String name, TimeAccelerator accelerator) {
		this.name = name;
		this.accelerator = accelerator;
	}
	
	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return accelerator
	 */
	public TimeAccelerator getAccelerator() {
		return accelerator;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
