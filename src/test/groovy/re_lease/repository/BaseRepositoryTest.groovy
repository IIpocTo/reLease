package re_lease.repository

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import re_lease.config.QueryDSLConfig
import spock.lang.Specification

@ActiveProfiles("test")
@Import(value = [QueryDSLConfig])
@DataJpaTest
abstract class BaseRepositoryTest extends Specification {}
