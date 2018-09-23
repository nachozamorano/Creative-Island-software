<?php
//Codigo utilizado para buscar la Subcategoria
require_once('EcoRutaConnectDB.php');

$id_categoria = $_POST["id_categoria"];

$BuscarSubCategoria = "SELECT subcategoria.id_subcategoria, subcategoria.nombre FROM subcategoria INNER JOIN categoria ON categoria.id_categoria = subcategoria.id_categoria WHERE categoria.id_categoria = '$id_categoria'";

$resultado = mysqli_query($conexion,$BuscarSubCategoria);

$rows = array();

while($r = mysqli_fetch_assoc($resultado)){
  $rows[] = $r;
}

echo json_encode($rows);
?>