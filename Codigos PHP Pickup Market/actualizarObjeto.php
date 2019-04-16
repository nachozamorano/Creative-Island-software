<?php
//Codigo que se encarga de actualizar la informacion de un objeto 

require_once('EcoRutaConnectDB.php');

$id_objeto = $_POST["id_objeto"];
$nombre_objeto = $_POST["nombre_objeto"];
$descripcion = $_POST["descripcion"];
$imagen = $_POST["imagen"];
$subcategoria = $_POST["subcategoria"];
$fecha_expiracion = $_POST["fecha_expiracion"];
$id_estado_objeto = $_POST["id_estado_objeto"];
$cantidad = $_POST["cantidad"];
$id_unidad = $_POST["id_unidad"];
$Latitud = $_POST["Latitud"];
$Longitud = $_POST["Longitud"];
$desde = $_POST["desde"];
$hasta = $_POST["hasta"];
$direccion = $_POST["direccion"];
$descripcion_estado = $_POST["descripcion_estado"];

$actualizar = "UPDATE `objeto` SET `nombre_objeto`= '$nombre_objeto',`descripcion`= '$descripcion', `descripcion_estado`= '$descripcion_estado',`subcategoria`= '$subcategoria',`fecha_expiracion`= '$fecha_expiracion',`id_estado_objeto`='$id_estado_objeto',`cantidad`= '$cantidad',`id_unidad`= '$id_unidad',`Latitud`= '$Latitud',`Longitud`= '$Longitud',`desde`= '$desde',`hasta`= '$hasta',`direccion`= '$direccion' WHERE id_objeto = '$id_objeto'";

$resultado = mysqli_query($conexion,$actualizar);

if(!$resultado){
    echo "Error al crear Usuario";
}else{
    echo "Usuario creado exitosamente" . '<br>';

//-------------Codigo que se encarga de subir la imagen------------------------
if($imagen == NULL){
	
$id = $id_objeto;
$actualpath = "https://ecoruta.webcindario.com/fotosObjetos/ERROR.png";
$agregarImagen = "UPDATE objeto SET objeto.imagen = '$actualpath' WHERE id_objeto = '$id_objeto'";
 
 if(mysqli_query($conexion,$agregarImagen)){
 file_put_contents($path,base64_decode($imagen));
 echo "Subio imagen Correctamente";
 }
 
 mysqli_close($conexion);

}else{   
if($_SERVER['REQUEST_METHOD']=='POST'){

 $id = $id_objeto;

 $path = "fotosObjetos/$id.png";
 
 $actualpath = "https://ecoruta.webcindario.com/$path";
 
 $agregarImagen = "UPDATE objeto SET objeto.imagen = '$actualpath' WHERE id_objeto = '$id_objeto'";
 
 if(mysqli_query($conexion,$agregarImagen)){
 file_put_contents($path,base64_decode($imagen));
 echo "Subio imagen Correctamente";
 }
 
 mysqli_close($conexion);
}else{
 echo "Error";
}
//----------------------------------------------------------------------------
}
}
?>