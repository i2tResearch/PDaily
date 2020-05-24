package co.haruk.sms.market.measurement.attribute;

import static co.haruk.core.testing.TestNamesGenerator.generateName;
import static co.haruk.core.testing.TestNamesGenerator.generateNameNoSpaces;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.haruk.core.testing.data.IDataSet;
import co.haruk.core.testing.data.IDataSetDependent;
import co.haruk.sms.market.measurement.attribute.app.MarketAttributeDTO;
import co.haruk.sms.market.measurement.attribute.app.PickListDetailDTO;
import co.haruk.sms.market.measurement.attribute.domain.model.DataType;
import co.haruk.sms.market.measurement.attribute.domain.model.SubjectType;
import co.haruk.sms.market.measurement.attribute.picklist.PickListTesting;
import co.haruk.testing.DataSets;
import co.haruk.testing.SMSTest;

@SMSTest
@DisplayName("Market attribute tests")
public class MarketAttributeResourceIT implements IDataSetDependent {
	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.PICK_LIST );
	}

	@Test
	@DisplayName("Find market attributes by business unit")
	public void findByBusinessUnit() {
		given().get( "/market/attribute/for-business-unit/{0}", MarketAttributeTesting.BUSINESS_UNIT_TO_MARKET_ATTRIBUTE )
				.then()
				.statusCode( 200 )
				.body(
						"$.size", greaterThan( 0 )
				);
	}

	@Test
	@DisplayName("Find market attributes type multiple list by id")
	public void findByIdListMultiple() {
		given().get( "/market/attribute/{0}", MarketAttributeTesting.MARKET_ATTRIBUTE_TO_MULTIPLE_PICKLIST_VALIDATE )
				.then()
				.statusCode( 200 )
				.body(
						"businessId", equalToIgnoringCase( MarketAttributeTesting.BUSINESS_UNIT_TO_MARKET_ATTRIBUTE ),
						"dataType", equalToIgnoringCase( DataType.LIST_MULTIPLE_ANS.dbKey() ),
						"id", notNullValue(),
						"options.id", hasItems(
								PickListTesting.PICK_MULTIPLE_LIST_TO_MARKET_ATTRIBUTE_B.toLowerCase(),
								PickListTesting.PICK_MULTIPLE_LIST_TO_MARKET_ATTRIBUTE_A.toLowerCase()
						)
				);
	}

	@Test
	@DisplayName("Find market attributes type single list by id")
	public void findByIdListSingle() {
		given().get( "/market/attribute/{0}", MarketAttributeTesting.MARKET_ATTRIBUTE_TO_SINGLE_PICKLIST_VALIDATE )
				.then()
				.statusCode( 200 )
				.body(
						"businessId", equalToIgnoringCase( MarketAttributeTesting.BUSINESS_UNIT_TO_MARKET_ATTRIBUTE ),
						"dataType", equalToIgnoringCase( DataType.LIST_SINGLE_ANS.dbKey() ),
						"id", notNullValue(),
						"options.id", hasItems(
								PickListTesting.PICK_SINGLE_LIST_TO_MARKET_ATTRIBUTE_B.toLowerCase(),
								PickListTesting.PICK_SINGLE_LIST_TO_MARKET_ATTRIBUTE_A.toLowerCase()
						)
				);
	}

	@Test
	@DisplayName("Save market attribute with picklist, type multiple answer")
	public void saveMarketAttributeWithPickList() {
		final var listADTO = PickListDetailDTO.of( null, generateName() );
		final var listBDTO = PickListDetailDTO.of( null, generateName() );
		final var dto = MarketAttributeDTO.of(
				null, MarketAttributeTesting.BUSINESS_UNIT_TO_MARKET_ATTRIBUTE, generateNameNoSpaces(),
				SubjectType.CUSTOMER.dbKey(), DataType.LIST_MULTIPLE_ANS.dbKey(), List.of( listADTO, listBDTO )
		);

		given().body( dto )
				.post( "/market/attribute" )
				.then()
				.statusCode( 200 )
				.body(
						"id", notNullValue(),
						"businessId", equalToIgnoringCase( dto.businessId ),
						"businessName", equalToIgnoringCase( "TO_MARKET_ATTRIBUTE" ),
						"dataType", equalToIgnoringCase( dto.dataType ),
						"label", equalToIgnoringCase( dto.label ),
						"options.id", notNullValue(),
						"options.listValue", hasItems( listADTO.listValue, listBDTO.listValue )
				);
	}

	@Test
	@DisplayName("Save market attribute with picklist, type multiple answer")
	public void saveMarketAttributeWithPickListSingle() {
		final var listADTO = PickListDetailDTO.of( null, generateName() );
		final var listBDTO = PickListDetailDTO.of( null, generateName() );
		final var dto = MarketAttributeDTO.of(
				null, MarketAttributeTesting.BUSINESS_UNIT_TO_MARKET_ATTRIBUTE, generateNameNoSpaces(),
				SubjectType.CUSTOMER.dbKey(), DataType.LIST_SINGLE_ANS.dbKey(), List.of( listADTO, listBDTO )
		);

		given().body( dto )
				.post( "/market/attribute" )
				.then()
				.statusCode( 200 )
				.body(
						"id", notNullValue(),
						"businessId", equalToIgnoringCase( dto.businessId ),
						"businessName", equalToIgnoringCase( "TO_MARKET_ATTRIBUTE" ),
						"dataType", equalToIgnoringCase( dto.dataType ),
						"label", equalToIgnoringCase( dto.label ),
						"options.id", notNullValue(),
						"options.listValue", hasItems( listADTO.listValue, listBDTO.listValue )
				);
	}

	@Test
	@DisplayName("Fail save market attribute with same lists values, type multiple answer")
	public void saveMarketAttributeWithSameLabel() {
		final var repeated = generateName();
		final var listADTO = PickListDetailDTO.of( null, repeated );
		final var listBDTO = PickListDetailDTO.of( null, repeated );
		final var dto = MarketAttributeDTO.of(
				null, MarketAttributeTesting.BUSINESS_UNIT_TO_MARKET_ATTRIBUTE, generateNameNoSpaces(),
				SubjectType.CUSTOMER.dbKey(), DataType.LIST_MULTIPLE_ANS.dbKey(), List.of( listADTO, listBDTO )
		);

		given().body( dto )
				.post( "/market/attribute" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fail save market attribute with value list repeated, type multiple answer")
	public void failSaveMarketAttributeWithSameLabelinDb() {
		final var listADTO = PickListDetailDTO.of( null, "EXISTENT" );
		final var listBDTO = PickListDetailDTO.of( null, "PREGUNTA_A" );
		final var dto = MarketAttributeDTO.of(
				MarketAttributeTesting.MARKET_ATTRIBUTE_ID, MarketAttributeTesting.BUSINESS_UNIT_TO_MARKET_ATTRIBUTE,
				generateNameNoSpaces(),
				SubjectType.CUSTOMER.dbKey(), DataType.LIST_MULTIPLE_ANS.dbKey(), List.of( listADTO, listBDTO )
		);

		given().body( dto )
				.post( "/market/attribute" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fail save market attribute with value list repeated ignore case, type multiple answer")
	public void failIgnoreCaseSaveMarketAttributeWithSameLabelinDb() {
		final var listADTO = PickListDetailDTO.of( null, "existent" );
		final var listBDTO = PickListDetailDTO.of( null, "PREGUNTA_A" );
		final var dto = MarketAttributeDTO.of(
				MarketAttributeTesting.MARKET_ATTRIBUTE_ID, MarketAttributeTesting.BUSINESS_UNIT_TO_MARKET_ATTRIBUTE,
				generateNameNoSpaces(),
				SubjectType.CUSTOMER.dbKey(), DataType.LIST_MULTIPLE_ANS.dbKey(), List.of( listADTO, listBDTO )
		);

		given().body( dto )
				.post( "/market/attribute" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fail save market attribute with empty list, type multiple answer")
	public void failSaveMarketAttributeWithEmptyList() {
		final var dto = MarketAttributeDTO.of(
				MarketAttributeTesting.MARKET_ATTRIBUTE_ID, MarketAttributeTesting.BUSINESS_UNIT_TO_MARKET_ATTRIBUTE,
				generateNameNoSpaces(),
				SubjectType.CUSTOMER.dbKey(), DataType.LIST_MULTIPLE_ANS.dbKey(), List.of()
		);

		given().body( dto )
				.post( "/market/attribute" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Update market attribute selecction list, type multiple answer")
	public void updateMarketAttributeSelecctionList() {
		final var listADTO = PickListDetailDTO.of( PickListTesting.PICK_LIST_TO_UPDATE_LIST, "PREGUNTA_UPDATED" );
		final var dto = MarketAttributeDTO.of(
				MarketAttributeTesting.MARKET_ATTRIBUTE_TO_UPDATE_LIST,
				MarketAttributeTesting.BUSINESS_UNIT_TO_MARKET_ATTRIBUTE, "PREGUNTA_LIST_UPDATED",
				SubjectType.CUSTOMER.dbKey(), DataType.LIST_MULTIPLE_ANS.dbKey(), List.of( listADTO )
		);

		given().body( dto )
				.post( "/market/attribute" )
				.then()
				.statusCode( 200 )
				.body(
						"id", notNullValue(),
						"businessId", equalToIgnoringCase( dto.businessId ),
						"businessName", equalToIgnoringCase( "TO_MARKET_ATTRIBUTE" ),
						"dataType", equalToIgnoringCase( dto.dataType ),
						"label", equalToIgnoringCase( dto.label ),
						"options.id", notNullValue(),
						"options.listValue", hasItems( listADTO.listValue )
				);
	}

	@Test
	@DisplayName("Save single market attribute")
	public void saveSingleMarketAttribute() {
		final var dto = MarketAttributeDTO.of(
				null, MarketAttributeTesting.BUSINESS_UNIT_TO_MARKET_ATTRIBUTE,
				generateName(), SubjectType.CUSTOMER.dbKey(), DataType.NUMBER.dbKey()
		);

		given().body( dto )
				.post( "/market/attribute" )
				.then()
				.statusCode( 200 )
				.body(
						"id", notNullValue(),
						"businessId", equalToIgnoringCase( MarketAttributeTesting.BUSINESS_UNIT_TO_MARKET_ATTRIBUTE ),
						"label", equalToIgnoringCase( dto.label ),
						"dataType", equalToIgnoringCase( DataType.NUMBER.dbKey() ),
						"subjectType", equalToIgnoringCase( SubjectType.CUSTOMER.dbKey() )
				);
	}

	@Test
	@DisplayName("Save multiple market attribute")
	public void saveMultipleMarketAttribute() {
		final var dtoA = MarketAttributeDTO.of(
				null, MarketAttributeTesting.BUSINESS_UNIT_TO_MARKET_ATTRIBUTE,
				generateName(), SubjectType.CUSTOMER.dbKey(), DataType.NUMBER.dbKey()
		);

		final var dtoB = MarketAttributeDTO.of(
				null, MarketAttributeTesting.BUSINESS_UNIT_TO_MARKET_ATTRIBUTE,
				generateName(), SubjectType.CONTACT.dbKey(), "STRING"
		);

		given().body( List.of( dtoA, dtoB ) )
				.post( "/market/attribute/group" )
				.then()
				.statusCode( 200 )
				.body(
						"size()", greaterThan( 0 ),
						"businessId", hasItems(
								MarketAttributeTesting.BUSINESS_UNIT_TO_MARKET_ATTRIBUTE.toLowerCase(),
								MarketAttributeTesting.BUSINESS_UNIT_TO_MARKET_ATTRIBUTE.toLowerCase()
						),
						"dataType", hasItems( DataType.NUMBER.dbKey(), DataType.STRING.dbKey() ),
						"subjectType", hasItems( SubjectType.CUSTOMER.dbKey(), SubjectType.CONTACT.dbKey() )
				);
	}

	@Test
	@DisplayName("Fail if duplicate attribute market")
	public void failDuplicateAttributeMarket() {
		final var dto = MarketAttributeDTO.of(
				null, MarketAttributeTesting.BUSINESS_UNIT_TO_MARKET_ATTRIBUTE,
				"EXISTENT", SubjectType.CONTACT.dbKey(), DataType.STRING.dbKey()
		);

		given().body( dto )
				.post( "/market/attribute" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fail if duplicate attribute market ignore case")
	public void failDuplicateAttributeMarketIgnoreCase() {
		final var dto = MarketAttributeDTO.of(
				null, MarketAttributeTesting.BUSINESS_UNIT_TO_MARKET_ATTRIBUTE,
				"existent", SubjectType.CONTACT.dbKey(), DataType.STRING.dbKey()
		);

		given().body( dto )
				.post( "/market/attribute" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Update market attribute")
	public void updateMarketAttribute() {
		final var dto = MarketAttributeDTO.of(
				MarketAttributeTesting.MARKET_ATTRIBUTE_TO_UPDATE, MarketAttributeTesting.BUSINESS_UNIT_TO_MARKET_ATTRIBUTE,
				"Testing update", SubjectType.CUSTOMER.dbKey(), DataType.NUMBER.dbKey()
		);

		given().body( dto )
				.post( "/market/attribute" )
				.then()
				.statusCode( 200 )
				.body(
						"id", notNullValue(),
						"businessId", equalToIgnoringCase( MarketAttributeTesting.BUSINESS_UNIT_TO_MARKET_ATTRIBUTE ),
						"label", equalToIgnoringCase( dto.label ),
						"dataType", equalToIgnoringCase( DataType.NUMBER.dbKey() ),
						"subjectType", equalToIgnoringCase( SubjectType.CUSTOMER.dbKey() )
				);
	}

	@Test
	@DisplayName("Update fail market attribute, by changed subject type")
	public void updateFailMarketAttribute() {
		final var dto = MarketAttributeDTO.of(
				MarketAttributeTesting.MARKET_ATTRIBUTE_TO_UPDATE_FAIL,
				MarketAttributeTesting.BUSINESS_UNIT_TO_MARKET_ATTRIBUTE,
				generateNameNoSpaces(), SubjectType.CUSTOMER.dbKey(), DataType.STRING.dbKey()
		);

		given().body( dto )
				.post( "/market/attribute" )
				.then()
				.statusCode( 400 )
				.body(
						"messages[0]",
						equalTo(
								"El atributo tiene valores asignados, solo se puede cambiar el nombre"
						)
				);
	}

	@Test
	@DisplayName("Update fail market attribute, by changed data type")
	public void updateFailMarketAttributeByDataType() {
		final var dto = MarketAttributeDTO.of(
				MarketAttributeTesting.MARKET_ATTRIBUTE_TO_UPDATE_FAIL,
				MarketAttributeTesting.BUSINESS_UNIT_TO_MARKET_ATTRIBUTE,
				generateNameNoSpaces(), SubjectType.CONTACT.dbKey(), DataType.BOOLEAN.dbKey()
		);

		given().body( dto )
				.post( "/market/attribute" )
				.then()
				.statusCode( 400 )
				.body(
						"messages[0]",
						equalTo(
								"El atributo tiene valores asignados, solo se puede cambiar el nombre"
						)
				);
	}

	@Test
	@DisplayName("Delete Market Attribute")
	public void deleteMarketAttribute() {
		given()
				.delete( "/market/attribute/{0}", MarketAttributeTesting.MARKET_ATTRIBUTE_TO_DELETE )
				.then()
				.statusCode( 204 );

		given().get( "/market/attribute/{0}", MarketAttributeTesting.MARKET_ATTRIBUTE_TO_DELETE )
				.then()
				.statusCode( 404 );
	}
}
