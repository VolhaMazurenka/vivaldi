package com.mazurenka.vivaldi.di

import com.mazurenka.vivaldi.data.repository.AudioRepository
import com.mazurenka.vivaldi.presentation.viewmodel.PlayerViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val appModule = module {

    single { AudioRepository(get()) }
    single { Moshi.Builder().add(KotlinJsonAdapterFactory()).build() }

    viewModel { PlayerViewModel(get()) }
}

val appModules = listOf(appModule)