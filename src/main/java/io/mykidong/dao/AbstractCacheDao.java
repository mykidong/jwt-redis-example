package io.mykidong.dao;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import io.mykidong.api.dao.CacheDao;
import redis.clients.jedis.JedisSharding;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public abstract class AbstractCacheDao<T> implements CacheDao<T> {

    protected static Kryo kryo;

    protected JedisSharding jedis;

    public AbstractCacheDao(JedisSharding jedis, Class<T> clazz) {
        this.jedis = jedis;
        kryo = new Kryo();
        kryo.register(clazz);
    }


    @Override
    public void set(String id, T t) {
        jedis.set(id.getBytes(), serialize(t));
    }

    @Override
    public T get(String id, Class<T> clazz) {
        if(jedis.exists(id.getBytes())) {
            return deserialize(jedis.get(id.getBytes()), clazz);
        } else {
            return null;
        }
    }

    @Override
    public void delete(String id) {
        jedis.del(id.getBytes());
    }




    private static <T> byte[]  serialize(T t) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Output output = new Output(os);
        kryo.writeObject(output, t);
        output.close();

        return os.toByteArray();
    }

    private static <T> T deserialize(byte[] bytes, Class<T> clazz) {
        ByteArrayInputStream is = new ByteArrayInputStream(bytes);
        Input input = new Input(is);
        T t = kryo.readObject(input, clazz);
        input.close();

        return t;
    }
}
