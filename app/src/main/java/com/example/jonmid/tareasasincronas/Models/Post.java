package com.example.jonmid.tareasasincronas.Models;

/**
 * Created by AULA 7 on 12/04/2018.
 */

public class Post {
    private int id;
    private String tittle;
    private String body;

    // click derecho generate, setter and getter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
