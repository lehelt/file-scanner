package com.swingingstring.file_scanner;

import com.swingingstring.file_scanner.repository.ScanHistoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestPropertySource(properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration",
        "app.user=testuser",
        "server.port=0"
})
class FileScannerApplicationTests {

    @MockitoBean
    private ScanHistoryRepository scanHistoryRepository;

    @Test
	void contextLoads() {
	}

}
