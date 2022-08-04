package com.devkey.loginretrofit

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.withCreated
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.devkey.loginretrofit.databinding.ActivityProfileBinding
import com.devkey.loginretrofit.retrofit.LoginService
import com.devkey.loginretrofit.retrofit.UserInfo
import com.devkey.loginretrofit.retrofit.UserService
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class ProfileActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        loadUserProfile()
    }

    private fun loadUserProfile() {

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(UserService::class.java)

        lifecycleScope.launch(Dispatchers.IO){
            try{
               val result = service.getSingleUser()
                updateUI(result.data, result.support)
            }catch (e: Exception){
                (e as? HttpException)?.let{
                    when (it.code()){
                        400 ->{
                            showMessage(getString(R.string.main_error_server))
                        }else->{
                        showMessage(getString(R.string.main_error_response))
                    }
                    }
                }
            }
        }

        val url = Constants.BASE_URL + Constants.API_PATH + Constants.USERS_PATH + Constants.TWO_PATH

    }
    private suspend fun updateUI(user: User, support: Support) = withContext(Dispatchers.Main){
        with(mBinding) {
            tvFullName.text = user.getFullName()
            tvEmail.text = user.email

            Glide.with(this@ProfileActivity)
                .load(user.avatar)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .circleCrop()
                .into(imgProfile)

            tvSupportMessage.text = support.text
            tvSupportUrl.text = support.url
        }
    }

    private fun showMessage(message: String){
        Snackbar.make(mBinding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}