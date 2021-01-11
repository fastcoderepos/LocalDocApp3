package com.fastcode.example.addons.reporting.application.dashboard;

import com.fastcode.example.addons.reporting.application.dashboard.dto.*;
import com.fastcode.example.addons.reporting.application.dashboardversion.dto.CreateDashboardversionInput;
import com.fastcode.example.addons.reporting.application.dashboardversion.dto.CreateDashboardversionOutput;
import com.fastcode.example.addons.reporting.application.dashboardversion.dto.UpdateDashboardversionInput;
import com.fastcode.example.addons.reporting.application.dashboardversion.dto.UpdateDashboardversionOutput;
import com.fastcode.example.addons.reporting.application.report.dto.CreateReportInput;
import com.fastcode.example.addons.reporting.domain.dashboard.DashboardEntity;
import com.fastcode.example.addons.reporting.domain.dashboardversion.DashboardversionEntity;
import com.fastcode.example.addons.reporting.domain.report.ReportEntity;
import com.fastcode.example.domain.core.authorization.user.UserEntity;
import java.time.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface IDashboardMapper {
    @Mappings({ @Mapping(source = "ownerId", target = "userId") })
    CreateDashboardversionInput creatDashboardInputToCreateDashboardversionInput(CreateDashboardInput dashboardDto);

    DashboardEntity createDashboardInputToDashboardEntity(CreateDashboardInput dashboardDto);

    CreateDashboardInput addNewReportToNewDashboardInputTocreatDashboardInput(AddNewReportToNewDashboardInput input);

    CreateDashboardInput addExistingReportToNewDashboardInputTocreatDashboardInput(
        AddExistingReportToNewDashboardInput input
    );

    DashboardEntity addExistingReportToNewDashboardInputToDashboardEntity(AddExistingReportToNewDashboardInput input);

    ReportEntity createDashboardAndReportInputToReportEntity(CreateReportInput reportDto);

    @Mappings(
        {
            @Mapping(source = "entity.id", target = "id"),
            @Mapping(source = "entity.user.id", target = "ownerId"),
            @Mapping(source = "entity.user.userName", target = "ownerDescriptiveField"),
        }
    )
    CreateDashboardOutput dashboardEntityAndCreateDashboardversionOutputToCreateDashboardOutput(
        DashboardEntity entity,
        CreateDashboardversionOutput dashboardversion
    );

    @Mappings(
        {
            @Mapping(source = "dashboardversion.dashboardId", target = "id"),
            @Mapping(source = "dashboardversion.userId", target = "ownerId"),
            @Mapping(source = "dashboardversion.user.userName", target = "ownerDescriptiveField"),
        }
    )
    CreateDashboardOutput dashboardEntityAndDashboardversionEntityToCreateDashboardOutput(
        DashboardEntity dashboard,
        DashboardversionEntity dashboardversion
    );

    UpdateDashboardversionInput updateDashboardInputToUpdateDashboardversionInput(UpdateDashboardInput dashboardDto);

    @Mappings(
        {
            @Mapping(source = "entity.id", target = "id"),
            @Mapping(source = "entity.user.id", target = "ownerId"),
            @Mapping(source = "entity.user.userName", target = "ownerDescriptiveField"),
        }
    )
    UpdateDashboardOutput dashboardEntityAndUpdateDashboardversionOutputToUpdateDashboardOutput(
        DashboardEntity entity,
        UpdateDashboardversionOutput dashboardversion
    );

    DashboardEntity updateDashboardInputToDashboardEntity(UpdateDashboardInput dashboardDto);

    @Mappings({ @Mapping(source = "user.id", target = "ownerId") })
    UpdateDashboardOutput dashboardEntityToUpdateDashboardOutput(DashboardEntity entity);

    @Mappings(
        {
            @Mapping(source = "user.id", target = "ownerId"),
            @Mapping(source = "user.userName", target = "ownerDescriptiveField"),
        }
    )
    FindDashboardByIdOutput dashboardEntityToFindDashboardByIdOutput(DashboardEntity entity);

    @Mappings({ @Mapping(source = "ownerId", target = "userId") })
    FindDashboardByIdOutput dashboardOutputToFindDashboardByIdOutput(CreateDashboardOutput entity);

    @Mappings(
        {
            @Mapping(source = "dashboardversion.userId", target = "userId"),
            @Mapping(source = "dashboard.user.id", target = "ownerId"),
            @Mapping(source = "dashboardversion.dashboardId", target = "id"),
            @Mapping(source = "dashboard.versiono", target = "versiono"),
        }
    )
    FindDashboardByIdOutput dashboardEntitiesToFindDashboardByIdOutput(
        DashboardEntity dashboard,
        DashboardversionEntity dashboardversion
    );

    @Mappings(
        {
            @Mapping(source = "dashboardversion.userId", target = "userId"),
            @Mapping(source = "dashboardversion.dashboardId", target = "id"),
        }
    )
    DashboardDetailsOutput dashboardEntitiesToDashboardDetailsOutput(
        DashboardEntity dashboard,
        DashboardversionEntity dashboardversion
    );

    @Mappings(
        { @Mapping(source = "user.id", target = "id"), @Mapping(source = "dashboard.id", target = "dashboardId") }
    )
    GetUserOutput userEntityToGetUserOutput(UserEntity user, DashboardEntity dashboard);
}
