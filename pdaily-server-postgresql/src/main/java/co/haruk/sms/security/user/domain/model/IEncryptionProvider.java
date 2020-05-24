package co.haruk.sms.security.user.domain.model;

/**
 * @author andres2508 on 9/2/20
 **/
public interface IEncryptionProvider {

	Password encrypt(String rawString);

	boolean verify(String rawString, Password expected);
}
