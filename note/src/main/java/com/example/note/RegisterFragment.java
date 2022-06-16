package com.example.note;

import com.example.note.enums.LaunchMode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 编译时注解 用来注册Fragment 类似 AndroidMainfest文件
 *
 * @author SongWenjun
 * @since 2022-06-10
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface RegisterFragment {
    String launchMode() default LaunchMode.STANDARD;

    String[] action() default "";

    int openAnim() default -1;

    int closeAnim() default -1;
}
