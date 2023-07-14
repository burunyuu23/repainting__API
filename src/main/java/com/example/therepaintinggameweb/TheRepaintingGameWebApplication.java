package com.example.therepaintinggameweb;

import org.python.util.PythonInterpreter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Properties;

@SpringBootApplication
public class TheRepaintingGameWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(TheRepaintingGameWebApplication.class, args);
    }

    @Bean
    public PythonInterpreter pythonInterpreter(){
        PythonInterpreter.initialize(System.getProperties(), new Properties(), new String[]{"-S"});
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.exec("import sys");
        interpreter.exec("print(sys.version_info)");
        interpreter.exec("print(sys.path)");
        return interpreter;
    }

}
