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
public class SkinTypeUserService {

    private final SkinTypeUsersRepository skinTypeUsersRepository;
    private final UserRepository userRepository; // UserEntity 조회를 위한 Repository
    private final UserVanityRepository userVanityRepository; // UserVanityEntity 조회를 위한 Repository

    // 특정 피부 타입의 사용자 및 화장대 점수 조회
    @Transactional(readOnly = true)
    public SkinTypeUsersResponse getUsersBySkinType(String typeName) {
        // 피부 타입에 해당하는 사용자 조회
        List<SkinTypeEntity> skinTypeEntities = skinTypeUsersRepository.findBySkinType(typeName);

        if (skinTypeEntities.isEmpty()) {
            throw new BadRequestException(ROW_DOES_NOT_EXIST, "해당 피부 타입 정보가 존재하지 않습니다.");
        }

        // 사용자별 화장대 점수 및 피부 타입 데이터 변환
        List<SkinTypeUsersResponse.UserVanityInfo> userVanityInfos = skinTypeEntities.stream()
                .map(skinType -> {
                    // 사용자 정보 조회
                    UserEntity user = userRepository.findById(skinType.getUserId())
                            .orElseThrow(()-> new BadRequestException(ROW_DOES_NOT_EXIST, "사용자가 존재하지 않습니다"));

                    // 사용자 화장대 점수 조회
                    UserVanityEntity vanity = userVanityRepository.findByUserId(user.getUserId())
                            .orElse(UserVanityEntity.of(user));

                    return SkinTypeUsersResponse.UserVanityInfo.builder()
                            .userId(user.getUserId().toString())
                            .username(user.getUsername())
                            .vanityScore(vanity.getVanityScore())
                            .ubunScore(skinType.getUbunScore())
                            .subunScore(skinType.getSubunScore())
                            .mingamScore(skinType.getMingamScore())
                            .build();
                })
                .collect(Collectors.toList());

        return SkinTypeUsersResponse.builder()
                .typeName(typeName)
                .users(userVanityInfos)
                .build();
    }
}
