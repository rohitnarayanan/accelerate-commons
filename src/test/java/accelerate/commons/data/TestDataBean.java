package accelerate.commons.data;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import accelerate.commons.constant.CommonTestConstants;

@SuppressWarnings("javadoc")
public class TestDataBean extends DataBean {
	private static final long serialVersionUID = 1L;

	@JacksonXmlProperty(isAttribute = true)
	private String beanId = null;
	private String beanName = null;

	/**
	 * @param aBeanId
	 * @param aBeanValue
	 */
	public TestDataBean() {
		this.beanId = CommonTestConstants.BEAN_ID_VALUE;
		this.beanName = CommonTestConstants.BEAN_NAME_VALUE;
	}

	public String getBeanId() {
		return this.beanId;
	}

	public void setBeanId(String aBeanId) {
		this.beanId = aBeanId;
	}

	public String getBeanName() {
		return this.beanName;
	}

	public void setBeanName(String aBeanValue) {
		this.beanName = aBeanValue;
	}
}