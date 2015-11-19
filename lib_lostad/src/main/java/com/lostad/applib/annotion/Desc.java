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
public @interface Desc {  
	
	String TYPE_PARSE_IGNORE = "TYPE_IGNORE"; //不自动解析和注入的属性
	String TYPE_LIST   = "TYPE_LIST"; //此属性对应一个List列表
	String TYPE_BEAN   = "TYPE_BEAN"; //此属性对应一个Bean对象
	//UI描述
	String TYPE_GONE_ON_UI   = "TYPE_GONE_ON_UI"; //不自动解析和注入的属性
	String UI_SPINNER        = "Spinner"; //生成
	String UI_DATE           = "Date"; //
	
    String type()  default "";   //类型 
    String label() default "";  //label值
} 