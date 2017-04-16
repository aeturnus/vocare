package io.bnguyen.vocare.data;

public class InvalidNameException extends InvalidDataException
{
    String accountName;
    public InvalidNameException(String accountName)
    {
        this.accountName = accountName;
        this.message = "Invalid account name: " + accountName;
    }
    
    public String getAccountName()
    {
        return accountName;
    }
}
