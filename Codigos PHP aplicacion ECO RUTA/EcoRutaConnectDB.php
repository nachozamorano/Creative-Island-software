<?php
//----------------Codigo que se encarga de realizar la conexion a la base de datos------------------------
 define('HOST','mysql.webcindario.com');
 define('USER','ecoruta');
 define('PASS','mininocat261194');
 define('DB','ecoruta');
 
 $conexion = mysqli_connect(HOST,USER,PASS,DB) or die('unable to connect to db');
 mysqli_set_charset($conexion,"utf8");
 
?>