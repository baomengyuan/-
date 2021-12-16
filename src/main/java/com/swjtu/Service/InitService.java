package com.swjtu.Service;
import com.swjtu.Util.SerialPortUtil;
import gnu.io.SerialPort;


import java.util.ArrayList;
import java.util.Date;

/**
 * @author baomengyuan
 * @create 2021-12-14 18:07
 */
public class InitService  {
    private String PortName1="COM1";
    private String PortName2="COM2";
    public static SerialPort serialPort1=null;
    public static SerialPort serialPort2=null;
    public void startMethod(String message,String address,String title) {
        SerialPortUtil serialPortUtil = SerialPortUtil.getSerialPortUtil();
        ArrayList<String> port = serialPortUtil.findPort();
        System.out.println("find all ports：" + port);
        System.out.println("plan to open port: "+PortName1);
        if(serialPort1==null&&serialPort2==null){
            System.out.println("plan to open port: "+PortName1);
            //open PortName1---->port1
            InitService.serialPort1 = serialPortUtil.openPort(PortName1, 9600, SerialPort.DATABITS_8, SerialPort.PARITY_NONE,SerialPort.STOPBITS_1);
            System.out.println("plan to open port: "+PortName2);
            //open PortName2---->port2
            InitService.serialPort2 = serialPortUtil.openPort(PortName2, 9600, SerialPort.DATABITS_8, SerialPort.PARITY_NONE,SerialPort.STOPBITS_1);
            //add Listener to port2
            MyListener listener=new MyListener(address,title);
            serialPortUtil.addListener(InitService.serialPort2, listener);
        }
        else{
            MyListener.address=address;
            MyListener.title=title;
        }
        serialPortUtil.sendToPort(InitService.serialPort1, message.toString().getBytes());
    }
}
