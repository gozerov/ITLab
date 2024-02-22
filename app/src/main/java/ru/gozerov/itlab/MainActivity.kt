package ru.gozerov.itlab

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.gozerov.domain.models.login.LoginData
import ru.gozerov.domain.usecases.login.PerformLogin
import ru.gozerov.domain.usecases.login.PerformRegister
import ru.gozerov.itlab.databinding.ActivityMainBinding
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
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            lifecycleScope.launch {
                performLogin.execute(
                    arg = LoginData("user1", "password"),
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
}