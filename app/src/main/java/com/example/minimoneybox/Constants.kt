package com.example.minimoneybox

object Constants {

        val EMAIL_REGEX = "[^@]+@[^.]+\\..+"
        val NAME_REGEX = "(.*?){0,30}"
        val PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-zA-Z]).{10,50}$"
        val firstAnim = 0 to 109
        val secondAnim = 131 to 158
        val ANIMATED_FRACTION_MAX_CONSTANT = 1.0;


        val BASE_URL : String = "https://api-test01.moneyboxapp.com/"
        val APP_ID : String = "AppId: 3a97b932a9d449c981b595"
        val CONTENT_TYPE : String = "application/json"
        val APP_VERSION : String = "5.10.0"
        val API_VERSION : String = "3.0.0"

        val TEMP_EMAIL : String = "androidtest@moneyboxapp.com"
        val TEMP_PASSWORD : String = "P455word12"
        val TEMP_IDFA : String = "ANYTHING"

        val AUTH_TOKEN_TIME_STAMP : String = "authTokenTimeStamp"
        val FIXED_TOP_UP_AMOUNT : Int = 10
        val EMAIL_KEY : String = "email"
        val PASSWORD_KEY : String = "password"
        val FULL_NAME_KEY : String = "idfa"
        val PLAN_VALUE_KEY : String = "planValueTotal"
        val PRODUCT_RESPONSES_KEY : String = "productResponses"
        val PRODUCT_RESPONSE_KEY : String = "productResponse"
        val BEARER_STR : String = "Bearer "

        val AUTH_TOKEN_KEY : String = "authToken"
        val SP_STORAGE : String = "storage"
        val FIVE_MINUTES : Long = 300000

}