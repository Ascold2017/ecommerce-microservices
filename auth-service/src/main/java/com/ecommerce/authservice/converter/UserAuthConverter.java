package com.ecommerce.authservice.converter;

import com.ecommerce.authservice.dto.RegisterRequest;
import com.ecommerce.authservice.service.model.RegisterUserModel;
import lombok.experimental.UtilityClass;

/**
 * @author Yurii Miedviediev
 * @version 1.0.0
 * @since 11/07/2026
 */
@UtilityClass
public class UserAuthConverter {

    //mapstruct - auto converter
    public RegisterUserModel convertToModel(RegisterRequest request) {
        return new RegisterUserModel(request.email(), request.password());
    }
}
