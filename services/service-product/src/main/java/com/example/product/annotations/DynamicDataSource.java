package com.example.product.annotations;

import com.example.product.constants.DataSourceConstants;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
@Inherited // 添加此项确保子类可继承
public @interface DynamicDataSource {
    String value() default DataSourceConstants.PRIMARY_DS;
}
