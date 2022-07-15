package ru.sberjavaproject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.sberjavaproject.controllers.SocksController;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class SberJavaProjectApplicationTests {
    @Autowired
    SocksController socksController;

    @Test
    void contextLoads() {
        assertNotNull(socksController, "socksController can't be null");
    }

}
