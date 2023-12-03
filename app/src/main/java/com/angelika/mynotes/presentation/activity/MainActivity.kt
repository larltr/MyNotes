package com.angelika.mynotes.presentation.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.angelika.mynotes.data.preferencies.PreferenceHelper
import com.angelika.mynotes.presentation.screens.authentication.register.RegisterScreen
import com.angelika.mynotes.presentation.screens.edit.EditScreen
import com.angelika.mynotes.presentation.screens.home.HomeScreen
import com.angelika.mynotes.ui.theme.MyNotesTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val authentication = FirebaseAuth.getInstance()
    private var isErrorPassword = mutableStateOf(false)
    private lateinit var preferenceHelper: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceHelper = PreferenceHelper(this)
        setContent {
            MyNotesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    var startDestination = "home"
                    if (!preferenceHelper.isAuthentication) {
                        startDestination = "register"
                    }
                    NavHost(navController = navController, startDestination = startDestination) {
                        composable("register") {
                            RegisterScreen(
                                modifier = Modifier.fillMaxSize(),
                                isErrorPassword = isErrorPassword.value,
                                registerByEmail = { userEmail, userPassword ->
                                    registerByEmailWithPassword(
                                        userEmail,
                                        userPassword,
                                        navController
                                    )
                                },
                                signByEmail = { userEmail, userPassword ->
                                    signByEmailWithPassword(
                                        userEmail,
                                        userPassword,
                                        navController
                                    )
                                }
                            )
                        }
                        composable("home") { HomeScreen() }
                        composable("edit") { EditScreen() }
                    }
                }
            }
        }
    }

    private fun registerByEmailWithPassword(
        email: String,
        password: String,
        navController: NavController
    ) {
        authentication.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    navController.navigate("home")
                    preferenceHelper.isAuthentication = true
                } else {
                    when (task.exception) {
                        is FirebaseAuthWeakPasswordException -> {
                            isErrorPassword.value = true
                            Toast.makeText(this, "Password is too small", Toast.LENGTH_LONG).show()
                        }

                        is FirebaseAuthUserCollisionException -> {
                            Toast.makeText(
                                this,
                                "Данный пользователь уже зарегестрирован",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        else -> {
                            Log.e("TAG", "registerByEmailWithPassword: ")
                        }
                    }
                }
            }
    }

    private fun signByEmailWithPassword(
        email: String,
        password: String,
        navController: NavController
    ) {
        authentication.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    navController.navigate("home")
                    preferenceHelper.isAuthentication = true
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        isErrorPassword.value = true
                        Toast.makeText(this, "Неверный пароль", Toast.LENGTH_LONG).show()
                    } else {
                        Log.e("TAG", "registerByEmailWithPassword: ")
                    }
                }
            }
    }
}