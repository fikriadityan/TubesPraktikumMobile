package id.ac.unpas.tubes.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import id.ac.unpas.tubes.networks.MobilApi
import id.ac.unpas.tubes.persistences.MobilDao
import id.ac.unpas.tubes.repositories.MobilRepository

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    @ViewModelScoped
    fun providePromoRepository (
        api: MobilApi,
        dao: MobilDao
    ): MobilRepository {
        return MobilRepository(api, dao)
    }
}