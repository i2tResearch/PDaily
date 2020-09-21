package co.icesi.pdaily.business.structure.patient.contact;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.haruk.core.testing.TestNamesGenerator;
import co.haruk.core.testing.data.IDataSet;
import co.haruk.core.testing.data.IDataSetDependent;
import co.icesi.pdaily.business.structure.address.AddressTesting;
import co.icesi.pdaily.business.structure.patient.contact.app.ContactRequestDTO;
import co.icesi.pdaily.business.structure.patient.contact.role.ContactRoleTesting;
import co.icesi.pdaily.testing.DataSets;
import co.icesi.pdaily.testing.PDailyTest;

@PDailyTest
@DisplayName("Contact tests")
public class ContactResourceIT implements IDataSetDependent {

	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.CONTACT );
	}

	@Test
	@DisplayName("Creates a minimal contact")
	void saveContact() {
		final var dto = ContactRequestDTO.of(
				null,
				TestNamesGenerator.generateName(),
				TestNamesGenerator.generateEmail(),
				null,
				null,
				null
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/patient/contact" )
				.then()
				.statusCode( 200 )
				.body( ContactReadViewMatcher.of( dto ) );
	}

	@Test
	@DisplayName("Updates a contact")
	void updateContact() {
		final var dto = ContactRequestDTO.of(
				ContactTesting.CONTACT_TO_UPDATE,
				TestNamesGenerator.generateName(),
				TestNamesGenerator.generateEmail(),
				TestNamesGenerator.generateNumericString( 7 ),
				TestNamesGenerator.generateNumericString( 10 ),
				ContactRoleTesting.ROLE_TO_UPDATE
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/patient/contact" )
				.then()
				.statusCode( 200 )
				.body( ContactReadViewMatcher.of( dto ) );
	}

	@Test
	@DisplayName("Updates a complete contact to minimal contact")
	void updateCompleteToMinimalContact() {
		final var dto = ContactRequestDTO.of(
				ContactTesting.CONTACT_TO_UPDATE,
				TestNamesGenerator.generateName(),
				TestNamesGenerator.generateEmail(),
				null,
				null,
				null
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/patient/contact" )
				.then()
				.statusCode( 200 )
				.body( ContactReadViewMatcher.of( dto ) );
	}

	@Test
	@DisplayName("Creates a full contact")
	void saveFullContact() {
		final var dto = ContactRequestDTO.of(
				null,
				TestNamesGenerator.generateName(),
				TestNamesGenerator.generateEmail(),
				TestNamesGenerator.generateNumericString( 7 ),
				TestNamesGenerator.generateNumericString( 10 ),
				ContactRoleTesting.ROLE_ID
		);
		dto.mainAddress = AddressTesting.generateRandom( null );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/patient/contact" )
				.then()
				.statusCode( 200 )
				.body( ContactReadViewMatcher.of( dto ) )
				.body(
						"id", notNullValue()
				);
	}

	@Test
	@DisplayName("Creates a full contact as sales contact")
	void saveFullContactAsSalesContact() {
		final var dto = ContactRequestDTO.of(
				null,
				TestNamesGenerator.generateName(),
				TestNamesGenerator.generateEmail(),
				TestNamesGenerator.generateNumericString( 7 ),
				TestNamesGenerator.generateNumericString( 10 ),
				ContactRoleTesting.ROLE_ID
		);
		dto.mainAddress = AddressTesting.generateRandom( null );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/patient/contact" )
				.then()
				.statusCode( 200 )
				.body( ContactReadViewMatcher.of( dto ) )
				.body(
						"id", notNullValue()
				);
	}

	@Test
	@DisplayName("Finds by id")
	void findsById() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/business/patient/contact/{0}", ContactTesting.CONTACT_ID )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( ContactTesting.CONTACT_ID ),
						"name", notNullValue(),
						"email", notNullValue(),
						"landlinePhone", notNullValue(),
						"mobilePhone", notNullValue(),
						"roleName", equalToIgnoringCase( "EXISTENT" ),
						"roleId", equalToIgnoringCase( ContactRoleTesting.ROLE_ID )
				);
	}

	@Test
	@DisplayName("Deletes by id")
	void deletesById() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "/business/patient/contact/{0}", ContactTesting.CONTACT_TO_DELETE )
				.then()
				.statusCode( 204 );
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/business/patient/contact/{0}", ContactTesting.CONTACT_TO_DELETE )
				.then()
				.statusCode( 404 );
	}
}
