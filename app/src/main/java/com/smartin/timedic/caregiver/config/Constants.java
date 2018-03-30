package com.smartin.timedic.caregiver.config;

/**
 * Created by Hafid on 9/23/2017.
 */

public class Constants {
    //Action constants
    public static final String actionPosition = "device_pos";

    //RestApi constants
    public static final String BASE_URL = "https://timedic.id:8443";
    //public static final String BASE_URL = "http://167.205.7.227:8080";
    //public static final String BASE_URL = "http://192.168.1.4:8080";
    //public static final String BASE_URL = "http://192.168.1.5:7778";

    //USER
    public static final String ROUTE_LOGIN = "/authenticate/user?";
    public static final String ROUTE_RESGISTER = "/register/user";
    public static final String ROUTE_USER_POST = "api/user";
    public static final String ROUTE_USER_BY_ID = "api/user/";

    //AUTH CAREGIVER
    public static final String ROUTE_LOGIN_CAREGIVER = "/authenticate/caregiver?";
    public static final String ROUTE_REGISTER_CAREGIVER = "/register/caregiver";
    public static final String ROUTE_LOGIN_FIREBASE = "/authenticateBySocial/user?";

    //HOMESTAY HOMEVISIT
    public static final String ROUTE_HOMECARE_SERVICES_GET = "/api/homecareservices";
    public static final String ROUTE_HOMECARE_SERVICES_UTIL = "/api/homecareservices/"; //WITH ID PARAM INCLUDED BEHIND get,put,delete
    public static final String ROUTE_HOMECARE_SERVICES_POST = "/api/homecareservices";

    //PATIENT
    public static final String ROUTE_ALL_PATIENTS_LIST = "/api/patients";
    public static final String ROUTE_UTILS_PATIENTS_BY_ID_PATIENT = "/api/patients/"; //WITH ID PATIENT PARAM PUT GET DELETE POST
    public static final String ROUTE_PATIENTS_BY_ID_APP_USER = "/api/patients/findbyiduser/"; //WITH ID USER APP PARAM

    //HOMECARE TRANSACTION
    public static final String ROUTE_TRANSACTION = "/api/transactions/homecare/";
    public static final String ROUTE_ACTIVE_ORDER = "/api/orderactive/homecare/";
    public static final String ROUTE_HISTORY_ORDER = "/api/history/homecare/";

    public static final String ROUTE_ASSESTMENTLIST = "/api/assessments/";
    public static final String ROUTE_ASSESTMENTS_BY_ID_SERVICES = "/api/assessments/findbyidservices/";

    //LABORATORY SERVICES
    public static final String ROUTE_LABORATORY_PACKAGES = "/api/lab/package";
    public static final String ROUTE_LABORATORY_SERVICES = "/api/lab/services";

    //LABORATORY TRANSACTION
    public static final String ROUTE_LABORATORY_TRANSACTION = "/api/lab/transaction";
    public static final String ROUTE_LABORATORY_ACTIVE_ORDER = "/lab/orderactive/";
    public static final String ROUTE_LABORATORY_HISTORY_ORDER = "/lab/history/";

    //API managing
    public static final String YOUTUBE_API_KEY = "AIzaSyCs06OoysZqT-UN1-er9Ob7q6cXzVPnViY";
    public static final String YOUTUBE_CHANNEL = "UCjDX29cI6EMpGwAIOER4vvg";
    public static final String YOUTUBE_PROJECT_NAME = "TimedicApps";

    public static final String APP_KEY = "timedictimedic18";
    public static final String SALT = "dc0da04af8fee58593442bf834b30739";
    public static final String IV = "dc0da04af8fee58593442bf834b30739";
    public static final int ITERATION = 1000;
    public static final int KEY_SIZE = 128;
    public static final String TRANSFORM = "AES/CBC/PKCS5Padding";
    public static final String CHARSET_ENC = "UTF-8";
    public static final String SECRET_KEY_FACTORY = "PBKDF2WithHmacSHA1";
    public static final String SECURITY_ALGO = "AES";

    //WEB
    public static final String TERM_AND_COND = "https://timedic.id/";
    public static final String PRIVACY_STATEMENT = "https://timedic.id/";

}
