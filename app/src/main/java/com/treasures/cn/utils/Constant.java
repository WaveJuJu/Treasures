package com.treasures.cn.utils;

public class Constant {
    public static final String MODIFY_KEY = "modify";
    public final static int PAGE_COUNT = 20;
    public static final String REGISTERED_ACTIVITY_KEY = "REGISTERED_ACTIVITY_KEY";
    public static final String start_figure = "http://www.scgj.de/start.jpg"; //启动图R

    public static class FRAGMENT {
        public static final int FRAGMENT_HOME_TAG = 0;
        public static final int FRAGMENT_CATEGORY_TAG= 1;
        public static final int FRAGMENT_USER_TAG = 2;
    }

    public static class BundleKey{
        public static final String SHOW_MODIFY = "SHOW_MODIFY";
        public static final String MODIFY_TREASURES = "MODIFY_TREASURES";/*编辑藏品传入Treasures对象的key*/
        public static final String MODIFY_TYPE = "MODIFY_TYPE";/*编辑藏品TYPE*/
        public static final String DATA_TYPE = "DATA_TYPE";/*编辑藏品TYPE*/
        public static final String TREAURES_DETAIL = "TREAURES_DETAIL";
        public static final String PHOTO_TREASURE = "PHOTO_TREASURE";
        public static final String PHOTO_POSITION = "PHOTO_POSITION";

    }
    public static class ModifyAction{
        public static final String MODIFY = "MODYFY";
        public static final String DELETE = "DELETE";
        public static final String COPY = "COPY";
    }

    public static class SHARED_PREFERENCES_KEY {
        public static final String SP_TREASURES_USER_INFO_KEY = "SP_TREASURES_USER_INFO_KEY";/*用户信息*/
            public static final String SP_CATEGORY_TYPE_INFO_KEY = "SP_CATEGORY_TYPE_INFO_KEY";/*类型选择信息*/
    }

    public static class RequestCode{
        public static final int SELECTED_PHOTO_REQUEST_CODE = 0x99;
        public static final int SELECTED_CAMERA_REQUEST_CODE = 0x100;
        public static final int SELECTED_PHOTO_CROP_REQUEST_CODE = 0x98;
        public static final int REQUEST_PERMISSION = 0x88;
    }
    public static class ImageId{
        private static final String IMAGE1 = "IMAGE1.jpg";
        private static final String IMAGE2 = "IMAGE2.jpg";
        private static final String IMAGE3 = "IMAGE3.jpg";
        private static final String IMAGE4 = "IMAGE4.jpg";
        private static final String IMAGE5 = "IMAGE5.jpg";
        private static final String IMAGE6 = "IMAGE6.jpg";
        private static final String IMAGE7 = "IMAGE7.jpg";
        private static final String IMAGE8 = "IMAGE8.jpg";
        private static final String IMAGE9 = "IMAGE9.jpg";
        public static final String[] imageNames = {IMAGE1,IMAGE2,IMAGE3,IMAGE4,IMAGE5
                ,IMAGE6,IMAGE7,IMAGE8,IMAGE9};
        public static final String USER_HEAD_NAME = "head_portrait.jpg";
    }
}
