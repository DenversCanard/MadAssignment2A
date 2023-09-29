package com.example.madassignment2a;

public class Contact {
    private int id;
    private String name;
    private int number;
    private String description;
    private String photoPath;

    public Contact(int id, String name, int number, String description, String photoPath)
    {
        this.id = id;
        this.name = name;
        this.number = number;
        this.description = description;
        this.photoPath = photoPath;
    }

    public void setId(int id)
    {
        this.id = id;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public void setNumber(int number)
    {
        this.number = number;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }
    public void setPhotoPath(String photoPath)
    {
        this.photoPath = photoPath;
    }

    public int getId()
    {
        return id;
    }
    public String getName()
    {
        return name;
    }

    public int getNumber()
    {
        return number;
    }

    public String getDescription()
    {
        return description;
    }
    public String getPhotoPath()
    {
        return photoPath;
    }
}
