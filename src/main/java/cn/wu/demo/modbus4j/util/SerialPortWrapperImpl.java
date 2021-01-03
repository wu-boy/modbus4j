package cn.wu.demo.modbus4j.util;

import com.serotonin.modbus4j.serial.SerialPortWrapper;
import gnu.io.SerialPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 自定义串口封装
 * @author wusq
 * @date 2021/1/3
 */
public class SerialPortWrapperImpl implements SerialPortWrapper {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 串口对象
     */
    private SerialPort serialPort;

    /**
     * 串口
     */
    private String port;

    /**
     * 波特率
     */
    private Integer baudRate;

    /**
     * 数据位的位数，RTU是8位，ASCII是7位
     */
    private Integer dataBits;

    /**
     * 停止位的位数，如果无奇偶校验为2，有奇偶校验为1
     */
    private Integer stopBits;

    /**
     * 奇偶校验位，无校验是0，奇校验是1，偶校验是2
     */
    private Integer parity;

    /**
     * 硬件之间输入流应答控制
     */
    private Integer flowControlIn;

    /**
     * 硬件之间输出流应答控制
     */
    private Integer flowControlOut;

    public SerialPortWrapperImpl() {
        super();
    }

    public SerialPortWrapperImpl(String port, int baudRate, int dataBits, int stopBits, int parity,
            int flowControlIn, int flowControlOut) {
        this.port = port;
        this.baudRate = baudRate;
        this.dataBits = dataBits;
        this.stopBits = stopBits;
        this.parity = parity;
        this.flowControlIn = flowControlIn;
        this.flowControlOut = flowControlOut;
    }

    @Override
    public void close() throws Exception {
        SerialPortUtils.close(serialPort);
    }

    @Override
    public void open() throws Exception {
        serialPort = SerialPortUtils.open(port, baudRate, dataBits, stopBits, parity);
    }

    @Override
    public InputStream getInputStream() {
        InputStream in = null;
        try {
            in = serialPort.getInputStream();
        } catch (IOException e) {
            log.error("获取串口输入流错误", e);
        }

        return in;
    }

    @Override
    public OutputStream getOutputStream() {
        OutputStream out = null;
        try {
            out = serialPort.getOutputStream();
        } catch (IOException e) {
            log.error("获取串口输出流错误", e);
        }

        return out;
    }

    @Override
    public int getBaudRate() {
        return this.baudRate;
    }

    @Override
    public int getDataBits() {
        return this.dataBits;
    }

    @Override
    public int getStopBits() {
        return this.stopBits;
    }

    @Override
    public int getParity() {
        return this.parity;
    }

    @Override
    public int getFlowControlIn() {
        return this.flowControlIn;
    }

    @Override
    public int getFlowControlOut() {
        return this.flowControlOut;
    }

    public SerialPort getSerialPort() {
        return serialPort;
    }

    public void setSerialPort(SerialPort serialPort) {
        this.serialPort = serialPort;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setBaudRate(Integer baudRate) {
        this.baudRate = baudRate;
    }

    public void setDataBits(Integer dataBits) {
        this.dataBits = dataBits;
    }

    public void setStopBits(Integer stopBits) {
        this.stopBits = stopBits;
    }

    public void setParity(Integer parity) {
        this.parity = parity;
    }

    public void setFlowControlIn(Integer flowControlIn) {
        this.flowControlIn = flowControlIn;
    }

    public void setFlowControlOut(Integer flowControlOut) {
        this.flowControlOut = flowControlOut;
    }
}
