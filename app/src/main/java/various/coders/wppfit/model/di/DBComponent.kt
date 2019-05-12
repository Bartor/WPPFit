package various.coders.wppfit.model.di

import dagger.Component
import various.coders.wppfit.model.AppViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [DBModule::class])
interface DBComponent {
    fun inject(appViewModel: AppViewModel)
}