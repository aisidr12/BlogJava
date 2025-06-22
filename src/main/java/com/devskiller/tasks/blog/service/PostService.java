package com.devskiller.tasks.blog.service;

import com.devskiller.tasks.blog.model.Comment;
import com.devskiller.tasks.blog.model.Post;
import com.devskiller.tasks.blog.model.dto.CommentDto;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

import com.devskiller.tasks.blog.model.dto.PostDto;
import com.devskiller.tasks.blog.repository.PostRepository;

@Service
public class PostService {

	private final PostRepository postRepository;

	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	public List<PostDto> retrievePosts(){
		return postRepository.findAll().stream().map(post -> new PostDto(post.getTitle(), post.getContent(), post.getCreationDate()))
			.toList();
	}

	public PostDto getPost(Long id) {
		return postRepository.findById(id)
			.map(post -> new PostDto(post.getTitle(), post.getContent(), post.getCreationDate()))
			.orElse(null);
	}

	public PostDto savePost(PostDto postDto) {
		Post postNuevo = new Post();
		postNuevo.setTitle(postDto.title());
		postNuevo.setContent(postDto.content());
		postNuevo.setCreationDate(LocalDateTime.now());
		Post savedPost = postRepository.save(postNuevo);// FIXME try catch
		return mapPostToDto(savedPost);
	}

	private PostDto mapPostToDto(Post post) {
		return new PostDto(post.getTitle(), post.getContent(), post.getCreationDate());
	}

}
