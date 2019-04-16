<?php
//Codigo que se utilizar para mostrar la cantidad de objetos donados en mi perfil

require_once('EcoRutaConnectDB.php');

$id_usuario = $_POST["id_usuario"];


$contarDonados = "SELECT COUNT(`id_objeto`) 'DONADOS' FROM `transaccion` WHERE transaccion.id_dueno = '$id_usuario'";


$resultadocontarDonados = mysqli_query($conexion,$contarDonados);

$rowscontarDonados[] = mysqli_fetch_assoc($resultadocontarDonados);


echo json_encode($rowscontarDonados) . "<br>";

?>