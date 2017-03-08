package info.movito.xrelto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.AbstractJsonMapping;

@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
public class XrelMovieAssetExtInfoFind extends AbstractJsonMapping {

	private static final long serialVersionUID = 1L;

	@JsonProperty("results")
	private List<XrelMovieAssetExtInfo> extInfoReleases;

	public List<XrelMovieAssetExtInfo> getExtInfoReleases() {
		return extInfoReleases;
	}

	public void setExtInfoReleases(List<XrelMovieAssetExtInfo> extInfoReleases) {
		this.extInfoReleases = extInfoReleases;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
