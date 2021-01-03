package cn.wu.demo.modbus4j;

import cn.hutool.core.util.RandomUtil;
import cn.wu.demo.modbus4j.util.MyProcessImageListener;
import cn.wu.demo.modbus4j.util.SerialPortWrapperImpl;
import com.serotonin.modbus4j.BasicProcessImage;
import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusSlaveSet;
import com.serotonin.modbus4j.ProcessImage;
import com.serotonin.modbus4j.exception.ModbusInitException;
import gnu.io.SerialPort;

/**
 * 模拟从站设备
 * @author wusq
 * @date 2021/1/3
 */
public class RtuSlaveTest {

    public static void main(String[] args) {
        createRtuSlave();
    }

    public static void createRtuSlave(){
        // 设置串口参数，串口是COM2，波特率是9600
        SerialPortWrapperImpl wrapper = new SerialPortWrapperImpl("COM2", 9600,
                SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE, 0, 0);

        // Modbus工厂，可以创建RTU、TCP等不同类型的Master和Slave
        ModbusFactory modbusFactory = new ModbusFactory();

        final ModbusSlaveSet slave = modbusFactory.createRtuSlave(wrapper);

        // 这玩意网上有人叫做过程影像区，其实就是寄存器
        // 寄存器里可以设置线圈状态、离散输入状态、保持寄存器和输入寄存器
        // 这里设置了从站设备ID是1
        BasicProcessImage processImage = new BasicProcessImage(1);
        processImage.setInvalidAddressValue(Short.MIN_VALUE);
        slave.addProcessImage(processImage);

        // 添加监听器，监听slave线圈状态和保持寄存器的写入
        processImage.addListener(new MyProcessImageListener());

        setCoil(processImage);
        setInput(processImage);
        setHoldingRegister(processImage);
        setInputRegister(processImage);

        // 开启线程启动从站设备
        new Thread(() -> {
            try {
                slave.start();
            }
            catch (ModbusInitException e) {
                e.printStackTrace();
            }
        }).start();

        /*new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // 间隔1秒修改从站设备1的保持寄存器数据
                updateHoldingRegister(slave.getProcessImage(1));
            }
        }, 1000, 1000);*/
    }

    private static void setCoil(ProcessImage processImage){
        // 模拟线圈状态
        processImage.setCoil(0, true);
        processImage.setCoil(1, false);
        processImage.setCoil(2, true);
    }

    private static void setInput(ProcessImage processImage){
        // 模拟离散输入状态
        processImage.setInput(0, false);
        processImage.setInput(1, true);
        processImage.setInput(2, false);
    }

    private static void setHoldingRegister(ProcessImage processImage){
        // 模拟保持寄存器的值
        processImage.setHoldingRegister(0,(short) 11);
        processImage.setHoldingRegister(1,(short) 22);
        processImage.setHoldingRegister(2,(short) 33);
    }

    private static void updateHoldingRegister(ProcessImage processImage){
        // 模拟修改保持寄存器的值
        processImage.setHoldingRegister(0, (short) RandomUtil.randomInt(0, 100));
        processImage.setHoldingRegister(1,(short) RandomUtil.randomInt(0, 100));
        processImage.setHoldingRegister(2,(short) RandomUtil.randomInt(0, 100));
    }

    private static void setInputRegister(ProcessImage processImage){
        // 模拟输入寄存器的值
        processImage.setInputRegister(0,(short) 44);
        processImage.setInputRegister(1,(short) 55);
        processImage.setInputRegister(2,(short) 66);
    }
}
