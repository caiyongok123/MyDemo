package com.example.cy;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by cy on 2017/8/28.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Ppp {
    // 定义注解的参数，类型可以为基本类型以及String、Class、enum、数组等，default为默认值
    String parameter1() default "ppa";
    int parameter2() default 1000;
}
