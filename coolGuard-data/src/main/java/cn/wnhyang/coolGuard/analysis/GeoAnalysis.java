package cn.wnhyang.coolGuard.analysis;

import cn.wnhyang.coolGuard.analysis.ad.Pca;

/**
 * @author wnhyang
 * @date 2024/5/31
 **/
public interface GeoAnalysis {

    default void init() {

    }

    Pca analysis(double lon, double lat);

    Pca analysis(String lonAndLat);
}
