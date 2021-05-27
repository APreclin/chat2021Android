package com.example.chat2021;

public class Message {
    String id;
    String contenu;
    String auteur;
    String couleur;

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", contenu='" + contenu + '\'' +
                ", auteur='" + auteur + '\'' +
                ", couleur='" + couleur + '\'' +
                '}';
    }
}
