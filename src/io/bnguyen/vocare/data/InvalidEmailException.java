package io.bnguyen.vocare.data;

public class InvalidEmailException extends InvalidDataException
{
    String email;
    public InvalidEmailException(String email)
    {
        this.email = email;
        this.message = "Invalid email: " + email;
    }
    
    public String getEmail()
    {
        return email;
    }
}
