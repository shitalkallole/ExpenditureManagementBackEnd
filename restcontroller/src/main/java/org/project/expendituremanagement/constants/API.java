package org.project.expendituremanagement.constants;

public interface API {
    String CATEGORY_PATH="/category";
    String CATEGORY_ID_PARAM_PATH="/{categoryId}";
    String FRIEND_PATH="/friend";
    String FRIEND_ID_PARAM_PATH="/{friendId}";
    String SESSION_PATH="/session";
    String VALIDATE_PATH="/validate";
    String DELETE_SESSION_PATH="/deleteSession";
    String LEND_BORROW_EXPENSE_PATH="/lendborrowexpense";
    String SHOW_PATH="/show";
    String START_DATE_PARAM_PATH="/{startDate}";
    String END_DATE_PARAM_PATH="/{endDate}";
    String DELETE_PATH="/delete";
    String TRANSACTION_ID_PARAM_PATH="/{transactionId}";
    String CALCULATE_PATH="/calculate";
    String ALL_PATH="/all";
    String PERSONAL_EXPENSE_PATH="/personalexpense";
    String USER_PATH="/user";
    String REGISTER_PATH="/register";
    String UPDATE_CREDENTIAL="/updatecredential";
}
