package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.dto.ad.AdDto;
import ru.skypro.homework.dto.comments.CommentDto;
import ru.skypro.homework.dto.comments.CommentsDto;
import ru.skypro.homework.dto.comments.CreateOrUpdateCommentDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;




@Service
public class CommentMapper {

public Comment toEntity (CommentDto dto, CreateOrUpdateCommentDto createOrUpdateCommentDto, User author, Ad ad){
    Comment comment = new Comment();
    comment.setPk(dto.getPk());
    comment.setCreatedAt(dto.getCreatedAt());
    comment.setText(createOrUpdateCommentDto.getText());
    comment.setUser(author);
    comment.setAd(ad);
    return comment;
}

public CommentDto toDto(Comment comment){

    CommentDto commentDto = new CommentDto();
    commentDto.setPk(comment.getPk());
    commentDto.setCreatedAt(comment.getCreatedAt());
    commentDto.setText(comment.getText());
    commentDto.setAuthor(comment.getUser().getId());
    commentDto.setAuthorFirstName(comment.getUser().getFirstName());
    commentDto.setAuthorImage(comment.getUser().getImage());

    return commentDto;
}


}