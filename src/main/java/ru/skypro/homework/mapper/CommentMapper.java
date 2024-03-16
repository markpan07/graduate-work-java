package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Service;

import ru.skypro.homework.dto.ad.AdDto;
import ru.skypro.homework.dto.ad.AdsDto;
import ru.skypro.homework.dto.comments.CommentDto;
import ru.skypro.homework.dto.comments.CommentsDto;
import ru.skypro.homework.dto.comments.CreateOrUpdateCommentDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CommentMapper {

public Comment toEntity (CreateOrUpdateCommentDto createOrUpdateCommentDto){
    Comment comment = new Comment();
    comment.setText(createOrUpdateCommentDto.getText());
    return comment;
}

public CommentDto toCommentDto(Comment comment){

    CommentDto commentDto = new CommentDto();
    commentDto.setPk(comment.getPk());
    commentDto.setCreatedAt(comment.getCreatedAt());
    commentDto.setText(comment.getText());
    commentDto.setAuthor(comment.getUser().getId());
    commentDto.setAuthorFirstName(comment.getUser().getFirstName());
    commentDto.setAuthorImage(comment.getUser().getImage());

    return commentDto;
}

    public CommentsDto toCommentsDto(List<Comment> comments) {
        CommentsDto commentsDto = new CommentsDto();
        List<CommentDto> commentDtoList = comments.stream()
                .map(this::toCommentDto)
                .collect(Collectors.toList());

        commentsDto.setCount(commentDtoList.size());
        commentsDto.setResult(commentDtoList);

        return commentsDto;
    }



}