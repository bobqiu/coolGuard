package cn.wnhyang.coolGuard.decision.analysis.ip;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.xdb.Searcher;

/**
 * @author wnhyang
 * @date 2024/5/31
 **/
@Slf4j
public class IpAnalysisDefaultImpl implements IpAnalysis {

    private static Searcher searcher;

    @Override
    public void init() {
        long start = System.currentTimeMillis();
        try {
            byte[] bytes = ResourceUtil.readBytes("ip2region.xdb");
            searcher = Searcher.newWithBuffer(bytes);
        } catch (Exception e) {
            log.error("初始化ip2region失败", e);
        }
        log.info("init success, cost:{}ms", System.currentTimeMillis() - start);
    }

    @Override
    public Ip2Region analysis(String ip) {
        try {
            String search = searcher.search(ip);
            if (StrUtil.isNotBlank(search)) {
                String[] split = search.split("\\|");
                if (split.length == 5) {
                    return new Ip2RegionDefault(ip, split[0], split[1], split[2], split[3], split[4]);
                }
            }
        } catch (Exception e) {
            log.error("ip2region查询失败", e);
        }
        return Ip2RegionDefault.unknown(ip);
    }
}
