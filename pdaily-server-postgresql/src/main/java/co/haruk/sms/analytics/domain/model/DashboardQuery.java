package co.haruk.sms.analytics.domain.model;

import java.util.List;
import java.util.Map;

import javax.json.JsonArray;
import javax.json.JsonObject;

import co.haruk.core.domain.model.persistence.IDataSourceProvider;
import co.haruk.core.domain.model.query.QueryEngine;
import co.haruk.core.domain.model.query.StdMappers;

/**
 * @author andres2508 on 23/5/20
 **/
public final class DashboardQuery {
	final String name;
	final String file;
	private String contentCache;

	private DashboardQuery(String name, String file) {
		this.name = name;
		this.file = file;
	}

	public static DashboardQuery of(String name, String file) {
		return new DashboardQuery( name, file );
	}

	public String file() {
		return file;
	}

	private String queryString() {
		if ( contentCache != null ) {
			return contentCache;
		}
		contentCache = QueryEngine.loadQueryFromResourceFile( file );
		return contentCache;
	}

	public JsonArray readAll(Map<String, Object> params) {
		return QueryEngine.executeReadQuery(
				IDataSourceProvider.current().getDataSource(),
				queryString(),
				StdMappers.JSON(),
				params
		);
	}

	public List<String> readAllAsStringArray(Map<String, Object> params) {
		return QueryEngine.executeReadQuery(
				IDataSourceProvider.current().getDataSource(),
				queryString(),
				StdMappers.STRING(),
				params
		);
	}

	public JsonObject readAsSingleRow(Map<String, Object> params) {
		final var values = readAll( params );
		if ( values.isEmpty() ) {
			return JsonObject.EMPTY_JSON_OBJECT;
		}
		return values.getJsonObject( 0 );
	}
}
