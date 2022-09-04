package com.toss.bean;

import java.util.List;

public class VerifyInfo {
    private String id;
    private List<String> characters;

    public VerifyInfo() {
    }

    public VerifyInfo(String id, List<String> characters) {
        this.id = id;
        this.characters = characters;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getCharacters() {
        return characters;
    }

    public void setCharacters(List<String> characters) {
        this.characters = characters;
    }
}
