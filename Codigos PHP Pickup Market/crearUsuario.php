<?php
//Codigo encargado de la creacion de usuarios
require_once('EcoRutaConnectDB.php');

$nombre = $_POST["nombre"];
$password = $_POST["password"];
$imagen = $_POST["img_perfil"];
$correo = $_POST["correo"];
$telefono_movil = $_POST["telefono_movil"];
$telefono_fijo = $_POST["telefono_fijo"];
$id_genero = $_POST["id_genero"];
$id_tipo_persona = 1;
$id_categoria = $_POST["id_categoria"];


$crearUsuario = "INSERT INTO `usuario`(`nombre`, `password`, `correo`, `telefono_movil`, `telefono_fijo`, `id_genero`, `id_tipo_persona`,`id_categoria`) VALUES ('$nombre','$password','$correo','$telefono_movil','$telefono_fijo','$id_genero', '$id_tipo_persona', '$id_categoria')";


$resultado = mysqli_query($conexion,$crearUsuario);

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
