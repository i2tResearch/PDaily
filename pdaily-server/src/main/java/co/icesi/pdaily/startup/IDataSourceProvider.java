package co.icesi.pdaily.startup;

import java.util.ServiceLoader;

import javax.sql.DataSource;

/**
 * @author cristhiank on 20/11/19
 **/
public interface IDataSourceProvider {
	static IDataSourceProvider current() {
		var service = ServiceLoader.load( IDataSourceProvider.class ).findFirst();
		if ( service.isEmpty() ) {
			throw new IllegalStateException( "No se encontró el proveedor de datasource" );
		}
		return service.get();
	}

	DataSource getDataSource();
}
