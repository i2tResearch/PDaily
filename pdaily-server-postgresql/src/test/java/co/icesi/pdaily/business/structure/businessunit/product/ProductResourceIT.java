package co.icesi.pdaily.business.structure.businessunit.product;

import static io.restassured.RestAssured.given;
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
import co.icesi.pdaily.business.structure.businessunit.product.app.ProductRequestDTO;
import co.icesi.pdaily.business.structure.businessunit.productbrand.ProductBrandTesting;
import co.icesi.pdaily.business.structure.businessunit.productgroup.ProductGroupTesting;
import co.icesi.pdaily.business.structure.businessunit.productline.ProductLineTesting;
import co.icesi.pdaily.testing.DataSets;
import co.icesi.pdaily.testing.PDailyTest;

@PDailyTest
@DisplayName("Product tests")
public class ProductResourceIT implements IDataSetDependent {
	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.PRODUCTS );
	}

	@Test
	@DisplayName("Finds all")
	void findAll() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/business/product" )
				.then()
				.statusCode( 200 )
				.body(
						"$.size", greaterThan( 0 )
				);
	}

	@Test
	@DisplayName("Finds all by business unit id")
	void findAllByBUnit() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/business/product/for-business-unit/{0}", BusinessUnitTesting.BUSINESS_UNIT_ID )
				.then()
				.statusCode( 200 )
				.body(
						"$.size", greaterThan( 0 )
				);
	}

	@Test
	@DisplayName("Find by id")
	void findById() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/business/product/{0}", ProductTesting.PRODUCT_ID )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( ProductTesting.PRODUCT_ID ),
						"name", notNullValue(),
						"reference", notNullValue(),
						"businessName", notNullValue(),
						"businessId", equalToIgnoringCase( BusinessUnitTesting.BUSINESS_UNIT_ID ),
						"brandName", notNullValue(),
						"brandId", equalToIgnoringCase( ProductBrandTesting.BRAND_ID ),
						"lineName", notNullValue(),
						"lineId", equalToIgnoringCase( ProductLineTesting.PROD_LINE_ID ),
						"groupName", notNullValue(),
						"groupId", equalToIgnoringCase( ProductGroupTesting.PROD_GROUP_ID )
				);
	}

	@Test
	@DisplayName("Fails if product id does not exist")
	void failsIfProductLineDoesntExist() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "business/product/{0}", UUID.randomUUID() )
				.then()
				.statusCode( 404 )
				.body(
						"messages[0]", equalTo( "No se encontró el registro" )
				);
	}

	@Test
	@DisplayName("Create product")
	void createProduct() {
		var dto = ProductRequestDTO.of(
				null,
				TestNamesGenerator.generateName(),
				TestNamesGenerator.generateCode(),
				BusinessUnitTesting.BUSINESS_UNIT_ID,
				ProductBrandTesting.BRAND_ID,
				ProductLineTesting.PROD_LINE_ID,
				ProductGroupTesting.PROD_GROUP_ID
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/product" )
				.then()
				.statusCode( 200 )
				.body(
						"id", notNullValue(),
						"name", notNullValue(),
						"reference", notNullValue(),
						"businessId", equalToIgnoringCase( BusinessUnitTesting.BUSINESS_UNIT_ID ),
						"brandId", equalToIgnoringCase( ProductBrandTesting.BRAND_ID ),
						"lineId", equalToIgnoringCase( ProductLineTesting.PROD_LINE_ID ),
						"groupId", equalToIgnoringCase( ProductGroupTesting.PROD_GROUP_ID )
				);
	}

	@Test
	@DisplayName("Create product with null values")
	void createProductNullValues() {
		var dto = ProductRequestDTO.of(
				null,
				TestNamesGenerator.generateName(),
				null,
				BusinessUnitTesting.BUSINESS_UNIT_ID,
				null,
				null,
				null
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/product" )
				.then()
				.statusCode( 200 )
				.body(
						"id", notNullValue(),
						"name", notNullValue(),
						"reference", nullValue(),
						"businessId", equalToIgnoringCase( BusinessUnitTesting.BUSINESS_UNIT_ID ),
						"brandId", nullValue(),
						"lineId", nullValue(),
						"groupId", nullValue()
				);
	}

	@Test
	@DisplayName("Fails if duplicated reference")
	void failsIfDuplicatedReference() {
		var dto = ProductRequestDTO.of(
				null,
				TestNamesGenerator.generateName(),
				"EXISTENT",
				BusinessUnitTesting.BUSINESS_UNIT_ID,
				ProductBrandTesting.BRAND_ID,
				ProductLineTesting.PROD_LINE_ID,
				ProductGroupTesting.PROD_GROUP_ID
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/product" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated reference ignoring case")
	void failsIfDuplicatedReferenceIgnoreCase() {
		var dto = ProductRequestDTO.of(
				null,
				TestNamesGenerator.generateName(),
				" existent ",
				BusinessUnitTesting.BUSINESS_UNIT_ID,
				ProductBrandTesting.BRAND_ID,
				ProductLineTesting.PROD_LINE_ID,
				ProductGroupTesting.PROD_GROUP_ID
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/product" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated name")
	void failsIfDuplicatedName() {
		var dto = ProductRequestDTO.of(
				null,
				"EXISTENT",
				TestNamesGenerator.generateCode(),
				BusinessUnitTesting.BUSINESS_UNIT_ID,
				ProductBrandTesting.BRAND_ID,
				ProductLineTesting.PROD_LINE_ID,
				ProductGroupTesting.PROD_GROUP_ID
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/product" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated name ignoring case")
	void failsIfDuplicatedNameIgnoringCase() {
		var dto = ProductRequestDTO.of(
				null,
				" existent ",
				TestNamesGenerator.generateCode(),
				BusinessUnitTesting.BUSINESS_UNIT_ID,
				ProductBrandTesting.BRAND_ID,
				ProductLineTesting.PROD_LINE_ID,
				ProductGroupTesting.PROD_GROUP_ID
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/product" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Deletes product")
	public void deleteProduct() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "business/product/{0}", ProductTesting.PRODUCT_TO_DELETE )
				.then()
				.statusCode( 204 );

		given().contentType( MediaType.APPLICATION_JSON )
				.get( "business/product/{0}", ProductTesting.PRODUCT_TO_DELETE )
				.then()
				.statusCode( 404 )
				.body(
						"messages[0]", equalTo( "No se encontró el registro" )
				);
	}

	@Test
	@DisplayName("Update product")
	public void updateProductLine() {
		var dto = ProductRequestDTO.of(
				ProductTesting.PRODUCT_TO_UPDATE,
				TestNamesGenerator.generateName(),
				TestNamesGenerator.generateCode(),
				BusinessUnitTesting.BUSINESS_UNIT_ID,
				ProductBrandTesting.BRAND_TO_UPDATE,
				ProductLineTesting.PROD_LINE_TO_UPDATE,
				ProductGroupTesting.PROD_GROUP_TO_UPDATE
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/product" )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( ProductTesting.PRODUCT_TO_UPDATE ),
						"name", equalToIgnoringCase( dto.name ),
						"reference", equalToIgnoringCase( dto.reference ),
						"businessId", equalToIgnoringCase( BusinessUnitTesting.BUSINESS_UNIT_ID ),
						"brandId", equalToIgnoringCase( ProductBrandTesting.BRAND_TO_UPDATE ),
						"lineId", equalToIgnoringCase( ProductLineTesting.PROD_LINE_TO_UPDATE ),
						"groupId", equalToIgnoringCase( ProductGroupTesting.PROD_GROUP_TO_UPDATE )
				);
	}
}
