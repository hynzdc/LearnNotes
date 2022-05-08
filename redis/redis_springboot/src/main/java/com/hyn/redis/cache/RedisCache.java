package com.hyn.redis.cache;

import com.hyn.redis.util.ApplicationContextUtils;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.DigestUtils;

import java.util.concurrent.TimeUnit;

public class RedisCache implements Cache {
    //我们需要用redistemplate 但是呢这个RedisCache的实例化并不是在spring工厂
    //中进行的，所以呢我们要用spring最大的工厂管理类ApplicationContext context.getbean)

    //当前放入缓存的napmespace
    private final String id;
    //必须存在构造方法

    public RedisCache(String id) {
        this.id = id;
    }

    //返回cache的唯一表示标识
    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
        //使用redis hash作为缓存存储类型
        getRedisTemplate().opsForHash().put(id.toString(),getKeyToMd5(key.toString()),value);

        if(id.equals("com.hyn.redis.dao.UserDao")){
            getRedisTemplate().expire(id.toString(),1, TimeUnit.HOURS);
        }
    }

    @Override
    public Object getObject(Object key) {

        return getRedisTemplate().opsForHash().get(id.toString(),getKeyToMd5(key.toString()));
    }
    //这个方法为mybatis的保留方法并没有实现
    @Override
    public Object removeObject(Object key) {
        //根据指定的key删除缓存
//        RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextUtils.getBean("redisTemplate");
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        return redisTemplate.opsForHash().delete(id.toString(),key.toString());
        return null;
    }

    @Override
    public void clear() {
        //清空缓存
        getRedisTemplate().delete(id.toString());
    }

    @Override
    public int getSize() {
        return getRedisTemplate().opsForHash().size(id.toString()).intValue();
    }

    private RedisTemplate getRedisTemplate(){
        RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextUtils.getBean("redisTemplate");
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    //封装一个对key进行处理的方法
    private String getKeyToMd5(String key){
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }
}
