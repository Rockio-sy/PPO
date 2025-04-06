package org.academo.academo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootTest
@EnableJdbcRepositories(basePackages = "org.academo.academo.repository")
class AcademoApplicationTests {

//	@Test
//	void contextLoads() {
//	}

}
