package cn.wnhyang.coolGuard.analysis.geo;

import cn.wnhyang.coolGuard.analysis.ad.Pca;

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
}
