package co.haruk.sms.analytics.market.attribute.domain.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import co.haruk.core.StreamUtils;

/**
 * @author andres2508 on 20/5/20
 **/
public class ReportRowBuilder {
	private final SortedSet<ReportColumn> columns = new TreeSet<>();
	private final Map<Integer, ReportRow> rows = new HashMap<>();

	private ReportRowBuilder() {
	}

	public static ReportRowBuilder empty() {
		return new ReportRowBuilder();
	}

	private void addColumnValues(ReportColumn column, List<String> attrValues) {
		int index = 0;
		// Add each value to and existing or new row
		for ( String value : attrValues ) {
			final var row = getRowOrCreate( index++ );
			row.setValue( column.index(), value );
		}
	}

	private ReportRow getRowOrCreate(int index) {
		return rows.computeIfAbsent( index, k -> ReportRow.newRow( columns ) );
	}

	public void addColumn(ReportColumn column) {
		columns.add( column );
	}

	public SortedSet<ReportColumn> columns() {
		return columns;
	}

	public List<JsonObjectBuilder> rowsAsJson(String subjectId) {
		final List<JsonObjectBuilder> result = new ArrayList<>();
		for ( ReportRow value : rows.values() ) {
			final var row = Json.createObjectBuilder();
			row.add( "Sujeto", subjectId );
			for ( ReportColumn column : columns ) {
				final JsonValue val = column.rowJsonValue( value );
				row.add( column.indexAsString(), val );
			}
			result.add( row );
		}
		return result;
	}

	public JsonArrayBuilder columnsAsJson() {
		final var array = Json.createArrayBuilder();
		for ( ReportColumn column : columns ) {
			array.add(
					Json.createObjectBuilder()
							.add( "id", column.indexAsString() )
							.add( "label", column.label() )
			);
		}
		return array;
	}

	public List<JsonObjectBuilder> processRows(String subjectId, List<String[]> rows,
			Function<String[], String> columnMapper,
			Function<String[], String> valueMapper) {
		// Each attribute is a column
		for ( ReportColumn column : columns() ) {
			// Filter attribute rows
			final var colValues = StreamUtils.filter(
					rows,
					it -> columnMapper.apply( it ).equalsIgnoreCase( column.label() )
			).stream().map( valueMapper ).collect( Collectors.toList() );
			// Generate rows for attribute values
			addColumnValues( column, colValues );
		}
		final var result = rowsAsJson( subjectId );
		rows.clear();
		return result;
	}
}
