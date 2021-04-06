package com.morash.forumdemo.data.entity;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "respondingToPost_id", referencedColumnName = "id")
	private Post respondingToPost;

	@ManyToOne
	@JoinColumn(name = "respondingToComment_id", referencedColumnName = "id")
	private Comment respondingToComment;

	@OneToMany(mappedBy = "respondingToComment")
	@JsonIgnore
	private Set<Comment> comments;
	private String contents;

	private LocalDateTime postDate;
	@ManyToOne
	@JoinColumn(name="poster_id", referencedColumnName="id")
	private User poster;
	
	@Transient
	private Integer commentCount;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Post getRespondingToPost() {
		return respondingToPost;
	}

	public void setRespondingToPost(Post respondingToPost) {
		this.respondingToPost = respondingToPost;
	}

	public Comment getRespondingToComment() {
		return respondingToComment;
	}

	public void setRespondingToComment(Comment respondingToComment) {
		this.respondingToComment = respondingToComment;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public LocalDateTime getPostDate() {
		return postDate;
	}

	public void setPostDate(LocalDateTime postDate) {
		this.postDate = postDate;
	}

	public User getPoster() {
		return poster;
	}

	public void setPoster(User poster) {
		this.poster = poster;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}
	
	public Integer getCommentCount() {
		return this.comments.size();
	}

}
