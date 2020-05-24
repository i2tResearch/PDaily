package co.haruk.sms.analytics.market.attribute.domain.model;

import java.util.Objects;

import javax.json.JsonValue;

/**
 * @author andres2508 on 20/5/20
 **/
public final class ReportColumn implements Comparable<ReportColumn> {
	private final String label;
	private final int index;
	private final ColumnType type;

	private ReportColumn(String label, int index, ColumnType type) {
		this.label = label;
		this.index = index;
		this.type = type;
	}

	public static ReportColumn of(String label, int index, ColumnType type) {
		return new ReportColumn( label, index, type );
	}

	public String label() {
		return label;
	}

	public int index() {
		return index;
	}

	public String indexAsString() {
		return String.valueOf( index );
	}

	public ColumnType type() {
		return type;
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		ReportColumn that = (ReportColumn) o;
		return label.equals( that.label );
	}

	@Override
	public int hashCode() {
		return Objects.hash( label );
	}

	@Override
	public int compareTo(ReportColumn o) {
		return Integer.compare( index, o.index );
	}

	public JsonValue rowJsonValue(ReportRow row) {
		final var value = row.columnValue( index );
		return type.toJSON( value );
	}
}
