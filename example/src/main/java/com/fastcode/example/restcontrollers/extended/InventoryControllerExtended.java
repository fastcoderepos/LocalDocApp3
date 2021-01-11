package com.fastcode.example.restcontrollers.extended;

import com.fastcode.example.application.extended.film.IFilmAppServiceExtended;
import com.fastcode.example.application.extended.inventory.IInventoryAppServiceExtended;
import com.fastcode.example.application.extended.rental.IRentalAppServiceExtended;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.restcontrollers.core.InventoryController;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory/extended")
public class InventoryControllerExtended extends InventoryController {

    public InventoryControllerExtended(
        IInventoryAppServiceExtended inventoryAppServiceExtended,
        IFilmAppServiceExtended filmAppServiceExtended,
        IRentalAppServiceExtended rentalAppServiceExtended,
        LoggingHelper helper,
        Environment env
    ) {
        super(inventoryAppServiceExtended, filmAppServiceExtended, rentalAppServiceExtended, helper, env);
    }
    //Add your custom code here

}
