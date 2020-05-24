package co.haruk.sms.security.user.infrastructure.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.enterprise.context.Dependent;

import co.haruk.sms.security.user.domain.model.IEncryptionProvider;
import co.haruk.sms.security.user.domain.model.Password;

/**
 * @author andres2508 on 9/2/20
 **/
@Dependent
public class Pbkdf2CryptoProvider implements IEncryptionProvider {
	public static final int DEFAULT_DERIVED_KEY_SIZE = 512;
	public static final int DEFAULT_ITERATIONS = 27500;
	private static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA256";

	private byte[] generateSalt() {
		var buffer = new byte[16];
		var secureRandom = new SecureRandom();
		secureRandom.nextBytes( buffer );
		return buffer;
	}

	private SecretKeyFactory secretKeyFactory() {
		try {
			return SecretKeyFactory.getInstance( PBKDF2_ALGORITHM );
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException( "PBKDF2 algorithm not found", e );
		}
	}

	@Override
	public Password encrypt(String rawString) {
		final var salt = generateSalt();
		final var encoded = encode( rawString, DEFAULT_ITERATIONS, salt, DEFAULT_DERIVED_KEY_SIZE );
		return Password.of( DEFAULT_ITERATIONS, encodeToBase64( salt ), encoded );
	}

	@Override
	public boolean verify(String rawString, Password expected) {
		return encode(
				rawString,
				expected.hashIterations(),
				decodeFromBase64( expected.salt() ),
				keySize( expected.text() )
		).equalsIgnoreCase( expected.text() );
	}

	private int keySize(String encodedPass) {
		final var bytes = decodeFromBase64( encodedPass );
		return bytes.length * 8;
	}

	public String encode(String rawString, int hashIterations, byte[] salt, int derivedKeySize) {
		final var spec = new PBEKeySpec( rawString.toCharArray(), salt, hashIterations, derivedKeySize );
		try {
			final var key = secretKeyFactory().generateSecret( spec ).getEncoded();
			return encodeToBase64( key );
		} catch (InvalidKeySpecException e) {
			throw new IllegalStateException( "Credential could not be encoded", e );
		} catch (Exception e) {
			throw new IllegalStateException( e );
		}
	}

	private byte[] decodeFromBase64(String encoded) {
		return Base64.getDecoder().decode( encoded );
	}

	private String encodeToBase64(byte[] decoded) {
		return Base64.getEncoder().encodeToString( decoded );
	}
}
