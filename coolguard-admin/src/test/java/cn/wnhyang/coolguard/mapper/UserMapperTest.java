package cn.wnhyang.coolguard.mapper;

import cn.wnhyang.coolguard.AdminApplication;
import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.system.convert.UserConvert;
import cn.wnhyang.coolguard.system.entity.UserDO;
import cn.wnhyang.coolguard.system.mapper.UserMapper;
import cn.wnhyang.coolguard.system.vo.user.UserPageVO;
import cn.wnhyang.coolguard.system.vo.user.UserRespVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author wnhyang
 * @date 2025/3/31
 **/
@Slf4j
@SpringBootTest(classes = AdminApplication.class)
public class UserMapperTest {

    @Resource
    private UserMapper userMapper;

    @Test
    void test() {
        PageResult<UserDO> pageResult = userMapper.selectPage(new UserPageVO());
        pageResult.getList().forEach(userDO -> {
            UserRespVO userRespVO = UserConvert.INSTANCE.convert02(userDO);
            log.info("userRespVO:{}", userRespVO);
        });
    }
}
