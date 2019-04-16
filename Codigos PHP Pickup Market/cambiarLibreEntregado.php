<?php
//Codigo que se utilizar para mostrar la cantidad de objetos donados en mi perfil

require_once('EcoRutaConnectDB.php');

$id_objeto = $_POST["id_objeto"];
$n_estado = $_POST["n_estado"];

if($n_estado == 'LIBRE'){


$actualizar = "UPDATE `transaccion` SET `id_estado`= '1' WHERE transaccion.id_objeto = '$id_objeto'";

$resultado = mysqli_query($conexion,$actualizar);

mysqli_close($conexion);


}else if( $n_estado == 'ENTREGADO'){

$actualizar = "UPDATE `transaccion` SET `id_estado`= '3' WHERE transaccion.id_objeto = '$id_objeto'";

$resultado = mysqli_query($conexion,$actualizar);

mysqli_close($conexion);


}else{

echo "ERROR";

}

?>