package co.icesi.pdaily.business.structure.businessunit.productbrand;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.haruk.core.testing.TestNamesGenerator;
import co.haruk.core.testing.data.IDataSet;
import co.haruk.core.testing.data.IDataSetDependent;
import co.icesi.pdaily.business.structure.businessunit.BusinessUnitTesting;
import co.icesi.pdaily.business.structure.businessunit.productbrand.app.ProductBrandDTO;
import co.icesi.testing.DataSets;
import co.icesi.testing.SMSTest;

@SMSTest
@DisplayName("Product Brand tests")
public class ProductBrandResourceIT implements IDataSetDependent {
	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.PRODUCTS );
	}

	@Test
	@DisplayName("Finds all")
	void findAll() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/business/product-brand" )
				.then()
				.statusCode( 200 )
				.body(
						"$.size", greaterThan( 0 )
				);
	}

	@Test
	@DisplayName("Finds all for business unit")
	void findAllForSubsidiary() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/business/product-brand/for-business-unit/{0}", BusinessUnitTesting.BUSINESS_UNIT_ID )
				.then()
				.statusCode( 200 )
				.body(
						"$.size", greaterThan( 0 )
				);
	}

	@Test
	@DisplayName("Finds by id")
	void findById() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/business/product-brand/{0}", ProductBrandTesting.BRAND_ID )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( ProductBrandTesting.BRAND_ID ),
						"name", notNullValue(),
						"businessId", equalToIgnoringCase( BusinessUnitTesting.BUSINESS_UNIT_ID )
				);
	}

	@Test
	@DisplayName("Creates product brand")
	void saveEntity() {
		var dto = ProductBrandDTO.of(
				null,
				TestNamesGenerator.generateName(),
				BusinessUnitTesting.BUSINESS_UNIT_ID
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/product-brand" )
				.then()
				.statusCode( 200 )
				.body(
						"id", notNullValue(),
						"name", equalToIgnoringCase( dto.name ),
						"businessId", equalToIgnoringCase( dto.businessId )
				);
	}

	@Test
	@DisplayName("Updates product brand")
	void updatesEntity() {
		var dto = ProductBrandDTO.of(
				ProductBrandTesting.BRAND_TO_UPDATE,
				TestNamesGenerator.generateName(),
				BusinessUnitTesting.BUSINESS_UNIT_ID
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/product-brand" )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( dto.id ),
						"name", equalToIgnoringCase( dto.name ),
						"businessId", equalToIgnoringCase( dto.businessId )
				);
	}

	@Test
	@DisplayName("Fails if duplicated name")
	void failsIfDuplicatedName() {
		var dto = ProductBrandDTO.of(
				null,
				"EXISTENT",
				BusinessUnitTesting.BUSINESS_UNIT_ID
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/product-brand" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated name ignoring case")
	void failsIfDuplicatedNameIgnoringCase() {
		var dto = ProductBrandDTO.of(
				null,
				" existent ",
				BusinessUnitTesting.BUSINESS_UNIT_ID
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/product-brand" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated name on existent")
	void failsIfDuplicatedNameOnExistent() {
		var dto = ProductBrandDTO.of(
				ProductBrandTesting.BRAND_TO_UPDATE,
				"EXISTENT",
				BusinessUnitTesting.BUSINESS_UNIT_ID
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/product-brand" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Deletes product brand")
	void deleteEntity() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "/business/product-brand/{0}", ProductBrandTesting.BRAND_TO_DELETE )
				.then()
				.statusCode( 204 );

		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/business/product-brand/{0}", ProductBrandTesting.BRAND_TO_DELETE )
				.then()
				.statusCode( 404 );
	}

	@Test
	@DisplayName("Fails if brand has products")
	void failsIfDeleteWithProducts() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "/business/product-brand/{0}", ProductBrandTesting.BRAND_ID )
				.then()
				.statusCode( 400 )
				.body(
						"messages[0]", containsStringIgnoringCase( "tiene productos asignados" )
				);
	}

}
