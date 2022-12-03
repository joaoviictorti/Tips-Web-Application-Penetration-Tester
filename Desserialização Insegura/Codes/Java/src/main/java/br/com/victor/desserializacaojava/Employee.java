package br.com.victor.desserializacaojava;

import java.io.Serializable;

public class Employee extends Role implements Serializable {

    public String nome;
    public String email;
    public String password;

    Employee(){
        System.out.println("Employee Created");
    }

    public String getName(){
        return this.nome;
    }
    public String getEmail(){
        return this.email;
    }
    public double getSalary(){
        return this.Salary;
    }
}
