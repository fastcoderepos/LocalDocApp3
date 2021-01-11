package com.fastcode.example.application.extended.authorization.user;

import com.fastcode.example.addons.reporting.domain.dashboardversion.*;
import com.fastcode.example.addons.reporting.domain.dashboardversionreport.*;
import com.fastcode.example.addons.reporting.domain.reportversion.*;
import com.fastcode.example.application.core.authorization.user.UserAppService;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.domain.core.authorization.userpreference.IUserpreferenceRepository;
import com.fastcode.example.domain.extended.authorization.user.IUserRepositoryExtended;
import org.springframework.stereotype.Service;

@Service("userAppServiceExtended")
public class UserAppServiceExtended extends UserAppService implements IUserAppServiceExtended {

    public UserAppServiceExtended(
        IDashboardversionRepository dashboardversionRepository,
        IReportversionRepository reportversionRepository,
        IDashboardversionreportRepository reportDashboardRepository,
        IUserRepositoryExtended userRepositoryExtended,
        IUserpreferenceRepository userpreferenceRepository,
        IUserMapperExtended mapper,
        LoggingHelper logHelper
    ) {
        super(
            dashboardversionRepository,
            reportversionRepository,
            reportDashboardRepository,
            userRepositoryExtended,
            userpreferenceRepository,
            mapper,
            logHelper
        );
    }
    //Add your custom code here

}
