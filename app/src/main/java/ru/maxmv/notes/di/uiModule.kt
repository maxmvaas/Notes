package ru.maxmv.notes.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

import ru.maxmv.notes.presentation.note_edit.NoteEditViewModel

val uiModule = module {
    viewModel {
        NoteEditViewModel(get())
    }
}