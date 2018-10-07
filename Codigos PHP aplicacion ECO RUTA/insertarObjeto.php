<?php
//Insertar un nuevo Objeto
require_once('EcoRutaConnectDB.php');


$id_propietario = $_POST["id_propietario"];
$nombre_objeto = $_POST["nombre_objeto"];
$descripcion = $_POST["descripcion"];
$imagen = $_POST["imagen"];
$subcategoria = $_POST["subcategoria"];
$fecha_publicacion = $_POST["fecha_publicacion"];
$fecha_expiracion = $_POST["fecha_expiracion"];
$id_estado_objeto = $_POST["id_estado_objeto"];
$cantidad = $_POST["cantidad"];
$id_unidad = $_POST["id_unidad"];
$Latitud = $_POST["Latitud"];
$Longitud = $_POST["Longitud"];
$desde = $_POST["desde"];
$hasta = $_POST["hasta"];
$direccion = $_POST["direccion"];

$insertar ="INSERT INTO `objeto`(`id_propietario`, `nombre_objeto`, `descripcion`, `subcategoria`, `fecha_publicacion`, `fecha_expiracion`, `id_estado_objeto`, `cantidad`, `id_unidad`, `Latitud`, `Longitud`, `desde`, `hasta`, `direccion`) VALUES ('$id_propietario','$nombre_objeto','$descripcion','$subcategoria','$fecha_publicacion','$fecha_expiracion','$id_estado_objeto','$cantidad','$id_unidad','$Latitud','$Longitud','$desde','$hasta', '$direccion')";

$resultado = mysqli_query($conexion,$insertar);


if(!$resultado){
    echo "Error al crear Usuario";
}else{
    echo "Usuario creado exitosamente" . '<br>';
   
    $last_id = mysqli_insert_id($conexion);

    echo $last_id . '<br>';

    $insertarTran = "INSERT INTO `transaccion`(`id_objeto`, `id_dueno`, `id_estado`) VALUES ('$last_id', '$id_propietario', '1')";

    $resultadotrans = mysqli_query($conexion,$insertarTran);

//-------------Codigo que se encarga de subir la imagen------------------------
if($imagen == NULL){
	
$id = $last_id;
$actualpath = "https://ecoruta.webcindario.com/fotosObjetos/ERROR.png";
$agregarImagen = "UPDATE objeto SET objeto.imagen = '$actualpath' WHERE id_objeto = '$last_id'";
 
 if(mysqli_query($conexion,$agregarImagen)){
 file_put_contents($path,base64_decode($imagen));
 echo "Subio imagen Correctamente";
 }
 
 mysqli_close($conexion);

}else{   
if($_SERVER['REQUEST_METHOD']=='POST'){

 $id = $last_id;

 $path = "fotosObjetos/$id.png";
 
 $actualpath = "https://ecoruta.webcindario.com/$path";
 
 $agregarImagen = "UPDATE objeto SET objeto.imagen = '$actualpath' WHERE id_objeto = '$last_id'";
 
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

