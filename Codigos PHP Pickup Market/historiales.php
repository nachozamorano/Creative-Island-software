<?php
//Codigo que se encarga de mostrar en pantalla los Historiales de objetos DONADOS, RESERVADOS y ADQUIRIDOS ----------------------

require_once('EcoRutaConnectDB.php');

$id_usuario = $_POST["id_usuario"];
$n_historial = $_POST["n_historial"];

if($n_historial == 'DONADOS'){

$Buscar = "SELECT objeto.id_objeto, objeto.nombre_objeto, objeto.descripcion, objeto.imagen, subcategoria.nombre 'subcategoria', categoria.nombre_categoria 'categoria', objeto.fecha_publicacion, objeto.fecha_expiracion, estado_objeto.nombre_estado_objeto, objeto.cantidad, unidad_medida.unidad_medida, objeto.Latitud, objeto.Longitud, objeto.desde, objeto.hasta, objeto.direccion, transaccion.nombre_interesado , Estado.nombre_estado FROM objeto INNER JOIN transaccion ON transaccion.id_objeto = objeto.id_objeto INNER JOIN usuario ON usuario.id_usuario = transaccion.id_dueno INNER JOIN estado_objeto ON estado_objeto.id_estado_objeto = objeto.id_estado_objeto INNER JOIN unidad_medida ON unidad_medida.id_unidad = objeto.id_unidad INNER JOIN Estado ON Estado.id_estado = transaccion.id_estado INNER JOIN subcategoria ON subcategoria.id_subcategoria = objeto.subcategoria INNER JOIN categoria ON categoria.id_categoria = subcategoria.id_categoria WHERE transaccion.id_dueno = '$id_usuario'";

$resultado = mysqli_query($conexion,$Buscar);

$rows = array();

while($r = mysqli_fetch_assoc($resultado)){
  $rows[] = $r;
}


echo json_encode($rows) . "<br>";

}else if($n_historial == 'RESERVADOS'){

$Buscar = "SELECT objeto.id_objeto,usuario.nombre 'nombre_propietario', objeto.nombre_objeto, objeto.descripcion, objeto.imagen, subcategoria.nombre 'subcategoria', categoria.nombre_categoria 'categoria', objeto.fecha_publicacion, estado_objeto.nombre_estado_objeto, objeto.cantidad, unidad_medida.unidad_medida, objeto.Latitud, objeto.Longitud, objeto.desde, objeto.hasta, objeto.direccion, Estado.nombre_estado FROM objeto INNER JOIN transaccion ON transaccion.id_objeto = objeto.id_objeto INNER JOIN usuario ON usuario.id_usuario = transaccion.id_dueno INNER JOIN estado_objeto ON estado_objeto.id_estado_objeto = objeto.id_estado_objeto INNER JOIN unidad_medida ON unidad_medida.id_unidad = objeto.id_unidad INNER JOIN Estado ON Estado.id_estado = transaccion.id_estado INNER JOIN subcategoria ON subcategoria.id_subcategoria = objeto.subcategoria INNER JOIN categoria ON categoria.id_categoria = subcategoria.id_categoria WHERE transaccion.id_interesado = '$id_usuario'";

$resultado = mysqli_query($conexion,$Buscar);

$rows = array();

while($r = mysqli_fetch_assoc($resultado)){
  $rows[] = $r;
}


echo json_encode($rows) . "<br>";

}else if($n_historial == 'ADQUIRIDOS'){

$Buscar = "SELECT objeto.id_objeto,usuario.nombre 'nombre_propietario', objeto.nombre_objeto, objeto.descripcion, objeto.imagen, subcategoria.nombre 'subcategoria', categoria.nombre_categoria 'categoria', objeto.fecha_publicacion, estado_objeto.nombre_estado_objeto, objeto.cantidad, unidad_medida.unidad_medida, objeto.Latitud, objeto.Longitud, objeto.desde, objeto.hasta, objeto.direccion, Estado.nombre_estado FROM objeto INNER JOIN transaccion ON transaccion.id_objeto = objeto.id_objeto INNER JOIN usuario ON usuario.id_usuario = transaccion.id_dueno INNER JOIN estado_objeto ON estado_objeto.id_estado_objeto = objeto.id_estado_objeto INNER JOIN unidad_medida ON unidad_medida.id_unidad = objeto.id_unidad INNER JOIN Estado ON Estado.id_estado = transaccion.id_estado INNER JOIN subcategoria ON subcategoria.id_subcategoria = objeto.subcategoria INNER JOIN categoria ON categoria.id_categoria = subcategoria.id_categoria WHERE transaccion.id_estado = '3' AND transaccion.id_interesado = '$id_usuario'";

$resultado = mysqli_query($conexion,$Buscar);

$rows = array();

while($r = mysqli_fetch_assoc($resultado)){
  $rows[] = $r;
}


echo json_encode($rows) . "<br>";

}else{
	echo "ERROR";
}

?>
