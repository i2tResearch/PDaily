package co.icesi.pdaily.subscription.account.user;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.icesi.pdaily.subscription.account.user.domain.model.UserName;

/**
 * @author cristhiank on 16/11/19
 **/
@DisplayName("UserName tests")
class UserNameTest {
	private final String[] valids = {
			"firstname.lastname",
			"testing_username",
			"testing",
			"testing-username",
			" testing ",
	};

	private final String[] invalids = {
			"invalid user",
			"...",
			"___",
			"%invalid_user%",
			"asd...Asd"
	};

	@Test
	@DisplayName("Valid usernames")
	void validUserNames() {
		for ( String user : valids ) {
			assertDoesNotThrow( () -> {
				UserName.of( user );
			} );
		}
	}

	@Test
	@DisplayName("Invalid usernames")
	void invalidEmails() {
		for ( String user : invalids ) {
			assertThrows( IllegalArgumentException.class, () -> System.out.println( UserName.of( user ) ) );
		}
	}
}
