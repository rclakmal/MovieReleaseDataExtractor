package info.movito.xrelto;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class XrelMovieAssetExtRelease {

	@JsonProperty("id")
	private String id;
	@JsonProperty("dirname")
	private String dirName;
	@JsonProperty("link_href")
	private String link;
	@JsonProperty("video_type")
	private String videoType;
	@JsonProperty("audio_type")
	private String auditoType;
	@JsonProperty("time")
	private Long time;

	public String getDate() {
		SimpleDateFormat ft = new SimpleDateFormat("yyyy.MM.dd hh:mm");
		Date d = new Date(time * 1000);
		return ft.format(d);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDirName() {
		return dirName;
	}

	public void setDirName(String dirName) {
		this.dirName = dirName;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getVideoType() {
		return videoType;
	}

	public void setVideoType(String videoType) {
		this.videoType = videoType;
	}

	public String getAuditoType() {
		return auditoType;
	}

	public void setAuditoType(String auditoType) {
		this.auditoType = auditoType;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

}
