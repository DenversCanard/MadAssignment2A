package com.example.madassignment2a;

public class Contact {
    private String name;
    private String number;
    private String email;
    private byte[] photo;

    public Contact(String name, String number, String email, byte[] photo)
    {
        this.name = name;
        this.number = number;
        this.email = email;
        this.photo = photo;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    public void setNumber(String number)
    {
        this.number = number;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
    public void setPhoto(byte[] photo)
    {
        this.photo = photo;
    }

    public String getName()
    {
        return name;
    }

    public String getNumber()
    {
        return number;
    }

    public String getEmail()
    {
        return email;
    }
    public byte[] getPhoto()
    {
        return photo;
    }
}
