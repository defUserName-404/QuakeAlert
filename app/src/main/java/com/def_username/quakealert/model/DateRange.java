package com.def_username.quakealert.model;

public class DateRange {

	private final String startDate, endDate;

	public DateRange(String startDate, String endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

}
