<?php
//-------Codigo que se encarga de mostrar la informacion del perfil de susario -----------------------------------------
require_once('EcoRutaConnectDB.php');

$id_usuario = $_POST["id_usuario"];

$verPerfil = "SELECT usuario.nombre, usuario.img_perfil, usuario.correo, usuario.telefono_movil, usuario.telefono_fijo, genero.nombre_genero FROM usuario INNER JOIN genero ON genero.id_genero = usuario.id_genero WHERE id_usuario = '$id_usuario'";

$resultado = mysqli_query($conexion,$verPerfil);

$rows = array();

while($r = mysqli_fetch_assoc($resultado)){
  $rows[] = $r;
  
 
}

echo json_encode($rows);

?>