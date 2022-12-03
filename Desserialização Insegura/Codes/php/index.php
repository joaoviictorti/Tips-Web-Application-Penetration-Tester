<?php
require_once "app/Auth.class.php";
require_once "app/filemaganer.php";

if($_COOKIE['session'] and !empty($_COOKIE['session'])){
    $session = unserialize($_COOKIE['session']);// -> Onde a vulnerabilidade acontece
    if($session->isAuth()){
        echo "Está autenticado";
        echo "<br/>";
        $file = new Filemanager();
        $file->putLog("Você está logado");
        $log = $file->readlog();
        echo $log;
    }else{
        $session = new Auth();
        setcookie("session",serialize($session),time()+3600);
        echo "Você não está autenticado";
    }
}else{
    $session = new Auth();
    setcookie("session",serialize($session),time()+3600);
    return "Você não está autenticado";
}



?>