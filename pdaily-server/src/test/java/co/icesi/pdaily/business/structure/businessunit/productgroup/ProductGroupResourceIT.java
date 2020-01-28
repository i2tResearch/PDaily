package co.icesi.pdaily.business.structure.businessunit.productgroup;

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
import co.icesi.pdaily.business.structure.businessunit.productgroup.app.ProductGroupDTO;
import co.icesi.testing.DataSets;
import co.icesi.testing.SMSTest;

@SMSTest
@DisplayName("Product Group tests")
public class ProductGroupResourceIT implements IDataSetDependent {

	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.PRODUCTS );
	}

	@Test
	@DisplayName("Finds all")
	void findAll() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/business/product-group" )
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
				.get( "/business/product-group/for-business-unit/{0}", BusinessUnitTesting.BUSINESS_UNIT_ID )
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
				.get( "/business/product-group/{0}", ProductGroupTesting.PROD_GROUP_ID )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( ProductGroupTesting.PROD_GROUP_ID ),
						"reference", notNullValue(),
						"name", notNullValue(),
						"businessId", equalToIgnoringCase( BusinessUnitTesting.BUSINESS_UNIT_ID )
				);
	}

	@Test
	@DisplayName("Creates product group")
	void saveEntity() {
		var dto = ProductGroupDTO.of(
				null,
				TestNamesGenerator.generateName(),
				TestNamesGenerator.generateCode(),
				BusinessUnitTesting.BUSINESS_UNIT_ID
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/product-group" )
				.then()
				.statusCode( 200 )
				.body(
						"id", notNullValue(),
						"reference", equalToIgnoringCase( dto.reference ),
						"name", equalToIgnoringCase( dto.name ),
						"businessId", equalToIgnoringCase( dto.businessId )
				);
	}

	@Test
	@DisplayName("Creates product group with null reference")
	void saveEntityWithNullReference() {
		var dto = ProductGroupDTO.of(
				null,
				TestNamesGenerator.generateName(),
				null,
				BusinessUnitTesting.BUSINESS_UNIT_ID
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/product-group" )
				.then()
				.statusCode( 200 )
				.body(
						"id", notNullValue(),
						"reference", nullValue(),
						"name", equalToIgnoringCase( dto.name ),
						"businessId", equalToIgnoringCase( dto.businessId )
				);
	}

	@Test
	@DisplayName("Updates product group")
	void updatesEntity() {
		var dto = ProductGroupDTO.of(
				ProductGroupTesting.PROD_GROUP_TO_UPDATE,
				TestNamesGenerator.generateName(),
				TestNamesGenerator.generateCode(),
				BusinessUnitTesting.BUSINESS_UNIT_ID
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/product-group" )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( dto.id ),
						"reference", equalToIgnoringCase( dto.reference ),
						"name", equalToIgnoringCase( dto.name ),
						"businessId", equalToIgnoringCase( dto.businessId )
				);
	}

	@Test
	@DisplayName("Fails if duplicated reference")
	void failsIfDuplicatedReference() {
		var dto = ProductGroupDTO.of(
				null,
				TestNamesGenerator.generateName(),
				"EXISTENT",
				BusinessUnitTesting.BUSINESS_UNIT_ID
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/product-group" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated reference ignoring case")
	void failsIfDuplicatedReferenceIgnoringCase() {
		var dto = ProductGroupDTO.of(
				null,
				TestNamesGenerator.generateName(),
				" existent ",
				BusinessUnitTesting.BUSINESS_UNIT_ID
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/product-group" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated reference on existent")
	void failsIfDuplicatedReferenceOnExistent() {
		var dto = ProductGroupDTO.of(
				ProductGroupTesting.PROD_GROUP_TO_UPDATE,
				TestNamesGenerator.generateName(),
				" existent ",
				BusinessUnitTesting.BUSINESS_UNIT_ID
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/product-group" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated name")
	void failsIfDuplicatedName() {
		var dto = ProductGroupDTO.of(
				null,
				"EXISTENT",
				TestNamesGenerator.generateCode(),
				BusinessUnitTesting.BUSINESS_UNIT_ID
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/product-group" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated name ignoring case")
	void failsIfDuplicatedNameIgnoringCase() {
		var dto = ProductGroupDTO.of(
				null,
				" existent ",
				TestNamesGenerator.generateCode(),
				BusinessUnitTesting.BUSINESS_UNIT_ID
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/product-group" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated name on existent")
	void failsIfDuplicatedNameOnExistent() {
		var dto = ProductGroupDTO.of(
				ProductGroupTesting.PROD_GROUP_TO_UPDATE,
				"EXISTENT",
				TestNamesGenerator.generateCode(),
				BusinessUnitTesting.BUSINESS_UNIT_ID
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/product-group" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Deletes product group")
	void deleteEntity() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "/business/product-group/{0}", ProductGroupTesting.PROD_GROUP_TO_DELETE )
				.then()
				.statusCode( 204 );

		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/business/product-group/{0}", ProductGroupTesting.PROD_GROUP_TO_DELETE )
				.then()
				.statusCode( 404 );
	}

	@Test
	@DisplayName("Fails if group has products")
	void failsIfDeleteWithProducts() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "/business/product-group/{0}", ProductGroupTesting.PROD_GROUP_ID )
				.then()
				.statusCode( 400 )
				.body(
						"messages[0]", containsStringIgnoringCase( "tiene productos asignados" )
				);
	}
}
