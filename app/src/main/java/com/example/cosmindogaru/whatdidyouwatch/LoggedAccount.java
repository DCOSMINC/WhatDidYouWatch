package com.example.cosmindogaru.whatdidyouwatch;

class LoggedAccount {

    private static String loggedAccount;

    public static String getLoggedAccount() {
        return loggedAccount;
    }

    public static void setLoggedAccount(String loggedAccount) {
        LoggedAccount.loggedAccount = loggedAccount;
    }
}
