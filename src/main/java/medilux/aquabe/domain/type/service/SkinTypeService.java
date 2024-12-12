package medilux.aquabe.domain.type.service;

import lombok.RequiredArgsConstructor;
import medilux.aquabe.domain.type.entity.SkinTypeEntity;
import medilux.aquabe.domain.type.repository.SkinTypeRepository;
import medilux.aquabe.domain.type.dto.SkinTypeRequest;
import medilux.aquabe.domain.type.dto.SkinTypeResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SkinTypeService {

    private final SkinTypeRepository skinTypeRepository;

    // 피부 타입 조회
    @Transactional(readOnly = true)
    public SkinTypeResponse getSkinType(UUID userId) {
        SkinTypeEntity skinType = skinTypeRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자의 피부 타입 정보가 없습니다."));
        return new SkinTypeResponse(skinType);
    }

    // 피부 타입 등록
    @Transactional
    public void createSkinType(UUID userId, SkinTypeRequest request) {
        if (skinTypeRepository.findByUserId(userId).isPresent()) {
            throw new IllegalArgumentException("이미 피부 타입 정보가 존재합니다.");
        }
        SkinTypeEntity skinType = new SkinTypeEntity(
                userId,
                request.getSkinType(),
                request.getUbunScore(),
                request.getSubunScore(),
                request.getMingamScore()
        );
        skinTypeRepository.save(skinType);
    }

    // 피부 타입 수정
    @Transactional
    public void updateSkinType(UUID userId, SkinTypeRequest request) {
        SkinTypeEntity skinType = skinTypeRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자의 피부 타입 정보가 없습니다."));
        skinType.updateSkinType(
                request.getSkinType(),
                request.getUbunScore(),
                request.getSubunScore(),
                request.getMingamScore()
        );
    }
}
