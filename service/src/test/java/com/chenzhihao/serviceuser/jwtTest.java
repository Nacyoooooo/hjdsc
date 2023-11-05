package com.chenzhihao.serviceuser;

import com.chenzhihao.commonutil.JwtHelper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class jwtTest {
    @Test
    public void testJwt(){
        Long userId = JwtHelper.getUserId("eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAKtWKi5NUrJSyshKKU7WDQ12DVLSUUqtKFCyMjQ3MDAzMjcystRRKi1OLfJMAYrVAgCqDqcsMAAAAA.W0qnN0B_cgle7htDDSM9CTcCnytmSnpHSBeZAIGFMmpL-VmN_kk4sPpAyaSsVc5vCArI_iUgMfbA4_38jXBXDQ");
        System.out.println(userId);
    }
}
