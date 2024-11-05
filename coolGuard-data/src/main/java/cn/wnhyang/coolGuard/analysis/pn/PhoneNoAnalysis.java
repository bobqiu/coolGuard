package cn.wnhyang.coolGuard.analysis.pn;

/**
 * @author wnhyang
 * @date 2024/5/31
 **/
public interface PhoneNoAnalysis {

    default void init() {

    }

    PhoneNoInfo analysis(String phoneNumber);
}
