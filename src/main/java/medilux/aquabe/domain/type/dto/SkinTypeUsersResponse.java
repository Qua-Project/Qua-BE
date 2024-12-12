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
        private int vanityScore;
        private int ubunScore;
        private int subunScore;
        private int mingamScore;
    }
}
