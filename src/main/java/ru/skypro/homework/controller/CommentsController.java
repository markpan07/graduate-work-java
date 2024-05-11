package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;
import ru.skypro.homework.dto.ad.AdDto;
import ru.skypro.homework.dto.ad.AdsDto;
import ru.skypro.homework.dto.ad.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.comments.CommentDto;
import ru.skypro.homework.dto.comments.CommentsDto;
import ru.skypro.homework.dto.comments.CreateOrUpdateCommentDto;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.impl.AdServiceImpl;
import ru.skypro.homework.service.impl.CommentServiceImpl;

@Slf4j
@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/ads")
public class CommentsController {

    private final UserRepository userRepository;
    private final CommentServiceImpl commentService;
    private final AdServiceImpl adService;

    public CommentsController(UserRepository userRepository, CommentServiceImpl commentService, AdServiceImpl adService) {
        this.userRepository = userRepository;
        this.commentService = commentService;
        this.adService = adService;
    }

    @Operation(
            tags = "Комментарии",
            summary = "Получение комментариев объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommentsDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = @Content()
                    )
            }
    )
    @GetMapping("/{id}/comments")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CommentsDto> getAllComments(@PathVariable Integer id, Authentication authentication) {
        try {
            commentService.getComments(id,authentication);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(commentService.getComments(id,authentication));

    }

    @Operation(
            tags = "Комментарии",
            summary = "Добавление комментария к объявлению",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommentDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = @Content()
                    )
            }
    )

    @PostMapping("/{id}/comments")
    @PreAuthorize("isAuthenticated()")
       public ResponseEntity<CommentDto> addComment(@PathVariable("id") Integer id,
                                                 @RequestBody CreateOrUpdateCommentDto createOrUpdateCommentDto,
                                                 Authentication authentication) {
        try {
            commentService.addComment(id, createOrUpdateCommentDto, authentication);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(commentService.addComment(id, createOrUpdateCommentDto, authentication));
    }
    /*  public ResponseEntity<CreateOrUpdateCommentDto> addComment(@PathVariable("id") Integer id, @RequestBody CreateOrUpdateCommentDto dto) {
          return ResponseEntity.ok(dto);
      }
  */
    @Operation(
            tags = "Комментарии",
            summary = "Удаление комментария",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = @Content()
                    )
            }
    )
    @DeleteMapping("/{adId}/comments/{commentId}")
    @PreAuthorize("hasAuthority('ADMIN') or @commentServiceImpl.getComment(#commentId).user.email == authentication.principal.username")
   public ResponseEntity<?> deleteComment (@PathVariable int adId, @PathVariable int commentId, Authentication authentication) {
        try {
            commentService.deleteComment(adId, commentId, authentication);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
    /*
    public ResponseEntity<?>  deleteComment (@PathVariable int adId, @PathVariable int commentId) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }*/
    @Operation(
            tags = "Комментарии",
            summary = "Обновление комментария",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = @Content()
                    )
            }
    )
    @PatchMapping("{adId}/comments/{commentId}")
    @PreAuthorize("hasAuthority('ADMIN') or @commentServiceImpl.getComment(#commentId).user.email == authentication.principal.username")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Integer adId, @PathVariable Integer commentId,
                                                                  @RequestBody CreateOrUpdateCommentDto createOrUpdateCommentDto,
                                                                  Authentication authentication) {
        try {
        commentService.updateComment(adId, commentId, createOrUpdateCommentDto, authentication);
        } catch (NotFoundException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(commentService.updateComment(adId, commentId, createOrUpdateCommentDto, authentication));
    }

}
