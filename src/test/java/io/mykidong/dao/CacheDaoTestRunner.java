package io.mykidong.dao;

import io.mykidong.api.dao.CacheDao;
import io.mykidong.config.RedisConfigurer;
import io.mykidong.domain.JwtToken;
import io.mykidong.util.JwtUtils;
import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.JedisSharding;

public class CacheDaoTestRunner {

    @Test
    public void jwtTokenToRedis() throws Exception {
        // create redis client.
        JedisSharding jedis = RedisConfigurer.jedis(null);

        // cache dao for jwt token.
        CacheDao<JwtToken> jwtTokenCacheDao = new JwtTokenCacheDao(jedis);


        // jwt token.
        String userName = "testUser";
        String token = JwtUtils.generateToken(null, userName);

        JwtToken jwtToken = new JwtToken();
        jwtToken.setUserName(userName);
        jwtToken.setToken(token);

        // set token to redis.
        jwtTokenCacheDao.set(token, jwtToken);

        // get token from redis.
        JwtToken retJwtToken = jwtTokenCacheDao.get(token, JwtToken.class);

        // assert.
        Assert.assertNotNull(retJwtToken);
        Assert.assertTrue(retJwtToken.getToken().equals(token));
        Assert.assertTrue(retJwtToken.getUserName().equals(userName));
    }
}
