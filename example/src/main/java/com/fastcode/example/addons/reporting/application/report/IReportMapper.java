package com.fastcode.example.addons.reporting.application.report;

import com.fastcode.example.addons.reporting.application.report.dto.*;
import com.fastcode.example.addons.reporting.application.reportversion.dto.CreateReportversionInput;
import com.fastcode.example.addons.reporting.application.reportversion.dto.CreateReportversionOutput;
import com.fastcode.example.addons.reporting.application.reportversion.dto.UpdateReportversionInput;
import com.fastcode.example.addons.reporting.application.reportversion.dto.UpdateReportversionOutput;
import com.fastcode.example.addons.reporting.domain.report.ReportEntity;
import com.fastcode.example.addons.reporting.domain.reportversion.ReportversionEntity;
import com.fastcode.example.domain.core.authorization.user.UserEntity;
import java.time.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface IReportMapper {
    ReportEntity createReportInputToReportEntity(CreateReportInput reportDto);

    @Mappings({ @Mapping(source = "ownerId", target = "userId") })
    CreateReportversionInput createReportInputToCreateReportversionInput(CreateReportInput reportDto);

    @Mappings(
        {
            @Mapping(source = "entity.id", target = "id"),
            @Mapping(source = "entity.user.id", target = "ownerId"),
            @Mapping(source = "entity.user.userName", target = "ownerDescriptiveField"),
        }
    )
    CreateReportOutput reportEntityAndCreateReportversionOutputToCreateReportOutput(
        ReportEntity entity,
        CreateReportversionOutput reportversionOutput
    );

    @Mappings(
        {
            @Mapping(source = "report.id", target = "id"),
            @Mapping(source = "report.user.id", target = "ownerId"),
            @Mapping(source = "report.user.userName", target = "ownerDescriptiveField"),
        }
    )
    CreateReportOutput reportEntityAndReportversionEntityToCreateReportOutput(
        ReportEntity report,
        ReportversionEntity reportversion
    );

    FindReportByIdOutput createReportOutputToFindReportByIdOutput(CreateReportOutput report);

    ReportEntity updateReportInputToReportEntity(UpdateReportInput reportDto);

    UpdateReportversionInput updateReportInputToUpdateReportversionInput(UpdateReportInput reportDto);

    @Mappings(
        {
            @Mapping(source = "entity.id", target = "id"),
            @Mapping(source = "entity.user.id", target = "ownerId"),
            @Mapping(source = "entity.user.userName", target = "ownerDescriptiveField"),
        }
    )
    UpdateReportOutput reportEntityAndUpdateReportversionOutputToUpdateReportOutput(
        ReportEntity entity,
        UpdateReportversionOutput reportversion
    );

    @Mappings(
        {
            @Mapping(source = "reportversion.userId", target = "userId"),
            @Mapping(source = "report.user.id", target = "ownerId"),
            @Mapping(source = "reportversion.reportId", target = "id"),
            @Mapping(source = "report.versiono", target = "versiono"),
        }
    )
    FindReportByIdOutput reportEntitiesToFindReportByIdOutput(ReportEntity report, ReportversionEntity reportversion);

    @Mappings(
        {
            @Mapping(source = "entity.user.id", target = "ownerId"),
            @Mapping(source = "entity.versiono", target = "versiono"),
        }
    )
    FindReportByIdOutput reportEntityToFindReportByIdOutput(ReportEntity entity, ReportversionEntity reportversion);

    @Mappings({ @Mapping(source = "user.id", target = "id"), @Mapping(source = "report.id", target = "reportId") })
    GetUserOutput userEntityToGetUserOutput(UserEntity user, ReportEntity report);

    @Mappings(
        {
            @Mapping(source = "reportversion.userId", target = "userId"),
            @Mapping(source = "reportversion.reportId", target = "id"),
        }
    )
    ReportDetailsOutput reportEntitiesToReportDetailsOutput(ReportEntity report, ReportversionEntity reportversion);
}
