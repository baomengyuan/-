package com.swjtu.Service;
import com.swjtu.Util.SerialPortUtil;
import com.swjtu.Util.TransformUtil;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import lombok.SneakyThrows;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author baomengyuan
 * @create 2021-12-14 18:17
 */
public class MyListener extends Thread implements SerialPortEventListener {
    private BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>();
    public static String address;
    public static String title;

    MyListener(String address,String title){
        this.address=address;
        this.title=title;
    }
    @SneakyThrows
    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        if(serialPortEvent.getEventType()==SerialPortEvent.DATA_AVAILABLE)
        {
            SerialPortUtil serialPortUtil=SerialPortUtil.getSerialPortUtil();
            byte[] bytes = serialPortUtil.readFromPort(InitService.serialPort2);
            String byteStr = new String(bytes, 0, bytes.length).trim();
            System.out.println(new Date() + "【串口读到的字节数组】：-----" + byteStr);
            msgQueue.add(new Date() + "邮件真实收到的数据为：-----" + byteStr);
//            System.out.println("===========start===========");
//            System.out.println(new Date() + "【读到的字节数组】：-----" + byteStr);
//            String needData = TransformUtil.printHexString(bytes);
//            System.out.println(new Date() + "【字节数组转字符串】：-----" + needData);
//            System.out.println(new Date() + "【16进制字符串转字符串】：" + TransformUtil.hexStringToString(needData));
//            System.out.println("===========end===========");
        }
    }
    @Override
    public void run() {
        try {
            System.out.println("--------------任务处理线程运行了--------------");
            while (true) {
                // 如果堵塞队列中存在数据就将其输出
                if (msgQueue.size() > 0) {
                    EmailService.sendMail(title,msgQueue.take(),address);
                    System.out.println("--------------任务处理线程进入阻塞状态--------------");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
