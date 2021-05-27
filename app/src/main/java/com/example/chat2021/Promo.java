package com.example.chat2021;

import java.util.ArrayList;

public class Promo {
    String promo;
    ArrayList<Enseignant> enseignants;

    //{"promo":"2020-2021",
    // "enseignants":[
    // {"prenom":"Mohamed","nom":"Boukadir"},
    // {"prenom":"Thomas","nom":"Bourdeaud'huy"},
    // {"prenom":"Mathieu","nom":"Haussher"},
    // {"prenom":"Slim","nom":"Hammadi"}]}

    @Override
    public String toString() {
        return "Promo{" +
                "promo='" + promo + '\'' +
                ", enseignants=" + enseignants +
                '}';
    }
}
