package cn.wnhyang.coolGuard.mapper;

import cn.wnhyang.coolGuard.entity.ServiceConfigField;
import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.page.ServiceConfigFieldPageVO;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 服务配置字段表 Mapper 接口
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Mapper
public interface ServiceConfigFieldMapper extends BaseMapperX<ServiceConfigField> {

    default PageResult<ServiceConfigField> selectPage(ServiceConfigFieldPageVO pageVO) {
        return selectPage(pageVO, new LambdaQueryWrapperX<ServiceConfigField>());
    }

    default void deleteByServiceConfigId(Long serviceConfigId) {
        delete(new LambdaUpdateWrapper<ServiceConfigField>().eq(ServiceConfigField::getServiceConfigId, serviceConfigId));
    }

    default List<ServiceConfigField> selectListByServiceId(Long serviceId) {
        return selectList(new LambdaQueryWrapperX<ServiceConfigField>().eq(ServiceConfigField::getServiceConfigId, serviceId)
                .orderByDesc(ServiceConfigField::getRequired));
    }
}
