package com.faisaldev.iso8583.service;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;
import org.springframework.stereotype.Service;

@Service
public class ISO8583Service {

    public byte[] packISOmessage() throws ISOException {
        GenericPackager genericPackager =  new GenericPackager("src/main/resources/packager.xml");


        ISOMsg isoMsg = new ISOMsg();
        isoMsg.setPackager(genericPackager);

        // setting the MTI
        isoMsg.setMTI("0200");

        // setting the DE's (Data Elements)
        isoMsg.set(2, "4567890123456789");  // PAN
        isoMsg.set(3, "000000");            // Processing Code
        isoMsg.set(4, "000000010000");      // Amount

        // Pack the message
        return isoMsg.pack();

    }

    public ISOMsg unpackISOmessage(byte[] isoMessage) throws ISOException {
        GenericPackager packager = new GenericPackager("src/main/resources/packager.xml");

        ISOMsg isoMsg = new ISOMsg();
        isoMsg.setPackager(packager);
        isoMsg.unpack(isoMessage);

        for (int i = 0; i <= isoMsg.getMaxField(); i++) {
            if (isoMsg.hasField(i)) {
                System.out.println("Field (" + i + "): " + isoMsg.getString(i));
            }
        }
        return isoMsg;
    }


}
