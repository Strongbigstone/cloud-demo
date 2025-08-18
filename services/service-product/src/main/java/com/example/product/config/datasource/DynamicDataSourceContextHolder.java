package com.example.product.config.datasource;

import com.example.product.constants.DataSourceConstants;

/**
 * @author dk
 * @Date 2025/8/15
 */

public class DynamicDataSourceContextHolder {
    private static final ThreadLocal<String> contextHolder = ThreadLocal.withInitial(() -> DataSourceConstants.PRIMARY_DS);

    public static void setDataSourceType(String dataSourceType) {
        contextHolder.set(dataSourceType);
    }

    public static String getDataSourceType() {
        return contextHolder.get();
    }

    public static void clearDataSourceType() {
        contextHolder.remove();
    }
}
