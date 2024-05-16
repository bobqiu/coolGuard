package cn.wnhyang.coolGuard.entity;

import cn.wnhyang.coolGuard.AdminApplication;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = AdminApplication.class)
@Slf4j
class ConfigFieldTest {

    @Resource
    ObjectMapper objectMapper;


    @Test
    void test() throws JsonProcessingException {
        ConfigField configField = new ConfigField().setParamName("appName").setRequired(true).setFieldName("N_S_appName");

        String s = objectMapper.writeValueAsString(configField);

        log.info("json:{}", s);
        ConfigField configField1 = objectMapper.readValue(s, ConfigField.class);
        log.info("{object:{}}", configField1);

    }

    @Test
    void test01() throws JsonProcessingException {
        ConfigField configField1 = new ConfigField().setParamName("appName").setRequired(true).setFieldName("N_S_appName");
        ConfigField configField2 = new ConfigField().setParamName("wrgw").setRequired(true).setFieldName("N_S_rgr");

        List<ConfigField> list = List.of(configField1, configField2);


        String json = objectMapper.writeValueAsString(list);

        log.info("json:{}", json);

        List<ConfigField> objectList = objectMapper.readValue(json,
                objectMapper.getTypeFactory().constructCollectionType(List.class, ConfigField.class));

        log.info("{object:{}}", objectList);

    }

    @Test
    void test02() throws JsonProcessingException {
        String json="[\n" +
                "{\"paramName\":\"appName\",\"required\":false,\"fieldName\":\"N_S_appName\"},\n" +
                "{\"paramName\":\"strategySetCode\",\"required\":false,\"fieldName\":\"N_S_strategySetCode\"},\n" +
                "{\"paramName\":\"strategyCode\",\"required\":false,\"fieldName\":\"N_S_strategyCode\"},\n" +
                "{\"paramName\":\"transTime\",\"required\":false,\"fieldName\":\"N_D_transTime\"},\n" +
                "{\"paramName\":\"transAmount\",\"required\":false,\"fieldName\":\"N_F_transAmount\"},\n" +
                "{\"paramName\":\"transSerialNo\",\"required\":false,\"fieldName\":\"N_S_transSerialNo\"},\n" +
                "{\"paramName\":\"payeeAccount\",\"required\":false,\"fieldName\":\"N_S_payeeAccount\"},\n" +
                "{\"paramName\":\"payerAccount\",\"required\":false,\"fieldName\":\"N_S_payerAccount\"},\n" +
                "{\"paramName\":\"payeeName\",\"required\":false,\"fieldName\":\"N_S_payeeName\"},\n" +
                "{\"paramName\":\"payerName\",\"required\":false,\"fieldName\":\"N_S_payerName\"},\n" +
                "{\"paramName\":\"payeeType\",\"required\":false,\"fieldName\":\"N_S_payeeType\"},\n" +
                "{\"paramName\":\"payerType\",\"required\":false,\"fieldName\":\"N_S_payerType\"},\n" +
                "{\"paramName\":\"payeeRiskRating\",\"required\":false,\"fieldName\":\"N_S_payeeRiskRating\"},\n" +
                "{\"paramName\":\"payerRiskRating\",\"required\":false,\"fieldName\":\"N_S_payerRiskRating\"},\n" +
                "{\"paramName\":\"payeeBankName\",\"required\":false,\"fieldName\":\"N_S_payeeBankName\"},\n" +
                "{\"paramName\":\"payerBankName\",\"required\":false,\"fieldName\":\"N_S_payerBankName\"},\n" +
                "{\"paramName\":\"payeeAddress\",\"required\":false,\"fieldName\":\"N_S_payeeAddress\"},\n" +
                "{\"paramName\":\"payerAddress\",\"required\":false,\"fieldName\":\"N_S_payerAddress\"},\n" +
                "{\"paramName\":\"payeePhoneNumber\",\"required\":false,\"fieldName\":\"N_S_payeePhoneNumber\"},\n" +
                "{\"paramName\":\"payerPhoneNumber\",\"required\":false,\"fieldName\":\"N_S_payerPhoneNumber\"},\n" +
                "{\"paramName\":\"payeeIDNumber\",\"required\":false,\"fieldName\":\"N_S_payeeIDNumber\"},\n" +
                "{\"paramName\":\"payerIDNumber\",\"required\":false,\"fieldName\":\"N_S_payerIDNumber\"},\n" +
                "{\"paramName\":\"payeeIDCountryRegion\",\"required\":false,\"fieldName\":\"N_S_payeeIDCountryRegion\"},\n" +
                "{\"paramName\":\"payerIDCountryRegion\",\"required\":false,\"fieldName\":\"N_S_payerIDCountryRegion\"},\n" +
                "{\"paramName\":\"ip\",\"required\":false,\"fieldName\":\"N_S_ip\"},\n" +
                "{\"paramName\":\"lonAndLat\",\"required\":false,\"fieldName\":\"N_S_lonAndLat\"}\n" +
                "]";
        List<ConfigField> objectList = objectMapper.readValue(json,
                objectMapper.getTypeFactory().constructCollectionType(List.class, ConfigField.class));

        log.info("{object:{}}", objectList);
    }

}