package cn.wnhyang.coolGuard.mybatis.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.github.yulichang.autoconfigure.consumer.MybatisPlusJoinIfExistsConsumer;
import com.github.yulichang.config.enums.IfExistsEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wnhyang
 * @date 2024/3/14
 **/
@Configuration
@Slf4j
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        log.info("[MybatisPlusInterceptor][初始化mybatisPlusInterceptor配置]");
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        // 分页插件
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return mybatisPlusInterceptor;
    }

    @Bean
    public MybatisPlusJoinIfExistsConsumer mybatisPlusJoinIfExistsConsumer() {
        /* val是条件值 enums是条件类型
           以下代码解释
           eqIfExists 判断条件为 Objects.nonNull
           likeIfExists 判断条件为 NOT_BLANK
           其他 xxIfExists 判断条件为 NOT_NULL
         */
        return (val, enums) -> IfExistsEnum.NOT_BLANK.test(val);
    }
}
