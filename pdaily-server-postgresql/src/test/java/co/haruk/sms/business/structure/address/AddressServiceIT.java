package co.haruk.sms.business.structure.address;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.transaction.UserTransaction;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import co.haruk.core.domain.model.entity.Identity;
import co.haruk.core.domain.model.persistence.EntityNotFoundException;
import co.haruk.core.testing.cdi.ICDIContainerDependent;
import co.haruk.core.testing.data.IDataSet;
import co.haruk.core.testing.data.IDataSetDependent;
import co.haruk.sms.business.structure.address.domain.model.AddressId;
import co.haruk.sms.business.structure.address.domain.model.AddressRequest;
import co.haruk.sms.business.structure.address.domain.model.AddressService;
import co.haruk.sms.business.structure.address.domain.model.view.AddressReadView;
import co.haruk.sms.common.infrastructure.session.HarukSession;
import co.haruk.sms.common.model.tenancy.TenantId;
import co.haruk.sms.subscription.account.AccountTesting;
import co.haruk.testing.DataSets;
import co.haruk.testing.SMSTest;

/**
 * @author cristhiank on 6/12/19
 **/
@SMSTest
@Tag("cdi")
@DisplayName("Address tests")
public class AddressServiceIT implements IDataSetDependent, ICDIContainerDependent {
	@Inject
	AddressService addressService;

	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.ADDRESS );
	}

	@BeforeEach
	void beforeEach() {
		HarukSession.setCurrentTenant( TenantId.of( AccountTesting.ACCOUNT_ID ) );
	}

	private void assertConsistency(AddressRequest expected, AddressReadView result) {
		assertNotNull( expected );
		assertNotNull( result );
		assertNotNull( result.id );
		assertNotNull( result.city );
		assertNotNull( result.state );
		assertNotNull( result.country );
		assertThat( expected.cityId, equalToIgnoringCase( result.cityId ) );
		assertEquals( expected.line1, result.line1 );
		assertEquals( expected.line2, result.line2 );
		assertThat( expected.referencedId, equalToIgnoringCase( result.referencedId ) );
		assertEquals( expected.latitude, result.latitude );
		assertEquals( expected.longitude, result.longitude );
		assertEquals( expected.description, result.description );
		assertEquals( expected.isMain, result.isMain );
	}

	@Test
	@DisplayName("Creates new address for referenced entity")
	void saveAddressForReferenced(UserTransaction transaction) throws Exception {
		final var dto = AddressTesting.generateRandom( UUID.randomUUID().toString() );
		final var addressView = addressService.createForReferenced( dto );
		transaction.commit();
		assertConsistency( dto, addressView );
	}

	@Test
	@DisplayName("Finds main address for referenced entity")
	void findAddressForReferenced() {
		final var found = addressService
				.findMainForReferencedOrFail( Identity.ofNotNull( AddressTesting.ADDRESS_REF_EXISTENT ) );
		assertTrue( AddressTesting.ADDRESS_REF_EXISTENT.equalsIgnoreCase( found.referencedId ) );
	}

	@Test
	@DisplayName("Creates a new address")
	void saveAddress(UserTransaction transaction) throws Exception {
		final var dto = AddressTesting.generateRandom( UUID.randomUUID().toString() );
		final var addressView = addressService.saveAddress( dto );
		transaction.commit();
		assertConsistency( dto, addressView );
	}

	@Test
	@DisplayName("Creates a minimal address")
	void saveMinimalAddress(UserTransaction transaction) throws Exception {
		final var dto = AddressTesting.generateRandomMinimal( UUID.randomUUID().toString() );
		final var addressView = addressService.saveAddress( dto );
		transaction.commit();
		assertConsistency( dto, addressView );
	}

	@Test
	@DisplayName("Updates address by id")
	void updateAddress(UserTransaction transaction) throws Exception {
		final var dto = AddressTesting.generateRandom( "8F510F15-C662-40C0-895C-E9D83CD85FE6" );
		dto.id = AddressTesting.ADDRESS_TO_UPDATE;
		dto.isMain = true;
		final var addressView = addressService.saveAddress( dto );
		transaction.commit();
		assertConsistency( dto, addressView );
	}

	@Test
	@DisplayName("Fails if missing city")
	void failsIfMissingCity(UserTransaction transaction) throws Exception {
		final var dto = AddressTesting.generateRandom( UUID.randomUUID().toString() );
		dto.cityId = null;
		assertThrows( IllegalArgumentException.class, () -> {
			addressService.saveAddress( dto );
			transaction.commit();
		} );
	}

	@Test
	@DisplayName("Fails if missing street line1")
	void failsIfMissingLine(UserTransaction transaction) throws Exception {
		final var dto = AddressTesting.generateRandom( UUID.randomUUID().toString() );
		dto.line1 = null;
		assertThrows( IllegalArgumentException.class, () -> {
			addressService.saveAddress( dto );
			transaction.commit();
		} );
	}

	@Test
	@DisplayName("Fails if missing referenced id")
	void failsIfMissingReferencedId(UserTransaction transaction) throws Exception {
		final var dto = AddressTesting.generateRandom( null );
		assertThrows( IllegalArgumentException.class, () -> {
			addressService.saveAddress( dto );
			transaction.commit();
		} );
	}

	@Test
	@DisplayName("Deletes addresses for referenced entity")
	void deleteAddressForReferenced(UserTransaction transaction) throws Exception {
		final Identity referencedId = Identity.ofNotNull( AddressTesting.ADDRESS_REF_TO_DELETE );
		addressService.deleteAllForReferenced( referencedId );
		transaction.commit();
		final var found = addressService.findAllForReferenced( referencedId );
		assertTrue( found.isEmpty() );
	}

	@Test
	@DisplayName("Deletes address by id")
	void deleteAddressById(UserTransaction transaction) throws Exception {
		final AddressId addressId = AddressId.ofNotNull( AddressTesting.ADDRESS_TO_DELETE );
		addressService.deleteById( addressId );
		transaction.commit();
		assertThrows( EntityNotFoundException.class, () -> {
			addressService.findById( addressId );
		} );
	}

	@Test
	@DisplayName("Fails if duplicated main address")
	void failsIfDuplicatedMainAddress(UserTransaction transaction) throws Exception {
		final var dto = AddressTesting.generateRandom( AddressTesting.ADDRESS_REF_EXISTENT );
		dto.isMain = true;
		assertThrows( IllegalStateException.class, () -> {
			addressService.createForReferenced( dto );
			transaction.commit();
		} );
	}

	@Test
	@DisplayName("Changes main address for referenced using address id")
	void changesMainAddress(UserTransaction transaction) throws Exception {
		// HAS A MAIN ADDRESS
		final Identity referencedId = Identity.ofNotNull( AddressTesting.ADDRESS_REF_EXISTENT );
		final var currentMainAddr = addressService.findMainForReferencedOrFail( referencedId );
		assertTrue( AddressTesting.ADDRESS_ID.equalsIgnoreCase( currentMainAddr.id ) );
		// CHANGE IT
		addressService.setAsMainAddress( AddressId.ofNotNull( AddressTesting.ADDRESS_ID_SECONDARY ) );
		transaction.commit();
		// NEW MAIN ADDRESS IS CHANGED
		final var newMainAddr = addressService.findMainForReferencedOrFail( referencedId );
		assertTrue( AddressTesting.ADDRESS_ID_SECONDARY.equalsIgnoreCase( newMainAddr.id ) );
	}

	@Test
	@DisplayName("Changes main address for referenced using request DTO")
	void changesMainAddressForReferenced(UserTransaction transaction) throws Exception {
		// GIVEN AN EXISTENT ADDRESS
		final AddressId addressId = AddressId.ofNotNull( AddressTesting.ADDRESS_ID_SECONDARY_FOR_DTO );
		final var secondaryAddress = addressService
				.findById( addressId );
		// SAVE IT AS MAIN
		final var dto = AddressRequest.of( secondaryAddress );
		dto.line2 = "UPDATED LINE 2";
		dto.isMain = true;
		addressService.saveMainForReferenced( dto );
		transaction.commit();
		// NEW MAIN ADDRESS IS CHANGED
		final var newMainAddr = addressService
				.findMainForReferencedOrFail( Identity.ofNotNull( secondaryAddress.referencedId ) );
		assertEquals( addressId.text(), newMainAddr.id );
		assertConsistency( dto, newMainAddr );
	}

	@Test
	@DisplayName("Saves new main address for referenced")
	void savesNewMainAddress(UserTransaction transaction) throws Exception {
		// DOES NOT HAS A MAIN ADDRESS
		final Identity randomReferenced = Identity.ofNotNull( UUID.randomUUID().toString() );
		Assertions.assertThrows( EntityNotFoundException.class, () -> {
			addressService.findMainForReferencedOrFail( randomReferenced );
		} );
		// CREATE NEW MAIN ADDRESS
		final var dto = AddressTesting.generateRandom( randomReferenced.text() );
		dto.isMain = true;
		addressService.saveMainForReferenced( dto );
		transaction.commit();
		// NEW MAIN ADDRESS EXISTS
		final var newMainAddr = addressService.findMainForReferencedOrFail( randomReferenced );
		assertConsistency( dto, newMainAddr );
	}

	@Test
	@DisplayName("Finds all addresses for referenced")
	void findAllForReferenced() {
		final var addresses = addressService.findAllForReferenced( Identity.ofNotNull( AddressTesting.ADDRESS_REF_EXISTENT ) );
		assertTrue( addresses.size() > 1 );
	}

	@Test
	@DisplayName("Deletes main for referenced")
	void deletesMainAddressForReferenced() {
		final Identity referencedId = Identity.ofNotNull( AddressTesting.ADDRESS_REF_TO_DELETE_MAIN );
		executeInTransaction( () -> addressService.deleteMainIfExistsFor( referencedId ) );
		final var notFound = addressService.findMainAddressFor( referencedId ).isEmpty();
		assertTrue( notFound );
	}
}