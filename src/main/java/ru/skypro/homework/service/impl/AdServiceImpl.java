package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ad.AdDto;
import ru.skypro.homework.dto.ad.AdsDto;
import ru.skypro.homework.dto.ad.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ad.ExtendedAdDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdService;

import java.io.IOException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;


@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {

    @Value("${path.to.ad.photo}")
    private String photoPath;
    private final AdMapper adMapper;
    private final AdRepository adRepository;
    private final UserRepository userRepository;

    @Override
    public AdDto addAd(CreateOrUpdateAdDto createOrUpdateAdDto, MultipartFile image, Authentication authentication) throws IOException {
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(RuntimeException::new);
        Ad ad = adMapper.toEntity(createOrUpdateAdDto);
        ad.setUser(user);
        return adMapper.toAdDto(adRepository.save(uploadImage(ad, image)));
    }

    private Ad uploadImage(Ad ad, MultipartFile image) throws IOException {
        Path filePath = Path.of(photoPath, ad.hashCode() + "." + StringUtils.getFilenameExtension(image.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = image.getInputStream();
                OutputStream os = Files.newOutputStream(filePath);
                BufferedInputStream bis = new BufferedInputStream(is, 512);
                BufferedOutputStream bos = new BufferedOutputStream(os, 512)
        ) {
            bis.transferTo(bos);
            ad.setImage(filePath.toString());
            return ad;
        }

    }

    @Override
    public AdsDto getAll() {
        return adMapper.toAdsDto(adRepository.findAll());
    }

    @Override
    public AdsDto getMyAds(String username) {
        User user = userRepository.findByEmail(username).orElseThrow();
        return adMapper.toAdsDto(adRepository.findAllByUserId(user.getId()));
    }

    @Override
    public Ad getAd(Integer id) {
        return adRepository.findById(id).orElseThrow();
    }

    @Override
    public byte[] getImage(Integer id) throws IOException {
        Ad ad = getAd(id);
        return Files.readAllBytes(Path.of(ad.getImage()));
    }

    @Override
    public ExtendedAdDto getExtendedAd(Integer id) {
        return adMapper.toExtendedAdDto(getAd(id));
    }


    @Override
    public AdDto updateAd(Integer id, CreateOrUpdateAdDto createOrUpdateAdDto, Authentication authentication) {
        Ad ad = getAd(id);
        ad.setDescription(createOrUpdateAdDto.getDescription());
        ad.setTitle(createOrUpdateAdDto.getTitle());
        ad.setPrice(createOrUpdateAdDto.getPrice());
        return adMapper.toAdDto(adRepository.save(ad));
    }

    @Override
    public byte[] updateAdImage(Integer id, MultipartFile image, Authentication authentication) {

            try {
                Ad ad = getAd(id);
                ad = uploadImage(ad, image);
                return Files.readAllBytes(Path.of(ad.getImage()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

    }

    @Override
    public void deleteAd(Integer id, Authentication authentication) {
        adRepository.delete(getAd(id));
    }


}
