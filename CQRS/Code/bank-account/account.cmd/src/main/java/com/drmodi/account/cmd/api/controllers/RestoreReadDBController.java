package com.drmodi.account.cmd.api.controllers;


import com.drmodi.account.cmd.api.commands.RestoreReadDBCommand;
import com.drmodi.account.common.dto.BaseResponse;
import com.drmodi.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/restoreReadDb")
public class RestoreReadDBController {
    private final Logger logger = Logger.getLogger(RestoreReadDBController.class.getName());


    @Autowired
    private CommandDispatcher commandDispatcher;

    @PostMapping
    public ResponseEntity<BaseResponse> restoreReadDb(){
        try{
            commandDispatcher.send(new RestoreReadDBCommand());
            return new ResponseEntity<>(new BaseResponse("Read Database Restore request completed successfully!"), HttpStatus.CREATED); //Http201
        }catch (IllegalStateException ex){
            logger.log(Level.WARNING, MessageFormat.format("Client made a bad request - {0}", ex.toString()));
            return new ResponseEntity<>(new BaseResponse(ex.toString()), HttpStatus.BAD_REQUEST); //Http400 - ClientError
        }catch (Exception e){
            var safeErrorMessage = "Error while processing request to restore read database!";
            logger.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR); //Http500 - Some internal issues
        }
    }
}
