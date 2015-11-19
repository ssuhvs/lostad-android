package com.lostad.applib.util;


import java.lang.reflect.Field;
public class BeanUtil {  

    public static void copyProperties(Object dist, Object src,boolean copyNull){  
        Class classDist = dist.getClass();
        Class classSrc = src.getClass();  


        Field fields[] = classSrc.getDeclaredFields();  
        for(int i=0;i<fields.length;i++){  
                Field filed = fields[i];
                filed.setAccessible(true);//
                
            try {  
            	Object srcValue = filed.get(src);
            	String fieldName = filed.getName();
            	//复制
            	//Field distField = classDist.getField(fieldName);//public feld
            	Field distField = classDist.getDeclaredField(fieldName);//all field
            	distField.setAccessible(true);
            	if(srcValue==null){
            		if(copyNull){
                		distField.set(dist, null);//强制赋值给dist
                	}
        		}else{
        			distField.set(dist, srcValue);//强制赋值给dist
        		}
            	
            } catch (Exception e) {  
            }  
        }  
    }  
    
    
    
}  