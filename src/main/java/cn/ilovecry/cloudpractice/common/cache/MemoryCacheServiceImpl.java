package cn.ilovecry.cloudpractice.common.cache;

import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * MemoryCacheServiceImpl
 *
 * @author yangyi
 * @version 1.0
 * @Date 2021/5/27 10:42
 */
@Service
public class MemoryCacheServiceImpl implements CacheService {
    private final static Map<String, ValueUnit> CACHE = new ConcurrentHashMap<>();

    @Override
    public <T> void set(String key, T o) {
        set(key, o, -1, TimeUnit.SECONDS);
    }

    @Override
    public <T> void set(String key, T o, long expire) {
        set(key, o, expire, TimeUnit.SECONDS);
    }

    @Override
    public <T> void set(String key, T o, long expire, TimeUnit timeUnit) {
        long expireMilliseconds = expire < 0 ? -1 : System.currentTimeMillis() + timeUnit.toMillis(expire);
        CACHE.put(key, new ValueUnit(o, expireMilliseconds));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        ValueUnit valueUnit = CACHE.get(key);
        if (isExpired(valueUnit)) {
            CACHE.remove(key);
            return null;
        }
        return (T) valueUnit.value;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T remove(String key) {
        ValueUnit valueUnit = CACHE.remove(key);
        return isExpired(valueUnit) ? null : (T) valueUnit.value;
    }

    private boolean isExpired(ValueUnit valueUnit) {
        return valueUnit == null || (valueUnit.timestamp != -1 && valueUnit.timestamp < System.currentTimeMillis());
    }

    private record ValueUnit(Object value, long timestamp) implements Serializable {
    }
}
