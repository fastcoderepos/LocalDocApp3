package com.fastcode.example.application.extended.authorization.userrole;

import com.fastcode.example.application.core.authorization.userrole.UserroleAppService;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.domain.extended.authorization.role.IRoleRepositoryExtended;
import com.fastcode.example.domain.extended.authorization.user.IUserRepositoryExtended;
import com.fastcode.example.domain.extended.authorization.userrole.IUserroleRepositoryExtended;
import org.springframework.stereotype.Service;

@Service("userroleAppServiceExtended")
public class UserroleAppServiceExtended extends UserroleAppService implements IUserroleAppServiceExtended {

    public UserroleAppServiceExtended(
        IUserroleRepositoryExtended userroleRepositoryExtended,
        IRoleRepositoryExtended roleRepositoryExtended,
        IUserRepositoryExtended userRepositoryExtended,
        IUserroleMapperExtended mapper,
        LoggingHelper logHelper
    ) {
        super(userroleRepositoryExtended, roleRepositoryExtended, userRepositoryExtended, mapper, logHelper);
    }
    //Add your custom code here

}
