package com.ytoxl.module.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taobao.api.internal.util.WebUtils;





public class BeanUtilsTest {
	protected static Logger log = LoggerFactory.getLogger(BeanUtilsTest.class);
	
	public static void main(String[] args) {
		Stu stu = new Stu("wanx",20);
		Stu s1 = new Stu("田成",25);
		Stu s2 = new Stu("华玉才",22); 
		List<Stu> list = new ArrayList<Stu>();
		list.add(s2);
		list.add(s1);
		
		
		StuItem item = new StuItem();
		item.setNames("学生");
		item.setStu(list);
		try {
			Map<String,String> map = BeanUtilsTest.toHashMap(item);
			System.out.println(BeanUtilsTest.toHashMap(item));
			/*try {
				WebUtils.doPost(null, map, 0, 0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		try {
			log.info(BeanUtilsTest.convertBean2(item).toString());
		} catch (IntrospectionException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e){
		}
//		
//		JSONObject json = JSONObject.fromObject(item);
//		log.info(json.toString());
//		Stu s1 = new Stu("田成",25);
//		Stu s2 = new Stu("华玉才",22); 
//		List<Stu> list = new ArrayList<Stu>();
//		list.add(s2);
//		list.add(s1);
//		
//		StuItem item = new StuItem();
//		item.setNames("学生");
//		item.setStu(list);
//		
//		JSONObject json = JSONObject.fromObject(item);
//		log.info(json.toString());
		
	}
	
	/**
     * 将一个 JavaBean 对象转化为一个  Map
     * @param bean 要转化的JavaBean 对象
     * @return 转化出来的  Map 对象
     * @throws IntrospectionException 如果分析类属性失败
     * @throws IllegalAccessException 如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    public static Map convertBean(Object bean)throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Class type = bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
 
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }
        return returnMap;
    }
    
    public static Map convertBean2(Object bean)throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Class type = bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            Class propertyNameType = descriptor.getPropertyType();
            if (!propertyName.equals("class") && !propertyNameType.equals("java.util.List")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null) {
                	//如果是list
                	if(result instanceof List){
                    	List list = (List)result;
                    	for(Object obj : list){
                    		if(returnMap.containsKey(propertyName)){
                    			returnMap.put(propertyName, returnMap.get(propertyName)+"|"+obj);
                    		}else{
                    			returnMap.put(propertyName, obj);
                    		}
                    		
                    	}
                    }
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }
        return returnMap;
    }
    
    public static Map<String,String> toHashMap(StuItem bean)throws IntrospectionException, IllegalAccessException, InvocationTargetException {
    	Map<String,String> returnMap = new HashMap<String,String>();
    	returnMap=convertBean(bean);
    	returnMap.remove("stu");
    	List<Stu> list = new ArrayList<Stu>();
    	list = bean.getStu();
    	for(int i=0;i<list.size();i++){
    		Stu s = list.get(i);
    		Map map = convertBean(s);
    		Set set = map.keySet();
    		if(set!=null){
    			Iterator iterator = set.iterator();
    			while(iterator.hasNext()){
    				String key = iterator.next().toString();
    				Object value = map.get(key);
    				if(returnMap.get(key)!=null){
    	    			returnMap.put(key, returnMap.get(key)+"|"+value);
    	    		}else{
    	    		returnMap.put(key,value.toString());
    	    		}
    			}
    		}
    	}
		return returnMap;
	}
    

    /**
     * 将一个 Map 对象转化为一个 JavaBean
     * @param type 要转化的类型
     * @param map 包含属性值的 map
     * @return 转化出来的 JavaBean 对象
     * @throws IntrospectionException
     *             如果分析类属性失败
     * @throws IllegalAccessException
     *             如果实例化 JavaBean 失败
     * @throws InstantiationException
     *             如果实例化 JavaBean 失败
     * @throws InvocationTargetException
     *             如果调用属性的 setter 方法失败
     */
    public static Object convertMap(Class type, Map map)throws IntrospectionException, IllegalAccessException,InstantiationException, InvocationTargetException {
        BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
        Object obj = type.newInstance(); // 创建 JavaBean 对象
 
        // 给 JavaBean 对象的属性赋值
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
 
            if (map.containsKey(propertyName)) {
                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
                Object value = map.get(propertyName);
 
                Object[] args = new Object[1];
                args[0] = value;
 
                descriptor.getWriteMethod().invoke(obj, args);
            }
        }
        return obj;
    }
}