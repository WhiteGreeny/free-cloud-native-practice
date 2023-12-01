package cn.ilovecry.cloudpractice.security;

import lombok.Data;

import java.util.Map;

/**
 * SecurityProperties
 *
 * @author yangyi
 * @version 1.0
 * @date 2023/12/1 17:24
 */
@Data
public class SecurityProperties {
    private Map<String,String> userMap;
}
