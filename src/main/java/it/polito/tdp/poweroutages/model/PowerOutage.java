package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class PowerOutage {

	

	Integer nerc_id;
	Integer customers_affected;
	LocalDateTime start;
	LocalDateTime end;
	Integer year;
	Integer duration;
	
	public PowerOutage(Integer nerc_id, Integer customers_affected, LocalDateTime start, LocalDateTime end) {
		
		this.nerc_id = nerc_id;
		this.customers_affected = customers_affected;
		this.start = start;
		this.end = end;
		year= start.getYear();
		duration= (int) start.until(end, ChronoUnit.MINUTES);
	}

	public Integer getNerc_id() {
		return nerc_id;
	}

	public Integer getCustomer_affected() {
		return customers_affected;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public Integer getYear() {
		return year;
	}

	public Integer getDuration() {
		return duration;
	}

	@Override
	public String toString() {
		return "PowerOutage [nerc_id=" + nerc_id + ", customers_affected=" + customers_affected + ", start=" + start
				+ ", end=" + end + ", year=" + year + ", duration=" + duration + "]"+"\n";
	}
	
	
	
}

