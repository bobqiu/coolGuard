package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.wnhyang.coolGuard.AD;
import cn.wnhyang.coolGuard.County;
import cn.wnhyang.coolGuard.service.AnalysisService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wnhyang
 * @date 2024/4/19
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class AnalysisServiceImpl implements AnalysisService {

    private static final Map<String, County> CITY_MAP = new HashMap<>();

    @PostConstruct
    public void init() {
        log.info("AnalysisServiceImpl init");
        try {
            InputStream inputStream = ResourceUtil.getStreamSafe("行政区划代码.xlsx");
            ExcelReader reader = ExcelUtil.getReader(inputStream);
            List<County> cities = reader.readAll(County.class);
            cities.forEach(county -> CITY_MAP.put(county.getCode(), county));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AD getCounty(String idCard) {
        String provinceCode = IdcardUtil.getProvinceCodeByIdCard(idCard);
        String province = CITY_MAP.get(provinceCode + "0000").getName();
        String cityCode = IdcardUtil.getCityCodeByIdCard(idCard);
        String districtCode = IdcardUtil.getDistrictCodeByIdCard(idCard);
        AD ad = new AD();
        ad.setProvince(province);
        if (CITY_MAP.get(cityCode + "00") == null) {
            ad.setCity(province);
        } else {
            ad.setCity(CITY_MAP.get(cityCode + "00").getName());
        }
        ad.setDistrict(CITY_MAP.get(districtCode).getName());
        return ad;
    }
}
