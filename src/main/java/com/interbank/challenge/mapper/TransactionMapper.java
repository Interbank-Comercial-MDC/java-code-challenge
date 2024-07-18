package com.interbank.challenge.mapper;

import com.interbank.challenge.dto.request.TransactionRequest;
import com.interbank.challenge.dto.response.TransactionResponse;
import com.interbank.challenge.entities.Transaction;
import com.interbank.challenge.util.Constantes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", imports = {Constantes.class})
public interface TransactionMapper {

    @Mappings({
            @Mapping(target = "status", expression = "java(Constantes.ESTADO_PENDIENTE)")
    })
    Transaction toEntity(TransactionRequest request);

    @Mapping(source = "id", target = "transactionExternalId")
    @Mapping(source = "tranType", target = "transactionType.name")
    @Mapping(source = "status", target = "transactionStatus.name")
    TransactionResponse toResponse(Transaction transaction);
}
