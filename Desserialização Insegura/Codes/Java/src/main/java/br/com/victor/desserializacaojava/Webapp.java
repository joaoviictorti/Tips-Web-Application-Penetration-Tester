package br.com.victor.desserializacaojava;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.*;
import java.util.Base64;

@RestController
public class Webapp {

    @RequestMapping("/api/test")
    public String GetRoute(@RequestParam(defaultValue = "default") String nome) throws java.io.IOException, ClassNotFoundException {

        Employee employee = new Employee();
        employee.nome = nome;
        employee.email = nome + "@teste.com";
        employee.Salary = 1000.00;
        employee.RoleName = "Pentester";

        ByteArrayOutputStream bais = new ByteArrayOutputStream();
        ObjectOutputStream oos =  new ObjectOutputStream(bais);

        oos.writeObject(employee);
        oos.flush();
        oos.close();

        String base64 = Base64.getEncoder().encodeToString(bais.toByteArray());
        return base64;

        /*
        Employee employee = new Employee();
        employee.nome = "João Victor da Silva Costa";
        employee.email = "teste@teste.com";
        employee.password = "Password123";
        employee.RoleName = "Pentester";
        employee.Salary = 1000.00;

        // File handler

        FileOutputStream file = new FileOutputStream("objetoserializado.txt");

        // Object handler

        ObjectOutputStream oos = new ObjectOutputStream(file);
        oos.writeObject(employee);
        oos.flush();
        oos.close();

        FileInputStream fileread = new FileInputStream("objetoserializado.txt");
        ObjectInputStream ois = new ObjectInputStream(fileread);

        Employee newemployye = (Employee) ois.readObject();

        return newemployye.getName();
        */

    }

    @RequestMapping("/deserialization")
    public String Desserialization(@RequestParam(defaultValue = "") String objeto) throws IOException, ClassNotFoundException {

        if (!objeto.equals("")) {

            System.out.println(objeto);
            // Base64 decode
            byte[] input = Base64.getDecoder().decode(objeto);

            //Byte Array Input Handler
            ByteArrayInputStream bais = new ByteArrayInputStream(input);

            ObjectInputStream ois = new ObjectInputStream(bais);
            Employee newemployee = (Employee) ois.readObject();

            return newemployee.nome;
        }
        else{
            return "Passe o parametro do objeto";
        }
    }
}
