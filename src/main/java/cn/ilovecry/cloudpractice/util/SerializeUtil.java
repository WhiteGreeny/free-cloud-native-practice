package cn.ilovecry.cloudpractice.util;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * SerializeUtil
 *
 * @author yangyi
 * @version 1.0
 * @date 2023/7/10 16:35
 */
public class SerializeUtil {
    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }
}
