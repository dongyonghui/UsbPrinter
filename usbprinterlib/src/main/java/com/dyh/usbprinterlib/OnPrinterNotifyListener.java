package com.dyh.usbprinterlib;

/**
 * 作者：DongYonghui
 * 日期：2019/10/30/030
 * 打印机状态通知监听
 */
public interface OnPrinterNotifyListener {
    enum NotifyMessage {
        PRINT_START(0x1000, "开始打印")
        , PRINT_FINISH(0x1010, "打印结束")
        , WAITING_CONNECT_DEVICE(0x1020, "正在连接打印机")
        , USB_PERMISSION_REJECT(0x1030, "USB设备请求被拒绝")
        , USB_PRINTER_NOT_FOUND(0x1031, "未发现可用的打印机")
        , USB_PRINTER_CONNECTED(0x1032, "设备已连接")
        , USB_PRINTER_CONNECTED_ERROR(0x1033, "设备连接异常")
        , USB_DEVICE_DETACHED(0x1034, "有设备拔出")
        , USB_DEVICE_ATTACHED(0x1035, "有设备插入")
        //0x1100-0x1200定义为异常消息区间
        , PRINT_FAILED_PRINT_ERROR(0x1100, "打印失败")
        , PRINT_FAILED_DEVICE_CANT_CONNECT(0x1101, "打印失败，无法连接到USB打印机")
        , PRINT_FAILED_PARAMS_ERROR(0x1102, "打印失败，传参异常，请确保程序传参的必要数据。")
        ;
        private int code;
        private String info;

        NotifyMessage(int code, String info) {
            this.code = code;
            this.info = info;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }

    void onPrinterNotify(NotifyMessage notifyMessage);
}
