package co.icesi.pdaily.business.structure.businessunit.productline;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.haruk.core.testing.TestNamesGenerator;
import co.haruk.core.testing.data.IDataSet;
import co.haruk.core.testing.data.IDataSetDependent;
import co.icesi.pdaily.business.structure.businessunit.BusinessUnitTesting;
import co.icesi.pdaily.business.structure.businessunit.productline.app.ProductLineDTO;
import co.icesi.pdaily.testing.DataSets;
import co.icesi.pdaily.testing.PDailyTest;

@PDailyTest
@DisplayName("ProductLine tests")
public class ProductLineResourceIT implements IDataSetDependent {

	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.PRODUCTS );
	}

	@Test
	@DisplayName("Creates a Product Line")
	public void createProductLine() {
		ProductLineDTO dto = ProductLineDTO.of(
				null, TestNamesGenerator.generateName(),
				TestNamesGenerator.generateCode(),
				BusinessUnitTesting.BUSINESS_UNIT_ID
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/product-line" )
				.then()
				.statusCode( 200 )
				.body(
						"id", notNullValue(),
						"name", equalToIgnoringCase( dto.name ),
						"reference", equalToIgnoringCase( dto.reference ),
						"businessId", equalToIgnoringCase( dto.businessId )
				);
	}

	@Test
	@DisplayName("Creates a Product Line with null reference")
	public void createProductLineWithNullReference() {
		ProductLineDTO dto = ProductLineDTO.of(
				null,
				TestNamesGenerator.generateName(),
				null,
				BusinessUnitTesting.BUSINESS_UNIT_ID
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/product-line" )
				.then()
				.statusCode( 200 )
				.body(
						"id", notNullValue(),
						"name", equalToIgnoringCase( dto.name ),
						"reference", nullValue(),
						"businessId", equalToIgnoringCase( dto.businessId )
				);
	}

	@Test
	@DisplayName("Fails if duplicated reference")
	public void failsIfDuplicatedReference() {
		ProductLineDTO dto = ProductLineDTO.of(
				null,
				TestNamesGenerator.generateName(),
				"EXISTENT",
				BusinessUnitTesting.BUSINESS_UNIT_ID
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/product-line" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated reference ignoring case")
	public void failsIfDuplicatedReferenceIgnoreCase() {
		ProductLineDTO dto = ProductLineDTO.of(
				null,
				TestNamesGenerator.generateName(),
				" existent ",
				BusinessUnitTesting.BUSINESS_UNIT_ID
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/product-line" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated name")
	public void failsIfDuplicatedName() {
		ProductLineDTO dto = ProductLineDTO.of(
				null,
				"EXISTENT",
				TestNamesGenerator.generateCode(),
				BusinessUnitTesting.BUSINESS_UNIT_ID
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/product-line" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated name ignoring case")
	public void failsIfDuplicatedNameIgnoringCase() {
		ProductLineDTO dto = ProductLineDTO.of(
				null,
				" existent ",
				TestNamesGenerator.generateCode(),
				BusinessUnitTesting.BUSINESS_UNIT_ID
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/product-line" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Finds all product lines")
	public void findAll() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/business/product-line" )
				.then()
				.statusCode( 200 )
				.body(
						"$.size", greaterThan( 0 )
				);
	}

	@Test
	@DisplayName("Finds product line by id")
	public void findProductLineById() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "business/product-line/{0}", ProductLineTesting.PROD_LINE_ID )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( ProductLineTesting.PROD_LINE_ID ),
						"reference", notNullValue(),
						"name", notNullValue()
				);
	}

	@Test
	@DisplayName("Fails if product line id does not exist")
	public void failsIfProductLineDoesntExist() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "business/product-line/{0}", UUID.randomUUID() )
				.then()
				.statusCode( 404 )
				.body(
						"messages[0]", equalTo( "No se encontró el registro" )
				);
	}

	@Test
	@DisplayName("Deletes product line")
	public void deleteProductLine() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "business/product-line/{0}", ProductLineTesting.PROD_LINE_TO_DELETE )
				.then()
				.statusCode( 204 );

		given().contentType( MediaType.APPLICATION_JSON )
				.get( "business/product-line/{0}", ProductLineTesting.PROD_LINE_TO_DELETE )
				.then()
				.statusCode( 404 )
				.body(
						"messages[0]", equalTo( "No se encontró el registro" )
				);
	}

	@Test
	@DisplayName("Update product line")
	public void updateProductLine() {
		ProductLineDTO dto = ProductLineDTO.of(
				ProductLineTesting.PROD_LINE_TO_UPDATE,
				TestNamesGenerator.generateName(),
				TestNamesGenerator.generateCode(),
				BusinessUnitTesting.BUSINESS_UNIT_ID
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/product-line" )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( ProductLineTesting.PROD_LINE_TO_UPDATE ),
						"name", equalTo( dto.name ),
						"reference", equalToIgnoringCase( dto.reference ),
						"businessId", equalToIgnoringCase( dto.businessId )
				);
	}

	@Test
	@DisplayName("Finds all products line for an business unit")
	void findAllByBusinessUnit() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/business/product-line/for-business-unit/{0}", BusinessUnitTesting.BUSINESS_UNIT_ID )
				.then()
				.statusCode( 200 )
				.body(
						"$.size", greaterThan( 0 )
				);
	}

	@Test
	@DisplayName("Fails if line has products")
	void failsIfDeleteWithProducts() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "/business/product-line/{0}", ProductLineTesting.PROD_LINE_ID )
				.then()
				.statusCode( 400 )
				.body(
						"messages[0]", containsStringIgnoringCase( "tiene productos asignados" )
				);
	}
}
