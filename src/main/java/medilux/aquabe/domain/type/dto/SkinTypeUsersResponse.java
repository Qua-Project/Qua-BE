package medilux.aquabe.domain.type.dto;

        import lombok.Builder;
        import lombok.Getter;
        import lombok.Setter;

        import java.util.List;

@Getter
@Builder
public class SkinTypeUsersResponse {
    private String typeName;
    private List<UserVanityInfo> users;

    @Getter
    @Builder
    public static class UserVanityInfo {
        private String userId;
        private String username;
        private Integer vanityScore;
        private Integer ubunScore;
        private Integer subunScore;
        private Integer mingamScore;

        public static UserVanityInfo fromMin(String userId, String username, Integer vanityScore) {
            return UserVanityInfo.builder()
                    .userId(userId)
                    .username(username)
                    .vanityScore(vanityScore)
                    .build();
        }
        public static UserVanityInfo fromFull(String userId, String username, Integer vanityScore, Integer ubunScore, Integer subunScore, Integer mingamScore) {
            return UserVanityInfo.builder()
                    .userId(userId)
                    .username(username)
                    .vanityScore(vanityScore)
                    .ubunScore(ubunScore)
                    .subunScore(subunScore)
                    .mingamScore(mingamScore)
                    .build();
        }
    }
}
