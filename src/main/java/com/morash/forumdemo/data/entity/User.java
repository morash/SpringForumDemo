/**
 * 
 */
package com.morash.forumdemo.data.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * @author Michael
 *
 */
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String username;

	private String email;
	
	@Column(name="isSiteAdmin")
	private Boolean isSiteAdmin = false;

	// TODO: Figure out hashing and Spring Security modules
	private String password;

	@OneToMany(mappedBy="poster")
	private Set<Post> posts;

	@OneToMany(mappedBy="poster")
	private Set<Comment> comments;
	
	@OneToMany(mappedBy="createdBy")
	private Set<Board> ownedBoards;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public Boolean getIsSiteAdmin() {
		return isSiteAdmin;
	}

	public void setIsSiteAdmin(Boolean isSiteAdmin) {
		this.isSiteAdmin = isSiteAdmin;
	}

	public String toString() {
		return "User[id=" + id + ";username='" + username + "';email='" + email+"';isSiteAdmin=" + isSiteAdmin + "]";
	}
}
