package info.movito.xrelto;

import java.io.IOException;
import java.net.MalformedURLException;

import info.movito.themoviedbapi.AbstractTmdbApi;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.tools.ApiUrlXrel;

public class XrelApiCaller extends AbstractTmdbApi {

	public XrelApiCaller(TmdbApi tmdbApi) {
		super(tmdbApi);
	}

	public static String EXT_INFO_IMDB_URL = "search/ext_info.json?q=";
	public static String EXT_INFO_FROM_ID = "release/ext_info.json?per_page=100&id=";
	public static String EXT_INFO_P2P_FROM_ID = "p2p/releases.json?per_page=100&ext_info_id=";

	public XrelMovieAssetExtInfoFind getExtInfoIdFromImdbId(String imdbId) throws MalformedURLException, IOException {
		return mapJsonResultXrel(new ApiUrlXrel(EXT_INFO_IMDB_URL + imdbId), XrelMovieAssetExtInfoFind.class);
	}

	public XrelMovieAssetExtReleaseFind getExtInfoReleasesFromId(String extId)
			throws MalformedURLException, IOException {
		return mapJsonResultXrel(new ApiUrlXrel(EXT_INFO_FROM_ID + extId), XrelMovieAssetExtReleaseFind.class);
	}

	public XrelMovieAssetExtReleaseFind getExtInfoReleasesFromId(String extId, int pageNumber)
			throws MalformedURLException, IOException {
		return mapJsonResultXrel(new ApiUrlXrel(EXT_INFO_FROM_ID + extId + "&page=" + pageNumber),
				XrelMovieAssetExtReleaseFind.class);
	}

	public XrelMovieAssetExtP2PReleaseFind getExtP2PInfoReleasesFromId(String extId)
			throws MalformedURLException, IOException {
		return mapJsonResultXrel(new ApiUrlXrel(EXT_INFO_P2P_FROM_ID + extId), XrelMovieAssetExtP2PReleaseFind.class);
	}

	public XrelMovieAssetExtP2PReleaseFind getExtP2PInfoReleasesFromId(String extId, int pageNumber)
			throws MalformedURLException, IOException {
		return mapJsonResultXrel(new ApiUrlXrel(EXT_INFO_P2P_FROM_ID + extId + "&page=" + pageNumber),
				XrelMovieAssetExtP2PReleaseFind.class);
	}

}
