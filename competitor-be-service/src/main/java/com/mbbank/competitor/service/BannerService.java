package com.mbbank.competitor.service;

import com.mbbank.competitor.dto.BannerRequest;
import com.mbbank.competitor.dto.BannerResponse;
import com.mbbank.competitor.entity.Banner;
import com.mbbank.competitor.repository.BannerRepository;
import lombok.extern.slf4j.Slf4j;
import vn.com.mb.ai.competitor.common.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BannerService {
    private final BannerRepository bannerRepository;
    protected final ModelMapper mapper = new ModelMapper();
    private final SecurityService securityService;
    public BannerResponse save(BannerRequest banner){
        String userId = securityService.getCurrentUser().getUserId();
        log.info("Saving banner with userId {}", userId);
        Banner bannerEntity = mapper.map(banner, Banner.class);
        bannerRepository.save(bannerEntity);
        return mapper.map(bannerEntity,BannerResponse.class);
    }
}
