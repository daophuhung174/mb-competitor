package com.mbbank.competitor.repository;

import com.mbbank.competitor.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BannerRepository extends JpaRepository<Banner, String> {
}
