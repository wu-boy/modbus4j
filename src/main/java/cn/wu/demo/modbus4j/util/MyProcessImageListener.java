package cn.wu.demo.modbus4j.util;

import com.serotonin.modbus4j.ProcessImageListener;

/**
 * @author wusq
 * @date 2020/12/26
 */
public class MyProcessImageListener implements ProcessImageListener {

    @Override
    public void coilWrite(int offset, boolean oldValue, boolean newValue) {
        System.out.println("线圈状态地址=" + offset + "，旧值=" + oldValue + "，新值=" + newValue);
    }

    @Override
    public void holdingRegisterWrite(int offset, short oldValue, short newValue) {
        System.out.println("保持寄存器地址=" + offset + "，旧值=" + oldValue + "，新值=" + newValue);
    }
}
