package io.mykidong.config;

import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisClientConfig;
import redis.clients.jedis.JedisSharding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RedisConfigurer {

    public  static JedisSharding jedis(List<HostAndPort> shards) {
        JedisClientConfig clientConfig = DefaultJedisClientConfig.builder().build();
        if(shards == null) {
            shards = new ArrayList<>();
            HostAndPort hostAndPort = new HostAndPort("localhost", 6379);
            shards.add(hostAndPort);
        }
        JedisSharding jedis = new JedisSharding(shards, clientConfig);

        return jedis;
    }
}
