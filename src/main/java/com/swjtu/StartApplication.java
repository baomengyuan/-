package com.swjtu;
import com.swjtu.Service.InitService;
import com.swjtu.Service.MyListener;
import com.swjtu.Util.SerialPortUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.annotation.PreDestroy;
/**
 * @author baomengyuan
 * @create 2021-12-14 17:22
 */
@SpringBootApplication
public class StartApplication {
    public static void main(String[] args){
        SpringApplication.run(StartApplication.class,args);
    }
    @PreDestroy
    public void ClosePort(){
        SerialPortUtil serialPortUtil = SerialPortUtil.getSerialPortUtil();
        serialPortUtil.removeListener(InitService.serialPort2);
        serialPortUtil.closePort(InitService.serialPort1);
        serialPortUtil.closePort(InitService.serialPort2);
    }
}
