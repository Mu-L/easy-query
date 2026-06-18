package com.easy.query.core.basic.extension.navigate;

import com.easy.query.core.annotation.NavigateCondition;

import java.util.List;
import java.util.Objects;

/**
 * create time 2026/6/14 21:58
 * 文件说明
 *
 * @author xuejiaming
 */
public class MemoryFilterConfiguration {
    /**
     * 导航属性依赖的属性
     */
    private final String[] dependencies;
    private final NavigateCondition[] navigateConditions;

    public MemoryFilterConfiguration(NavigateCondition[] navigateConditions) {
        String[] dependencies = new String[navigateConditions.length];
        for (int i = 0; i < navigateConditions.length; i++) {
            dependencies[i] = navigateConditions[i].property();
        }
        this.dependencies = dependencies;
        this.navigateConditions = navigateConditions;
    }

    public boolean canInclude(List<Object> values) {
        for (int i = 0; i < navigateConditions.length; i++) {
            boolean whenAssert = includeConditionAssert(navigateConditions[i], values.get(i));
            if (!whenAssert) {
                return false;
            }
        }
        return true;
    }

    private boolean includeConditionAssert(NavigateCondition navigateCondition, Object value) {
        if (navigateCondition.matchIfMissing()) {
            return Objects.isNull(value)||Objects.equals(value,"");
        }
        if (value == null) {
            return false;
        }
        if (value instanceof String) {
            return Objects.equals(value, navigateCondition.havingValue());
        }
        return Objects.equals(value.toString(), navigateCondition.havingValue());
    }

    public String[] getDependencies() {
        return dependencies;
    }

    public NavigateCondition[] getIncludeConditions() {
        return navigateConditions;
    }
}
