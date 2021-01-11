package com.fastcode.example.application.core.actor;

import com.fastcode.example.application.core.actor.dto.*;
import com.fastcode.example.domain.core.actor.ActorEntity;
import java.time.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IActorMapper {
    ActorEntity createActorInputToActorEntity(CreateActorInput actorDto);

    CreateActorOutput actorEntityToCreateActorOutput(ActorEntity entity);

    ActorEntity updateActorInputToActorEntity(UpdateActorInput actorDto);

    UpdateActorOutput actorEntityToUpdateActorOutput(ActorEntity entity);

    FindActorByIdOutput actorEntityToFindActorByIdOutput(ActorEntity entity);
}
