package cn.wnhyang.coolGuard.system.vo.menu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wnhyang
 * @date 2023/11/7
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MenuTreeRespVO extends MenuRespVO {

    /**
     * 子节点
     */
    private List<MenuTreeRespVO> children;
}

