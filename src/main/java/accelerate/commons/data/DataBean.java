package accelerate.commons.data;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import accelerate.commons.constant.CommonConstants;
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
	private transient final DataMap dataMap = DataMap.newMap();

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
		bean.dataMap.addAll(aArgs);

		return bean;
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
		if (CommonUtils.isEmpty(aFieldNames)) {
			return;
		}

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
		if (CommonUtils.isEmpty(aFieldNames)) {
			return;
		}

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
	 * This methods returns a XML representation of this bean
	 *
	 * @return JSON Representation
	 * @throws ApplicationException
	 */
	public String toXML() throws ApplicationException {
		return serialize(1);
	}

	/**
	 * This methods returns a YAML representation of this bean
	 *
	 * @return JSON Representation
	 * @throws ApplicationException
	 */
	public String toYAML() throws ApplicationException {
		return serialize(2);
	}

	/**
	 * This methods returns a serialized representation of this bean
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
	 * Delegate Methods
	 */
	/**
	 * @param aKey
	 * @param aValue
	 * @return
	 * @see accelerate.commons.data.DataMap#add(java.lang.String, java.lang.Object)
	 */
	public DataBean add(String aKey, Object aValue) {
		this.dataMap.add(aKey, aValue);
		return this;
	}

	/**
	 * @param aSourceMap
	 * @return
	 * @see accelerate.commons.data.DataMap#addAll(java.util.Map)
	 */
	public DataBean addAll(Map<? extends String, ? extends Object> aSourceMap) {
		this.dataMap.addAll(aSourceMap);
		return this;
	}

	/**
	 * @param aArgs
	 * @return
	 * @see accelerate.commons.data.DataMap#addAll(Object...)
	 */
	public DataBean addAll(Object... aArgs) {
		this.dataMap.addAll(aArgs);
		return this;
	}

	/**
	 * @param <T>
	 * @param aKey
	 * @return
	 * @see java.util.HashMap#get(java.lang.Object)
	 */
	public <T> T get(String aKey) {
		return this.dataMap.get(aKey);
	}

	/**
	 * @param <T>
	 * @param aKey
	 * @param aDefaultValue
	 * @return
	 */
	public <T> T getOrDefault(String aKey, T aDefaultValue) {
		return this.dataMap.getOrDefault(aKey, aDefaultValue);
	}

	/**
	 * @param aKey
	 * @return
	 * @see java.util.HashMap#get(java.lang.Object)
	 */
	public String getString(String aKey) {
		Object value = this.dataMap.getOrDefault(aKey, CommonConstants.EMPTY_STRING);
		return value.toString();
	}

	/**
	 * @param <T>
	 * @param aKey
	 * @param aClass
	 * @return
	 * @see java.util.HashMap#get(java.lang.Object)
	 */
	public <T extends Number> T getNumber(String aKey, Class<T> aClass) {
		return this.dataMap.getNumber(aKey, aClass);
	}

	/**
	 * @param aKey
	 * @param aValue
	 * @return
	 * @see java.util.HashMap#get(java.lang.Object)
	 */
	public boolean checkValue(String aKey, Object aValue) {
		return this.dataMap.checkValue(aKey, aValue);
	}

	/**
	 * @param <T>
	 * @param aKey
	 * @return
	 */
	public <T> T remove(String aKey) {
		return this.dataMap.remove(aKey);
	}
}