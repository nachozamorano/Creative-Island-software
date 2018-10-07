<?php
require_once('EcoRutaConnectDB.php');

$id_objeto = $_POST["id_objeto"];
$id_propietario = $_POST["id_propietario"];


$Buscar = "SELECT * FROM objeto WHERE id_objeto = '$id_objeto' AND id_propietario = '$id_propietario'";

$resultado = mysqli_query($conexion,$Buscar);
$totalFilas  = mysqli_num_rows($resultado);

if($totalFilas === 0){
	$valor[] = array('num' => "NO");
	echo json_encode($valor);

}else{
	$valor[] = array('num' => "SI");
	echo json_encode($valor);	
}

?>

<?php
require_once('EcoRutaConnectDB.php');

$id_objeto = $_POST["id_objeto"];
$id_propietario = $_POST["id_propietario"];


$Buscar = "SELECT * FROM objeto WHERE id_objeto = '$id_objeto' AND id_propietario = '$id_propietario'";

$resultado = mysqli_query($conexion,$Buscar);
$totalFilas  = mysqli_num_rows($resultado);

if($totalFilas === 0){
	$valor[] = "NO";
	echo json_encode($valor);

}else{
	$valor[] = "SI";
	echo json_encode($valor);	
}

?>
