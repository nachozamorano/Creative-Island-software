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

$actualizar = "UPDATE usuario SET nombre = '$nombre', correo = '$correo', telefono_movil = '$telefono_movil', telefono_fijo = '$telefono_fijo', id_genero = '$id_genero' WHERE id_usuario = '$id_usuario'";

$resultado = mysqli_query($conexion,$actualizar);

if(!$resultado){
    echo "Error al actualizar informacion";
}else{
    echo "Informacion actualizada exitosamente";
//-------------Codigo que se encarga de subir la imagen------------------------
if($_SERVER['REQUEST_METHOD']=='POST'){
	
 $id = $id_usuario;

 $path = "uploads/$id.png";
 
 $actualpath = "https://ecoruta.webcindario.com/$path";
 
 $agregarImagen = "UPDATE usuario SET usuario.img_perfil = '$actualpath' WHERE id_usuario = '$id_usuario'";
 
 if(mysqli_query($conexion,$agregarImagen)){
 file_put_contents($path,base64_decode($img_perfil));
 echo "Subio imagen Correctamente";
 }
 
 mysqli_close($conexion);
}else{
 echo "Error";
}
//----------------------------------------------------------------------------    
}
?>