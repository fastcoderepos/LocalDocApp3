package com.fastcode.example.addons.reporting.application.reportversion;

import com.fastcode.example.addons.reporting.application.reportversion.dto.CreateReportversionInput;
import com.fastcode.example.addons.reporting.application.reportversion.dto.CreateReportversionOutput;
import com.fastcode.example.addons.reporting.application.reportversion.dto.FindReportversionByIdOutput;
import com.fastcode.example.addons.reporting.application.reportversion.dto.GetUserOutput;
import com.fastcode.example.addons.reporting.application.reportversion.dto.UpdateReportversionInput;
import com.fastcode.example.addons.reporting.application.reportversion.dto.UpdateReportversionOutput;
import com.fastcode.example.addons.reporting.domain.reportversion.ReportversionEntity;
import com.fastcode.example.domain.core.authorization.user.UserEntity;
import java.time.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface IReportversionMapper {
    ReportversionEntity createReportversionInputToReportversionEntity(CreateReportversionInput reportversionDto);

    @Mappings(
        {
            @Mapping(source = "userId", target = "userId"),
            @Mapping(source = "user.userName", target = "userDescriptiveField"),
            @Mapping(source = "report.id", target = "reportId"),
        }
    )
    CreateReportversionOutput reportversionEntityToCreateReportversionOutput(ReportversionEntity entity);

    @Mappings(
        { @Mapping(source = "userId", target = "userId"), @Mapping(source = "version", target = "reportVersion") }
    )
    ReportversionEntity reportversionEntityToReportversionEntity(
        ReportversionEntity entity,
        Long userId,
        String version
    );

    ReportversionEntity updateReportversionInputToReportversionEntity(UpdateReportversionInput reportversionDto);

    @Mappings(
        {
            @Mapping(source = "userId", target = "userId"),
            @Mapping(source = "user.userName", target = "userDescriptiveField"),
            @Mapping(source = "report.id", target = "reportId"),
        }
    )
    UpdateReportversionOutput reportversionEntityToUpdateReportversionOutput(ReportversionEntity entity);

    @Mappings(
        {
            @Mapping(source = "userId", target = "userId"),
            @Mapping(source = "user.userName", target = "userDescriptiveField"),
            @Mapping(source = "report.id", target = "reportId"),
        }
    )
    FindReportversionByIdOutput reportversionEntityToFindReportversionByIdOutput(ReportversionEntity entity);

    @Mappings(
        {
            @Mapping(source = "user.id", target = "id"),
            @Mapping(source = "reportversion.reportVersion", target = "reportVersion"),
        }
    )
    GetUserOutput userEntityToGetUserOutput(UserEntity user, ReportversionEntity reportversion);
}
