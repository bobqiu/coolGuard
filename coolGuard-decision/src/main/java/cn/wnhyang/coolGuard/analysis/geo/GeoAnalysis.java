package cn.wnhyang.coolGuard.analysis.geo;

import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.analysis.ad.Pca;
import cn.wnhyang.coolGuard.constant.FieldCode;
import cn.wnhyang.coolGuard.context.FieldContext;
import cn.wnhyang.coolGuard.enums.FieldType;
import cn.wnhyang.coolGuard.util.GeoHash;

/**
 * @author wnhyang
 * @date 2024/5/31
 **/
public interface GeoAnalysis {

    default void init() {

    }

    /**
     * 解析经纬度
     *
     * @param lon 纬度
     * @param lat 经度
     * @return Pca
     */
    Pca analysis(double lon, double lat);

    /**
     * 解析经纬度
     *
     * @param lonAndLat 格式 纬度,经度
     * @return Pca
     */
    Pca analysis(String lonAndLat);

    default void parseGeo(FieldContext fieldContext) {
        String lonAndLat = fieldContext.getData(FieldCode.LON_AND_LAT, String.class);
        if (StrUtil.isNotBlank(lonAndLat)) {
            Pca pca = analysis(lonAndLat);
            if (pca != null) {
                fieldContext.setDataByType(FieldCode.GEO_PROVINCE, pca.getProvince(), FieldType.STRING);
                fieldContext.setDataByType(FieldCode.GEO_CITY, pca.getCity(), FieldType.STRING);
                fieldContext.setDataByType(FieldCode.GEO_DISTRICT, pca.getArea(), FieldType.STRING);
            }
            // 经纬度geoHash编码
            fieldContext.setDataByType(FieldCode.GEO_HASH, GeoHash.geoHash(lonAndLat), FieldType.STRING);
        }
    }
}
