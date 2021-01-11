package com.fastcode.example.application.extended.staff;

import com.fastcode.example.application.core.staff.IStaffMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IStaffMapperExtended extends IStaffMapper {}
