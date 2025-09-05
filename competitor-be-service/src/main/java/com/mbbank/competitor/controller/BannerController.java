package vn.com.mb.ai.competitor.controller;

import com.mbbank.competitor.dto.BannerResponse;
import vn.com.mb.ai.competitor.dto.BannerRequest;
import vn.com.mb.ai.competitor.entity.Banner;
import vn.com.mb.ai.competitor.repository.BannerRepository;
import vn.com.mb.ai.competitor.service.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/banner")
public class BannerController {
    private final BannerService bannerService;

    @PostMapping("/create")
    public BannerResponse create(@RequestBody BannerRequest bannerRequest){
        return bannerService.save(bannerRequest);
    }
}
