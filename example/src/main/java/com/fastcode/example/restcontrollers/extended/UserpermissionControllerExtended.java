package com.fastcode.example.restcontrollers.extended;

import com.fastcode.example.application.extended.authorization.permission.IPermissionAppServiceExtended;
import com.fastcode.example.application.extended.authorization.user.IUserAppServiceExtended;
import com.fastcode.example.application.extended.authorization.userpermission.IUserpermissionAppServiceExtended;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.restcontrollers.core.UserpermissionController;
import com.fastcode.example.security.JWTAppService;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userpermission/extended")
public class UserpermissionControllerExtended extends UserpermissionController {

    public UserpermissionControllerExtended(
        IUserpermissionAppServiceExtended userpermissionAppServiceExtended,
        IPermissionAppServiceExtended permissionAppServiceExtended,
        IUserAppServiceExtended userAppServiceExtended,
        JWTAppService jwtAppService,
        LoggingHelper helper,
        Environment env
    ) {
        super(
            userpermissionAppServiceExtended,
            permissionAppServiceExtended,
            userAppServiceExtended,
            jwtAppService,
            helper,
            env
        );
    }
    //Add your custom code here

}
