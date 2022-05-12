package com.nttdata.api.bankaccount.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Unwrapped.Nullable;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {

    @Nullable
    private Object obj;
    @Nullable
    private String message;
    private Date timestamp;
}
