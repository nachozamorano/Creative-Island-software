<?php
//Codigo utilizado para comprobar el Login de usuario------------------
if ($_SERVER['REQUEST_METHOD']=='POST') {

    $email = $_POST['correo'];
    $password = $_POST['password'];
    

    require_once 'EcoRutaConnectDB.php';

    $sql = "SELECT * FROM usuario WHERE usuario.correo = '$email'";

    $response = mysqli_query($conexion, $sql);
    $result = array();
    $result['login'] = array();
    
    if ( mysqli_num_rows($response) === 1 ) {
        
        $row = mysqli_fetch_assoc($response);
       
        if ($password === $row['password']) {    
            $index['id_usuario'] = $row['id_usuario'];
            

            array_push($result['login'], $index);

            $result['success'] = "1";
            $result['message'] = "success";
            echo json_encode($result);

            mysqli_close($conexion);

        } else if ($password != $row['password']){

        	$result['success'] = "2";
            $result['message'] = "Usuario incorrecto";
            echo json_encode($result);

            mysqli_close($conexion);


        } else {

            $result['success'] = "0";
            $result['message'] = "error";
            echo json_encode($result);

            mysqli_close($conexion);

        }

    }

}

?>