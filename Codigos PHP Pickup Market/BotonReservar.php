<?php
require_once('EcoRutaConnectDB.php');

$id_objeto = $_POST["id_objeto"];
$fecha_retiro = $_POST["fecha_retiro"];
$id_interesado = $_POST["id_interesado"];


$datosInteresado = "SELECT `nombre`, `correo`, `telefono_movil`, `telefono_fijo` FROM `usuario` WHERE id_usuario = '$id_interesado'";

$resultado1 = mysqli_query($conexion,$datosInteresado);

$datos = mysqli_fetch_array($resultado1);

$nombre = $datos["nombre"];

$reservarObjeto = "UPDATE `transaccion` SET `id_estado`= '2',`fecha_retiro`='$fecha_retiro',`id_interesado`='$id_interesado', `nombre_interesado` = '$nombre'  WHERE id_objeto = '$id_objeto'";

$resultado = mysqli_query($conexion,$reservarObjeto);

$objeto = "SELECT `id_propietario`,`nombre_objeto` FROM `objeto` WHERE id_objeto = '$id_objeto'";

$resultadoObj = mysqli_query($conexion,$objeto);

$datoObj = mysqli_fetch_array($resultadoObj);

$propietario = $datoObj["id_propietario"];

$correoPropietario = "SELECT `correo`, `nombre`, `telefono_movil`, `telefono_fijo` FROM `usuario` WHERE id_usuario =  '$propietario'";

$resultadoCorreo = mysqli_query($conexion,$correoPropietario);

$correoProp = mysqli_fetch_array($resultadoCorreo);

$correoInteresado = "SELECT `correo` FROM `usuario` WHERE id_usuario =  '$id_interesado'";

$resultadoCorreoInt = mysqli_query($conexion,$correoInteresado);

$correoInt = mysqli_fetch_array($resultadoCorreoInt);


//-----------------------------ENVIO MENSAJE AL PROPIETARIO---------------------------------------------------------------------

    $from = "test@hostinger-tutorials.com";
    $to = $correoProp['correo'];
    $subject = "Alguien a reservado uno sus objetos";
    $message = 'Saludos el usuario ' . $datos["nombre"] . ' ha reservado el objeto ' . $datoObj["nombre_objeto"] . ' puede ponerse en contacto con el usuario escribiendole al siguiente correo: ' . $datos["correo"] . " o llamando a los siguientes numeros de telefono: Celular: " . $datos["telefono_movil"] . " Telefono fijo: " . $datos["telefono_fijo"];
    $headers = "From:" . $from;
    mail($to,$subject,$message, $headers);
    echo "The email message was sent.";


//-----------------------------ENVIO MENAJE A INTERESADO----------------------------------------------------------------------

    $from = "test@hostinger-tutorials.com";
    $to = $correoInt['correo'];
    $subject = "Su reserva se ha realizado con exito";
    $message = 'Saludos ha reservado el objeto ' . $datoObj["nombre_objeto"] . ' del propietario ' . $correoProp["nombre"]  . ' puede ponerse en contacto con el usuario escribiendole al siguiente correo: ' . $correoProp["correo"] . " o llamando a los siguientes numeros de telefono: Celular: " . $correoProp["telefono_movil"] . " Telefono fijo: " . $correoProp["telefono_fijo"];
    $headers = "From:" . $from;
    mail($to,$subject,$message, $headers);
    echo "The email message was sent.";
//----------------------------------------------------------------------------------------------------------------------------    

mysqli_close($conexion);



?>


