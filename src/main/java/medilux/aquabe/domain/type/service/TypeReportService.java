package medilux.aquabe.domain.type.service;

import lombok.RequiredArgsConstructor;
import medilux.aquabe.common.error.ErrorCode;
import medilux.aquabe.common.error.exceptions.BadRequestException;
import medilux.aquabe.domain.type.dto.TypeReportResponse;
import medilux.aquabe.domain.type.entity.TypeReportEntity;
import medilux.aquabe.domain.type.repository.TypeReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TypeReportService {

    private final TypeReportRepository typeReportRepository;

    @Transactional(readOnly = true)
    public TypeReportResponse getTypeReportByTypeName(String typeName) {
        TypeReportEntity report = typeReportRepository.findByTypeName(typeName)
                .orElseThrow(() -> new BadRequestException(ErrorCode.ROW_DOES_NOT_EXIST, "해당 피부 타입 리포트가 없습니다: " + typeName));
        return TypeReportResponse.fromEntity(report);
    }
}
