package com.smartin.timedic.caregiver.config;

/**
 * Created by Hafid on 9/23/2017.
 */

public class Constants {
    //Action constants
    public static final String actionPosition = "device_pos";

    public static final String ROUTE_LOGIN_FIREBASE = "/authenticateBySocial/caregiver?";
    public static final String ROUTE_LOGIN_FIREBASE_TOKEN = "/authenticateBySocialToken/caregiver?";

    //RestApi constants
    public static final String BASE_URL = "https://timedic.id:8443";
    //public static final String BASE_URL = "http://167.205.7.227:8080";
    //public static final String BASE_URL = "http://192.168.1.5:8080";
    //public static final String BASE_URL = "http://192.168.1.5:7778";

    //USER
    //public static final String ROUTE_LOGIN = "/authenticate/user?";
    //public static final String ROUTE_RESGISTER = "/register/user";
    //public static final String ROUTE_USER_POST = "api/user";
    //public static final String ROUTE_USER_BY_ID = "api/user/";

    //AUTH CAREGIVER
    public static final String ROUTE_LOGIN_CAREGIVER = "/authenticate/caregiver?";
    public static final String ROUTE_REGISTER_CAREGIVER = "/register/caregiver";
    public static final String ROUTE_CAREGIVER_POST = "api/caregiver";
    public static final String ROUTE_CAREGIVER_BY_ID = "api/caregiver/";

    //CAREGIVER
    public static final String ROUTE_CAREGIVER = "/api/caregiver/";

    //RATING
    public static final String ROUTE_RATE_POST = "/api/caregiverRate";
    public static final String ROUTE_RATE_GET = "/api/getCaregiverRateByIdCaregiver/";

    //HOMECARE TRANSACTION
    public static final String ROUTE_TRANSACTION = "/api/transactions/homecare/";
    public static final String ROUTE_ACTIVE_ORDER = "/api/orderactive/homecare/";
    public static final String ROUTE_HISTORY_ORDER = "/api/history/homecare/";
    public static final String ROUTE_ACTIVE_ORDER_PAGINATION = "/api/transactionOrderActiveWithPaginationByField";
    public static final String ROUTE_HISTORY_ORDER_PAGINATION = "/api/transactionOrderHistoryWithPaginationByField";
    public static final String ROUTE_ITEM_ACTIVE_ORDER = "/api/transactionOrderActiveWithPaginationByFieldByIdCaregiver";
    public static final String ROUTE_ITEM_HISTORY_ORDER = "/api/transactionOrderHistoryWithPaginationByFieldByIdCaregiver";

    public static final String ROUTE_ITEM_ACTIVE_ORDER_GROUP_BY = "/api/transactionOrderActiveWithPaginationByFieldByGroupByIdIdCaregiver";
    public static final String ROUTE_ITEM_HISTORY_ORDER_GROUP_BY = "/api/transactionOrderHistoryWithPaginationByFieldByGroupByIdIdCaregiver";

    public static final String ROUTE_GET_SCHEDULE_BY_ID_CAREGIVER_AND_ID_TRX = "/api/getByIdCaregiverAndIdTrx";
    public static final String ROUTE_UPDATE_CAREGIVER_TRX_LIST = "api/caregiverTrxList/";


    //SCHEDULE
    public static final String ROUTE_ADD_SCHEDULE = "api/caregiverSchedulle";
    public static final String ROUTE_CHECK_SCHEDULE_DATA = "api/caregiverSchedulleCekData?";
    public static final String ROUTE_CHECK_SCHEDULE_STATUS = "api/caregiverSchedulleCekStatus?";
    public static final String ROUTE_UPDATE_SCHEDULE_ID_CAREGIVER_AND_DAY= "/api/caregiverSchedulleByIdCaregiverAndDay?";
    public static final String ROUTE_UPDATE_SCHEDULE_FOR_TIME= "/api/updateCaregiverSchedulleForTime?";
    public static final String ROUTE_SCHEDULE_BY_ID = "api/caregiverSchedulle/";

    //CONTACT
    public static final String ROUTE_CONTACT = "api/getContactUsForNurse/";

    /*
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
    */


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
    public static final String TERM_AND_COND = "https://timedic.id/#privacyAndPolicy";
    public static final String PRIVACY_STATEMENT = "https://timedic.id/#privacyAndPolicy";

}
