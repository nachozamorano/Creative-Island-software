<?php

require_once('EcoRutaConnectDB.php');

$correo = $_POST['correo'];

$BuscarPass = "SELECT usuario.password FROM `usuario` WHERE usuario.correo = '$correo'";

$resultado = mysqli_query($conexion,$BuscarPass);
$resultado1 = mysqli_query($conexion,$BuscarPass);

$rowPass = mysqli_fetch_array($resultado1);

$pass = $rowPass['password'];

$rows = array();

if($pass == NULL){

	while($r = mysqli_fetch_assoc($resultado)){
	$rows[] = $r;
	}

	echo json_encode($rows);

}else{
	//ini_set( 'display_errors', 1 );
    //error_reporting( E_ALL );
    $from = "test@hostinger-tutorials.com";
    $to = $correo;
    $subject = "Recuperación de contraseña";
    $message = "Su contraseña es: " . $pass;
    $headers = "From:" . $from;
    mail($to,$subject,$message, $headers);

    while($r = mysqli_fetch_assoc($resultado)){
	$rows[] = $r;
	}

	echo json_encode($rows);
}
   
?>