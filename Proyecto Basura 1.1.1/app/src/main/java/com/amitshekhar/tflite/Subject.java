package com.amitshekhar.tflite;

import android.widget.ImageView;

public class Subject  {

    String SubName = null;
    String SubFullForm = null;
    int imagen;

    public Subject(String Sname, String SFullForm, int Simagen) {

        super();

        this.SubName = Sname;

        this.SubFullForm = SFullForm;
        this.imagen=Simagen;
    }

    public Subject(String tempName, String tempFullForm, String rojo) {
    }

    public String getSubName() {

        return SubName;

    }
    public void setSubName(String code) {

        this.SubName = code;

    }
    public String getSubFullForm() {

        return SubFullForm;

    }
    public void setSubFullForm(String name) {

        this.SubFullForm = name;

    }

    @Override
    public String toString() {

        return  SubName + " " + SubFullForm ;

    }

    public int getImage() {
        return imagen;
    }
}
