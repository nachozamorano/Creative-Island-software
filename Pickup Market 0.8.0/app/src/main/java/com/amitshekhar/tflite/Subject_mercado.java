package com.amitshekhar.tflite;

public class Subject_mercado  {

    String SubName = null;
    String SubFullForm = null;
    String imagen;
    String estado;

    public Subject_mercado(String Sname, String SFullForm, String Simagen, String estado) {

        super();

        this.SubName = Sname;

        this.SubFullForm = SFullForm;
        this.imagen=Simagen;
        this.estado = estado;
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

    public String getImage() {
        System.out.println("holi"+imagen);
        return imagen;
    }
    public String getEstado() {
        return estado;
    }
}

