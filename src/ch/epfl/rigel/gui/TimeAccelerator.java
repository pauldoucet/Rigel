package ch.epfl.rigel.gui;

import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * @author Paul Doucet (316442)
 *
 */
@FunctionalInterface
public interface TimeAccelerator {
	
	public static final double NANOS_PER_MILLIS = 1000d;
	public static final double SECONDS_PER_NANOS = 1d / 1e9d;
	public static final Duration SIDERAL_DAY_DURATION = Duration.parse("PT23H56M4S");
	/**
	 * @param initialTime : initial time
	 * @param elapsedTime : elapsed time since the beginning of the animation (in nanoseconds)
	 * @return adjusted time
	 */
	ZonedDateTime adjust(ZonedDateTime initialTime, long elapsedTime);
	
	static TimeAccelerator continuous(int accelerationFactor) {
		return (ZonedDateTime initialTime, long elapsedTime) -> 
			initialTime.plusNanos(accelerationFactor * elapsedTime);
	}
	
	static TimeAccelerator discrete(int frequency, Duration step) {
		return (ZonedDateTime initialTime, long elapsedTime) ->
			initialTime.plusNanos((long)(Math.floor(frequency * elapsedTime) + step.toNanos()));
	}
}