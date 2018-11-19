package com.amitshekhar.tflite;

public class Subject  {

    String SubName = null;
    String SubFullForm = null;
    String imagen;
    String estado;

    public Subject(String Sname, String SFullForm, String Simagen,String Sestado) {

        super();

        this.SubName = Sname;

        this.SubFullForm = SFullForm;
        this.imagen=Simagen;
        this.estado=Sestado;
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
        return imagen;
    }
    public String getEstado(){
        return estado;
    }

}

