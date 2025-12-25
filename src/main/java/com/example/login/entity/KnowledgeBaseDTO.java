package com.example.login.entity;

import java.util.List;

public class KnowledgeBaseDTO {
    private String name;
    private String owner;
    private String date;
    private List<String> editors;

    // getter 和 setter
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public List<String> getEditors() { return editors; }
    public void setEditors(List<String> editors) { this.editors = editors; }
}