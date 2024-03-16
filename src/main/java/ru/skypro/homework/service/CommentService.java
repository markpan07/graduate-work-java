package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.comments.CommentDto;
import ru.skypro.homework.dto.comments.CommentsDto;
import ru.skypro.homework.dto.comments.CreateOrUpdateCommentDto;
import ru.skypro.homework.entity.Comment;

public interface CommentService {
    CommentsDto getComments(Integer adPk, Authentication authentication);

    CommentDto addComment(Integer adPk, CreateOrUpdateCommentDto dto, Authentication authentication);

    CommentDto updateComment(Integer adPk, Integer commentId, CreateOrUpdateCommentDto createOrUpdateCommentDto, Authentication authentication);

    Comment getComment(Integer pk);

    void deleteComment(Integer adId, Integer commentId, Authentication authentication);
}
