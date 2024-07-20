package com.nttdata.service.mapper;

import com.nttdata.controller.request.TransactionReq;
import com.nttdata.entity.Transaction;
import com.nttdata.helper.Util;

import java.time.LocalDateTime;

public class TransactionMapper {

    public static Transaction TransactionRequestMapperToTransaction(TransactionReq request) {
        return Transaction.builder()
                .accountExternalIdDebit(request.getAccountExternalIdDebit())
                .accountExternalIdCredit(request.getAccountExternalIdCredit())
                .transferTypeId(request.getTransferTypeId())
                .value(request.getValue())
                .status(Util.TransactionStatus.PENDING.name())
                .createdAt(LocalDateTime.now()).build();
    }
}
