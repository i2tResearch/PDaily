package co.haruk.sms.analytics.market.attribute.domain.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cristhiank on 20/5/20
 **/
public final class ReportRow {
	private final Map<Integer, String> columns = new HashMap<>();

	private ReportRow(Collection<ReportColumn> cols) {
		cols.forEach( it -> this.addColumn( it.index() ) );
	}

	public static ReportRow newRow(Collection<ReportColumn> cols) {
		return new ReportRow( cols );
	}

	// Creates empty column
	public void addColumn(int columnIndex) {
		columns.put( columnIndex, null );
	}

	public void setValue(int columnIndex, String value) {
		columns.put( columnIndex, value );
	}

	public String columnValue(int index) {
		return columns.get( index );
	}
}