package utils;

import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public class PayloadBuilderUtil<V> {

    private final Map<String, V> payload;

    public PayloadBuilderUtil() {
        this.payload = new LinkedHashMap<>();
    }

    public PayloadBuilderUtil<V> add(String key, V value) {
        payload.put(key, value);
        return this;
    }
}