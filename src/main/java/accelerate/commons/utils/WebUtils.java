package accelerate.commons.utils;

import java.util.Arrays;
import java.util.Collections;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import accelerate.commons.constants.CommonConstants;
import accelerate.commons.data.DataMap;

/**
 * Class providing utility methods for web app operations
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since October 20, 2018
 */
public class WebUtils {
	/**
	 * {@link Logger} instance
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(WebUtils.class);

	/**
	 * private constructor
	 */
	private WebUtils() {
	}

	/**
	 * @param aRequest
	 * @return
	 */
	public static DataMap<Object> debugRequest(HttpServletRequest aRequest) {
		DataMap<String> requestDetails = DataMap.newMap();
		requestDetails.put("contextPath", aRequest.getContextPath());
		requestDetails.put("localAddr", aRequest.getLocalAddr());
		requestDetails.put("localName", aRequest.getLocalName());
		requestDetails.put("localPort", String.valueOf(aRequest.getLocalPort()));
		requestDetails.put("pathInfo", aRequest.getPathInfo());
		requestDetails.put("pathTranslated", aRequest.getPathTranslated());
		requestDetails.put("protocol", aRequest.getProtocol());
		requestDetails.put("queryString", aRequest.getQueryString());
		requestDetails.put("remoteAddr", aRequest.getRemoteAddr());
		requestDetails.put("remoteHost", aRequest.getRemoteHost());
		requestDetails.put("remotePort", String.valueOf(aRequest.getRemotePort()));
		requestDetails.put("remoteUser", aRequest.getRemoteUser());
		requestDetails.put("requestURI", aRequest.getRequestURI());
		requestDetails.put("requestURL", aRequest.getRequestURL().toString());
		requestDetails.put("scheme", aRequest.getScheme());
		requestDetails.put("serverName", aRequest.getServerName());
		requestDetails.put("serverPort", String.valueOf(aRequest.getServerPort()));
		requestDetails.put("servletPath", aRequest.getServletPath());

		DataMap<String> requestParams = DataMap.newMap();
		aRequest.getParameterMap().entrySet().parallelStream()
				.forEach(entry -> requestParams.put(entry.getKey(), entry.getValue()[0]));

		return DataMap.newMap().putAnd("requestDetails", requestDetails).putAnd("requestParams", requestParams);
	}

	/**
	 * @param aRequest
	 * @return
	 */
	public static final DataMap<Object> debugRequestDeep(HttpServletRequest aRequest) {
		DataMap<Object> debugMap = debugRequest(aRequest);

		DataMap<String> requestAttributes = DataMap.newMap();
		Collections.list(aRequest.getAttributeNames()).parallelStream().forEach(name -> {
			var attribute = aRequest.getAttribute(name);
			var value = attribute == null ? null
					: (CommonUtils.isPrimitive(attribute) ? attribute.toString() : attribute.getClass().getName());
			requestAttributes.put(name, value);
		});
		debugMap.put("requestAttributes", requestAttributes);

		return debugMap;
	}

	/**
	 * @param aRequest {@link HttpServletRequest} instance
	 * @return
	 */
	public static final DataMap<Object> debugErrorRequest(HttpServletRequest aRequest) {
		DataMap<Object> debugMap = debugRequest(aRequest);

		DataMap<String> errorDetails = DataMap.newMap();
		errorDetails.put("requestURI",
				CommonUtils.safeToString(aRequest.getAttribute(RequestDispatcher.ERROR_REQUEST_URI)));
		errorDetails.put("errorStatusCode",
				CommonUtils.safeToString(aRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)));
		errorDetails.put("errorMessage",
				CommonUtils.safeToString(aRequest.getAttribute(RequestDispatcher.ERROR_MESSAGE)));
		errorDetails.put("errorType",
				CommonUtils.safeToString(aRequest.getAttribute(RequestDispatcher.ERROR_EXCEPTION_TYPE)));
		errorDetails.put("errorStackTrace",
				CommonUtils.getErrorLog((Throwable) aRequest.getAttribute(RequestDispatcher.ERROR_EXCEPTION)));
		debugMap.put("errorDetails", errorDetails);

		return debugMap;
	}

	/**
	 * @param aRequest
	 * @param aResponse
	 * @param aCookieList
	 * @throws ServletException
	 */
	public static final void logout(HttpServletRequest aRequest, HttpServletResponse aResponse, String... aCookieList)
			throws ServletException {
		HttpSession session = aRequest.getSession(false);
		if (session != null) {
			LOGGER.debug("Invalidating session[{}] for user [{}]", session.getId(), aRequest.getRemoteUser());
			session.invalidate();
		}

		aRequest.logout();
		deleteCookies(aRequest, aResponse, (aCookieList != null) ? aCookieList : new String[] { "JSESSIONID" });
	}

	/**
	 * @param aRequest
	 * @param aResponse
	 * @param aCookieList
	 */
	public static final void deleteCookies(HttpServletRequest aRequest, HttpServletResponse aResponse,
			String... aCookieList) {

		Arrays.stream(aCookieList).map(cookieName -> {
			LOGGER.debug("Resetting cookie [{}]", cookieName);
			Cookie cookie = new Cookie(cookieName, null);
			cookie.setPath(StringUtils.defaultString(aRequest.getContextPath(), CommonConstants.UNIX_PATH_CHAR));
			cookie.setMaxAge(0);
			return cookie;
		}).forEach(cookie -> aResponse.addCookie(cookie));
	}
}
