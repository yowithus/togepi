package com.example.togepi.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiConstant {

    public static final String v1 = "/v1";

    // Parameter
    public static final String PARAM_USER_ID = "user_id";
    public static final String PARAM_RECEIPT_NO = "receipt_no";

    // Api
    public static final String CREATE_TRANSACTION = v1 + "/transactions";
    public static final String FIND_TRANSACTION_BY_ID = v1 + "/transactions/{id}";
    public static final String SEARCH_TRANSACTIONS = v1 + "/transactions";
}
