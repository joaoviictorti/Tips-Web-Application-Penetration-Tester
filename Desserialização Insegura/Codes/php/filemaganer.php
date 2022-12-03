<?php

class Filemanager{
    public $path = "/tmp/";

    public function __construct(){
        file_put_contents($this->path ."Log.txt","Sessão iniciada com sucesso!");    
    }

    public function putLog($content){
        file_put_contents($this->path."Log.txt",$content);
    }

    public function readlog(){
        return file_get_contents($this->path."Log.txt");
    }
    public function __destruct(){
        shell_exec("rm -rf ".$this->path."*"); // -> Vetor da vulnerabilidade
    }
}
?>