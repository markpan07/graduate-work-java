package ru.skypro.homework.dto.comments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skypro.homework.dto.ad.AdDto;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentsDto {
    private Integer count;
    private List<CommentDto> result;
}
