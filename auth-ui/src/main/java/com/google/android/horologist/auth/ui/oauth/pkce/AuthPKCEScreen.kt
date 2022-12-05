/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.horologist.auth.ui.oauth.pkce

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.horologist.auth.composables.screens.CheckYourPhoneScreen
import com.google.android.horologist.auth.ui.ExperimentalHorologistAuthUiApi

@ExperimentalHorologistAuthUiApi
@Composable
public fun <AuthPKCEConfig, OAuthCodePayload, TokenPayload> AuthPKCEScreen(
    viewModel: AuthPKCEViewModel<AuthPKCEConfig, OAuthCodePayload, TokenPayload>
) {
    var executedOnce by rememberSaveable { mutableStateOf(false) }
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    when (state) {
        AuthPKCEScreenState.Idle -> {
            SideEffect {
                if (!executedOnce) {
                    executedOnce = true
                    viewModel.startAuthFlow()
                }
            }
        }

        AuthPKCEScreenState.Loading,
        AuthPKCEScreenState.CheckPhone -> {
            CheckYourPhoneScreen()
        }

        AuthPKCEScreenState.Success,
        AuthPKCEScreenState.Failed -> {
            // do nothing
        }
    }
}