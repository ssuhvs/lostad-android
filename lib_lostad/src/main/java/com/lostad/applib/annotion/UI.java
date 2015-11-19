package com.lostad.applib.annotion;
  
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
  
/**
 * 属性描述信息 (解析XML、生成界面等)
 * @author sszvip
 *
 */
@Target(ElementType.FIELD)  
@Retention(RetentionPolicy.RUNTIME)  
@Documented  
public @interface UI {  
	String DATE           = "Date"; //
	String IGNORE         = "Ignore"; //
	String SPINNER        = "Spinner"; //生成
	String EDITTEXT       = "EditText"; //
	String TEXTVIEW       = "TextView"; //
	
    String type()  default "";   //类型 
    String label() default "";  //label值
	
} 