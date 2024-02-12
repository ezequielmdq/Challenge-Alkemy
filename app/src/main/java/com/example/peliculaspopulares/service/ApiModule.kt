package com.example.peliculaspopulares.service

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@InstallIn(ActivityComponent::class)
@Module
abstract class ApiModule {
    @Binds
    abstract fun bindApiModule(impl : MoshiPeliculaInterfaceImpl) : MoshiPeliculaInterface
}