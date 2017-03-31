package com.bvrith.snapdirect.common;

import com.bvrith.snapdirect.utils.ImageUtils;

public class Constants {
	public final static String LOG_TAG = "com.bvrith.snapdirect";
	public static final String PREFS_NAME = "PhotoBookPrefs";

    public static final int BOOKMARKS_HEIGHT = ImageUtils.dpToPx(60);

	public static final int INTENT_PICK_IMAGE = 1;

    public static final int EVENT_NEW_COMMENT = 0;
    public static final int EVENT_NEW_IMAGE = 1;
    public static final int EVENT_NEW_MESSAGE = 2;

    public static final int STATUS_UNREGISTERED = 0;
    public static final int STATUS_SMS_WAIT = 1;
    public static final int STATUS_REGISTERED = 2;

    //public final static String SERVER_URL = "http://bvrith-snapdirect.rhcloud.com";
    public final static String SERVER_URL = "http://dev.snufan.com";
    public final static String SERVER_PATH_USER = "/user";
    public final static String SERVER_PATH_IMAGE = "/image";
    public final static String SERVER_PATH_IMAGE_PRESIGNED_URL = "/image/upload_url";
    public final static String SERVER_PATH_CONTACTS = "/contacts";
    public final static String SERVER_PATH_FRIENDS = "/friends";
    public final static String SERVER_PATH_CHANNELS = "/channels";
    public final static String SERVER_PATH_FEED = "/feed";
    public final static String SERVER_PATH_LIKE = "/like";
    public final static String SERVER_PATH_COMMENTS = "/comments";
    public final static String SERVER_PATH_INFO = "/info";
    public final static String SERVER_PATH_FOLLOWERS = "/followers";

    public final static String HEADER_USERID = "userid";
    public final static String HEADER_IMAGEID = "imageid";
    public final static String HEADER_COMMENTID = "commentid";
    public final static String HEADER_MODTIME = "from";
    public final static String HEADER_ID = "id";

    public final static String RESPONSE_RESULT = "result";
    public final static String RESPONSE_RESULT_OK = "OK";
    public final static String RESPONSE_DATA = "data";
    public final static String RESPONSE_CODE = "code";

    public final static String KEY_TIMESTAMP = "timestamp";
    public final static String KEY_IMAGEID = "image_id";
    public final static String KEY_TITLE = "title";
    public final static String KEY_TEXT = "text";
    public final static String KEY_REPLY_TO = "reply_to";
    public final static String KEY_NAME = "name";
    public final static String KEY_EMAIL = "email";
    public final static String KEY_PHONE = "phone";
    public final static String KEY_NUMBER = "number";
    public final static String KEY_CODE = "code";
    public final static String KEY_ID = "id";
    public final static String KEY_PUBLIC_ID = "public_id";
    public final static String KEY_URL_SMALL = "url_small";
    public final static String KEY_LOCAL_URI = "local_uri";
    public final static String KEY_URL = "url";
    public final static String KEY_IMAGE = "image";
    public final static String KEY_AUTHOR = "author";
    public final static String KEY_SERVER_VERSION  = "version";
    public final static String KEY_MIN_CLIENT_VERSION= "min_client";
    public final static String KEY_LATEST_APK_URL = "latest_apk";
    public final static String KEY_LATEST_APK_VER ="latest_ver";

    public final static String FILENAME_AVATAR = "avatar.jpg";
    public final static String APP_FOLDER = "snapdirect";
    public final static String PUSH_SENDER_ID = "69387014044";

    public final static String DEMO_ACCOUNT_ID = "";
}

