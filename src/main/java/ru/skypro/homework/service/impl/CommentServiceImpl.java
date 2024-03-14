package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.comments.CommentDto;
import ru.skypro.homework.dto.comments.CommentsDto;
import ru.skypro.homework.dto.comments.CreateOrUpdateCommentDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.CommentService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final AdRepository adsRepository;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;


    @Override
    public CommentsDto getComments(Integer adPk) {
        List<CommentDto> comments = commentRepository.findByAdPk(adPk)
                .stream()
                .map(commentMapper::toCommentDto)
                .collect(Collectors.toList());
        return new CommentsDto(comments.size(), comments);
    }


    public CommentDto addComment(Integer pk, CreateOrUpdateCommentDto dto, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(RuntimeException::new);
        Ad ad = adsRepository.findById(pk).orElse(null);

        //Создаем сущность comment и заполняем поля
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setAd(ad);
        comment.setText(dto.getText());
        comment.setCreatedAt(System.currentTimeMillis());

        //Сохраняем сущность commentEntity в БД
        commentRepository.save(comment);

        //Заполняем поле с комментариями у пользователя и сохраняем в БД
       // user..add(commentEntity);
     //   userRepository.save(user);

        //Создаем возвращаемую сущность ДТО comment и заполняем поля
        CommentDto commentDto = new CommentDto();
        commentDto.setAuthor(user.getId());

        String avatar = user.getImage();
        commentDto.setAuthorImage(avatar);

        commentDto.setAuthorFirstName(user.getFirstName());
        commentDto.setCreatedAt(comment.getCreatedAt());
        commentDto.setPk(commentRepository.findFirstByText(dto.getText()).getPk());
        commentDto.setText(commentRepository.findFirstByText(dto.getText()).getText());

        return commentDto;
    }


    @Override
    public CommentDto updateComment(Integer adPk,
                                 Integer commentId,
                                 CreateOrUpdateCommentDto createOrUpdateCommentDto, Authentication authentication) {


        Comment comment = commentRepository.findById(commentId).get();
        comment.setText(createOrUpdateCommentDto.getText());
        commentRepository.save(comment);
        return commentMapper.toCommentDto(comment);
    }
    @Override
    public Comment getComment(Integer pk) {
        return commentRepository.findById(pk).orElseThrow();
    }
    @Override
    public void deleteComment(Integer adId, Integer commentId, Authentication authentication) {

        commentRepository.deleteByPkAndAdPk(adId,commentId);
    }
}
