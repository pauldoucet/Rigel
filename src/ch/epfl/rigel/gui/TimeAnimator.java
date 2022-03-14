package ch.epfl.rigel.gui;

import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public final class TimeAnimator extends AnimationTimer {
	
	private DateTimeBean instant;
	private TimeAccelerator accelerator;
	private BooleanProperty running = new SimpleBooleanProperty(false);
	private long last;
	
	/**
	 * @param instant : initial time
	 */
	public TimeAnimator(DateTimeBean instant) {
		this.instant = instant;
		last = 0;
	}

	/**
	 * @return if animation running
	 */
	public boolean getRunning() {
		return running.getValue();
	}
	
	/**
	 * @param running : set running true or false
	 */
	private void setRunning(boolean running) {
		this.running.set(running);
	}
	
	/**
	 * @return
	 */
	public ReadOnlyBooleanProperty runningProperty(){
		return running;
	}
	
	/**
	 * @param accelerator
	 */
	public void setAccelerator(TimeAccelerator accelerator) {
		this.accelerator = accelerator;
	}
	
	@Override
	public void handle(long now) {
		long elapsedTime = 0l;
		if(last != 0) {
			elapsedTime = now - last;
		}
		last = now;
		instant.setZonedDateTime(accelerator.adjust(instant.getZonedDateTime(), elapsedTime));
	}
	
	@Override
	public void start() {
		super.start();
		this.setRunning(true);
	}
	
	@Override
	public void stop() {
		super.stop();
		this.setRunning(false);
		last = 0;
	}
	
	
}
