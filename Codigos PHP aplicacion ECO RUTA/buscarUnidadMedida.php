<?php
//Codigo utilizado para buscar la Unidad de medida del objeto
require_once('EcoRutaConnectDB.php');


$BuscarUnidad = "SELECT id_unidad, unidad_medida FROM unidad_medida";

$resultado = mysqli_query($conexion,$BuscarUnidad);

$rows = array();

while($r = mysqli_fetch_assoc($resultado)){
  $rows[] = $r;
}

echo json_encode($rows);
?>