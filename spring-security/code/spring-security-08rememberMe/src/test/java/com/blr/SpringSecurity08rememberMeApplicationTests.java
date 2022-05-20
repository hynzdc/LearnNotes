package com.blr;

import org.junit.jupiter.api.Test;
import org.springframework.util.Base64Utils;

//@SpringBootTest
class SpringSecurity08rememberMeApplicationTests {

    @Test
    void contextLoads() {
        byte[] result = Base64Utils.decodeFromString("R1JqT2NJQzBySkRxVnNRUXREWGhYQSUzRCUzRDpyREtDYlFBOWw5RmNOcTNKVnhjR0JRJTNEJTNE");
        System.out.println(new String(result));
    }

}
