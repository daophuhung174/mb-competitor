package com.mbbank.competitor.service;

import com.mbbank.competitor.dto.BannerRequest;
import com.mbbank.competitor.entity.Banner;
import com.mbbank.competitor.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BannerService {
    private final BannerRepository bannerRepository;
    protected final ModelMapper mapper = new ModelMapper();
    public void save(BannerRequest banner){
        Banner bannerEntity = mapper.map(banner,Banner.class);
        bannerRepository.save(bannerEntity);
    }
}
