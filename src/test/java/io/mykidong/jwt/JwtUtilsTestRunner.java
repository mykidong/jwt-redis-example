package io.mykidong.jwt;

import io.jsonwebtoken.Claims;
import io.mykidong.util.JwtUtils;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtilsTestRunner {

    @Test
    public void generateJwtToken() throws Exception {
        String userName = "any-user-name";
        Map<String, Object> claims = new HashMap<>();
        claims.put("user_age", 53);
        claims.put("gender", "male");

        // generate jwt token.
        String jwtToken = JwtUtils.generateToken(claims, userName);

        // get all claims from jwt token.
        Claims retClaims = JwtUtils.getAllClaimsFromToken(jwtToken);

        // get expiration date.
        Date expiration = retClaims.getExpiration();


        Assert.assertTrue(expiration.getTime() > DateTime.now().getMillis());
        Assert.assertTrue(retClaims.getSubject().equals(userName));
        Assert.assertTrue((Integer) retClaims.get("user_age") == 53);
        Assert.assertTrue(String.valueOf(retClaims.get("gender")).equals("male"));
    }
}
