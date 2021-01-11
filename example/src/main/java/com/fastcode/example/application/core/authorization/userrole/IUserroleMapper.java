package com.fastcode.example.application.core.authorization.userrole;

import com.fastcode.example.application.core.authorization.userrole.dto.*;
import com.fastcode.example.domain.core.authorization.role.RoleEntity;
import com.fastcode.example.domain.core.authorization.user.UserEntity;
import com.fastcode.example.domain.core.authorization.userrole.UserroleEntity;
import java.time.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface IUserroleMapper {
    UserroleEntity createUserroleInputToUserroleEntity(CreateUserroleInput userroleDto);

    @Mappings(
        {
            @Mapping(source = "user.id", target = "userId"),
            @Mapping(source = "user.userName", target = "userDescriptiveField"),
            @Mapping(source = "role.displayName", target = "roleDescriptiveField"),
            @Mapping(source = "role.id", target = "roleId"),
        }
    )
    CreateUserroleOutput userroleEntityToCreateUserroleOutput(UserroleEntity entity);

    UserroleEntity updateUserroleInputToUserroleEntity(UpdateUserroleInput userroleDto);

    @Mappings(
        {
            @Mapping(source = "entity.role.displayName", target = "roleDescriptiveField"),
            @Mapping(source = "entity.user.userName", target = "userDescriptiveField"),
        }
    )
    UpdateUserroleOutput userroleEntityToUpdateUserroleOutput(UserroleEntity entity);

    @Mappings(
        {
            @Mapping(source = "entity.role.displayName", target = "roleDescriptiveField"),
            @Mapping(source = "entity.user.userName", target = "userDescriptiveField"),
        }
    )
    FindUserroleByIdOutput userroleEntityToFindUserroleByIdOutput(UserroleEntity entity);

    @Mappings(
        {
            @Mapping(source = "foundUserrole.roleId", target = "userroleRoleId"),
            @Mapping(source = "foundUserrole.userId", target = "userroleUserId"),
        }
    )
    GetRoleOutput roleEntityToGetRoleOutput(RoleEntity role, UserroleEntity foundUserrole);

    @Mappings(
        {
            @Mapping(source = "foundUserrole.roleId", target = "userroleRoleId"),
            @Mapping(source = "foundUserrole.userId", target = "userroleUserId"),
        }
    )
    GetUserOutput userEntityToGetUserOutput(UserEntity user, UserroleEntity foundUserrole);
}
