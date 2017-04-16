package io.bnguyen.vocare.data;

public class InvalidAccountNameException extends InvalidDataException
{
    String accountName;
    public InvalidAccountNameException(String accountName)
    {
        this.accountName = accountName;
        this.message = "Invalid account name: " + accountName;
    }
    
    public String getAccountName()
    {
        return accountName;
    }
}
