package com.itrun.wenhuav2.entity;

public class Article {
	
	private int articleId;
	private String title;
	private String auther;//������
	private String content;//��������
	private String date;//��������
	private long likeCount;//������
	private int status;//��ͨOR����
	
	public Article(){
		
	}

	public int getArticleId() {
		return articleId;
	}
	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuther() {
		return auther;
	}

	public void setAuther(String auther) {
		this.auther = auther;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public long getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(long likeCount2) {
		this.likeCount = likeCount2;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
