<?php
//--------------------Codigo que se encarga de editar la informacion del usuario --------------------------------------------------
require_once('EcoRutaConnectDB.php');

$id_usuario  = $_POST["id_usuario"];
$nombre = $_POST["nombre"];
$img_perfil = $_POST["img_perfil"];
$correo = $_POST["correo"];
$telefono_movil = $_POST["telefono_movil"];
$telefono_fijo = $_POST["telefono_fijo"];
$id_genero =$_POST["id_genero"];
//$id_tipo_persona = $_POST["id_tipo_persona"];
$id_categoria = $_POST["id_categoria"];

$actualizar = "UPDATE usuario SET nombre = '$nombre', correo = '$correo', telefono_movil = '$telefono_movil', telefono_fijo = '$telefono_fijo', id_genero = '$id_genero', id_categoria = '$id_categoria' WHERE id_usuario = '$id_usuario'";

$resultado = mysqli_query($conexion,$actualizar);

if(!$resultado){
    echo "Error al crear Usuario";
}else{
    echo "Usuario creado exitosamente" . '<br>';
   
    $last_id = mysqli_insert_id($conexion);

    echo $last_id . '<br>';

//-------------Codigo que se encarga de subir la imagen------------------------
if($imagen == NULL){
	
$id = $last_id;
$actualpath = "https://ecoruta.webcindario.com/uploads/ERROR.png";
$agregarImagen = "UPDATE usuario SET usuario.img_perfil = '$actualpath' WHERE id_usuario = '$last_id'";
 
 if(mysqli_query($conexion,$agregarImagen)){
 file_put_contents($path,base64_decode($imagen));
 echo "Subio imagen Correctamente";
 }
 
 mysqli_close($conexion);

}else{   
if($_SERVER['REQUEST_METHOD']=='POST'){

 $id = $last_id;

 $path = "uploads/$id.png";
 
 $actualpath = "https://ecoruta.webcindario.com/$path";
 
 $agregarImagen = "UPDATE usuario SET usuario.img_perfil = '$actualpath' WHERE id_usuario = '$last_id'";
 
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