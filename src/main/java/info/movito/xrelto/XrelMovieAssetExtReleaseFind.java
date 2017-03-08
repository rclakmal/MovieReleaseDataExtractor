package info.movito.xrelto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class XrelMovieAssetExtReleaseFind {

	@JsonProperty("list")
	private List<XrelMovieAssetExtRelease> extInfoReleases;

	@JsonProperty("total_count")
	private String totalCount;

	@JsonProperty("pagination")
	private XrelPagination pagination;

	public List<XrelMovieAssetExtRelease> getExtInfoReleases() {
		return extInfoReleases;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public XrelPagination getPagination() {
		return pagination;
	}

	public void setPagination(XrelPagination pagination) {
		this.pagination = pagination;
	}

	public void setExtInfoReleases(List<XrelMovieAssetExtRelease> extInfoReleases) {
		this.extInfoReleases = extInfoReleases;
	}

}
