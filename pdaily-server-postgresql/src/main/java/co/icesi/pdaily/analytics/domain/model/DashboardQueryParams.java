package co.icesi.pdaily.analytics.domain.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author andres2508 on 23/5/20
 **/
public class DashboardQueryParams {
	private final Map<String, Object> params = new HashMap<>();

	public static DashboardQueryParams of(String name, String value) {
		return new DashboardQueryParams().and( name, value );
	}

	public DashboardQueryParams and(String key, String value) {
		params.put( key, value );
		return this;
	}

	public Map<String, Object> build() {
		return params;
	}
}
