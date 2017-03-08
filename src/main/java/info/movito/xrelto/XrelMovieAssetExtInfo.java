package info.movito.xrelto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import info.movito.themoviedbapi.model.core.AbstractJsonMapping;

@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
public class XrelMovieAssetExtInfo extends AbstractJsonMapping {

	private static final long serialVersionUID = 1L;

	@JsonProperty("title")
	private String title;

	@JsonProperty("type")
	private String type;

	@JsonProperty("id")
	private String id;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
