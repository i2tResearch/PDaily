package co.icesi.pdaily.business.structure.businessunit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.haruk.core.testing.TestNamesGenerator;
import co.haruk.core.testing.data.IDataSet;
import co.haruk.core.testing.data.IDataSetDependent;
import co.icesi.pdaily.business.structure.businessunit.app.BusinessUnitDTO;
import co.icesi.pdaily.business.structure.businessunit.product.ProductTesting;
import co.icesi.pdaily.business.structure.businessunit.productbrand.ProductBrandTesting;
import co.icesi.pdaily.business.structure.businessunit.productgroup.ProductGroupTesting;
import co.icesi.pdaily.business.structure.businessunit.productline.ProductLineTesting;
import co.icesi.pdaily.business.structure.businessunit.zone.ZoneTesting;
import co.icesi.pdaily.testing.DataSets;
import co.icesi.pdaily.testing.PDailyTest;

@PDailyTest
@DisplayName("Business Unit tests")
public class BusinessUnitResourceIT implements IDataSetDependent {

	@Override
	public List<IDataSet> dataSets() {
		return List.of(
				DataSets.ZONE, DataSets.PRODUCTS
		);
	}

	@Test
	@DisplayName("Create a Business Unit")
	public void createBusinessUnit() {
		BusinessUnitDTO dto = BusinessUnitDTO.of(
				null,
				TestNamesGenerator.generateName(),
				TestNamesGenerator.generateCode()
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/business-unit" )
				.then()
				.statusCode( 200 )
				.body(
						"id", notNullValue(),
						"name", equalTo( dto.name ),
						"reference", equalTo( dto.reference ),
						"effectiveThreshold", notNullValue()
				);
	}

	@Test
	@DisplayName("Create a Business Unit with null reference")
	public void createBusinessUnitWithNullRef() {
		BusinessUnitDTO dto = BusinessUnitDTO.of(
				null,
				TestNamesGenerator.generateName(),
				null
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/business-unit" )
				.then()
				.statusCode( 200 )
				.body(
						"id", notNullValue(),
						"name", equalTo( dto.name ),
						"reference", nullValue(),
						"effectiveThreshold", notNullValue()
				);
	}

	@Test
	@DisplayName("Fails if duplicated reference")
	public void failsIfDuplicatedReference() {
		BusinessUnitDTO dto = BusinessUnitDTO.of(
				null,
				TestNamesGenerator.generateName(),
				"EXISTENT"
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/business-unit" )
				.then()
				.assertThat()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated reference ignoring case")
	public void failsIfDuplicatedReferenceIgnoringCase() {
		BusinessUnitDTO dto = BusinessUnitDTO.of(
				null,
				TestNamesGenerator.generateName(),
				" existent "
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/business-unit" )
				.then()
				.assertThat()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated name")
	public void failsIfDuplicatedName() {
		BusinessUnitDTO dto = BusinessUnitDTO.of(
				null,
				"EXISTENT",
				TestNamesGenerator.generateCode()
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/business-unit" )
				.then()
				.assertThat()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated name ignoring case")
	public void failsIfDuplicatedNameIgnoringCase() {
		BusinessUnitDTO dto = BusinessUnitDTO.of(
				null,
				" existent ",
				TestNamesGenerator.generateCode()
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/business-unit" )
				.then()
				.assertThat()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Finds business unit by id")
	public void findById() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "business/business-unit/{0}", BusinessUnitTesting.BUSINESS_UNIT_ID )
				.then()
				.assertThat()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( BusinessUnitTesting.BUSINESS_UNIT_ID ),
						"reference", notNullValue(),
						"name", notNullValue(),
						"effectiveThreshold", notNullValue()
				);
	}

	@Test
	@DisplayName("Fails if business id doesn't exist")
	public void failsIfIdDoesntExist() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "business/business-unit/{0}", UUID.randomUUID().toString() )
				.then()
				.assertThat()
				.statusCode( 404 );
	}

	@Test
	@DisplayName("Delete business unit")
	public void deleteBusinessUnit() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "business/business-unit/{0}", BusinessUnitTesting.BUSINESS_UNIT_TO_DELETE )
				.then()
				.assertThat()
				.statusCode( 204 );

		given().contentType( MediaType.APPLICATION_JSON )
				.get( "business/business-unit/{0}", BusinessUnitTesting.BUSINESS_UNIT_TO_DELETE )
				.then()
				.assertThat()
				.statusCode( 404 );
	}

	@Test
	@DisplayName("Fails to delete if has product lines")
	public void failsDeleteIfHasProductLines() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "business/business-unit/{0}", ProductLineTesting.BUSINESS_UNIT_FOR_DELETE_VALIDATION )
				.then()
				.assertThat()
				.statusCode( 400 )
				.body(
						"messages[0]", containsStringIgnoringCase( "tiene lineas de producto asignadas" )
				);
	}

	@Test
	@DisplayName("Fails to delete if has product groups")
	public void failsDeleteIfHasProductGroups() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "business/business-unit/{0}", ProductGroupTesting.BUSINESS_UNIT_FOR_DELETE_VALIDATION )
				.then()
				.assertThat()
				.statusCode( 400 )
				.body(
						"messages[0]", containsStringIgnoringCase( "tiene grupos de producto asignadas" )
				);
	}

	@Test
	@DisplayName("Fails to delete if has product brands")
	public void failsDeleteIfHasProductBrands() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "business/business-unit/{0}", ProductBrandTesting.BUSINESS_UNIT_FOR_DELETE_VALIDATION )
				.then()
				.assertThat()
				.statusCode( 400 )
				.body(
						"messages[0]", containsStringIgnoringCase( "tiene marcas asignadas" )
				);
	}

	@Test
	@DisplayName("Update business unit")
	public void updateBusinessUnit() {
		BusinessUnitDTO dto = BusinessUnitDTO.of(
				BusinessUnitTesting.BUSINESS_UNIT_TO_UPDATE,
				TestNamesGenerator.generateName(),
				TestNamesGenerator.generateCode()
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/business-unit" )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( BusinessUnitTesting.BUSINESS_UNIT_TO_UPDATE ),
						"name", equalTo( dto.name ),
						"reference", equalTo( dto.reference )
				);
	}

	@Test
	@DisplayName("Finds all business unit")
	public void findAll() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/business/business-unit" )
				.then()
				.statusCode( 200 )
				.body(
						"$.size", greaterThan( 0 )
				);
	}

	@Test
	@DisplayName("Fails if business unit has zones")
	void failsIfDeleteWithZones() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "/business/business-unit/{0}", ZoneTesting.BUSINESS_UNIT_DELETE_VALIDATION )
				.then()
				.statusCode( 400 )
				.body(
						"messages[0]", containsStringIgnoringCase( "tiene zonas asignadas" )
				);
	}

	@Test
	@DisplayName("Fails if business unit has products")
	void failsIfDeleteWithProducts() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "/business/business-unit/{0}", ProductTesting.BUSINESS_UNIT_FOR_DELETE_VALIDATION )
				.then()
				.statusCode( 400 )
				.body(
						"messages[0]", containsStringIgnoringCase( "tiene productos asignados" )
				);
	}

	@Test
	@DisplayName("Change effective threshold")
	public void changeEffectiveThreshold() {
		given().contentType( MediaType.APPLICATION_JSON )
				.body( 48 )
				.post( "business/business-unit/{0}/effective-threshold", BusinessUnitTesting.BUSINESS_UNIT_TO_UPDATE )
				.then()
				.assertThat()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( BusinessUnitTesting.BUSINESS_UNIT_TO_UPDATE ),
						"reference", notNullValue(),
						"name", notNullValue(),
						"effectiveThreshold", equalTo( 48 )
				);
	}

}
