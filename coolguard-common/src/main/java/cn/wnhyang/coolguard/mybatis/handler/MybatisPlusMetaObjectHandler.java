package cn.wnhyang.coolguard.mybatis.handler;

import cn.wnhyang.coolguard.satoken.util.LoginUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author wnhyang
 * @date 2024/3/14
 **/
@Component
@Slf4j
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());

        try {
            this.strictInsertFill(metaObject, "creator", String.class, LoginUtil.getUsername());
            this.strictInsertFill(metaObject, "updater", String.class, LoginUtil.getUsername());
        } catch (Exception e) {
            log.error("获取登录用户失败", e);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());

        try {
            this.strictUpdateFill(metaObject, "updater", String.class, LoginUtil.getUsername());
        } catch (Exception e) {
            log.error("获取登录用户失败", e);
        }
    }
}
