package com.faisaldev.iso8583.controller;

import com.faisaldev.iso8583.service.ISO8583Service;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ISOController {

    private final ISO8583Service iso8583Service;

    @Autowired
    public ISOController(ISO8583Service iso8583Service) {
        this.iso8583Service = iso8583Service;
    }

    @GetMapping("/api/packISO")
    public ResponseEntity<String> packISOMessage() throws ISOException {
        byte[] packedMessage = iso8583Service.packISOmessage();
        return ResponseEntity.ok(new String(packedMessage));
    }

    @GetMapping("/api/unpackISO")
    public ResponseEntity<String> unpackISOMessage() throws ISOException {
        byte[] message = iso8583Service.packISOmessage();
        ISOMsg unpackedMessage = iso8583Service.unpackISOmessage(message);

        String pan = unpackedMessage.getString(2);

        String logMessage = "Unpacked ISO Message: MTI = " + unpackedMessage.getMTI() + "\n";
        logMessage += "PAN: " + pan + "\n";
        // Log all fields if needed
        for (int i = 0; i < unpackedMessage.getMaxField(); i++) {
            if (unpackedMessage.hasField(i)) {
                logMessage += "Field " + i + ": " + unpackedMessage.getString(i) + "\n";
            }
        }

        System.out.println(logMessage);
        return ResponseEntity.ok("Unpacked ISO Message with MTI: " + unpackedMessage.getMTI());
    }



}