package com.example.administrator.ceshigongju.bean;

/**
 * @author James
 * @date 2018/10/24
 * @describe:
 */

public class LockBean {

    /**
     * code : 1
     * msg : 成功
     * data : {"id":"753","lock_id":"F7:97:F8:FE:B8:DA","lock_no":"INNI","lock_name":"BT02-F797F8FEB8DA","house_id":"0","lock_version":"0","lock_img":"./upload/code/QrCode/de/1540263341753.jpg "}
     */

    public int code;
    public String msg;
    public DataBean data;

    public static class DataBean {
        /**
         * id : 753
         * lock_id : F7:97:F8:FE:B8:DA
         * lock_no : INNI
         * lock_name : BT02-F797F8FEB8DA
         * house_id : 0
         * lock_version : 0
         * lock_img : ./upload/code/QrCode/de/1540263341753.jpg
         */

        public int id;
        public String lock_id;
        public String lock_no;
        public String lock_name;
        public String house_id;
        public String lock_version;
        public String lock_img;
    }
}
