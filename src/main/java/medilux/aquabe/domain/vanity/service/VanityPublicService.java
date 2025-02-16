package medilux.aquabe.domain.vanity.service;

import lombok.RequiredArgsConstructor;
import medilux.aquabe.common.error.exceptions.BadRequestException;
import medilux.aquabe.domain.user.entity.UserEntity;
import medilux.aquabe.domain.user.repository.UserRepository;
import medilux.aquabe.domain.vanity.dto.VanityProductResponse;
import medilux.aquabe.domain.vanity.dto.VanityResponse;
import medilux.aquabe.domain.vanity.entity.VanityProductsEntity;
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

    @Transactional(readOnly = true)
    public VanityResponse getUserVanity(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "해당 사용자가 존재하지 않습니다."));

        // 화장대 점수 조회 (없으면 기본값 0)
        Integer vanityScore = userVanityRepository.findVanityScoreByUserId(user.getUserId());
        if (vanityScore == null) {
            vanityScore = 0;
        }

        // 특정 사용자의 화장대 제품 조회 (VanityProductsEntity 리스트 가져오기)
        List<VanityProductsEntity> products = vanityProductsRepository.findUserVanityProducts(user.getUserId());

        if (products.isEmpty()) {
            throw new BadRequestException(ROW_DOES_NOT_EXIST, "해당 사용자의 화장대에 제품이 없습니다.");
        }

        // DTO 변환 후 반환
        List<VanityProductResponse> productResponses = products.stream()
                .map(p -> new VanityProductResponse(p.getProduct(), p.getCompatibilityScore(), p.getRanking(), p.getCompatibilityRatio().name()))
                .toList();

        return new VanityResponse(user.getUserId(), vanityScore, productResponses);
    }

}
