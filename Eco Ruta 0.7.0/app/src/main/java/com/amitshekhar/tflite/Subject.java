package com.amitshekhar.tflite;

public class Subject  {

    String SubName = null;
    String SubFullForm = null;
    String imagen;

    public Subject(String Sname, String SFullForm, String Simagen) {

        super();

        this.SubName = Sname;

        this.SubFullForm = SFullForm;
        this.imagen=Simagen;
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

}

