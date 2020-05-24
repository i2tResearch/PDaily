package co.haruk.sms.common.model;

import static co.haruk.core.domain.model.guards.Guards.require;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Schedule implements Serializable {
	private static final Pattern pattern = Pattern.compile( "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$" );
	private final static SimpleDateFormat format = new SimpleDateFormat( "HH:mm" );

	@Column
	private String schedule;

	protected Schedule() {
	}

	private Schedule(String schedule) {
		setSchedule( schedule );
	}

	public static Schedule of(String schedule) {
		return new Schedule( schedule );
	}

	private void setSchedule(String schedule) {
		require( isValid( schedule ), String.format( "El formato de fecha para %s es incorrecto, se espera HH:mm", schedule ) );
		this.schedule = schedule;
	}

	public String cronExpression() throws ParseException {
		final LocalDateTime date;
		date = (LocalDateTime) format.parseObject( this.schedule );
		return String.format( "0 %s %s ? * *", date.getMinute(), date.getHour() );
	}

	public String dateExpression() {
		return schedule;
	}

	public static boolean isValid(String schedule) {
		return pattern.matcher( schedule ).matches();
	}

}
