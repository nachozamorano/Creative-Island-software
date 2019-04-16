<?php
//Codigo que se utiliza para contar los objetos Adquiridos y mostrarlos en el perfil de Usuario

require_once('EcoRutaConnectDB.php');

$id_usuario = $_POST["id_usuario"];

$contarAdquiridos = "SELECT COUNT(`id_objeto`) 'RESERVADOS' FROM `transaccion` WHERE transaccion.id_interesado = '$id_usuario'"; 

$resultadocontarAdquiridos = mysqli_query($conexion,$contarAdquiridos);

$rowscontarAdquiridos[] = mysqli_fetch_assoc($resultadocontarAdquiridos);

echo json_encode($rowscontarAdquiridos) . "<br>";

?>