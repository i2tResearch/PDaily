package co.icesi.pdaily.testing;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.extension.ExtendWith;

import co.haruk.core.testing.HarukTest;

/**
 * @author andres2508 on 8/11/19
 **/
@HarukTest
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(PDailyTestingExtension.class)
public @interface PDailyTest {
}
