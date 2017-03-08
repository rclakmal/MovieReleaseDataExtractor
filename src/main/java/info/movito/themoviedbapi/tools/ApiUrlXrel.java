package info.movito.themoviedbapi.tools;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.net.URL;

import org.apache.commons.lang3.StringUtils;

import info.movito.themoviedbapi.AbstractTmdbApi;

public class ApiUrlXrel {

	private static final String XREL_API_BASE = "https://api.xrel.to/v2/";
	private static final String APPEND_TO_RESPONSE = "append_to_response";

	private String baseUrl;

	public ApiUrlXrel(String urlParts) {
		baseUrl = XREL_API_BASE + urlParts;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public URL buildUrl() {
		StringBuilder urlBuilder = new StringBuilder(baseUrl);

		try {

			return new URL(urlBuilder.toString());

		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	public void addParam(String name, Object value) {
	}

	public void addParam(String name, String value) {

	}

	/**
	 * Add argument
	 *
	 * @param key
	 * @param value
	 */
	public void addParam(String key, int value) {
	}

	/**
	 * Add argument
	 *
	 * @param key
	 * @param value
	 */
	public void addParam(String key, boolean value) {
		addParam(key, Boolean.toString(value));
	}

	/**
	 * Convenience wrapper around addArgument
	 *
	 * @param appendToResponse
	 *            Comma separated, any movie method
	 */
	public void appendToResponse(String... appendToResponse) {
		if (appendToResponse == null || appendToResponse.length == 0) {
			return;
		}

		addParam(APPEND_TO_RESPONSE, StringUtils.join(appendToResponse, ","));
	}

	public void addPage(Integer page) {
		if (page != null && page > 0) {
			addParam(AbstractTmdbApi.PARAM_PAGE, page);
		}
	}

	public void addLanguage(String language) {
		if (isNotBlank(language)) {
			addParam(AbstractTmdbApi.PARAM_LANGUAGE, language);
		}
	}
}
