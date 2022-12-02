<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <center>
    <form action="#" method="get">

        <input type="text" name="page" placeholder="Qual diretório que ler?">
        <br>
        <input type="button" value="Enviar">
    </form>
    </center>
</body>
</html>


<?php

if($_GET['page'] === "teste.php"){
    
    include("teste.php"); // Corrigido

}else{
    echo "<center>";
    echo "</br>";
    echo "Não foi possível carregar o arquivo, por conta de não ser especificado direito!";
    echo "</center>";
}

?>