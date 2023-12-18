package id.anaktoba.lucid.di

import id.anaktoba.lucid.LucidViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { LucidViewModel(get()) }
}