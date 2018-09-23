<?php
//codigo que se encarga de la busqueda de todos los objetos

require_once('EcoRutaConnectDB.php');
header("Content-Type: text/html;charset=utf-8");

$Buscar = "SELECT objeto.id_objeto, objeto.id_propietario, objeto.nombre_objeto, objeto.descripcion, objeto.imagen, subcategoria.nombre 'subcategoria', categoria.nombre_categoria 'categoria', objeto.fecha_publicacion, objeto.fecha_expiracion, estado_objeto.nombre_estado_objeto, objeto.cantidad, unidad_medida.unidad_medida, objeto.Latitud, objeto.Longitud, objeto.desde, objeto.hasta, objeto.direccion FROM objeto INNER JOIN estado_objeto ON estado_objeto.id_estado_objeto = objeto.id_estado_objeto INNER JOIN unidad_medida ON unidad_medida.id_unidad = objeto.id_unidad INNER JOIN subcategoria ON subcategoria.id_subcategoria = objeto.subcategoria INNER JOIN categoria ON categoria.id_categoria = subcategoria.id_categoria ORDER BY estado_objeto.nombre_estado_objeto";


$resultado = mysqli_query($conexion,$Buscar);

$rows = array();

while($r = mysqli_fetch_assoc($resultado)){
  $rows[] = $r;
  
 
}

echo json_encode($rows);

?>