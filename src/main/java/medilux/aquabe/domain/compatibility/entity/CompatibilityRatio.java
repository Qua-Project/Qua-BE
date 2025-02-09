package medilux.aquabe.domain.compatibility.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CompatibilityRatio {
    VERY_SUITABLE("매우 적합"),
    SUITABLE("적합"),
    NORMAL("보통"),
    UNSUITABLE("부적합"),
    VERY_UNSUITABLE("매우 부적합");

    private final String description;
}
