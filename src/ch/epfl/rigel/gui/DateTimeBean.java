package ch.epfl.rigel.gui;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * @author Paul Doucet (316442)
 *
 */
public final class DateTimeBean {

	private ObjectProperty<LocalDate> date = new SimpleObjectProperty<LocalDate>();
	private ObjectProperty<LocalTime> time = new SimpleObjectProperty<LocalTime>();
	private ObjectProperty<ZoneId> zone = new SimpleObjectProperty<ZoneId>();

	
	/**
	 * @return date property
	 */
	public ObjectProperty<LocalDate> dateProperty() {
		return date;
	}
	
	/**
	 * @return date
	 */
	public LocalDate getDate() {
		return date.getValue();
	}
	
	/**
	 * @param date : local date
	 */
	public void setDate(LocalDate date) {
		this.date.set(date);;
	}
	
	/**
	 * @return time property
	 */
	public ObjectProperty<LocalTime> timeProperty(){
		return time;
	}
	
	/**
	 * @return time
	 */
	public LocalTime getTime() {
		return time.getValue();
	}
	
	/**
	 * @param time : local time
	 */
	public void setTime(LocalTime time) {
		this.time.set(time);
	}
	
	/**
	 * @return zone property
	 */
	public ObjectProperty<ZoneId> zoneProperty() {
		return zone;
	}
	
	/**
	 * @return zone
	 */
	public ZoneId getZone() {
		return zone.getValue();
	}
	
	/**
	 * @param zone : zone id
	 */
	public void setZone(ZoneId zone) {
		this.zone.set(zone);
	}
	
	/**
	 * @return zoned date time
	 */
	public ZonedDateTime getZonedDateTime() {
		return ZonedDateTime.of(getDate(), getTime(), getZone());
	}
	
	/**
	 * @param when : zoned date time
	 */
	public void setZonedDateTime(ZonedDateTime when) {
		setDate(when.toLocalDate());
		setTime(when.toLocalTime());
		setZone(when.getZone());
	}
	
}
