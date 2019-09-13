package com.demo.bankingproject;

public class BankDetails {
    String transactionamt;
    String walletbalance;
    String dateandtime;
    String description;
    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BankDetails(String transactionamt, String walletbalance, String dateandtime, String description, String status) {
        this.transactionamt = transactionamt;
        this.walletbalance = walletbalance;
        this.dateandtime = dateandtime;
        this.description = description;
        this.status = status;
    }

    public String getTransactionamt() {
        return transactionamt;
    }

    public void setTransactionamt(String transactionamt) {
        this.transactionamt = transactionamt;
    }

    public String getWalletbalance() {
        return walletbalance;
    }

    public void setWalletbalance(String walletbalance) {
        this.walletbalance = walletbalance;
    }

    public String getDateandtime() {
        return dateandtime;
    }

    public void setDateandtime(String dateandtime) {
        this.dateandtime = dateandtime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
