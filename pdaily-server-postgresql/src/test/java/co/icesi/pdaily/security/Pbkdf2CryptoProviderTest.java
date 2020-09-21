package co.icesi.pdaily.security;

import java.util.Base64;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.icesi.pdaily.security.user.domain.model.Password;
import co.icesi.pdaily.security.user.infrastructure.security.Pbkdf2CryptoProvider;

/**
 * @author andres2508 on 10/5/20
 **/
@DisplayName("Crypto provider test")
class Pbkdf2CryptoProviderTest {
	final Pbkdf2CryptoProvider provider = new Pbkdf2CryptoProvider();

	@Test
	@DisplayName("Encrypts correctly")
	void encrypt() {
		final var iterations = Pbkdf2CryptoProvider.DEFAULT_ITERATIONS;
		final var keySize = Pbkdf2CryptoProvider.DEFAULT_DERIVED_KEY_SIZE;
		final var salt = Base64.getDecoder().decode( "o5mxNvXiCinpxnS1Q5X06g==" );
		final var expected = "p+kFo+CmHkj/kdNIro/OjIwZi6QURqhbkApOHYV0y8EroX2YJN7F+xkNm32PppoliddeWYunYHt7E4D5SKsbLg==";
		final var encrypted = provider.encode( "PASSWORD", iterations, salt, keySize );
		Assertions.assertEquals( expected, encrypted );
	}

	@Test
	@DisplayName("Verifies correctly")
	void verify() {
		final var expected = Password.of(
				Pbkdf2CryptoProvider.DEFAULT_ITERATIONS,
				"o5mxNvXiCinpxnS1Q5X06g==",
				"p+kFo+CmHkj/kdNIro/OjIwZi6QURqhbkApOHYV0y8EroX2YJN7F+xkNm32PppoliddeWYunYHt7E4D5SKsbLg=="
		);
		final var valid = provider.verify( "PASSWORD", expected );
		Assertions.assertTrue( valid );
	}
}