package co.icesi.pdaily.analytics.patients.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import co.icesi.pdaily.common.model.UTCDateTime;

public class PatientAnimicDTO {
	public String id;
	public Long date;
	public int intensity;
	@JsonIgnore
	private int day;

	public PatientAnimicDTO(String id, Long date, int intensity, int day) {
		this.id = id;
		this.date = date;
		this.intensity = intensity;
		this.day = day;
	}

	public static PatientAnimicDTO of(String params) {
		final var paramRefactor = params.split( "\\|" );
		final var date = UTCDateTime.of( paramRefactor[1] );
		return new PatientAnimicDTO(
				paramRefactor[0],
				date.dateAsLong(),
				Integer.parseInt( paramRefactor[2] ),
				date.dayOfWeek()
		);
	}

	public int day() {
		return day;
	}
}
