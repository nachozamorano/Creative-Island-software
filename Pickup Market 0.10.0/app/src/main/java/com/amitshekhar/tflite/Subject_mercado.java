package com.amitshekhar.tflite;

public class Subject_mercado  {

    String SubName = null;
    String SubFullForm = null;
    String imagen;
    String estado;
    String id_objeto,publicacion,sub,expiracion,desde,hasta,descripcion_estado,direccion,cantidad,unidad_medida,id_estado,nombre_estado;

    public Subject_mercado(String Sname, String SFullForm, String Simagen, String estado,String id_objeto,String publicacion,String sub,
                           String expiracion,String desde,String hasta,String descripcion_estado,String direccion,String cantidad,
                           String unidad_medida, String id_estado,String nombre_estado) {

        super();

        this.SubName = Sname;

        this.SubFullForm = SFullForm;
        this.imagen=Simagen;
        this.estado = estado;
        this.id_objeto=id_objeto;
        this.publicacion=publicacion;
        this.sub=sub;
        this.expiracion=expiracion;
        this.desde=desde;
        this.hasta=hasta;
        this.descripcion_estado=descripcion_estado;
        this.direccion=direccion;
        this.cantidad=cantidad;
        this.unidad_medida=unidad_medida;
        this.id_estado=id_estado;
        this.nombre_estado=nombre_estado;
    }
    public String getNombre_estado() {

        return nombre_estado;

    }
    public String getId_estado() {

        return id_estado;

    }
    public String getUnidad_medida() {

        return unidad_medida;

    }
    public String getCantidad() {

        return cantidad;

    }
    public String getDireccion() {

        return direccion;

    }
    public String getDescripcion_estado() {

        return descripcion_estado;

    }
    public String getHasta() {

        return hasta;

    }
    public String getDesde() {

        return desde;

    }
    public String getExpiracion() {

        return expiracion;

    }
    public String getSub() {

        return sub;

    }
    public String getPublicacion() {

        return publicacion;

    }
    public String getId_objeto() {

        return id_objeto;

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
    public String getEstado() {
        return estado;
    }
}

