package com.reto.interbank.ms_transaction.mapper;

import com.reto.interbank.ms_transaction.dto.TransactionDTO;
import com.reto.interbank.ms_transaction.dto.TransactionStatusDTO;
import com.reto.interbank.ms_transaction.dto.TransactionTypeDTO;
import com.reto.interbank.ms_transaction.model.Transactions;

public class TransactionMapper {
    public static TransactionDTO toTransactionDTO(Transactions transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setTransactionExternalId(transaction.getId());
        TransactionTypeDTO typeDTO = new TransactionTypeDTO();
        
        switch (transaction.getTransactionTypeId()) {
	        case 1:
	            typeDTO.setName("DEPOSIT");
	            break;
	        case 2:
	            typeDTO.setName("WITHDRAWAL");
	            break;
	        case 3:
	            typeDTO.setName("TRANSFER");
	            break;
	        default:
	            typeDTO.setName("UNKNOW");
        }       
        dto.setTransactionType(typeDTO);
        TransactionStatusDTO statusDTO = new TransactionStatusDTO();
        statusDTO.setName(transaction.getStatus());
        dto.setTransactionStatus(statusDTO);
        dto.setValue(transaction.getValue());
        dto.setCreatedAt(transaction.getCreatedAt());
        return dto;
    }
}