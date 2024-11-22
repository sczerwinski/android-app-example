package it.czerwinski.examples.compose.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import it.czerwinski.examples.compose.model.DummyItemsRepository
import it.czerwinski.examples.compose.model.ItemsRepository

@Module
@InstallIn(SingletonComponent::class)
interface MainModule {
    @Binds
    fun bindItemsRepository(impl: DummyItemsRepository): ItemsRepository
}
