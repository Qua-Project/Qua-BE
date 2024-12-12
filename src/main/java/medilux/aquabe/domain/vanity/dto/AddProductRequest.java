package medilux.aquabe.domain.vanity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class AddProductRequest {
    private UUID productId;
    private Integer compatibilityScore;
}
