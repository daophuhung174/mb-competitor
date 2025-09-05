package vn.com.mb.ai.competitor.repository;

import vn.com.mb.ai.competitor.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BannerRepository extends MongoRepository<Banner, String> {
}
