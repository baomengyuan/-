package com.swjtu.Util;

/**
 * @author baomengyuan
 * @create 2021-12-14 17:27
 */
import com.swjtu.Service.MyListener;
import gnu.io.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.TooManyListenersException;

public class SerialPortUtil {
    private static final Logger logger=LoggerFactory.getLogger(SerialPortUtil.class);
    private static SerialPortUtil serialPortUtil=null;
    static{
        if(serialPortUtil==null)
            serialPortUtil=new SerialPortUtil();
    }
    private SerialPortUtil(){}

    public static SerialPortUtil getSerialPortUtil()
    {
        if(serialPortUtil==null)
            serialPortUtil=new SerialPortUtil();
        return serialPortUtil;
    }

    //find all can use ports
    public ArrayList<String> findPort() {
        Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();
        ArrayList<String> portNameList = new ArrayList<>();
        while (portList.hasMoreElements()) {
            String portName = portList.nextElement().getName();
            portNameList.add(portName);
        }
        return portNameList;
    }

    //open port return null ---->fail
    public SerialPort openPort(String portName,int baudrate,int databits,int parity,int stopbits)
    {
        try{
            CommPortIdentifier portIdentifier=CommPortIdentifier.getPortIdentifier(portName);
            CommPort commPort=portIdentifier.open(portName,2000);
            if(commPort instanceof SerialPort){
                SerialPort serialPort=(SerialPort) commPort;
                try{
                    serialPort.setSerialPortParams(baudrate,databits,stopbits,parity);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
                System.out.println("open "+portName+" successfully"+new Date());
                return serialPort;
            }else {
                logger.error("******not port******");
            }
        }catch (NoSuchPortException e)
        {
            logger.error("NoSuch Port");
            e.printStackTrace();
        }
        catch (PortInUseException e)
        {
            logger.error("Port In Use");
            e.printStackTrace();
        }
        return null;
    }

    //close port
    public void closePort(SerialPort serialPortl)
    {
        if(serialPortl!=null)
        {
            serialPortl.close();
        }
    }

    //sent bytes to port
    public void sendToPort(SerialPort serialPort,byte[] bytes)
    {
        OutputStream out= null;
        try{
            out=serialPort.getOutputStream();
            out.write(bytes);
            out.flush();
        }catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            try{
                if(out!=null)
                {
                    out.close();
                    System.out.println("sent bytes successfully "+ new Date());
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    //read bytes from port
    public byte[] readFromPort(SerialPort serialPort)
    {
        InputStream in=null;
        byte[] bytes=null;
        try {
            in=serialPort.getInputStream();
            int buffLength=in.available();
            while(buffLength!=0)
            {
                bytes=new byte[buffLength];
                in.read(bytes);
                buffLength=in.available();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }finally
        {
            try{
                if(in!=null) {
                    in.close();
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return bytes;
    }
    //add SerialListener to port
    public void addListener(SerialPort port,MyListener listener)
    {
        try{
            port.addEventListener(listener);
            port.notifyOnBreakInterrupt(true);
            port.notifyOnDataAvailable(true);
        }catch (TooManyListenersException e)
        {
            logger.error("too many listeners");
            e.printStackTrace();
        }
        listener.start();
    }
    //remove listener
    public void removeListener(SerialPort port) {
        port.removeEventListener();
    }
}
