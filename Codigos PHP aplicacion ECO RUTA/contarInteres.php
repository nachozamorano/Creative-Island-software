<?php

require_once('EcoRutaConnectDB.php');

$id_usuario = $_POST["id_usuario"];

$contarCategoria = "SELECT COUNT(`id_categoria`) 'CATEGORIA' FROM `objeto` INNER JOIN usuario ON usuario.id_usuario = objeto.id_propietario WHERE usuario.id_categoria = '$id_categoria'"; 

$resultadocontarCategoria = mysqli_query($conexion,$contarCategoria);

$rowscontarCategoria[] = mysqli_fetch_assoc($resultadocontarCategoria);

echo json_encode($rowscontarCategoria) . "<br>";

?>