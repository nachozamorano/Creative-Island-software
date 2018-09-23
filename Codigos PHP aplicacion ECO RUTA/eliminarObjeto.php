<?php
//------------------Codigo que se utiliza para eliminar un objeto----------------------------------------------

require_once('EcoRutaConnectDB.php');

$id_objeto = $_POST["id_objeto"];

$eliminar = "DELETE FROM `objeto` WHERE id_objeto = '$id_objeto'";

$resultado = mysqli_query($conexion,$eliminar);

if(!$resultado){
    echo "Error al eliminar";
}else{
    echo "Eliminacion Exitosa";
}

?>