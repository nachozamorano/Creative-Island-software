//Este es el que usamos
<?php
require_once('EcoRutaConnectDB.php');

$id_objeto = $_POST["id_objeto"];
$id_propietario = $_POST["id_propietario"];


$Buscar = "SELECT * FROM objeto WHERE id_objeto = '$id_objeto' AND id_propietario = '$id_propietario'";

$resultado = mysqli_query($conexion,$Buscar);
$totalFilas  = mysqli_num_rows($resultado);
$rows = array();

while($r = mysqli_fetch_assoc($resultado)){
	$rows[] = $r;
}

echo json_encode($rows);

?>






