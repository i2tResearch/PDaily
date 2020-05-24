/*
 * Copyright 2019. All Rights Reserved.
 *
 * Software and code property of SYNEKUS S.A.S.
 *
 * NOTICE: All information contained herein is, and remains the property of
 * SYNEKUS S.A.S and its suppliers, if any. The intellectual and technical
 * concepts contained herein are proprietary to SYNEKUS S.A.S and its suppliers
 * and may be covered by Colombian and Foreign Patents, patents in process, and
 * are protected by trade secret or copyright law.
 *
 * Dissemination of this information or reproduction of this material is
 * strictly forbidden unless prior written permission is obtained from SYNEKUS S.A.S.
 */

package co.haruk.testing;

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
@ExtendWith(SMSTestingExtension.class)
public @interface SMSTest {
}
