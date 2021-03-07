package com.vmw.assignments.ui.model;

/**
 * @author adarsh
 *
 */
public class Article {

	private String title;
	private String videoUrl;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	@Override
	public String toString() {
		return "Article [title=" + title + "]";
	}

}
