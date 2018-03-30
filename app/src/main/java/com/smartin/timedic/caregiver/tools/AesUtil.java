package com.smartin.timedic.caregiver.tools;


import com.smartin.timedic.caregiver.config.Constants;

import static com.smartin.timedic.caregiver.config.Constants.APP_KEY;
import static com.smartin.timedic.caregiver.config.Constants.ITERATION;
import static com.smartin.timedic.caregiver.config.Constants.IV;
import static com.smartin.timedic.caregiver.config.Constants.KEY_SIZE;
import static com.smartin.timedic.caregiver.config.Constants.SALT;

public class AesUtil {

   
    public static String Encrypt(String PLAIN_TEXT) {
        AesUtilHelper util = new AesUtilHelper(KEY_SIZE, ITERATION);
        String encrypt = util.encrypt(SALT, IV, Constants.APP_KEY, PLAIN_TEXT);
     //   System.out.println(encrypt);
        return encrypt;
    }
    
   
    public static String  Decrypt(String CIPHER_TEXT) {
        AesUtilHelper util = new AesUtilHelper(KEY_SIZE, ITERATION);
        String decrypt = util.decrypt(SALT, IV, APP_KEY, CIPHER_TEXT);
     //   System.out.println(decrypt);
        return decrypt;
    }

}
