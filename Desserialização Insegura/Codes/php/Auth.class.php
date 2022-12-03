<?php

class Auth{

    public $user = "guest";
    public $role = "guest";
    public $session_status = false;

    public function isAuth(){
        return $this->session_status;
    }

    public function isAdmin(){

        if($this->role == "admin"){
            return true;
        }else{
            return false;
        }
    }
    public function doAuth($user, $password){

        if($user == "admin" and $password == "admin@123"){
            return true;
        }else{
            return false;
        }   
    }
}
?>