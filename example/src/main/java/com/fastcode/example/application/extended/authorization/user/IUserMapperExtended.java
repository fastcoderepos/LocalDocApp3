package com.fastcode.example.application.extended.authorization.user;

import com.fastcode.example.application.core.authorization.user.IUserMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IUserMapperExtended extends IUserMapper {}
