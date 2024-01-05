package kr.getx.fitnessteachers.common

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties
import mu.KotlinLogging
import org.jasypt.encryption.StringEncryptor
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableEncryptableProperties
class JasyptConfig( @Value("\${jasypt.encryptor.password}")  private val password: String ) {
  val log = KotlinLogging.logger {}

  @Bean("jasyptStringEncryptor")
  fun stringEncryptor(): StringEncryptor {
    log.info("jasyptStringEncryptor Start")
    log.info("jasyptStringEncryptor password : $password")
    val encryptor = PooledPBEStringEncryptor()
    val config = SimpleStringPBEConfig()
    config.password = password
    config.algorithm = "PBEWITHMD5AndDES"
    config.setKeyObtentionIterations("1000")
    config.setPoolSize("1")
    config.providerName = "SunJCE"
    config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator")
    config.setIvGeneratorClassName("org.jasypt.iv.NoIvGenerator")
    config.stringOutputType = "base64"
    encryptor.setConfig(config)
    return encryptor
  }
}

