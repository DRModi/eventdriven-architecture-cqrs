package com.drmodi.account.cmd.api.controllers;

import com.drmodi.account.cmd.api.commands.DepositFundsCommand;
import com.drmodi.account.cmd.api.commands.WithdrawFundsCommand;
import com.drmodi.account.common.dto.BaseResponse;
import com.drmodi.cqrs.core.exceptions.AggregateNotFoundException;
import com.drmodi.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/withdrawFunds")
public class WithdrawFundsController {
    private final Logger logger = Logger.getLogger(DepositFundsController.class.getName());

    @Autowired
    private CommandDispatcher commandDispatcher;

    @PutMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> withdrawFunds(@PathVariable(value = "id") String id,
                                                     @RequestBody WithdrawFundsCommand command){

        try {
            command.setId(id);
            commandDispatcher.send(command);
            return new ResponseEntity<>(new BaseResponse("Withdraw funds request completed successfully!"), HttpStatus.OK); //Http200-Updates success
        }catch (IllegalStateException | AggregateNotFoundException ex){
            logger.log(Level.WARNING, MessageFormat.format("Client made a bad request - {0}", ex.toString()));
            return new ResponseEntity<>(new BaseResponse(ex.toString()), HttpStatus.BAD_REQUEST); //Http400 - ClientError
        }catch (Exception e){
            var safeErrorMessage = MessageFormat.format("Error while processing request to withdraw funds from bank account with id - {0}", id);
            logger.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR); //Http500 - Some internal issues
        }
    }
}
