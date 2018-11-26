package com.example.administrator.ceshigongju;

/**
 * 作者：create by zzy on 2017/3/14 13:56
 * 邮箱：kevinchung0769@gmail.com
 */

//全局常量 改变11111
public class Constant {

    //网络接口api
    public static class ApiConstant {

        public static final String HTTP_URL = "http://demo.jiandanzu.cn/";
        public static final String HTTP_LANDLORD  = "http://demo.jiandanzu.cn/api/";
        public static final String API_entry_lock = HTTP_LANDLORD + "entry_lock";
        public static final String API_lock_message = HTTP_LANDLORD + "lock_message";



        /*门禁*/
//        public final static String SERVER_UUID = "0000fff0-0000-1000-8000-00805f9b34fb";
//        public final static String C_READ_UUID = "0000fff1-0000-1000-8000-00805f9b34fb";
//        public final static String C_WRITE_UUID = "0000fff2-0000-1000-8000-00805f9b34fb";

        public final static String SERVER_UUID = "0000FEE7-0000-1000-8000-00805f9b34fb";
        public final static String C_READ_UUID = "0000FEC9-0000-1000-8000-00805f9b34fb";
        public final static String C_WRITE_UUID = "0000FEC7-0000-1000-8000-00805f9b34fb";

        /*所有指令的头部*/
        public final static String HEAD_CODE = "3AA300000000";
        /*获取随机数code*/
        public final static String RANDOM_CODE = "00020A07";
        /*获取随机数code*/
        public final static String SET_TIME_CODE = "000A0181";
        /*开门的code*/
        public final static String OPEN_DOOR_CODE = "000A0A88";
        /*设置密码code*/
        public final static String RESET_PW_CODE = "000C008D";
        /*设置设备2字节地址头部*/
        public final static String head_set_address = "3AA300000000000100040082";
        public final static String lock_num = "11";
        public final static String lock_id = "96:B3:85:7E:8C:C7";


        public final static String random_numble = "3AA300000000000100020A07173D";//获取随机数0A07
        public final static String get_time = "3AA300000000000100020101AB01";//读设备时间0101
        public final static String openHead = "3AA3000000000001000A0A88";//无线开门0A88,这是拼接的头部

    }
}
