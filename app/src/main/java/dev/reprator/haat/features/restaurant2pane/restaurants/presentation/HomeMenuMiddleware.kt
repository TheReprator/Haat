package dev.reprator.haat.features.restaurant2pane.restaurants.presentation


import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.ModelUIMenuContainer
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.usecase.HomeMenuUseCase
import dev.reprator.haat.util.AppCoroutineDispatchers
import dev.reprator.haat.util.AppError
import dev.reprator.haat.util.AppSuccess
import dev.reprator.haat.util.base.mvi.Middleware
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class HomeMenuMiddleware @Inject constructor(
    private val homeMenuUseCase: HomeMenuUseCase,
    private val dispatchers: AppCoroutineDispatchers
) : Middleware<HomeMenuState, HomeMenuAction, HomeMenuEffect>, CoroutineScope, AutoCloseable {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->

    }

    override val coroutineContext: CoroutineContext =
        SupervisorJob() + Dispatchers.Main.immediate + coroutineExceptionHandler

    private val mviDispatcher = CompletableDeferred<(HomeMenuAction) -> Unit>()

    override fun attach(dispatcher: (HomeMenuAction) -> Unit) {
        mviDispatcher.complete(dispatcher)
    }

    override fun onAction(
        action: HomeMenuAction,
        state: HomeMenuState
    ) {
        when (action) {
            is HomeMenuAction.RetryHomeMenu, HomeMenuAction.FetchHomeMenu -> {
                handleHomeMenu()
            }

            else -> {}
        }
    }

    private fun handleHomeMenu() {
        launch(dispatchers.io) {
            val result = homeMenuUseCase()
            withContext(dispatchers.main) {
                when (result) {
                    is AppSuccess<ModelUIMenuContainer> -> {
                        mviDispatcher.await()(HomeMenuAction.UpdateHomeMenu(result.data))
                    }

                    is AppError -> {
                        mviDispatcher.await()(
                            HomeMenuAction.UserHomeMenuError(
                                result.message ?: result.throwable?.message ?: ""
                            )
                        )
                    }
                }
            }
        }
    }
}