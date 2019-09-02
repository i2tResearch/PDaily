package co.pdaily.cloud.testing

import org.junit.jupiter.api.extension.ExtendWith
import javax.enterprise.util.Nonbinding

@ExtendWith(PdailyTestExtension::class)
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
annotation class PdailyTest(@get:Nonbinding val sqlDataSets: Array<String> = [])
