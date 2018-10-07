<?php
require_once('EcoRutaConnectDB.php');


$id_objeto = $_POST["id_objeto"];
$id_estado = 1;



$cancelar = "UPDATE `transaccion` SET `id_estado`= '$id_estado',`fecha_retiro`= '0000-00-00',`id_interesado`= NULL,`nombre_interesado`= NULL WHERE id_objeto = '$id_objeto'";

$resultado = mysqli_query($conexion,$cancelar);
mysqli_close($conexion);


?>