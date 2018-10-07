<?php
//Codigo utilizado para buscar el Genero del usuario
require_once('EcoRutaConnectDB.php');


$BuscarGenero = "SELECT nombre_genero FROM genero";

$resultado = mysqli_query($conexion,$BuscarGenero);

$rows = array();

while($r = mysqli_fetch_assoc($resultado)){
  $rows[] = $r;
}

echo json_encode($rows);



?>