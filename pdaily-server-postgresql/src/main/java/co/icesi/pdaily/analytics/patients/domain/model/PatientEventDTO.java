package co.icesi.pdaily.analytics.patients.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import co.icesi.pdaily.common.model.UTCDateTime;

public class PatientEventDTO {
	public String id;
	public Long date;
	public String type;
	@JsonIgnore
	private int day;

	public PatientEventDTO(String id, Long date, String type, int day) {
		this.id = id;
		this.date = date;
		this.type = type;
		this.day = day;
	}

	public static PatientEventDTO of(String params) {
		final var paramRefactor = params.split( "\\|" );
		final var date = UTCDateTime.of( paramRefactor[1] );
		return new PatientEventDTO(
				paramRefactor[0],
				date.dateAsLong(),
				paramRefactor[2],
				date.dayOfWeek()
		);
	}

	public int day() {
		return day;
	}
}
