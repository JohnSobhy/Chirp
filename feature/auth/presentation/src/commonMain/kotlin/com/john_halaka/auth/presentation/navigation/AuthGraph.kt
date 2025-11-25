package com.john_halaka.auth.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navDeepLink
import com.john_halaka.auth.presentation.email_verification.EmailVerificationRoot
import com.john_halaka.auth.presentation.login.LoginRoot
import com.john_halaka.auth.presentation.register.RegisterRoot
import com.john_halaka.auth.presentation.register_success.RegisterSuccessRoot

fun NavGraphBuilder.authGraph(
    navController: NavController,
    onLoginSuccess: () -> Unit
) {
    navigation<AuthGraphRoutes.Graph>(
        startDestination = AuthGraphRoutes.Login
    ) {

        composable<AuthGraphRoutes.Login> {
            LoginRoot(
                onLoginSuccess = onLoginSuccess,
                onForgotPasswordClick = {
                    navController.navigate(AuthGraphRoutes.ForgotPassword)
                },
                onCreateAccountClick = {
                    navController.navigate(AuthGraphRoutes.Register) {
                        launchSingleTop = true   //if there is an instance of this screen in the backstack we show it
                        restoreState = true      //when navigate back to Register we restore the state if it is saved
                    }
                }
            )
        }

        composable<AuthGraphRoutes.Register> {
            RegisterRoot(
                onRegisterSuccess = {
                    navController.navigate(AuthGraphRoutes.RegisterSuccess(it))
                },
                onLoginClick = {
                    navController.navigate(AuthGraphRoutes.Login) {
                        popUpTo(AuthGraphRoutes.Register) {    //pop all screen up to the current one
                            inclusive = true
                            saveState = true    //save the state of our current screen (register)
                        }
                        launchSingleTop = true   //if there is an instance of this screen in the backstack we show it
                        restoreState = true      //when navigate back to Login after a while we restore the state if saved
                    }
                }
            )
        }
        composable<AuthGraphRoutes.RegisterSuccess> {
            RegisterSuccessRoot(
                onLoginClick = {
                    navController.navigate(AuthGraphRoutes.Login) {
                        popUpTo(AuthGraphRoutes.RegisterSuccess) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<AuthGraphRoutes.EmailVerification>(
            deepLinks = listOf(
                navDeepLink {
                    this.uriPattern =
                        "https://chirp.pl-coding.com/api/auth/verify?token={token}" //the placeholder {token} matches the name that the navigation route takes and automatically assign it
                },
                navDeepLink {
                    this.uriPattern = "chirp://chirp.pl-coding.com/api/auth/verify?token={token}"
                }
            )
        ) {
            EmailVerificationRoot(
                onLoginClick = {
                    navController.navigate(AuthGraphRoutes.Login) {
                        popUpTo(AuthGraphRoutes.Login) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                },
                onCloseClick = {
                    navController.navigate(AuthGraphRoutes.Login) {
                        popUpTo(AuthGraphRoutes.Login) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                }
            )
        }


    }
}