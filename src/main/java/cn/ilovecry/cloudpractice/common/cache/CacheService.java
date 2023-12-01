package cn.ilovecry.cloudpractice.common.cache;

import java.util.concurrent.TimeUnit;

/**
 * CacheService
 *
 * @author yangyi
 * @version 1.0
 * @Date 2021/5/27 10:37
 */
public interface CacheService {
    <T> void set(String key,T o);
    <T> void set(String key,T o,long expire);
    <T> void set(String key, T o, long expire, TimeUnit timeUnit);
    <T> T get(String key);
    <T> T remove(String key);

}
