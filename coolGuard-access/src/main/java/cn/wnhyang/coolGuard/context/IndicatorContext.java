package cn.wnhyang.coolGuard.context;

import cn.wnhyang.coolGuard.vo.IndicatorResult;
import cn.wnhyang.coolGuard.vo.IndicatorVO;
import lombok.Setter;

import java.util.List;

/**
 * @author wnhyang
 * @date 2024/4/3
 **/
@Setter
public class IndicatorContext {

    private List<IndicatorVO> indicatorList;

    public IndicatorVO getIndicator(int index) {
        return indicatorList.get(index);
    }

    public void setIndicatorValue(int index, double value) {
        indicatorList.get(index).setValue(String.valueOf(value));
    }

    public String getIndicatorValue(long id) {
        for (IndicatorVO indicatorVO : indicatorList) {
            if (indicatorVO.getId() == id) {
                return indicatorVO.getValue();
            }
        }
        return "";
    }

    public List<IndicatorResult> convert() {
        if (indicatorList == null) {
            return null;
        }
        return indicatorList.stream().map(
                indicatorVO -> new IndicatorResult(indicatorVO.getId(), indicatorVO.getName(), indicatorVO.getType(), indicatorVO.getVersion(), indicatorVO.getValue())).toList();
    }
}
