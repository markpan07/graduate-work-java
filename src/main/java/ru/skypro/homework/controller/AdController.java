package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ad.AdDto;
import ru.skypro.homework.dto.ad.AdsDto;
import ru.skypro.homework.dto.ad.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ad.ExtendedAdDto;
import ru.skypro.homework.service.AdService;

import java.io.IOException;

@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class AdController {


    private final AdService adService;


    @GetMapping
    public ResponseEntity<AdsDto> getAllAds() {
        AdsDto dto = new AdsDto();
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/me")
    public ResponseEntity<AdsDto> getAdsMe() {
        AdsDto dto = new AdsDto();
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAdDto> getAds(@PathVariable Integer id) {
        ExtendedAdDto dto = new ExtendedAdDto();
        return ResponseEntity.ok(dto);
    }

    @GetMapping(name = "{id}/image", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> getImage(@PathVariable Integer id) throws IOException {
        return ResponseEntity.ok(adService.getImage(id));
    }

    @PostMapping
    public ResponseEntity<AdDto> addAd(@RequestBody AdDto dto) {
        return ResponseEntity.ok(dto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CreateOrUpdateAdDto> updateAds(@PathVariable Integer id) {
        CreateOrUpdateAdDto dto = new CreateOrUpdateAdDto();
        return ResponseEntity.ok(dto);
    }

    @PatchMapping("/{id}/image")
    public ResponseEntity<Void> updateImage(@PathVariable Integer id, MultipartFile image) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping(name = "{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> updateAdImage(@PathVariable Integer id, @RequestParam MultipartFile image, Authentication authentication) {
        return ResponseEntity.ok(adService.updateAdImage(id, image, authentication));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAds(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
