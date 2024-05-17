package com.interbank.anti.fraud.core.mapper;

import com.interbank.anti.fraud.core.domain.Transaction;
import com.interbank.anti.fraud.core.dto.FraudDetailRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AntiFraudMapper {
    AntiFraudMapper MAPPER = Mappers.getMapper(AntiFraudMapper.class);

    @Mapping(target = "accountExternalIdDebit", source = "accountExternalIdDebit")
    @Mapping(target = "accountExternalIdCredit", source = "accountExternalIdCredit")
    @Mapping(target = "transactionType.name", source = "tranferTypeId")
    @Mapping(target = "transactionStatus.name", defaultValue = "PENDIENTE")
    @Mapping(target = "value", source = "value")
    Transaction buildToAntiFraudDetail(FraudDetailRequestDto fraudDetailRequestDto);
}
