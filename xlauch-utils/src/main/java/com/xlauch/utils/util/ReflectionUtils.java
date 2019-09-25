package com.xlauch.utils.util;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 与反射相关的工具类
 * PS：参考了Spring的异常处理。
 *
 * @author Huangxy
 * @since 2017/11/10 10:58
 */
public abstract class ReflectionUtils {

	public static Field getDeclaredField(Class<?> clazz, String fieldName){
		try {
			return clazz.getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		throw new IllegalStateException("Should never get here");
	}

	public static Method getDeclaredMethod(Class<?> clazz, String methodName){
		try {
			return clazz.getDeclaredMethod(methodName);
		} catch (NoSuchMethodException e) {
			handleReflectionException(e);
		}
		throw new IllegalStateException("Should never get here");
	}

	public static Method getGetter(Class<?> clazz, String field){
		return getDeclaredMethod(clazz, "get" + StringUtils.capitalize(field));
	}

	/**
	 * Java的坑，同名方法却没有做接口统一的处理。
	 * 因此，该方法实际上是作为一个适配器的概念存在。
	 *
	 * @param annotatedElement
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getClassElementName(AnnotatedElement annotatedElement){
		if(annotatedElement instanceof Class){
			return ((Class)annotatedElement).getName();
		}else if(annotatedElement instanceof Member){
			return ((Member)annotatedElement).getName();
		}else{
			throw new IllegalArgumentException("参数annotatedElement未被识别");
		}
	}

	/**
	 * 通过反射获取对象的字段值
	 *
	 * @param field
	 * @param target
	 * @return
	 */
	public static Object getFieldValue(Field field, Object target) {
		try {
			return field.get(target);
		}
		catch (IllegalAccessException ex) {
			handleReflectionException(ex);
		}
		throw new IllegalStateException("Should never get here");
	}

	/**
	 * 获取所有私有属性（包括父类上的）
	 *
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Field> getHierarchicalFields(Class<?> clazz) {
		if(clazz == Object.class){
			return Collections.emptyList();
		}

		List<Field> fields = new LinkedList<Field>();
		Class<?> classCopy = clazz;
		do{
			fields.addAll(Arrays.asList(classCopy.getDeclaredFields()));
			classCopy = classCopy.getSuperclass();
		}while(classCopy != Object.class);

		return fields;
	}

	/**
	 * 将私有字段，从反射层面设置为对外可访问
	 *
	 * @param field
	 */
	public static void makeAccessible(Field field) {
		if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers()) ||
				Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
			field.setAccessible(true);
		}
	}

	/**
	 * 通过反射调用方法
	 *
	 * @param clazz
	 * @param methodName
	 * @param target
	 * @param args
	 * @return
	 */
	public static Object invokeMethod(Class<?> clazz, String methodName, Object target, Object... args){
		Class<?>[] parameterTypes = new Class<?>[args.length];
		for (int i = 0; i < args.length; i++) {
			parameterTypes[i] = parameterTypes.getClass();

		}
		try {
			return clazz.getMethod(methodName, parameterTypes).invoke(target, args);
		} catch (IllegalAccessException e) {
			handleReflectionException(e);
		} catch (InvocationTargetException e) {
			handleReflectionException(e);
		} catch (NoSuchMethodException e) {
			handleReflectionException(e);
		}
		throw new IllegalStateException("Should never get here");
	}

	public static void handleReflectionException(Exception e) {
		if (e instanceof NoSuchMethodException) {
			throw new IllegalStateException("Method not found: " + e.getMessage());
		}
		if (e instanceof IllegalAccessException) {
			throw new IllegalStateException("Could not access method: " + e.getMessage());
		}
		if (e instanceof InvocationTargetException) {
			handleInvocationTargetException((InvocationTargetException) e);
		}
		if (e instanceof RuntimeException) {
			throw (RuntimeException) e;
		}
		throw new UndeclaredThrowableException(e);
	}

	public static void handleInvocationTargetException(InvocationTargetException e) {
		rethrowRuntimeException(e.getTargetException());
	}

	public static void rethrowRuntimeException(Throwable e) {
		if (e instanceof RuntimeException) {
			throw (RuntimeException) e;
		}
		if (e instanceof Error) {
			throw (Error) e;
		}
		throw new UndeclaredThrowableException(e);
	}
}
