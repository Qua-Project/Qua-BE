package medilux.aquabe.domain.type.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import medilux.aquabe.domain.type.dto.TypeReportResponse;
import medilux.aquabe.domain.type.service.TypeReportService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/typereport")
@RequiredArgsConstructor
public class TypeReportController {

    private final TypeReportService typeReportService;

    // 피부 타입 리포트 조회
    @GetMapping("/{type}")
    @Operation(summary = "피부타입별 피부타입 리포트 조회 api")
    public TypeReportResponse getTypeReport(@PathVariable("type") String type) {
        return typeReportService.getTypeReportByTypeName(type);
    }
}
