package co.haruk.sms.market.measurement.attribute.value;

import static co.haruk.core.testing.TestNamesGenerator.generateName;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.haruk.core.testing.data.IDataSet;
import co.haruk.core.testing.data.IDataSetDependent;
import co.haruk.sms.business.structure.contact.ContactTesting;
import co.haruk.sms.market.measurement.attribute.MarketAttributeTesting;
import co.haruk.sms.market.measurement.attribute.picklist.PickListTesting;
import co.haruk.sms.market.measurement.attribute.value.app.AttributeValueDTO;
import co.haruk.testing.DataSets;
import co.haruk.testing.SMSTest;

@SMSTest
@DisplayName("Attribute value test")
public class AttributeValueResourceIT implements IDataSetDependent {

	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.ATTRIBUTE_VALUES );
	}

	@Test
	@DisplayName("Finds by subjectId")
	void findBySubjectId() {
		given().get( "/market/attribute/value/for-subject/{0}", ContactTesting.CONTACT_ID )
				.then()
				.statusCode( 200 )
				.body(
						"$.size", greaterThan( 0 )
				);
	}

	@Test
	@DisplayName("Find attribute value")
	void findAttributeValue() {
		given().get( "/market/attribute/value/{0}", AttributeValueTesting.ATTRIBUTE_VALUE )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( AttributeValueTesting.ATTRIBUTE_VALUE ),
						"attributeId", equalToIgnoringCase( MarketAttributeTesting.MARKET_ATTRIBUTE_ID ),
						"subjectId", equalToIgnoringCase( ContactTesting.CONTACT_ID ),
						"value", equalToIgnoringCase( "\"EXISTENT\"" )
				);
	}

	@Test
	@DisplayName("Save attribute value type String")
	void saveStringAttributeValue() {
		final var dto = AttributeValueDTO.of(
				null, MarketAttributeTesting.ATTRIBUTE_TO_VALUE_CREATE,
				"\"" + generateName() + "\"", ContactTesting.CONTACT_ID
		);

		given().body( dto )
				.post( "/market/attribute/value" )
				.then()
				.statusCode( 200 )
				.body(
						"attributeId", equalToIgnoringCase( dto.attributeId ),
						"id", notNullValue(),
						"subjectId", equalToIgnoringCase( dto.subjectId ),
						"value", equalToIgnoringCase( dto.value )
				);
	}

	@Test
	@DisplayName("Fail save attribute value with same MarketAttribute")
	void failStringAttributeValue() {
		final var dto = AttributeValueDTO.of(
				null, MarketAttributeTesting.ATTRIBUTE_TO_VALUE_UPDATE,
				"\"" + generateName() + "\"", ContactTesting.CONTACT_ID
		);

		given().body( dto )
				.post( "/market/attribute/value" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fail save null attribute value")
	void failNullAttributeValue() {
		final var dto = AttributeValueDTO.of(
				null, MarketAttributeTesting.ATTRIBUTE_TO_VALUE_NULL_CREATE,
				null, ContactTesting.CONTACT_ID
		);

		given().body( dto )
				.post( "/market/attribute/value" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fail save empty attribute value")
	void failEmptyAttributeValue() {
		final var dto = AttributeValueDTO.of(
				null, MarketAttributeTesting.ATTRIBUTE_TO_VALUE_NULL_CREATE,
				"", ContactTesting.CONTACT_ID
		);

		given().body( dto )
				.post( "/market/attribute/value" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fail save empty two case attribute value")
	void failEmptyTwoAttributeValue() {
		final var dto = AttributeValueDTO.of(
				null, MarketAttributeTesting.ATTRIBUTE_TO_VALUE_NULL_CREATE,
				" ", ContactTesting.CONTACT_ID
		);

		given().body( dto )
				.post( "/market/attribute/value" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Save attribute value type Int")
	void saveIntAttributeValue() {
		final var dto = AttributeValueDTO.of(
				null, MarketAttributeTesting.ATTRIBUTE_TO_VALUE_INT,
				"10", ContactTesting.CONTACT_ID
		);

		given().body( dto )
				.post( "/market/attribute/value" )
				.then()
				.statusCode( 200 )
				.body(
						"attributeId", equalToIgnoringCase( dto.attributeId ),
						"id", notNullValue(),
						"subjectId", equalToIgnoringCase( dto.subjectId ),
						"value", equalToIgnoringCase( dto.value )
				);
	}

	@Test
	@DisplayName("fail save attribute value type Int but it is String")
	void failIntAttributeValue() {
		final var dto = AttributeValueDTO.of(
				null, MarketAttributeTesting.ATTRIBUTE_TO_VALUE_INT,
				"10L", ContactTesting.CONTACT_ID
		);

		given().body( dto )
				.post( "/market/attribute/value" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Save attribute value type List Multiple")
	void saveListAttributeMultipleValue() {
		final var listIDs = List.of(
				PickListTesting.MULTIPLE_LIST_TO_VALUE_ATTRIBUTE_A,
				PickListTesting.MULTIPLE_LIST_TO_VALUE_ATTRIBUTE_B
		);
		final var dto = AttributeValueDTO.of(
				null, MarketAttributeTesting.ATTRIBUTE_TO_VALUE_MULTIPLE_LIST,
				listIDs.toString(), ContactTesting.CONTACT_ID
		);

		given().body( dto )
				.post( "/market/attribute/value" )
				.then()
				.statusCode( 200 )
				.body(
						"attributeId", equalToIgnoringCase( dto.attributeId ),
						"id", notNullValue(),
						"subjectId", equalToIgnoringCase( dto.subjectId ),
						"value", equalToIgnoringCase( dto.value )
				);
	}

	@Test
	@DisplayName("Fail save attribute value type Multiple List by listIDs not existent")
	void failListAttributeMultipleValue() {
		final var listIDs = List.of(
				PickListTesting.MULTIPLE_LIST_TO_VALUE_ATTRIBUTE_A,
				"\"" + UUID.randomUUID() + "\""
		);
		final var dto = AttributeValueDTO.of(
				null, MarketAttributeTesting.ATTRIBUTE_TO_VALUE_MULTIPLE_LIST,
				listIDs.toString(), ContactTesting.CONTACT_ID
		);

		given().body( dto )
				.post( "/market/attribute/value" )
				.then()
				.statusCode( 404 );
	}

	@Test
	@DisplayName("Fail save attribute value type Multiple List by body is not a list")
	void failListAttributeMultipleValueWithOnlyOne() {
		final var dto = AttributeValueDTO.of(
				null, MarketAttributeTesting.ATTRIBUTE_TO_VALUE_MULTIPLE_LIST,
				PickListTesting.MULTIPLE_LIST_TO_VALUE_ATTRIBUTE_A, ContactTesting.CONTACT_ID
		);

		given().body( dto )
				.post( "/market/attribute/value" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fail save attribute value type Multiple List by empty list")
	void failEmptyListAttributeMultipleValue() {
		final var listIDs = List.of();
		final var dto = AttributeValueDTO.of(
				null, MarketAttributeTesting.ATTRIBUTE_TO_VALUE_MULTIPLE_LIST,
				listIDs.toString(), ContactTesting.CONTACT_ID
		);

		given().body( dto )
				.post( "/market/attribute/value" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fail save attribute value type Multiple List by empty values list")
	void failEmptyValuesListAttributeMultipleValue() {
		final var listIDs = List.of( "", "" );
		final var dto = AttributeValueDTO.of(
				null, MarketAttributeTesting.ATTRIBUTE_TO_VALUE_MULTIPLE_LIST,
				listIDs.toString(), ContactTesting.CONTACT_ID
		);

		given().body( dto )
				.post( "/market/attribute/value" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Save attribute value type List Single")
	void saveListAttributeSingleValue() {
		final var dto = AttributeValueDTO.of(
				null, MarketAttributeTesting.ATTRIBUTE_TO_VALUE_SINGLE_LIST,
				PickListTesting.SINGLE_LIST_TO_VALUE_ATTRIBUTE_A, ContactTesting.CONTACT_ID
		);

		given().body( dto )
				.post( "/market/attribute/value" )
				.then()
				.statusCode( 200 )
				.body(
						"attributeId", equalToIgnoringCase( dto.attributeId ),
						"id", notNullValue(),
						"subjectId", equalToIgnoringCase( dto.subjectId ),
						"value", equalToIgnoringCase( dto.value )
				);
	}

	@Test
	@DisplayName("Fail save attribute value type Single List by body is a list")
	void failListAttributeSingleValue() {
		final var listIDs = List.of(
				PickListTesting.SINGLE_LIST_TO_VALUE_ATTRIBUTE_A,
				PickListTesting.SINGLE_LIST_TO_VALUE_ATTRIBUTE_B
		);
		final var dto = AttributeValueDTO.of(
				null, MarketAttributeTesting.ATTRIBUTE_TO_VALUE_SINGLE_LIST,
				listIDs.toString(), ContactTesting.CONTACT_ID
		);

		given().body( dto )
				.post( "/market/attribute/value" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Save boolean attribute value")
	void saveBooleanAttribute() {
		final var dto = AttributeValueDTO.of(
				null, MarketAttributeTesting.ATTRIBUTE_TO_VALUE_BOOLEAN,
				"true", ContactTesting.CONTACT_ID
		);

		given().body( dto )
				.post( "/market/attribute/value" )
				.then()
				.statusCode( 200 )
				.body(
						"attributeId", equalToIgnoringCase( dto.attributeId ),
						"id", notNullValue(),
						"subjectId", equalToIgnoringCase( dto.subjectId ),
						"value", equalToIgnoringCase( dto.value.toUpperCase() )
				);
	}

	@Test
	@DisplayName("Equals ignore case in save boolean attribute value")
	void ignoreCaseSaveBooleanAttribute() {
		final var dto = AttributeValueDTO.of(
				null, MarketAttributeTesting.ATTRIBUTE_TO_VALUE_BOOLEAN_IGNORE_CASE,
				"tRuE", ContactTesting.CONTACT_ID
		);

		given().body( dto )
				.post( "/market/attribute/value" )
				.then()
				.statusCode( 200 )
				.body(
						"attributeId", equalToIgnoringCase( dto.attributeId ),
						"id", notNullValue(),
						"subjectId", equalToIgnoringCase( dto.subjectId ),
						"value", equalToIgnoringCase( dto.value.toUpperCase() )
				);
	}

	@Test
	@DisplayName("Fail save boolean attribute value, by value is not boolean")
	void failSaveNotBooleanAttributeValue() {
		final var dto = AttributeValueDTO.of(
				null, MarketAttributeTesting.ATTRIBUTE_TO_VALUE_BOOLEAN,
				"ok", ContactTesting.CONTACT_ID
		);

		given().body( dto )
				.post( "/market/attribute/value" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Update attribute value type String")
	void updateStringAttributeValue() {
		final var dto = AttributeValueDTO.of(
				AttributeValueTesting.ATTRIBUTE_VALUE_TO_UPDATE, MarketAttributeTesting.ATTRIBUTE_TO_VALUE_UPDATE,
				"\"" + generateName() + "\"", ContactTesting.CONTACT_ID
		);

		given().body( dto )
				.post( "/market/attribute/value" )
				.then()
				.statusCode( 200 )
				.body(
						"attributeId", equalToIgnoringCase( dto.attributeId ),
						"id", notNullValue(),
						"subjectId", equalToIgnoringCase( dto.subjectId ),
						"value", equalToIgnoringCase( dto.value )
				);
	}

	@Test
	@DisplayName("Deletes attribute value")
	void deleteEntity() {
		given().delete( "/market/attribute/value/{0}", AttributeValueTesting.ATTRIBUTE_VALUE_TO_DELETE )
				.then()
				.statusCode( 204 );

		given().get( "/market/attribute/value/{0}", AttributeValueTesting.ATTRIBUTE_VALUE_TO_DELETE )
				.then()
				.statusCode( 404 );
	}
}
