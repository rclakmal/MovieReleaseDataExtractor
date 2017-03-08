package application;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import info.movito.themoviedbapi.model.core.AbstractJsonMapping;
import info.movito.xrelto.XrelMovieAssetExtInfo;

public class XrelSearchExtInfoImdbId extends AbstractJsonMapping {

	private static final long serialVersionUID = 1L;

	@JsonProperty("results")
	private List<XrelMovieAssetExtInfo> xrelMovieAssetExtInfos;
}
