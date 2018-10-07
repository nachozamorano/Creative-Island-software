<?php
//Codigo utilizado para buscar la estado nuevo, dañado o usado del objeto

require_once('EcoRutaConnectDB.php');

//codigo que se encarga de la busqueda de Estado

$BuscarEstado = "SELECT nombre_estado_objeto FROM estado_objeto";

$resultado = mysqli_query($conexion,$BuscarEstado);

$rows = array();

while($r = mysqli_fetch_assoc($resultado)){
  $rows[] = $r;
  
 
}

echo json_encode($rows);

?>