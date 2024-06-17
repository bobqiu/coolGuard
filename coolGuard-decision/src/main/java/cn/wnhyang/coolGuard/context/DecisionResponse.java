package cn.wnhyang.coolGuard.context;

import cn.wnhyang.coolGuard.vo.PolicySetResult;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wnhyang
 * @date 2024/4/8
 **/
@Data
public class DecisionResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = -1627715296419489019L;

    private PolicySetResult policySetResult;

    private final Map<String, String> outputFields = new ConcurrentHashMap<>();

    public void setOutputData(String key, String value) {
        outputFields.put(key, value);
    }

    public String getOutputData(String key) {
        return outputFields.get(key);
    }

}
