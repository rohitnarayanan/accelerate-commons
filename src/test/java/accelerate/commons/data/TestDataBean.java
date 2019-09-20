package accelerate.commons.data;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import accelerate.commons.constant.CommonTestConstants;

/**
 * {@link DataBean} extension for Tests
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since August 16, 2019
 */
public class TestDataBean extends DataBean {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Field 1
	 */
	@JacksonXmlProperty(isAttribute = true)
	private String beanId = null;

	/**
	 * Field 2
	 */
	private String beanName = null;

	/**
	 * default constructor
	 */
	public TestDataBean() {
		this.beanId = CommonTestConstants.BEAN_ID_VALUE;
		this.beanName = CommonTestConstants.BEAN_NAME_VALUE;
	}

	/**
	 * overloaded constructor
	 * 
	 * @param aBeanId
	 * @param aBeanName
	 */
	public TestDataBean(String aBeanId, String aBeanName) {
		this.beanId = aBeanId;
		this.beanName = aBeanName;
	}

	/**
	 * Getter method for "beanId" property
	 * 
	 * @return beanId
	 */
	public String getBeanId() {
		return this.beanId;
	}

	/**
	 * Setter method for "beanId" property
	 * 
	 * @param aBeanId
	 */
	public void setBeanId(String aBeanId) {
		this.beanId = aBeanId;
	}

	/**
	 * Getter method for "beanName" property
	 * 
	 * @return beanName
	 */
	public String getBeanName() {
		return this.beanName;
	}

	/**
	 * Setter method for "beanName" property
	 * 
	 * @param aBeanName
	 */
	public void setBeanName(String aBeanName) {
		this.beanName = aBeanName;
	}
}