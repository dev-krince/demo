package com.pcn.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Tag("test")
@Tag("integrationTest")
@DisplayName("application 실행 테스트")
@ActiveProfiles("test")
@SpringBootTest
class ApplicationTests {

    @Test
    @DisplayName("실행 성공")
    void contextLoads() {
    }

}
