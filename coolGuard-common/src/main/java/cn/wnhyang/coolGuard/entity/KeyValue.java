package cn.wnhyang.coolGuard.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author wnhyang
 * @date 2024/11/28
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeyValue<K, V> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1226578872513917559L;

    private K key;

    private V value;
}
