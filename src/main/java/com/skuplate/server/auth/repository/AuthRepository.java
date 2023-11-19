package com.skuplate.server.auth.repository;

import com.skuplate.server.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AuthRepository extends JpaRepository<RefreshToken,Long> {
    RefreshToken findRefreshTokenByMemberId(Long memberId);
    @Transactional
    void deleteRefreshTokenByMemberId(Long memberId);
}
