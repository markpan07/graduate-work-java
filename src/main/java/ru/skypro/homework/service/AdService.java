package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ad.AdDto;
import ru.skypro.homework.dto.ad.AdsDto;
import ru.skypro.homework.dto.ad.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ad.ExtendedAdDto;
import ru.skypro.homework.entity.Ad;

import java.io.IOException;

public interface AdService {


    AdDto createAd(CreateOrUpdateAdDto createOrUpdateAdDto, MultipartFile image, Authentication authentication) throws IOException;
    AdsDto getAll();
    AdsDto getMyAds(String username);
    Ad getAd(Integer id);
    byte[] getImage(Integer id) throws IOException;
    ExtendedAdDto getExtendedAd(Integer id);
    AdDto updateAd(Integer id, CreateOrUpdateAdDto createOrUpdateAdDto, Authentication authentication);
    byte[] updateAdImage(Integer id, MultipartFile image, Authentication authentication);
    void deleteAd(Integer id, Authentication authentication);


}
