package com.example.product.aspect;

import com.example.product.annotations.DynamicDataSource;
import com.example.product.config.datasource.DynamicDataSourceContextHolder;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author dk
 * @Date 2025/8/15
 */

@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)// 确保在事务切面前执行
public class DynamicDataSourceAspect {

    private final Cache<Method, DynamicDataSource> annotationCache =
            Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.HOURS).build();

    // 只保留注解驱动的切换逻辑
    @Around("@annotation(com.example.product.annotations.DynamicDataSource)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 1. 获取方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        // 缓存获取逻辑
        DynamicDataSource dynamicDS = annotationCache.get(method, m ->
                // 当缓存未命中时执行此函数
                AnnotationUtils.findAnnotation(m, DynamicDataSource.class)
        );
        return handleDataSourceSwitch(joinPoint, dynamicDS);
    }

    // 类级别切入点
    @Around("target(com.example.product.config.datasource.DynamicDataSource) " +
            "&& execution(public * *(..))")
    public Object aroundClass(ProceedingJoinPoint point) throws Throwable {
        Class<?> clazz = point.getTarget().getClass();
        DynamicDataSource classAnnotation = clazz.getAnnotation(DynamicDataSource.class);
        if (classAnnotation != null) {
            return handleDataSourceSwitch(point, classAnnotation);
        }
        return point.proceed();
    }

    private Object handleDataSourceSwitch(ProceedingJoinPoint point, DynamicDataSource dynamicDS) throws Throwable {
        String previousKey = DynamicDataSourceContextHolder.getDataSourceType();
        try {
            DynamicDataSourceContextHolder.setDataSourceType(dynamicDS.value());
            return point.proceed();
        } finally {
            if (previousKey != null) {
                DynamicDataSourceContextHolder.setDataSourceType(previousKey);
            } else {
                DynamicDataSourceContextHolder.clearDataSourceType();
            }
        }
    }
}
