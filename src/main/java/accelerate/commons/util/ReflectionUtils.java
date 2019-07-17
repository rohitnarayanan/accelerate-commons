package accelerate.commons.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import accelerate.commons.exception.ApplicationException;

/**
 * Class providing utility methods for reflection operations
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since January 14, 2015
 */
public final class ReflectionUtils {
	/**
	 * @param aTargetClass
	 * @param aTargetInstance
	 * @param aTargetField
	 * @return
	 * @throws ApplicationException - Wrapping the following exceptions thrown due
	 *                              to {@link Field} operations -
	 *                              {@link IllegalArgumentException} |
	 *                              {@link IllegalAccessException} |
	 *                              {@link NoSuchFieldException} |
	 *                              {@link SecurityException}
	 */
	public static Object getFieldValue(Class<?> aTargetClass, Object aTargetInstance, String aTargetField)
			throws ApplicationException {
		try {
			Field field = aTargetClass.getField(aTargetField);
			if (field == null) {
				throw new NoSuchFieldException("Field " + aTargetField + " not found.");
			}

			boolean accessible = field.canAccess(aTargetInstance);
			field.setAccessible(true);
			Object value = field.get(aTargetInstance);
			field.setAccessible(accessible);

			return value;
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException error) {
			throw new ApplicationException(error);
		}
	}

	/**
	 * @param aTargetInstance
	 * @param aTargetField
	 * @return
	 * @throws ApplicationException - Wrapping the following exceptions thrown due
	 *                              to {@link Field} operations -
	 *                              {@link IllegalArgumentException} |
	 *                              {@link IllegalAccessException} |
	 *                              {@link SecurityException}
	 */
	public static Object getFieldValue(Object aTargetInstance, Field aTargetField) throws ApplicationException {
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
	 * @param aTargetClass
	 * @param aTargetInstance
	 * @param aTargetField
	 * @param aFieldValue
	 * @throws ApplicationException on {@link Field} operations
	 * @throws ApplicationException - Wrapping the following exceptions thrown due
	 *                              to {@link Field} operations -
	 *                              {@link IllegalArgumentException} |
	 *                              {@link IllegalAccessException} |
	 *                              {@link NoSuchFieldException} |
	 *                              {@link SecurityException}
	 */
	public static void setFieldValue(Class<?> aTargetClass, Object aTargetInstance, String aTargetField,
			Object aFieldValue) throws ApplicationException {
		try {
			Field field = aTargetClass.getField(aTargetField);
			if (field == null) {
				throw new NoSuchFieldException("Field " + aTargetField + " not found.");
			}

			boolean accessible = field.canAccess(aTargetInstance);
			field.setAccessible(true);
			field.set(aTargetInstance, aFieldValue);
			field.setAccessible(accessible);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException error) {
			throw new ApplicationException(error);
		}
	}

	/**
	 * @param aTargetClass
	 * @param aTargetInstance
	 * @param aTargetMethodName
	 * @param aMethodArgTypes
	 * @param aMethodArgs
	 * @return
	 * @throws ApplicationException - Wrapping the following exceptions thrown due
	 *                              to {@link Method} operations -
	 *                              {@link NoSuchMethodException} |
	 *                              {@link SecurityException} |
	 *                              {@link IllegalAccessException} |
	 *                              {@link IllegalArgumentException} |
	 *                              {@link InvocationTargetException}
	 * 
	 */
	public static Object invokeMethod(Class<?> aTargetClass, Object aTargetInstance, String aTargetMethodName,
			Class<?>[] aMethodArgTypes, Object[] aMethodArgs) throws ApplicationException {
		try {
			Method method = aTargetClass.getMethod(aTargetMethodName, aMethodArgTypes);
			if (aMethodArgTypes == null) {
				throw new NoSuchMethodException("Method " + aTargetMethodName + " not found.");
			}

			boolean accessible = method.canAccess(aTargetInstance);
			method.setAccessible(true);
			Object value = method.invoke(aTargetInstance, aMethodArgs);
			method.setAccessible(accessible);

			return value;
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException error) {
			throw new ApplicationException(error);
		}
	}

	/**
	 * @param aTargetInstance
	 * @param aTargetField
	 * @return String
	 * @throws ApplicationException thrown by
	 *                              {@link #invokeMethod(Class, Object, String, Class[], Object[])}
	 */
	public static Object invokeGetter(Object aTargetInstance, String aTargetField) throws ApplicationException {
		return invokeMethod(aTargetInstance.getClass(), aTargetInstance, "get" + StringUtils.capitalize(aTargetField),
				(Class<?>[]) null, (Object[]) null);
	}

	/**
	 * @param aTargetInstance
	 * @param aTargetField
	 * @param aFieldValue
	 * @throws ApplicationException thrown by
	 *                              {@link #invokeMethod(Class, Object, String, Class[], Object[])}
	 */
	public static void invokeSetter(Object aTargetInstance, String aTargetField, Object aFieldValue)
			throws ApplicationException {
		invokeMethod(aTargetInstance.getClass(), aTargetInstance, "set" + StringUtils.capitalize(aTargetField),
				new Class<?>[] { aFieldValue.getClass() }, new Object[] { aFieldValue });
	}

	/**
	 * hidden constructor
	 */
	private ReflectionUtils() {
	}
}