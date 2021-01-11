package com.fastcode.example.restcontrollers.extended;

import com.fastcode.example.application.extended.category.ICategoryAppServiceExtended;
import com.fastcode.example.application.extended.filmcategory.IFilmCategoryAppServiceExtended;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.restcontrollers.core.CategoryController;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category/extended")
public class CategoryControllerExtended extends CategoryController {

    public CategoryControllerExtended(
        ICategoryAppServiceExtended categoryAppServiceExtended,
        IFilmCategoryAppServiceExtended filmCategoryAppServiceExtended,
        LoggingHelper helper,
        Environment env
    ) {
        super(categoryAppServiceExtended, filmCategoryAppServiceExtended, helper, env);
    }
    //Add your custom code here

}
