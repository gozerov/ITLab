package ru.gozerov.presentation.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.gozerov.domain.models.login.LoginData
import ru.gozerov.domain.usecases.login.PerformLogin
import ru.gozerov.domain.usecases.login.PerformRegister
import ru.gozerov.presentation.databinding.ActivityMainBinding
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var performLogin: PerformLogin

    @Inject
    lateinit var performRegister: PerformRegister

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        //MapKitFactory.initialize(this)
        setContentView(binding.root)

         binding.loginButton.setOnClickListener {
             lifecycleScope.launch {
                 performLogin.execute(
                     arg = LoginData("user31", "password"),
                 )
                 performLogin.result.collect {
                     Log.e("AAA", it.javaClass.simpleName)
                 }
             }
         }
         binding.registerButton.setOnClickListener {
             lifecycleScope.launch {
                 performRegister.execute(LoginData("user52", "password"))
                 performRegister.result.collect {
                     Log.e("AAA", it.javaClass.simpleName)
                 }
             }
         }
    }

    override fun onStart() {
        super.onStart()
        /*MapKitFactory.getInstance().onStart()
        binding.mapView.onStart()*/
    }

    override fun onStop() {/*
        binding.mapView.onStop()
        MapKitFactory.getInstance().onStop()*/
        super.onStop()
    }

}