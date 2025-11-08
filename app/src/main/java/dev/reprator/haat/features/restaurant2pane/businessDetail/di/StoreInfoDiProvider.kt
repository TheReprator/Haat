package dev.reprator.haat.features.restaurant2pane.businessDetail.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dev.reprator.haat.features.restaurant2pane.businessDetail.data.StoreMenuDataRepository
import dev.reprator.haat.features.restaurant2pane.businessDetail.data.StoreMenuDataRepositoryImpl
import dev.reprator.haat.features.restaurant2pane.businessDetail.data.remote.StoreMenuRemoteImplRepository
import dev.reprator.haat.features.restaurant2pane.businessDetail.data.remote.mapper.StoreMenuMapper
import dev.reprator.haat.features.restaurant2pane.businessDetail.data.remote.modal.EntityStoreInfoContainer
import dev.reprator.haat.features.restaurant2pane.businessDetail.data.remote.modal.EntityStoreMenuContainer
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.ModelUIStoreContainer
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.repository.StoreMenuRepository
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.usecase.StoreMenuUseCase
import dev.reprator.haat.features.restaurant2pane.businessDetail.presentation.StoreInfoAction
import dev.reprator.haat.features.restaurant2pane.businessDetail.presentation.StoreInfoEffect
import dev.reprator.haat.features.restaurant2pane.businessDetail.presentation.StoreInfoScreenReducer
import dev.reprator.haat.features.restaurant2pane.businessDetail.presentation.StoreInfoState
import dev.reprator.haat.util.Mapper
import dev.reprator.haat.util.base.mvi.Reducer

@Module
@InstallIn(ViewModelComponent::class)
interface StoreInfoDiProvider {

    @Binds
    fun provideStoreInfoDataRepository(bind: StoreMenuRemoteImplRepository): StoreMenuDataRepository

    @Binds
    fun provideStoreInfoRepository(bind: StoreMenuDataRepositoryImpl): StoreMenuRepository

    @Binds
    fun bindStoreInfoReducer(reducer: StoreInfoScreenReducer): Reducer<StoreInfoState, StoreInfoAction, StoreInfoEffect>

    companion object {

        @Provides
        fun provideStoreInfoMapper(
        ): Mapper<Pair<EntityStoreMenuContainer, EntityStoreInfoContainer>?, ModelUIStoreContainer> {
            return StoreMenuMapper()
        }

        @Provides
        fun provideStoreInfoUseCase(repository: StoreMenuRepository): StoreMenuUseCase = StoreMenuUseCase(repository)
    }
}
