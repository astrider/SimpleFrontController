package com.astrider.sfc.lib.helper.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;

/**
 * Validator用アノテーション<br>
 * isXX=true, XX=valueとして設定
 * @author 01002552
 *
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface Valid {
    boolean isNotNull() default false;
    boolean isNotBlank() default false;
    boolean isLength() default false;
    boolean isMaxLength() default false;
    boolean isMinLength() default false;
    boolean isMax() default false;
    boolean isMin() default false;
    boolean isRegexp() default false;
    boolean isUrl() default false;
    boolean isEmail() default false;
    boolean isPhone() default false;
    int length() default 0;
    int maxLength() default 0;
    int minLength() default 0;
    int max() default 0;
    int min() default 0;
    String regexp() default "";
}
