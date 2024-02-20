package ru.gozerov.itlab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.gozerov.domain.repositories.LoginRepository
import ru.gozerov.itlab.app.appComponent
import ru.gozerov.itlab.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var loginRepository: LoginRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            lifecycleScope.launch {
                try {
                loginRepository.login("user1", "password")

                } catch (e: HttpException) {
                    Log.e("AAA", e.stackTraceToString())
                }
            }
        }
    }
}