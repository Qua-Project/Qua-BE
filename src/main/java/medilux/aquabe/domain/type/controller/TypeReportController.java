package medilux.aquabe.domain.type.controller;

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
    public TypeReportResponse getTypeReport(@PathVariable("type") String type) {
        return typeReportService.getTypeReportByTypeName(type);
    }
}
