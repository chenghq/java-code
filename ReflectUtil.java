
package salt.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
* 反射工具的编写
*/
public class ReflectUtil {
	/** 获取类中所有的属性
	 * cmdClass 中的属性与 baseCmdClass 中的属性 集合*/
	public static List<Field> getAllFielsForClass(Class<?> cmdClass, Class<?> baseCmdClass) {
		List<Field> fields = new ArrayList<Field>();
        Collections.addAll(fields, cmdClass.getDeclaredFields());
        /** 获取cmdClass的父类*/
        Class<?> superClass = cmdClass.getSuperclass();
        while (baseCmdClass.isAssignableFrom(superClass)) {
            Field[] superClassFields = superClass.getDeclaredFields();
            if (superClassFields != null) {
            	Collections.addAll(fields, superClassFields);
            }
            superClass = superClass.getSuperclass();
        }
        return fields;
	}
	
	/**
	 * 功能：通过对象和具体的字段名字获得字段的值
	 *##### （先获取字段的get方法）
	 * */
	public static Object getFieldValueByGetMethod(Object obj, String fieldName) throws Exception {
		Object o = null;
		if (StringUtils.isNotBlank(fieldName) && null != obj) { // 字段为空直接返回空
			Class<?> clazz = obj.getClass();  
		    PropertyDescriptor pd = new PropertyDescriptor(fieldName, clazz);  
		    Method getMethod = pd.getReadMethod();// 获得get方法  
		  
	        if (pd != null) {  
	            o = getMethod.invoke(obj);//执行get方法返回一个Object  
	        }  
		}
		return o;
	}
	
	/**
	 * 功能：通过对象和具体的字段名字获得字段的值
	 *##### （对应参数为public类型的）
	 * */
	public static Object getFieldVauleByPublic(Object obj, String fieldName) {
		Object o = null;
		if (null != obj && StringUtils.isNotBlank(fieldName)) {
			Field field;
			try {
				field = obj.getClass().getDeclaredField(fieldName);
				if (null != field) {
					o = field.get(obj);
				}
			} catch (Exception e) {
				return o;
			}
		}
		return o;
	}
}
