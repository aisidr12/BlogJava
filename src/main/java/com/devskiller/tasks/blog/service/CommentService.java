package com.devskiller.tasks.blog.service;

import com.devskiller.tasks.blog.exceptions.PostNotFoundException;
import com.devskiller.tasks.blog.model.Comment;
import com.devskiller.tasks.blog.model.Post;
import com.devskiller.tasks.blog.repository.CommentRepository;
import com.devskiller.tasks.blog.repository.PostRepository;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import java.util.Optional;
import org.springframework.stereotype.Service;

import com.devskiller.tasks.blog.model.dto.CommentDto;
import com.devskiller.tasks.blog.model.dto.NewCommentDto;

@Service
public class CommentService {

	private final CommentRepository commentRepository;
	private final PostRepository postRepository;

	public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
	}

	/**
	 * Returns a list of all comments for a blog post with passed id.
	 *
	 * @param postId id of the post
	 * @return list of comments sorted by creation date descending - most recent first
	 */
	public List<CommentDto> getCommentsForPost(Long postId) {
		return postRepository.findById(postId)
			.map(Post::getComments)
			.map(comments -> comments
				.stream()
				.sorted(Comparator.comparing(Comment::getCreationDate).reversed())
				.map(this::commentToDto)
				.toList())
			.orElse(List.of());
	}

	private CommentDto commentToDto(Comment comment) {
		return new CommentDto(comment.getIdComment(), comment.getContent(), comment.getAuthor(),
			comment.getCreationDate());
	}

	/**
	 * Creates a new comment
	 *
	 * @param postId        id of the post
	 * @param newCommentDto data of new comment
	 * @return id of the created comment
	 * @throws IllegalArgumentException if postId is null or there is no blog post for passed
	 *                                  postId
	 */
	public Long addComment(Long postId, NewCommentDto newCommentDto) {
		Optional<Post> post = postRepository.findById(postId);
		if(post.isEmpty()){
			throw new PostNotFoundException("Post Does not Exist");
		}
		Post currentPost = post.get();
		//FIXME buscar el error aqui porque no se añade
		currentPost.getComments().add(commentRepository.save(addNewComment(newCommentDto)));
		postRepository.save(currentPost);
		return currentPost.getId();
	}

	private Comment addNewComment(NewCommentDto newCommentDto){
		Comment comment = new Comment();
		comment.setAuthor(newCommentDto.author());
		comment.setContent(newCommentDto.content());
		comment.setCreationDate(LocalDateTime.now());
		return comment;
	}
}
