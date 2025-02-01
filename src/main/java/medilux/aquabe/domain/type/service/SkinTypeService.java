package medilux.aquabe.domain.type.service;

import lombok.RequiredArgsConstructor;
import medilux.aquabe.common.error.exceptions.BadRequestException;
import medilux.aquabe.domain.type.entity.SkinTypeEntity;
import medilux.aquabe.domain.type.repository.SkinTypeRepository;
import medilux.aquabe.domain.type.dto.SkinTypeRequest;
import medilux.aquabe.domain.type.dto.SkinTypeResponse;
import medilux.aquabe.domain.type.dto.SkinTypeUsersResponse;
import medilux.aquabe.domain.type.repository.SkinTypeUsersRepository;
import medilux.aquabe.domain.user.entity.UserEntity;
import medilux.aquabe.domain.user.repository.UserRepository;
import medilux.aquabe.domain.vanity.entity.UserVanityEntity;
import medilux.aquabe.domain.vanity.repository.UserVanityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


import static medilux.aquabe.common.error.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class SkinTypeService {

    private final SkinTypeRepository skinTypeRepository;
    private final SkinTypeUsersRepository skinTypeUsersRepository;
    private final UserRepository userRepository; // UserEntity 조회를 위한 Repository
    private final UserVanityRepository userVanityRepository; // UserVanityEntity 조회를 위한 Repository


    // 피부 타입 조회
    @Transactional(readOnly = true)
    public SkinTypeResponse getSkinType(String loginEmail) {
        UUID userId = userRepository.findUserIdByEmail(loginEmail).orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "존재하지 않는 사용자입니다."));
        SkinTypeEntity skinType = skinTypeRepository.findByUserId(userId)
                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "해당 사용자의 피부 타입 정보가 존재하지 않습니다."));

        return new SkinTypeResponse(skinType);
    }

    // 피부 타입 등록
    @Transactional
    public void createSkinType(String loginEmail, SkinTypeRequest request) {
        UUID userId = userRepository.findUserIdByEmail(loginEmail)
                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "존재하지 않는 사용자입니다."));

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "존재하지 않는 사용자입니다."));

        if (skinTypeRepository.findByUser(user).isPresent()) {
            throw new BadRequestException(ROW_ALREADY_EXIST, "이미 등록된 피부 타입 정보가 존재합니다.");
        }

        SkinTypeEntity skinType = new SkinTypeEntity(
                user,
                request.getSkinType(),
                request.getUbunScore(),
                request.getSubunScore(),
                request.getMingamScore()
        );

        skinTypeRepository.save(skinType);
    }



    // 피부 타입 수정
    @Transactional
    public void updateSkinType(String loginEmail, SkinTypeRequest request) {
        UUID userId = userRepository.findUserIdByEmail(loginEmail).orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "존재하지 않는 사용자입니다."));

        SkinTypeEntity skinType = skinTypeRepository.findByUserId(userId)
                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "해당 사용자의 피부 타입 정보가 존재하지 않습니다."));

        skinType.updateSkinType(
                request.getSkinType(),
                request.getUbunScore(),
                request.getSubunScore(),
                request.getMingamScore()
        );
    }

}
