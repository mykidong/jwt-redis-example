package io.mykidong.dao;

import io.mykidong.domain.JwtToken;
import redis.clients.jedis.JedisSharding;


public class JwtTokenCacheDao extends AbstractCacheDao<JwtToken> {

    public JwtTokenCacheDao(JedisSharding jedis) {
        super(jedis, JwtToken.class);
    }
}
