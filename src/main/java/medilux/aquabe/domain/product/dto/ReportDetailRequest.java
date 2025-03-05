package medilux.aquabe.domain.product.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ReportDetailRequest {
    private UUID productId;
}
