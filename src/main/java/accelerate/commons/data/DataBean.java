package accelerate.commons.data;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import accelerate.commons.exceptions.ApplicationException;
import accelerate.commons.utils.JSONUtils;

/**
 * Generic Data Bean class with embedded {@link DataMap} for extensibility
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since October 2, 2017
 */
@JsonFilter("default")
public class DataBean implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Name of the id field for the bean
	 */
	private transient String idField;

	/**
	 * {@link Set} to include names of fields to exclude while logging
	 */
	private transient Set<String> jsonIgnoreFields = Collections.emptySet();

	/**
	 * Flag to indicate that the bean stores a huge amount of data, so exception
	 * handlers and interceptors can avoid serializing the entire bean
	 */
	private transient boolean largeDataset;

	/**
	 * Instance of {@link DataMap} for generic storage
	 */
	@JsonIgnore
	private DataMap<Object> dataMap;

	/*
	 * Public API
	 */
	/**
	 * Enabling method chaining
	 * 
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
	public String toJSON(boolean aForce) throws ApplicationException {
		if (this.largeDataset && !aForce) {
			return toShortJSON();
		}

		if (this.jsonIgnoreFields.isEmpty()) {
			return JSONUtils.serialize(this);
		}

		return JSONUtils.serializeExcept(this, this.jsonIgnoreFields.stream().toArray(String[]::new));
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
				: String.format("{\"id\":\"%s\"}", super.toString());
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

	/*
	 * Delegate methods
	 */
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
	 * @see accelerate.commons.data.DataMap#getInteger(java.lang.String)
	 */
	public Integer getInt(String aKey) {
		return this.dataMap.getInteger(aKey);
	}

	/**
	 * @param aKey
	 * @return
	 * @see accelerate.commons.data.DataMap#getBoolean(java.lang.String)
	 */
	public boolean getBoolean(String aKey) {
		return this.dataMap.getBoolean(aKey);
	}

	/**
	 * @return
	 * @see java.util.HashMap#size()
	 */
	public int size() {
		return this.dataMap.size();
	}

	/**
	 * @return
	 * @see java.util.HashMap#isEmpty()
	 */
	public boolean isEmpty() {
		return this.dataMap.isEmpty();
	}

	/**
	 * @param aKey
	 * @return
	 * @see java.util.HashMap#get(java.lang.Object)
	 */
	public Object get(String aKey) {
		return this.dataMap.get(aKey);
	}

	/**
	 * @param aKey
	 * @return
	 * @see java.util.HashMap#containsKey(java.lang.Object)
	 */
	public boolean containsKey(String aKey) {
		return this.dataMap.containsKey(aKey);
	}

	/**
	 * @param aKey
	 * @param aValue
	 * @return
	 * @see java.util.HashMap#put(java.lang.Object, java.lang.Object)
	 */
	public Object put(String aKey, Object aValue) {
		return this.dataMap.put(aKey, aValue);
	}

	/**
	 * @param aM
	 * @see java.util.HashMap#putAll(java.util.Map)
	 */
	public void putAll(Map<? extends String, ? extends Object> aM) {
		this.dataMap.putAll(aM);
	}

	/**
	 * @param aKey
	 * @return
	 * @see java.util.HashMap#remove(java.lang.Object)
	 */
	public Object remove(String aKey) {
		return this.dataMap.remove(aKey);
	}

	/**
	 * 
	 * @see java.util.HashMap#clear()
	 */
	public void clear() {
		this.dataMap.clear();
	}

	/**
	 * @param aValue
	 * @return
	 * @see java.util.HashMap#containsValue(java.lang.Object)
	 */
	public boolean containsValue(Object aValue) {
		return this.dataMap.containsValue(aValue);
	}

	/**
	 * @return
	 * @see java.util.HashMap#keySet()
	 */
	public Set<String> keySet() {
		return this.dataMap.keySet();
	}

	/**
	 * @return
	 * @see java.util.HashMap#values()
	 */
	public Collection<Object> values() {
		return this.dataMap.values();
	}

	/**
	 * @return
	 * @see java.util.HashMap#entrySet()
	 */
	public Set<Entry<String, Object>> entrySet() {
		return this.dataMap.entrySet();
	}

	/**
	 * @param aKey
	 * @param aDefaultValue
	 * @return
	 * @see java.util.HashMap#getOrDefault(java.lang.Object, java.lang.Object)
	 */
	public Object getOrDefault(Object aKey, Object aDefaultValue) {
		return this.dataMap.getOrDefault(aKey, aDefaultValue);
	}

	/**
	 * @param aKey
	 * @param aValue
	 * @return
	 * @see java.util.HashMap#putIfAbsent(java.lang.Object, java.lang.Object)
	 */
	public Object putIfAbsent(String aKey, Object aValue) {
		return this.dataMap.putIfAbsent(aKey, aValue);
	}

	/**
	 * @param aKey
	 * @param aValue
	 * @return
	 * @see java.util.HashMap#remove(java.lang.Object, java.lang.Object)
	 */
	public boolean remove(Object aKey, Object aValue) {
		return this.dataMap.remove(aKey, aValue);
	}

	/**
	 * @param aKey
	 * @param aOldValue
	 * @param aNewValue
	 * @return
	 * @see java.util.HashMap#replace(java.lang.Object, java.lang.Object,
	 *      java.lang.Object)
	 */
	public boolean replace(String aKey, Object aOldValue, Object aNewValue) {
		return this.dataMap.replace(aKey, aOldValue, aNewValue);
	}

	/**
	 * @param aKey
	 * @param aValue
	 * @return
	 * @see java.util.HashMap#replace(java.lang.Object, java.lang.Object)
	 */
	public Object replace(String aKey, Object aValue) {
		return this.dataMap.replace(aKey, aValue);
	}

	/*
	 * Constructors
	 */
	/**
	 * default constructor
	 */
	public DataBean() {
		this.dataMap = new DataMap<>();
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
	 * Getters/Setters
	 */
	/**
	 * Getter method for "idField" property
	 * 
	 * @return idField
	 */
	public String getIdField() {
		return this.idField;
	}

	/**
	 * Setter method for "idField" property
	 * 
	 * @param aIdField
	 */
	public void setIdField(String aIdField) {
		this.idField = aIdField;
	}

	/**
	 * Getter method for "largeDataset" property
	 * 
	 * @return largeDataset
	 */
	public boolean isLargeDataset() {
		return this.largeDataset;
	}

	/**
	 * Setter method for "largeDataset" property
	 * 
	 * @param aLargeDataset
	 */
	public void setLargeDataset(boolean aLargeDataset) {
		this.largeDataset = aLargeDataset;
	}

	/**
	 * Getter method for "jsonIgnoreFields" property
	 * 
	 * @return jsonIgnoreFields
	 */
	public Set<String> getJsonIgnoreFields() {
		return this.jsonIgnoreFields;
	}

	/**
	 * Getter method for "dataMap" property
	 * 
	 * @return dataMap
	 */
	@JsonAnyGetter
	public DataMap<Object> getDataMap() {
		return this.dataMap;
	}
}