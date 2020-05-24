package co.haruk.sms.common.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author andres2508 on 16/11/19
 **/
@DisplayName("Email validation tests")
class EmailAddressTest {

	private final String[] valids = {
			"email@example.com",
			"firstname.lastname@example.com",
			"email@subdomain.example.com",
			"firstname+lastname@example.com",
			"email@123.123.123.123",
			"email@[123.123.123.123]",
			"\"email\"@example.com",
			"1234567890@example.com",
			"email@example-one.com",
			"_______@example.com",
			"email@example.name",
			"email@example.museum",
			"email@example.co.jp",
			"firstname-lastname@example.com"
	};

	private final String[] invalids = {
			"plainaddress",
			"#@%^%#$@#$@#.com",
			"@example.com",
			"Joe Smith <email@example.com>",
			"email.example.com",
			"email@example@example.com",
			".email@example.com",
			"email.@example.com",
			"email..email@example.com",
			"あいうえお@example.com",
			"email@example.com (Joe Smith)",
			"email@example",
			"email@-example.com",
			"email@example..com",
			"Abc..123@example.com",
	};

	@Test
	@DisplayName("Valid emails")
	void validEmails() {
		for ( String email : valids ) {
			assertDoesNotThrow( () -> {
				EmailAddress.of( email );
			} );
		}
	}

	@Test
	@DisplayName("Invalid emails")
	void invalidEmails() {
		for ( String email : invalids ) {
			assertThrows( IllegalArgumentException.class, () -> System.out.println( EmailAddress.of( email ) ) );
		}
	}

	@Test
	@DisplayName("Allows empty and null")
	void emptyAndNull() {
		assertNull( EmailAddress.ofNullable( null ) );
		assertNull( EmailAddress.ofNullable( "" ) );
		assertNull( EmailAddress.ofNullable( "  " ) );
	}
}