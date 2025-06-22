package com.devskiller.tasks.blog.exceptions;

public class PostNotFoundException extends RuntimeException{

	private String message;

	public PostNotFoundException(String message){
		this.message = message;
	}
}
