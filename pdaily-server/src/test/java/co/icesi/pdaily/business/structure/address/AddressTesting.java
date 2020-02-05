package co.icesi.pdaily.business.structure.address;

import static co.haruk.core.testing.TestNamesGenerator.generateName;
import static co.haruk.core.testing.TestNamesGenerator.generateNumericString;
import static co.haruk.core.testing.TestNamesGenerator.randomFloat;

import co.haruk.core.testing.TestNamesGenerator;
import co.icesi.pdaily.business.structure.address.domain.model.AddressRequest;
import co.icesi.pdaily.business.structure.geography.GeographyTesting;

/**
 * @author cristhiank on 7/12/19
 **/
public final class AddressTesting {
	public static final String ADDRESS_ID = "90E21347-E4B6-4AB1-A004-DB99B9DCD949";
	public static final String ADDRESS_ID_SECONDARY = "E7CCD6FE-6A98-4A75-82A3-0C1F3D0BB411";
	public static final String ADDRESS_ID_SECONDARY_FOR_DTO = "EB9ED3E5-BC3F-463D-A86D-FE66611CD1DE";
	public static final String ADDRESS_REF_EXISTENT = "E05DCE19-F0E9-49A5-A04E-02798DE3E795";
	public static final String ADDRESS_TO_DELETE = "B1DFA682-4CA5-40DF-91AD-63390CAC6622";
	public static final String ADDRESS_REF_TO_DELETE = "11EDB595-52BD-4DCF-ABEF-3A74DE63B27F";
	public static final String ADDRESS_REF_TO_DELETE_MAIN = "CE6474ED-F94B-4806-8DFF-B2EDC01C653C";
	public static final String ADDRESS_TO_UPDATE = "BA4259DF-AA51-412C-8B9D-3635F92CA02E";

	private AddressTesting() {
	}

	public static AddressRequest generateRandom(String referencedId) {
		final String line1 = String.format(
				"CALLE %s #%s-%s", generateNumericString( 2 ), generateNumericString( 2 ), generateNumericString( 2 )
		);
		return AddressRequest.of(
				null,
				referencedId,
				TestNamesGenerator.generateName(),
				line1,
				"BARRIO " + generateName(),
				GeographyTesting.CITY_ID,
				randomFloat( -90, 90 ),
				randomFloat( -180, 180 ),
				false
		);
	}

	public static AddressRequest generateRandomMinimal(String referencedId) {
		final String line1 = String.format(
				"CALLE %s #%s-%s", generateNumericString( 2 ), generateNumericString( 2 ), generateNumericString( 2 )
		);
		return AddressRequest.of(
				null,
				referencedId,
				line1,
				GeographyTesting.CITY_ID
		);
	}
}
