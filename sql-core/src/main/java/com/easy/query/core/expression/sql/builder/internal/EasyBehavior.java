package com.easy.query.core.expression.sql.builder.internal;

import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.util.EasyBitwiseUtil;

/**
 * create time 2023/3/30 08:16
 * 文件说明
 *
 * @author xuejiaming
 */
public final class EasyBehavior {
    public static EasyBehavior DEFAULT=new EasyBehavior();
    public static int DEFAULT_BEHAVIOR = EasyBehaviorEnum.LOGIC_DELETE.getCode() | EasyBehaviorEnum.USE_INTERCEPTOR.getCode() | EasyBehaviorEnum.JDBC_LISTEN.getCode();
    private int behavior = DEFAULT_BEHAVIOR;

    public boolean isDefaultBehavior() {
        return behavior == 0;
    }

    public boolean hasBehavior(EasyBehaviorEnum easyBehavior) {
        return EasyBitwiseUtil.hasBit(behavior, easyBehavior.getCode());
    }

    public boolean addBehavior(EasyBehaviorEnum easyBehavior) {
        if (hasBehavior(easyBehavior)) {
            return false;
        } else {
            behavior = EasyBitwiseUtil.addBit(behavior, easyBehavior.getCode());
            return true;
        }
    }
    public EasyBehavior add(EasyBehaviorEnum easyBehavior){
        if (hasBehavior(easyBehavior)) {
            return this;
        } else {
            behavior = EasyBitwiseUtil.addBit(behavior, easyBehavior.getCode());
            return this;
        }
    }

    public boolean removeBehavior(EasyBehaviorEnum easyBehavior) {
        if (hasBehavior(easyBehavior)) {
            behavior = EasyBitwiseUtil.removeBit(behavior, easyBehavior.getCode());
            return true;
        }
        return false;
    }
    public EasyBehavior remove(EasyBehaviorEnum easyBehavior) {
        if (hasBehavior(easyBehavior)) {
            behavior = EasyBitwiseUtil.removeBit(behavior, easyBehavior.getCode());
            return this;
        }
        return this;
    }

    public void copyTo(EasyBehavior easyBehavior) {
        easyBehavior.behavior = this.behavior;
    }
}
