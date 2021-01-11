package com.fastcode.example.application.extended.category;

import com.fastcode.example.application.core.category.CategoryAppService;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.domain.extended.category.ICategoryRepositoryExtended;
import org.springframework.stereotype.Service;

@Service("categoryAppServiceExtended")
public class CategoryAppServiceExtended extends CategoryAppService implements ICategoryAppServiceExtended {

    public CategoryAppServiceExtended(
        ICategoryRepositoryExtended categoryRepositoryExtended,
        ICategoryMapperExtended mapper,
        LoggingHelper logHelper
    ) {
        super(categoryRepositoryExtended, mapper, logHelper);
    }
    //Add your custom code here

}
