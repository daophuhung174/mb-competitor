package vn.com.mb.ai.competitor.service;

import vn.com.mb.ai.competitor.dto.BannerRequest;
import vn.com.mb.ai.competitor.entity.Banner;
import vn.com.mb.ai.competitor.repository.BannerRepository;
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
