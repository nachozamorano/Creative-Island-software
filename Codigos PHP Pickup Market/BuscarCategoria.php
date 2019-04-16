<?php
//Codigo utilizado para buscar la categoria del objeto
require_once('EcoRutaConnectDB.php');

//codigo que se encarga de la busqueda de Categoria

$BuscarCategoria = "SELECT nombre_categoria FROM categoria";

$resultado = mysqli_query($conexion,$BuscarCategoria);

$rows = array();

while($r = mysqli_fetch_assoc($resultado)){
  $rows[] = $r;
}

echo json_encode($rows);


?>