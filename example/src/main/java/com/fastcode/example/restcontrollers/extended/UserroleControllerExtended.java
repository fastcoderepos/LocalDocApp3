package com.fastcode.example.restcontrollers.extended;

import com.fastcode.example.application.extended.authorization.role.IRoleAppServiceExtended;
import com.fastcode.example.application.extended.authorization.user.IUserAppServiceExtended;
import com.fastcode.example.application.extended.authorization.userrole.IUserroleAppServiceExtended;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.restcontrollers.core.UserroleController;
import com.fastcode.example.security.JWTAppService;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userrole/extended")
public class UserroleControllerExtended extends UserroleController {

    public UserroleControllerExtended(
        IUserroleAppServiceExtended userroleAppServiceExtended,
        IRoleAppServiceExtended roleAppServiceExtended,
        IUserAppServiceExtended userAppServiceExtended,
        JWTAppService jwtAppService,
        LoggingHelper helper,
        Environment env
    ) {
        super(userroleAppServiceExtended, roleAppServiceExtended, userAppServiceExtended, jwtAppService, helper, env);
    }
    //Add your custom code here

}
