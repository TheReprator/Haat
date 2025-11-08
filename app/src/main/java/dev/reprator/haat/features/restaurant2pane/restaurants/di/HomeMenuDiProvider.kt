package dev.reprator.haat.features.restaurant2pane.restaurants.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.multibindings.IntoSet
import dev.reprator.haat.features.restaurant2pane.restaurants.data.HomeMenuDataRepository
import dev.reprator.haat.features.restaurant2pane.restaurants.data.HomeMenuDataRepositoryImpl
import dev.reprator.haat.features.restaurant2pane.restaurants.data.remote.HomeMenuRemoteImplRepository
import dev.reprator.haat.features.restaurant2pane.restaurants.data.remote.mapper.HomeMenuMapper
import dev.reprator.haat.features.restaurant2pane.restaurants.data.remote.modal.EntityMenuContainer
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.ModelUIMenuContainer
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.repository.HomeMenuRepository
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.usecase.HomeMenuUseCase
import dev.reprator.haat.features.restaurant2pane.restaurants.presentation.HomeMenuAction
import dev.reprator.haat.features.restaurant2pane.restaurants.presentation.HomeMenuEffect
import dev.reprator.haat.features.restaurant2pane.restaurants.presentation.HomeMenuMiddleware
import dev.reprator.haat.features.restaurant2pane.restaurants.presentation.HomeMenuScreenReducer
import dev.reprator.haat.features.restaurant2pane.restaurants.presentation.HomeMenuState
import dev.reprator.haat.util.Mapper
import dev.reprator.haat.util.base.mvi.Middleware
import dev.reprator.haat.util.base.mvi.Reducer

@Module
@InstallIn(ViewModelComponent::class)
interface HomeMenuDiProvider {

    @Binds
    fun provideHomeMenuMapper(bind: HomeMenuMapper): Mapper<EntityMenuContainer?, ModelUIMenuContainer>

    @Binds
    fun provideHomeMenuDataRepository(bind: HomeMenuRemoteImplRepository): HomeMenuDataRepository

    @Binds
    fun provideHomeMenuRepository(bind: HomeMenuDataRepositoryImpl): HomeMenuRepository

    @Binds
    @IntoSet
    fun bindHomeMenuMiddleware(middleWare: HomeMenuMiddleware): Middleware<HomeMenuState, HomeMenuAction, HomeMenuEffect>

    @Binds
    fun bindHomeMenuReducer(reducer: HomeMenuScreenReducer): Reducer<HomeMenuState, HomeMenuAction, HomeMenuEffect>

    companion object {

        @Provides
        fun provideHomeMenuUseCase(repository: HomeMenuRepository): HomeMenuUseCase = HomeMenuUseCase(repository)
    }
}