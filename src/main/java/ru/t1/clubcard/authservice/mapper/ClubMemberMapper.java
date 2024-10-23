package ru.t1.clubcard.authservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.t1.clubcard.authservice.dto.in.ClubMemberRegisterRequest;
import ru.t1.clubcard.authservice.dto.out.ClubMemberRegisterResponse;
import ru.t1.clubcard.authservice.model.ClubMember;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClubMemberMapper {
    ClubMember mapClubMemberRegisterRequest(ClubMemberRegisterRequest request);
    ClubMemberRegisterResponse mapClubMember(ClubMember clubMember);
}
