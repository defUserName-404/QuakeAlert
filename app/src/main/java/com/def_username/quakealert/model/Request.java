package com.def_username.quakealert.model;

public class Request {

	private final  Coordinate coordinate;
	private final MagnitudeRange magnitudeRange;
	private final DateRange dateRange;
	private final String sortBy;

	public Request(Coordinate coordinate, MagnitudeRange magnitudeRange, DateRange dateRange, String sortBy) {
		this.coordinate = coordinate;
		this.magnitudeRange = magnitudeRange;
		this.dateRange = dateRange;
		this.sortBy = sortBy;
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}

	public MagnitudeRange getMagnitudeRange() {
		return magnitudeRange;
	}

	public DateRange getDateRange() {
		return dateRange;
	}

	public String getSortBy() {
		return sortBy;
	}
}