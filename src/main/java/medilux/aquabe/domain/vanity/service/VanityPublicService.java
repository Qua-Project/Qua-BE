package medilux.aquabe.domain.vanity.service;

import lombok.RequiredArgsConstructor;
import medilux.aquabe.common.error.exceptions.BadRequestException;
import medilux.aquabe.domain.user.entity.UserEntity;
import medilux.aquabe.domain.user.repository.UserRepository;
import medilux.aquabe.domain.vanity.dto.VanityProductResponse;
import medilux.aquabe.domain.vanity.dto.VanityResponse;
import medilux.aquabe.domain.vanity.repository.UserVanityRepository;
import medilux.aquabe.domain.vanity.repository.VanityProductsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static medilux.aquabe.common.error.ErrorCode.ROW_DOES_NOT_EXIST;

@Service
@RequiredArgsConstructor
public class VanityPublicService {


    private final UserRepository userRepository;
    private final UserVanityRepository userVanityRepository;
    private final VanityProductsRepository vanityProductsRepository;

    // 특정 사용자의 화장대 정보 조회 (vanityScore 포함)
    @Transactional(readOnly = true)
    public VanityResponse getUserVanity(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "해당 사용자가 존재하지 않습니다."));

        Integer vanityScore = userVanityRepository.findVanityScoreByUserId(user.getUserId());

        List<VanityProductResponse> products = vanityProductsRepository.findUserVanityProducts(user.getUserId());

        return new VanityResponse(user.getUserId(), vanityScore, products);
    }
}
