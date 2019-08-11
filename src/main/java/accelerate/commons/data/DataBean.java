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
import accelerate.commons.util.JacksonUtils;

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
	 * Instance of {@link DataMap} for generic storage
	 */
	@JsonAnySetter
	private transient DataMap dataMap = DataMap.newMap();

	/**
	 * {@link Set} to include names of fields to exclude while serializing
	 */
	protected transient Set<String> ignoredFields = Collections.emptySet();

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
	 * This method registers the aFieldName as a field to be excluded from
	 * serialization
	 *
	 * @param aFieldNames
	 */
	public synchronized void addIgnoredFields(String... aFieldNames) {
		if (this.ignoredFields == Collections.EMPTY_SET) {
			this.ignoredFields = new HashSet<>();
		}

		for (String field : aFieldNames) {
			this.ignoredFields.add(field);
		}
	}

	/**
	 * This method remove a FieldName from the serialzation exclusion list
	 *
	 * @param aFieldNames
	 */
	public synchronized void removeIgnoredFields(String... aFieldNames) {
		if (this.ignoredFields.isEmpty()) {
			return;
		}

		for (String field : aFieldNames) {
			this.ignoredFields.remove(field);
		}
	}

	/**
	 * This method clears out the serialzation exclusion list
	 */
	public synchronized void clearIgnoredFields() {
		this.ignoredFields.clear();
	}

	/**
	 * This methods returns a JSON representation of this bean
	 *
	 * @return JSON Representation
	 * @throws ApplicationException
	 */
	public String toJSON() throws ApplicationException {
		return serialize(0);
	}

	/**
	 * This methods returns a JSON representation of this bean
	 *
	 * @return JSON Representation
	 * @throws ApplicationException
	 */
	public String toXML() throws ApplicationException {
		return serialize(1);
	}

	/**
	 * This methods returns a JSON representation of this bean
	 *
	 * @return JSON Representation
	 * @throws ApplicationException
	 */
	public String toYAML() throws ApplicationException {
		return serialize(2);
	}

	/**
	 * This methods returns a JSON representation of this bean
	 * 
	 * @param aMode
	 *              <dd>0 or any other input: JSON</dd>
	 *              <dd>1: XML</dd>
	 *              <dd>2: YAML</dd>
	 * @return
	 * @throws ApplicationException
	 */
	private String serialize(int aMode) throws ApplicationException {
		if (this.ignoredFields.isEmpty()) {
			switch (aMode) {
			case 1:
				return JacksonUtils.toXML(this);
			case 2:
				return JacksonUtils.toYAML(this);
			default:
				return JacksonUtils.toJSON(this);
			}
		}

		String[] excludedFieldArray = this.ignoredFields.toArray(new String[this.ignoredFields.size()]);
		switch (aMode) {
		case 1:
			return JacksonUtils.toXMLExcludeFields(this, excludedFieldArray);
		case 2:
			return JacksonUtils.toYAMLExcludeFields(this, excludedFieldArray);
		default:
			return JacksonUtils.toJSONExcludeFields(this, excludedFieldArray);
		}
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
	 * @throws ApplicationException
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