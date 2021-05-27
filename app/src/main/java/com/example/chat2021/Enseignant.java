package com.example.chat2021;

import com.google.gson.annotations.SerializedName;

public class Enseignant {
    @SerializedName("prenom")
    public String firstname;
    @SerializedName("nom")
    public String lastname;

    @Override
    public String toString() {
        return "Enseignant{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
