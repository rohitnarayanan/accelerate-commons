package accelerate.commons.data;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import accelerate.commons.exception.ApplicationException;
import accelerate.commons.util.CommonUtils;
import accelerate.commons.util.JSONUtils;

/**
 * Generic Data Bean class with embedded {@link DataMap} for extensibility
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since January 14, 2015
 */
public class DataBean implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Name of the id field for the bean
	 */
	private transient String idField;

//	/**
//	 * Flag to indicate that the bean stores a huge amount of data, so exception
//	 * handlers and interceptors can avoid serializing the entire bean
//	 */
//	private transient boolean largeDataset;

	/**
	 * Instance of {@link DataMap} for generic storage
	 */
	@JsonAnySetter
	private DataMap dataMap = DataMap.newMap();

	/**
	 * {@link Set} to include names of fields to exclude while serializing
	 */
	protected transient Set<String> jsonIgnoreFields = Collections.emptySet();

	/*
	 * Static Methods
	 */
	/**
	 * Static shortcut method to build a new instance
	 * 
	 * @param aArgs
	 * @return
	 */
	public static final DataBean newBean(Object... aArgs) {
		DataBean bean = new DataBean();
		if (!CommonUtils.isEmpty(aArgs)) {
			bean.dataMap.putAllAnd(aArgs);
		}

		return bean;
	}

	/**
	 * default constructor
	 */
	public DataBean() {
	}

	/**
	 * alternate constructor to set id field
	 * 
	 * @param aIdField
	 */
	public DataBean(String aIdField) {
		this.idField = aIdField;
	}

	/*
	 * Public API
	 */
	/**
	 * Getter method for "dataMap" property
	 * 
	 * @return dataMap
	 */
	@JsonAnyGetter
	public DataMap getDataMap() {
		return this.dataMap;
	}

	/**
	 * This method registers the aFieldName as a field to be excluded from logging
	 *
	 * @param aFieldNames
	 */
	public synchronized void addJsonIgnoreFields(String... aFieldNames) {
		if (this.jsonIgnoreFields == Collections.EMPTY_SET) {
			this.jsonIgnoreFields = new HashSet<>();
		}

		for (String field : aFieldNames) {
			this.jsonIgnoreFields.add(field);
		}
	}

	/**
	 * This method registers the aFieldName as a field to be excluded from logging
	 *
	 * @param aFieldNames
	 */
	public synchronized void removeJsonIgnoreFields(String... aFieldNames) {
		if (this.jsonIgnoreFields.isEmpty()) {
			return;
		}

		for (String field : aFieldNames) {
			this.jsonIgnoreFields.remove(field);
		}
	}

	/**
	 * This methods returns a JSON representation of this bean
	 *
	 * @return JSON Representation
	 * @throws ApplicationException thrown due to {@link #toJSON(boolean)}
	 */
	public String toJSON() throws ApplicationException {
		return toJSON(false);
	}

	/**
	 * This methods returns a JSON representation of this bean
	 * 
	 * @param aForce
	 * @return
	 * @throws ApplicationException thrown due to
	 *                              {@link JSONUtils#serialize(Object)}
	 */
	public String toJSON(@SuppressWarnings("unused") boolean aForce) throws ApplicationException {
//		if (this.largeDataset && !aForce) {
//			return toShortJSON();
//		}

		if (this.jsonIgnoreFields.isEmpty()) {
			return JSONUtils.serialize(this);
		}

		return JSONUtils.serializeExcept(this, this.jsonIgnoreFields.toArray(new String[this.jsonIgnoreFields.size()]));
	}

	/**
	 * This method return a short JSON representation of this bean to save memory or
	 * disk space
	 *
	 * @return log string
	 * @throws ApplicationException thrown due to
	 *                              {@link JSONUtils#serialize(Object)}
	 */
	public String toShortJSON() throws ApplicationException {
		return (this.idField != null) ? JSONUtils.serializeOnly(this, this.idField)
				: JSONUtils.buildJSON("id", super.toString());
	}

	/*
	 * Override Methods
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	/**
	 * @return
	 * @throws ApplicationException thrown due to {@link #toJSON()}
	 */
	@Override
	public String toString() throws ApplicationException {
		return toJSON();
	}

	/*
	 * Delegate Methods
	 */
	/**
	 * @param aKey
	 * @param aValue
	 * @return
	 * @see accelerate.commons.data.DataMap#putAnd(java.lang.String,
	 *      java.lang.Object)
	 */
	public DataBean putAnd(String aKey, Object aValue) {
		this.dataMap.putAnd(aKey, aValue);

		return this;
	}

	/**
	 * @param aSourceMap
	 * @return
	 * @see accelerate.commons.data.DataMap#putAllAnd(java.util.Map)
	 */
	public DataBean putAllAnd(Map<? extends String, ? extends Object> aSourceMap) {
		this.dataMap.putAllAnd(aSourceMap);

		return this;
	}

	/**
	 * @param aArgs
	 * @return
	 * @see accelerate.commons.data.DataMap#putAllAnd(Object...)
	 */
	public DataBean putAllAnd(Object... aArgs) {
		for (int idx = 0; idx < aArgs.length; idx += 2) {
			this.dataMap.put((String) aArgs[idx], aArgs[idx + 1]);
		}

		return this;
	}

	/**
	 * @param <T>
	 * @param aKey
	 * @return
	 * @see accelerate.commons.data.DataMap#get(java.lang.String)
	 */
	public <T> T get(String aKey) {
		return this.dataMap.get(aKey);
	}

	/**
	 * @param aKey
	 * @return
	 * @see accelerate.commons.data.DataMap#getString(java.lang.String)
	 */
	public String getString(String aKey) {
		return this.dataMap.getString(aKey);
	}

	/**
	 * @param aKey
	 * @return
	 * @see accelerate.commons.data.DataMap#getInt(java.lang.String)
	 */
	public Integer getInt(String aKey) {
		return this.dataMap.getInt(aKey);
	}

	/**
	 * @param aKey
	 * @param aValue
	 * @return
	 * @see accelerate.commons.data.DataMap#checkValue(java.lang.String,
	 *      java.lang.Object)
	 */
	public boolean checkValue(String aKey, Object aValue) {
		return this.dataMap.checkValue(aKey, aValue);
	}
}