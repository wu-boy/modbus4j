package cn.wu.demo.modbus4j.util;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 串口工具类
 * @author wusq
 * @date 2021/1/1
 */
public class SerialPortUtils {

    private static Logger log = LoggerFactory.getLogger(SerialPortUtils.class);

    /**
     * 打卡串口
     * @param portName 串口名
     * @param baudRate 波特率
     * @param dataBits 数据位
     * @param stopBits 停止位
     * @param parity 校验位
     * @return 串口对象
     */
    public static SerialPort open(String portName, Integer baudRate, Integer dataBits,
                                  Integer stopBits, Integer parity) {
        SerialPort result = null;
        try {
            // 通过端口名识别端口
            CommPortIdentifier identifier = CommPortIdentifier.getPortIdentifier(portName);
            // 打开端口，并给端口名字和一个timeout（打开操作的超时时间）
            CommPort commPort = identifier.open(portName, 2000);
            // 判断是不是串口
            if (commPort instanceof SerialPort) {
                result = (SerialPort) commPort;
                // 设置一下串口的波特率等参数
                result.setSerialPortParams(baudRate, dataBits, stopBits, parity);
                log.info("打开串口{}成功", portName);
            }else{
                log.info("{}不是串口", portName);
            }
        } catch (Exception e) {
            log.error("打开串口{}错误", portName, e);
        }
        return result;
    }

    /**
     * 关闭串口
     * @param serialPort
     */
    public static void close(SerialPort serialPort) {
        if (serialPort != null) {
            serialPort.close();
            log.warn("串口{}关闭", serialPort.getName());
        }
    }

}
