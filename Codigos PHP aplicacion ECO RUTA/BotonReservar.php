<?php
require_once('EcoRutaConnectDB.php');

$id_objeto = $_POST["id_objeto"];
$fecha_retiro = $_POST["fecha_retiro"];
$id_interesado = $_POST["id_interesado"];



$reservarObjeto = "UPDATE `transaccion` SET `id_estado`= '2',`fecha_retiro`='$fecha_retiro',`id_interesado`='$id_interesado' WHERE id_objeto = '$id_objeto'";


$resultado = mysqli_query($conexion,$reservarObjeto);
mysqli_close($conexion);

?>