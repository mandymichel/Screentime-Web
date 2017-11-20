package com.luv2code.web.jdbc;

public class Video {

	private int id;
	private String videoURL;
		
	public Video(int id, String videoURL) {
		this.id = id;
		this.videoURL = videoURL;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getVideoURL() {
		return videoURL;
	}
	public void setVideoURL(String videoURL) {
		this.videoURL = videoURL;
	}
	@Override
	public String toString() {
		return "Video [id=" + id + ", videoURL=" + videoURL + "]";
	}
}
