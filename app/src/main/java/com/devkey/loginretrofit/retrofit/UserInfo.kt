package com.devkey.loginretrofit.retrofit

import com.devkey.loginretrofit.Constants
import com.google.gson.annotations.SerializedName

class UserInfo (
    @SerializedName(Constants.EMAIL_PARAM) val email: String,
    @SerializedName(Constants.PASSWORD_PARAM) val pass: String
)
