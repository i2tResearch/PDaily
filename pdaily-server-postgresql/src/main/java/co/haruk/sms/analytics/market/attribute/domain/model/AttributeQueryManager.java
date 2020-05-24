package co.haruk.sms.analytics.market.attribute.domain.model;

import static co.haruk.core.domain.model.guards.Guards.require;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

import co.haruk.core.domain.model.persistence.IDataSourceProvider;
import co.haruk.core.domain.model.query.QueryEngine;
import co.haruk.core.domain.model.query.StdMappers;
import co.haruk.core.domain.model.text.StringOps;

@ApplicationScoped
public class AttributeQueryManager {
	protected AttributeQueryManager() {
	}

	public JsonObject getContactsMarketAttributes(List<String> attributes) {
		return executeAttributeQuery( queryForContacts(), attributes );
	}

	public JsonObject getCustomersMarketAttributes(List<String> attributes) {
		return executeAttributeQuery( queryForCustomers(), attributes );
	}

	private JsonObject executeAttributeQuery(String query, List<String> attributes) {
		require( attributes.size() > 0, "Debe indicar los atributos de mercado a consultar" );
		Map<String, Object> params = new HashMap<>();
		final String inString = StringOps.joinToString( attributes, it -> "'" + it.trim() + "'" );
		params.put( "attributes", inString );
		final var labels = labelsForSelectedAttributes( attributes );
		final var rows = executeQuery( query, params ).stream()
				.map( it -> it.split( "\\|" ) ).collect( Collectors.toList() );
		return processAttributes( rows, labels );
	}

	private List<String[]> labelsForSelectedAttributes(List<String> attributes) {
		Map<String, Object> params = new HashMap<>();
		final String inString = StringOps.joinToString( attributes, it -> "'" + it.trim() + "'" );
		params.put( "attributes", inString );
		return executeQuery( queryForAttributes(), params ).stream()
				.map( it -> it.split( "\\|" ) ).collect( Collectors.toList() );
	}

	private List<String> executeQuery(String query, Map<String, Object> params) {
		return QueryEngine.executeReadQuery(
				IDataSourceProvider.current().getDataSource(),
				query, StdMappers.STRING(), params
		);
	}

	private String queryForContacts() {
		return "SELECT subject,type,label,value FROM analytics_market_contact_attributes a" +
				" WHERE a.attr_id IN ({{attributes}})" +
				" ORDER BY subject, label;";
	}

	private String queryForCustomers() {
		return "SELECT subject,type,label,value FROM analytics_market_customer_attributes a" +
				" WHERE a.attr_id IN ({{attributes}})" +
				" ORDER BY subject, label;";
	}

	private String queryForAttributes() {
		return "SELECT DISTINCT label,datatype FROM market_attributes WHERE id IN ({{attributes}})";
	}

	private JsonObject processAttributes(List<String[]> rawRows, List<String[]> attributeLabels) {
		JsonArrayBuilder subjectsJson = Json.createArrayBuilder();
		JsonArrayBuilder jsonRows = Json.createArrayBuilder();
		ReportRowBuilder rowBuilder = ReportRowBuilder.empty();
		// Generates columns metadata
		for ( int i = 0; i < attributeLabels.size(); i++ ) {
			final var attribute = attributeLabels.get( i );
			final var type = ColumnType.ofString( attribute[1] );
			rowBuilder.addColumn( ReportColumn.of( attribute[0], i, type ) );
		}
		// Group rows by subject
		final var subjects = rawRows.stream().collect( Collectors.groupingBy( it -> it[0] ) );
		// For each subject...
		int subjectId = 0;
		for ( Map.Entry<String, List<String[]>> subject : subjects.entrySet() ) {
			final var subjectName = subject.getKey();
			final var subjectRows = subject.getValue();
			// Add subject to json metadata
			final String subjectIdString = String.valueOf( subjectId );
			final var jsonObject = Json.createObjectBuilder()
					.add( "id", subjectIdString )
					.add( "label", subjectName );
			subjectsJson.add( jsonObject );
			// Generate Json Rows for subject
			final var subjectJsonRows = rowBuilder.processRows(
					subjectIdString, // Subject id
					subjectRows, // Rows
					it -> it[2], // Column/Attribute name
					it -> it[3] // Column/Attribute value
			);
			// Add json rows
			subjectJsonRows.forEach( jsonRows::add );
			subjectId++;
		}
		return formatJsonResponse( subjectsJson, rowBuilder.columnsAsJson(), jsonRows );
	}

	private JsonObject formatJsonResponse(JsonArrayBuilder entities, JsonArrayBuilder attributes, JsonArrayBuilder rows) {
		final var metadata = Json.createObjectBuilder()
				.add( "entities", entities )
				.add( "attributes", attributes );
		return Json.createObjectBuilder()
				.add( "metadata", metadata )
				.add( "rows", rows ).build();
	}
}
