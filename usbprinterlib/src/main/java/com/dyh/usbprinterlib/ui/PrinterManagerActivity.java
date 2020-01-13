package com.dyh.usbprinterlib.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dyh.usbprinterlib.OnPrinterNotifyListener;
import com.dyh.usbprinterlib.PrinterConfig;
import com.dyh.usbprinterlib.R;
import com.dyh.usbprinterlib.UsbPrintManager;
import com.dyh.usbprinterlib.velocity.PrinterBean;


/**
 * 打印机设置页面
 */
public class PrinterManagerActivity extends Activity {
    TextView mStatusTextView;
    CheckBox mUse80WidthCheckBox;
    EditText mPrintCountEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printer_manager);

        mStatusTextView = findViewById(R.id.mStatusTextView);
        mUse80WidthCheckBox = findViewById(R.id.mUse80WidthCheckBox);
        mPrintCountEditText = findViewById(R.id.mPrintCountEditText);
        //初始化打印机配置信息
        PrinterConfig printerConfig = UsbPrintManager.getInstance().getPrinterConfig(this);
        if (printerConfig != null) {
            mUse80WidthCheckBox.setChecked(printerConfig.getPagerWidth() == 80);
            mPrintCountEditText.setText(String.valueOf(printerConfig.getPrintCount()));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //页面关闭时销毁资源
        UsbPrintManager.getInstance().releasePrinter();
    }

    /**
     * 关闭页面
     *
     * @param view
     */
    public void back(View view) {
        finish();
    }

    /**
     * 保存配置
     *
     * @param view
     */
    public void save(View view) {
        PrinterConfig printerConfig = new PrinterConfig();
        int count = 1;
        try {
            count = Integer.parseInt(mPrintCountEditText.getText().toString());
        } catch (Exception e) {
            count = 1;
        }
        printerConfig.setPrintCount(count);
        printerConfig.setPagerWidth(mUse80WidthCheckBox.isChecked() ? 80 : 58);
        UsbPrintManager.getInstance().saveConfigInfo(this, printerConfig);
        finish();
    }

    /**
     * 打印测试页
     *
     * @param view
     */
    public void printTest(View view) {
        PrinterBean printerBean = new PrinterBean();
        printerBean.templateInfo = "\n欢迎使用DW-USB打印系统\nUSB打印机已成功配置";
        UsbPrintManager.getInstance()
                .setOnPrinterNotifyListener(new OnPrinterNotifyListener() {
                    @Override
                    public void onPrinterNotify(NotifyMessage notifyMessage) {
                        mStatusTextView.setText(notifyMessage.getInfo());
                        if (NotifyMessage.PRINT_FINISH == notifyMessage) {
                            Toast.makeText(PrinterManagerActivity.this, "打印完成", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).print(this, printerBean);
    }
}
