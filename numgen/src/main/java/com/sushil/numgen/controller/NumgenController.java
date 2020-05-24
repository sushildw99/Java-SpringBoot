package com.sushil.numgen.controller;

import com.sushil.numgen.model.*;
import com.sushil.numgen.service.NumgenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Optional;
import java.util.UUID;

import static com.sushil.numgen.exception.ExceptionCode.BAD_REQUEST;
import static com.sushil.numgen.exception.ExceptionCode.PROCESSING_EXCEPTION;
import static com.sushil.numgen.util.Constants.*;


@RestController
@RequestMapping("/api")
public class NumgenController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NumgenController.class);

    @Autowired
    private NumgenService numgenService;


    /**
     *
     * This method should be called with below URL and payload.
     *
     * URL:
     * localhost:8080/api/generate/
     *
     * Payload:
     * {
     * 	"Goal":"10",
     * 	 "Step": "2"
     * }
     *
     * @param request
     * @return
     */
    @PostMapping(value = "/generate", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ResponseEntity<? super BaseResponse> generarteNumber(@Valid @RequestBody NumGenRequest request) {
        LOGGER.debug("Received request: {}", request.toString());
        try {
            final UUID uuid = numgenService.generateNumbers(request);
            Task task = new Task();
            task.setTask(uuid.toString());
            return new ResponseEntity<>(task, HttpStatus.ACCEPTED);
        } catch (UncheckedIOException e) {
            LOGGER.error("There is an error while processing this request: {}", e);
            e.printStackTrace();
            return new ResponseEntity<>(new ErrorResponse(PROCESSING_EXCEPTION), HttpStatus.NOT_ACCEPTABLE);

        }
    }


    /**
     * This method should be called with below URL.
     *
     * URL:
     * localhost:8080/api/tasks/911a47a6-722c-4666-9b94-90c2c6a39169/status
     *
     * @param uuid
     * @return
     */
    @GetMapping(value = "/tasks/{uuid}/status", produces = APPLICATION_JSON)
    public ResponseEntity<? super BaseResponse> getStatus(@PathVariable("uuid") @NotBlank @Valid
                                                              @Pattern(regexp = REG_EX_UUID, message = UUID_VAL_MSG) String uuid) {
        LOGGER.debug("Received uuid: {}", uuid);
        ResponseResult status = numgenService.getStatus(uuid);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }


    /**
     *
     * This method should be called with below URL.
     * URL:
     * localhost:8080/api/tasks/911a47a6-722c-4666-9b94-90c2c6a39169?action=get_numlist
     *
     * @param uuid
     * @param action
     * @return
     */
    @GetMapping(value = "/tasks/{uuid}", produces = APPLICATION_JSON)
    public ResponseEntity<? super BaseResponse> getNumList(@NotBlank @PathVariable("uuid") String uuid,
                                                           @RequestParam(ACTION) Optional<String> action) {

        LOGGER.debug("Received action: {}", action);
        if (action.isPresent() && GET_NUMLIST.equals(action.get())) {
            try {
                ResponseResult result = numgenService.getNumList(uuid);
                return new ResponseEntity<>(result, HttpStatus.OK);
            } catch (IOException e) {
                LOGGER.error("There is an error while processing this request: {}", e);
                e.printStackTrace();
                return new ResponseEntity<>(new ErrorResponse(PROCESSING_EXCEPTION), HttpStatus.NO_CONTENT);
            }
        }
        LOGGER.debug("action: {} is not valid, Please give action value: {}", action, GET_NUMLIST);
        return new ResponseEntity<>(new ErrorResponse(BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }
}
