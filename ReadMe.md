![小票](https://image.goukugogo.com/2019122411100905061b6bdf5)

![设置](https://image.goukugogo.com/201912241110284025adc9802)

# gradle引入方法
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.dongyonghui:UsbPrinter:1.1'
	}

# 使用
```
<!-- 打印纯文本消息 -->
PrinterBean printerBean = new PrinterBean();
        printerBean.templateInfo = "欢迎使用DYH蓝牙打印类库\n测试纯文本消息打印\n此打印方式打印无格式内容";
        UsbPrintManager.getInstance().print(this, printerBean);

<!-- 打印静态格式化文本消息 -->
StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<row><col weight=\"16\">商品</col><col weight=\"8\">价格</col><col weight=\"8\" gravity=\"right\">数量</col></row><BR>");
        stringBuilder.append("<row><col weight=\"16\">红烧肉</col><col weight=\"8\">12元</col><col weight=\"8\" gravity=\"right\">3</col></row><BR>");
        stringBuilder.append("<row><col weight=\"16\">可口可乐</col><col weight=\"8\">6元</col><col weight=\"8\" gravity=\"right\">3</col></row><BR>");
        stringBuilder.append("<C><B>放大居中</B></C><BR>");
        stringBuilder.append("<C><L>变高居中</L></C><BR>");
        stringBuilder.append("<C><W>变宽居中</W></C><BR>");
        stringBuilder.append("<C>字体加粗</C><BR>");
        stringBuilder.append("<RIGHT>右对齐效果</RIGHT><BR>");
        stringBuilder.append("<RIGHT><L>变高右对齐</L></RIGHT><BR>");
        stringBuilder.append("<RIGHT><W>变宽右对齐</W></RIGHT><BR>");
        stringBuilder.append("<C><QR>www.baidu.com</QR></C><BR>");
        stringBuilder.append("<C><CODE>123456890</CODE></C><BR>");


        PrinterBean printerBean = new PrinterBean();
        printerBean.templateInfo = stringBuilder.toString();
        UsbPrintManager.getInstance().print(this, printerBean);
        
        
        //组织打印信息
        OrderPrinterBean orderPrinterBean = getOrderPrinterBean();
        PrinterConfig printerConfig = UsbPrintManager.getInstance().getPrinterConfig(this);
        String tempPath = "/assets/printer_template_" + printerConfig.getPagerWidth() + "/order.vm";
        PrinterBean printerBean = UsbPrintManager.getInstance().getPrinterBean(this,
                tempPath,
                "printBean", orderPrinterBean
                , 1);


<!-- 打印模板消息 -->
UsbPrintManager.getInstance()
                .setAutoOpenSettingActivity(true)//如果需要操作蓝牙设备，自动跳转到设置页面
                .setNeedShowPrintingDialog(this, true)//是否展示打印中对话框
                .setOnPrinterNotifyListener(new OnPrinterNotifyListener() {
                    @Override
                    public void onPrinterNotify(NotifyMessage notifyMessage) {
                        switch (notifyMessage) {
                            case PRINT_FINISH://打印成功后回调
                                //递归打印列表项
                                Toast.makeText(getApplicationContext(), "打印成功", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                })
                .print(this, printerBean);
                
                
                private OrderPrinterBean getOrderPrinterBean() {
                        OrderPrinterBean orderPrinterBean = new OrderPrinterBean();
                        orderPrinterBean.setRemark("不要辣");
                        orderPrinterBean.setOrderNumber("36823");
                        orderPrinterBean.setUserAddress("望京SOHO");
                        orderPrinterBean.setUserName("王先生");
                        orderPrinterBean.setUserPhone("138****3242");
                
                        List<OrderPrinterBean.SkuItemBean> list = new ArrayList<>();
                        OrderPrinterBean.SkuItemBean skuItemBean = new OrderPrinterBean.SkuItemBean();
                        skuItemBean.setSkuCount("2");
                        skuItemBean.setSkuName("红烧肉");
                        list.add(skuItemBean);
                        skuItemBean = new OrderPrinterBean.SkuItemBean();
                        skuItemBean.setSkuCount("2");
                        skuItemBean.setSkuName("可乐");
                        list.add(skuItemBean);
                        skuItemBean = new OrderPrinterBean.SkuItemBean();
                        skuItemBean.setSkuCount("2");
                        skuItemBean.setSkuName("炸鸡");
                        list.add(skuItemBean);
                        orderPrinterBean.setSkuList(list);
                        return orderPrinterBean;
                    }
```
<!-- 设置打印机参数 -->
```
        startActivity(new Intent(this, PrinterManagerActivity.class));
```

<!-- 释放资源 -->
```
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //页面关闭时销毁资源
        UsbPrintManager.getInstance().releasePrinter();
    }
```

# 模板样例：
```
<CB>DW国际饭店</CB>
#if( $printBean.orderNumber )
$row.format("","取餐码: ","8","0","left", $printBean.orderNumber, "24","0","left")
#end
#if( $printBean.skuList )
--------------------------------
商品信息：
#foreach( $itemBean in $printBean.skuList)
#set( $countInfo ="X$itemBean.skuCount" )
<B>$row.format("",$itemBean.skuName,"12","0","left", $countInfo, "4","0","right")</B>
#end
#end
--------------------------------
顾客信息：
<L>$row.format("",$printBean.userName,"16","0","left", $printBean.userPhone, "16","0","right")</L>
#if( $printBean.userAddress )
$printBean.userAddress
#end
#if( $printBean.remark )
<B>备注：$printBean.remark</B>
#end
                    
```
                    
### $row.format 模板工具说明：
第一个参数为追加字符，传“”即可；从第二个参数开始，每4个一组代表一列，一种依次为：1、显示的文本，2、列宽每行中所有列宽相加之和需要等于 32（58小票） 或 48（80小票），3、padding，4、对齐方式 left center right;默认left

# 标签说明
 ```<BR> ：换行符
 <CUT> ：切刀指令(主动切纸,仅限切刀打印机使用才有效果) 
 <PLUGIN> ：钱箱或者外置音响指令
 <CB></CB>：居中放大
 <B></B>：放大一倍
 <C></C>：居中
 <L></L>：字体变高一倍
 <W></W>：字体变宽一倍
 <QR></QR>：二维码
 <CODE></CODE>：条形码
 <RIGHT></RIGHT>：右对齐
 <ROW></ROW>：行标签，内嵌列配合使用
 <COL weight="32" gravity="left" padding="0"></COL>：列标签，需要包含在行标签中
 列标签属性说明：
 weight：列宽，每行中所有列宽相加之和需要等于 32（58小票） 或 48（80小票）
gravity：对齐方式 left center right;默认left
padding: 边距
```

# UsbPrintManager 其他API方法说明
```
    /**
      * 打印消息
      *
      * @param context
      * @param printerBean 获取方法见demo和 getPrinterBean()方法
      * @return
      */
     public UsbPrintManager print(final Context context, final PrinterBean printerBean)
    /**
     * 生成小票信息数据
     *
     * @param context               上下文
     * @param assesTemplateFileName assess文件夹中小票模板文件名
     * @param templateRootKeyName   小票模板中自定义数据key
     * @param data                  展示的数据
     * @return
     */
    public PrinterBean getPrinterBean(Context context, String assesTemplateFileName, String templateRootKeyName, Object data)
    
     /**
     * 生成小票信息数据
     *
     * @param context               上下文
     * @param assesTemplateFileName assess文件夹中小票模板文件名
     * @param templateRootKeyName   小票模板中自定义数据key
     * @param data                  展示的数据
     * @param count                 打印数量
     * @return
     */
    public PrinterBean getPrinterBean(Context context, String assesTemplateFileName, String templateRootKeyName, Object data, int count)
    
    
 /**
     * 获取打印机配置信息
     *
     * @param context
     * @return
     */
    public PrinterConfig getPrinterConfig(Context context)

 /**
     * 保存打印机配置信息
     *
     * @param context
     * @param printerConfig
     * @return
     */
    public UsbPrintManager saveConfigInfo(Context context, PrinterConfig printerConfig) 

/**
     * 检查是否已经绑定打印机<BR>
     * 本地缓存的已经绑定过的设备名称和地址与当前手机绑定设备列表遍历对比
     *
     * @param mContext
     * @return true 表示绑定了打印机 否则表示没有绑定打印机
     */
    //是否绑定了打印机设备
    public boolean isBondedPrinter(Context mContext) 
/**
     * 是否需要弹框提示正在打印
     *
     * @param activity
     * @param needShowPrintingDialog
     * @return
     */
    public UsbPrintManager setNeedShowPrintingDialog(Activity activity, boolean needShowPrintingDialog)
/**
     * 设置通知回调监听器
     *
     * @param onPrinterNotifyListener 需要监听的监听器对象
     * @return
     */
    public UsbPrintManager setOnPrinterNotifyListener(OnPrinterNotifyListener onPrinterNotifyListener) 

```
