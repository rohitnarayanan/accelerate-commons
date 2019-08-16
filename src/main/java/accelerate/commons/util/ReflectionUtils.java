package accelerate.commons.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

import accelerate.commons.exception.ApplicationException;

/**
 * Class providing utility methods for reflection operations
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since October 2, 2017
 */
public final class ReflectionUtils {
	/**
	 * @param aTargetInstance
	 * @param aFieldName
	 * @return
	 * @throws ApplicationException Wrapping exceptions thrown by reflection
	 *                              operations
	 */
	public static Object getFieldValue(Object aTargetInstance, String aFieldName) throws ApplicationException {
		Field field = getField(aTargetInstance, aFieldName);
		if (field == null) {
			throw new ApplicationException("Field '{}' not found", aFieldName);
		}

		return getFieldValue(aTargetInstance, field);
	}

	/**
	 * @param aTargetClass
	 * @param aFieldName
	 * @return
	 * @throws ApplicationException Wrapping exceptions thrown by reflection
	 *                              operations
	 */
	public static Object getStaticFieldValue(Class<?> aTargetClass, String aFieldName) throws ApplicationException {
		Field field = getField(aTargetClass, aFieldName);
		if (field == null) {
			throw new ApplicationException("Field '{}' not found", aFieldName);
		}

		return getFieldValue(null, field);
	}

	/**
	 * @param aTargetInstance
	 * @param aTargetField
	 * @return
	 * @throws ApplicationException Wrapping exceptions thrown by reflection
	 *                              operations
	 */
	private static Object getFieldValue(Object aTargetInstance, Field aTargetField) throws ApplicationException {
		try {
			boolean accessible = aTargetField.canAccess(aTargetInstance);
			aTargetField.setAccessible(true);
			Object value = aTargetField.get(aTargetInstance);
			aTargetField.setAccessible(accessible);

			return value;
		} catch (IllegalArgumentException | IllegalAccessException | SecurityException error) {
			throw new ApplicationException(error);
		}
	}

	/**
	 * This method searches the given object to find the field.
	 * 
	 * @param aTargetInstance
	 * @param aFieldName
	 * @return
	 * @throws ApplicationException Wrapping exceptions thrown by reflection
	 *                              operations
	 */
	public static Field getField(Object aTargetInstance, String aFieldName) throws ApplicationException {
		return getField(aTargetInstance.getClass(), aFieldName);
	}

	/**
	 * This method searches the given Class and all its superclasses to find the
	 * field.
	 * 
	 * @param aTargetClass
	 * @param aFieldName
	 * @return
	 * @throws ApplicationException Wrapping exceptions thrown by reflection
	 *                              operations
	 */
	public static Field getField(Class<?> aTargetClass, String aFieldName) throws ApplicationException {
		try {
			Class<?> searchType = aTargetClass;
			while (Object.class != searchType && searchType != null) {
				Optional<Field> field = Arrays.stream(searchType.getDeclaredFields())
						.filter(aField -> CommonUtils.compare(aFieldName, aField.getName())).findFirst();
				if (field.isPresent()) {
					return field.get();
				}

				searchType = searchType.getSuperclass();
			}

			return null;

		} catch (IllegalArgumentException | SecurityException error) {
			throw new ApplicationException(error);
		}
	}

	/**
	 * @param aTargetInstance
	 * @param aFieldName
	 * @param aFieldValue
	 * @throws ApplicationException Wrapping exceptions thrown by reflection
	 *                              operations
	 */
	public static void setFieldValue(Object aTargetInstance, String aFieldName, Object aFieldValue)
			throws ApplicationException {
		Field field = getField(aTargetInstance.getClass(), aFieldName);
		if (field == null) {
			throw new ApplicationException("Field '{}' not found", aFieldName);
		}

		setFieldValue(aTargetInstance, field, aFieldValue);
	}

	/**
	 * @param aTargetClass
	 * @param aFieldName
	 * @param aFieldValue
	 * @throws ApplicationException Wrapping exceptions thrown by reflection
	 *                              operations
	 */
	public static void setStaticFieldValue(Class<?> aTargetClass, String aFieldName, Object aFieldValue)
			throws ApplicationException {
		Field field = getField(aTargetClass, aFieldName);
		if (field == null) {
			throw new ApplicationException("Field '{}' not found", aFieldName);
		}

		setFieldValue(null, field, aFieldValue);
	}

	/**
	 * @param aTargetInstance
	 * @param aTargetField
	 * @param aFieldValue
	 * @throws ApplicationException Wrapping exceptions thrown by reflection
	 *                              operations
	 */
	private static void setFieldValue(Object aTargetInstance, Field aTargetField, Object aFieldValue)
			throws ApplicationException {
		try {
			boolean accessible = aTargetField.canAccess(aTargetInstance);
			aTargetField.setAccessible(true);
			aTargetField.set(aTargetInstance, aFieldValue);
			aTargetField.setAccessible(accessible);
		} catch (IllegalArgumentException | IllegalAccessException | SecurityException error) {
			throw new ApplicationException(error);
		}
	}

	/**
	 * @param aTargetInstance
	 * @param aTargetField
	 * @return String
	 * @throws ApplicationException Wrapping exceptions thrown by reflection
	 *                              operations
	 */
	public static Object invokeGetter(Object aTargetInstance, String aTargetField) throws ApplicationException {
		return invokeMethod(aTargetInstance, "get" + StringUtils.capitalize(aTargetField), new Class<?>[] {},
				(Object[]) null);
	}

	/**
	 * @param aTargetInstance
	 * @param aTargetField
	 * @param aFieldValue
	 * @throws ApplicationException Wrapping exceptions thrown by reflection
	 *                              operations
	 */
	public static void invokeSetter(Object aTargetInstance, String aTargetField, Object aFieldValue)
			throws ApplicationException {
		invokeMethod(aTargetInstance, "set" + StringUtils.capitalize(aTargetField),
				new Class<?>[] { aFieldValue.getClass() }, new Object[] { aFieldValue });
	}

	/**
	 * @param aTargetInstance
	 * @param aMethodName
	 * @param aMethodArgTypes
	 * @param aMethodArgs
	 * @return
	 * @throws ApplicationException Wrapping exceptions thrown by reflection
	 *                              operations
	 * 
	 */
	public static Object invokeMethod(Object aTargetInstance, String aMethodName, Class<?>[] aMethodArgTypes,
			Object[] aMethodArgs) throws ApplicationException {
		Method method = findMethod(aTargetInstance, aMethodName, aMethodArgTypes);
		if (method == null) {
			throw new ApplicationException("Method '{}' not found", aMethodName);
		}

		return invokeMethod(aTargetInstance, method, aMethodArgs);
	}

	/**
	 * @param aTargetClass
	 * @param aMethodName
	 * @param aMethodArgTypes
	 * @param aMethodArgs
	 * @return
	 * @throws ApplicationException Wrapping exceptions thrown by reflection
	 *                              operations
	 * 
	 */
	public static Object invokeStaticMethod(Class<?> aTargetClass, String aMethodName, Class<?>[] aMethodArgTypes,
			Object[] aMethodArgs) throws ApplicationException {
		Method method = findMethod(aTargetClass, aMethodName, aMethodArgTypes);
		if (method == null) {
			throw new ApplicationException("Method '{}' not found", aMethodName);
		}

		return invokeMethod(null, method, aMethodArgs);
	}

	/**
	 * @param aTargetInstance
	 * @param aTargetMethod
	 * @param aMethodArgs
	 * @return
	 * @throws ApplicationException Wrapping exceptions thrown by reflection
	 *                              operations
	 * 
	 */
	private static Object invokeMethod(Object aTargetInstance, Method aTargetMethod, Object[] aMethodArgs)
			throws ApplicationException {
		try {
			boolean accessible = aTargetMethod.canAccess(aTargetInstance);
			aTargetMethod.setAccessible(true);
			Object value = aTargetMethod.invoke(aTargetInstance, aMethodArgs);
			aTargetMethod.setAccessible(accessible);

			return value;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException error) {
			throw new ApplicationException(error);
		}
	}

	/**
	 * @param aTargetInstance
	 * @param aMethodName
	 * @param aMethodArgTypes
	 * @return
	 * @throws ApplicationException Wrapping exceptions thrown by reflection
	 *                              operations
	 * 
	 */
	public static Method findMethod(Object aTargetInstance, String aMethodName, Class<?>[] aMethodArgTypes)
			throws ApplicationException {
		return findMethod(aTargetInstance.getClass(), aMethodName, aMethodArgTypes);
	}

	/**
	 * @param aTargetClass
	 * @param aMethodName
	 * @param aMethodArgTypes
	 * @return
	 * @throws ApplicationException Wrapping exceptions thrown by reflection
	 *                              operations
	 * 
	 */
	public static Method findMethod(Class<?> aTargetClass, String aMethodName, Class<?>[] aMethodArgTypes)
			throws ApplicationException {

		try {
			Class<?> searchType = aTargetClass;
			while (Object.class != searchType && searchType != null) {
				Optional<Method> method = Arrays.stream(searchType.getDeclaredMethods())
						.filter(aMethod -> CommonUtils.compare(aMethodName, aMethod.getName())
								&& Arrays.equals(aMethodArgTypes, aMethod.getParameterTypes()))
						.findFirst();
				if (method.isPresent()) {
					return method.get();
				}

				searchType = searchType.getSuperclass();
			}

			return null;
		} catch (IllegalArgumentException | SecurityException error) {
			throw new ApplicationException(error);
		}
	}

	/**
	 * hidden constructor
	 */
	private ReflectionUtils() {
	}
}