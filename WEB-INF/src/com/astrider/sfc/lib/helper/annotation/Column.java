package com.astrider.sfc.lib.helper.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;

/**
 * DBカラム/FormFieldNameを指定するアノテーション
 * physicは物理名、logicはエラー出力等に利用する論理名
 * @author 01002552
 *
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface Column {
    boolean isPrimaryKey() default false;
    String physic();
    String logic();
}
