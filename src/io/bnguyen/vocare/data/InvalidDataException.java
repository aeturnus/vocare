package io.bnguyen.vocare.data;

public class InvalidDataException extends Exception
{
    protected String message;
    public InvalidDataException()
    {
        message = "Invalid data encountered";
    }
    public InvalidDataException(String message)
    {
        this.message = message;
    }
    public String toString()
    {
        return message; 
    }
}
