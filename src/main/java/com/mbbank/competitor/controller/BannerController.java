package com.mbbank.competitor.controller;

import com.mbbank.competitor.dto.BannerRequest;
import com.mbbank.competitor.entity.Banner;
import com.mbbank.competitor.repository.BannerRepository;
import com.mbbank.competitor.service.BannerService;
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
    public void create(@RequestBody BannerRequest bannerRequest){
        bannerService.save(bannerRequest);
    }
}
