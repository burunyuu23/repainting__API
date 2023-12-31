package com.example.therepaintinggameweb;

import com.example.therepaintinggameweb.dtos.responses.ErrorResponseDTO;
import com.example.therepaintinggameweb.exceptions.AppException;
import com.example.therepaintinggameweb.logic.GameWrapperFactory;
import com.example.therepaintinggameweb.utils.GameSessionManager;
import com.nimbusds.jose.shaded.gson.Gson;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
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

    @Bean
    public Gson gson(){
        return new Gson();
    }
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        Gson gson = gson();

        TypeMap<AppException, ErrorResponseDTO> typeMapException = modelMapper.createTypeMap(AppException.class, ErrorResponseDTO.class);
        typeMapException.addMappings(mapper -> {
            mapper.map(AppException::getMessage, ErrorResponseDTO::setMessage);
            mapper.map(AppException::getStatus, ErrorResponseDTO::setStatus);
        });

        return modelMapper;
    }

    @Bean
    public GameWrapperFactory gameWrapperFactory(PythonInterpreter pythonInterpreter) {
        return new GameWrapperFactory(pythonInterpreter);
    }

    @Bean
    public GameSessionManager gameSessionManager() {
        return new GameSessionManager();
    }

}
