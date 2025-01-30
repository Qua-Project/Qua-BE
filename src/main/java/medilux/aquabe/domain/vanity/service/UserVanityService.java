package medilux.aquabe.domain.vanity.service;

import lombok.RequiredArgsConstructor;
import medilux.aquabe.domain.type.repository.SkinTypeRepository;
import medilux.aquabe.domain.type.service.SkinTypeService;
import medilux.aquabe.domain.vanity.repository.UserVanityRepository;
import medilux.aquabe.domain.type.repository.SkinTypeUsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserVanityService {
    private final UserVanityRepository userVanityRepository;
    private final SkinTypeUsersRepository skinTypeUsersRepository;
    private final SkinTypeRepository skinTypeRepository;

    @Transactional(readOnly = true)
    public Integer getVanityRank(UUID userId) {
        // 1. 사용자의 피부 타입 조회
        String skinType = skinTypeRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자의 피부 타입이 존재하지 않습니다."))
                .getSkinType();

        // 2. 해당 피부 타입 사용자들의 vanity 점수 조회
        List<Integer> vanityScores = userVanityRepository.findVanityScoresBySkinType(skinType);
        if (vanityScores.isEmpty()) {
            throw new IllegalArgumentException("해당 피부 타입에 대한 데이터가 존재하지 않습니다.");
        }

        // 3. 내 점수 가져오기
        Integer myScore = userVanityRepository.findVanityScoreByUserId(userId);
        if (myScore == null) {
            throw new IllegalArgumentException("해당 사용자의 화장대 점수가 존재하지 않습니다.");
        }

        // 4. 내 점수의 순위 계산
        int myRank = vanityScores.indexOf(myScore) + 1;
        int totalUsers = vanityScores.size();

        // 5. 백분위 계산 (높을수록 0, 낮을수록 100)
        return (int) (((double) myRank / totalUsers) * 100);
    }
}
