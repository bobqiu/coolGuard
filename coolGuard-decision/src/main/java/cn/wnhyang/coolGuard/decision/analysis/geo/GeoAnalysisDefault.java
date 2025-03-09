package cn.wnhyang.coolGuard.decision.analysis.geo;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.decision.analysis.ad.Pca;
import cn.wnhyang.coolGuard.decision.util.AdocUtil;
import cn.wnhyang.coolGuard.decision.util.DistanceCalculator;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wnhyang
 * @date 2024/5/31
 **/
@Slf4j
public class GeoAnalysisDefault implements GeoAnalysis {

    private static final Map<String, String> GEO_MAP = new HashMap<>(4096);

    private static final Map<String, AreaWithGeo> AREA_WITH_GEO_MAP = new HashMap<>(4096);


    @Override
    public void init() {
        long start = System.currentTimeMillis();
        List<AreaWithGeo> areaList = CsvUtil.getReader().read(
                ResourceUtil.getUtf8Reader("areas_with_geo.csv"), AreaWithGeo.class);
        areaList.forEach(area -> {
            // 四舍五入
            String key = Math.round(area.getLon()) + "," + Math.round(area.getLat());
            String value = GEO_MAP.get(key);
            if (StrUtil.isNotBlank(value)) {
                GEO_MAP.put(key, value + "," + area.getCode());
            } else {
                GEO_MAP.put(key, area.getCode());
            }
            AREA_WITH_GEO_MAP.put(area.getCode(), area);
        });
        log.info("init success, cost:{}ms", System.currentTimeMillis() - start);
    }

    private List<AreaWithGeo> getSimilarPcaByGeo(double lon, double lat) {
        List<AreaWithGeo> list = new ArrayList<>();
        String key = Math.round(lon) + "," + Math.round(lat);
        String areaCodes = GEO_MAP.get(key);
        if (StrUtil.isNotBlank(areaCodes)) {
            for (String areaCode : areaCodes.split(",")) {
                AreaWithGeo areaWithGeo = AREA_WITH_GEO_MAP.get(areaCode);
                if (areaWithGeo != null) {
                    list.add(areaWithGeo);
                }
            }
        }
        return list;
    }

    @Override
    public Pca analysis(double lon, double lat) {
        List<AreaWithGeo> areaWithGeos = getSimilarPcaByGeo(lon, lat);
        if (CollUtil.isEmpty(areaWithGeos)) {
            return null;
        }
        double distance = 10000.0;
        Pca result = new Pca();
        for (AreaWithGeo areaWithGeo : areaWithGeos) {
            double tmpDistance = DistanceCalculator.distance(areaWithGeo.getLon(), areaWithGeo.getLat(), lon, lat);
            if (tmpDistance < distance) {
                distance = tmpDistance;
                Pca pca = AdocUtil.getPca(areaWithGeo.getCode());
                if (pca != null) {
                    result.setProvince(pca.getProvince());
                    result.setCity(pca.getCity());
                    result.setArea(pca.getArea());
                }
            }
        }

        return result;
    }

    @Override
    public Pca analysis(String lonAndLat) {
        try {
            String[] split = lonAndLat.split(",");
            double lat = Double.parseDouble(split[0]);
            double lon = Double.parseDouble(split[1]);
            return analysis(lon, lat);
        } catch (Exception e) {
            log.error("经纬度格式错误");
            return null;
        }
    }
}
