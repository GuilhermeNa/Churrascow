package br.com.apps.churrascow.di.modules

import androidx.room.Room
import br.com.apps.churrascow.database.AppDataBase
import br.com.apps.churrascow.database.DATABASE_NAME
import br.com.apps.churrascow.database.dao.UserDao
import br.com.apps.churrascow.datasource.external.ExternalUserDataSource
import br.com.apps.churrascow.datasource.internal.InternalUserDataSource
import br.com.apps.churrascow.repository.UserRepository
import br.com.apps.churrascow.ui.fragments.home.HomeFragmentViewModel
import br.com.apps.churrascow.ui.fragments.login.LoginFragmentViewModel
import br.com.apps.churrascow.ui.fragments.register.RegisterFragmentViewModel
import br.com.apps.churrascow.useCase.CredentialUseCase
import br.com.apps.churrascow.useCase.ParticipantUseCase
import br.com.apps.churrascow.useCase.UserUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val roomModule = module {
    single<AppDataBase> {
        Room.databaseBuilder(get(), AppDataBase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
    single<UserDao>{get<AppDataBase>().userDao()}
}

val viewModelModule = module {
    viewModel<LoginFragmentViewModel> { LoginFragmentViewModel(get(), get()) }
    viewModel<RegisterFragmentViewModel> { RegisterFragmentViewModel(get(), get()) }
    viewModel<HomeFragmentViewModel> { HomeFragmentViewModel() }
}

val repositoryModule = module {
    single<UserRepository> { UserRepository(get(), get()) }
}

val internalDataSource = module {
    single<InternalUserDataSource> { InternalUserDataSource(get()) }
}

val externalDataSource = module {
    single<ExternalUserDataSource> { ExternalUserDataSource() }
}

val useCaseModule = module {
    single<UserUseCase> { UserUseCase(get()) }
    single<ParticipantUseCase> { ParticipantUseCase() }
    single<CredentialUseCase> { CredentialUseCase() }
}



val appModules = listOf(
    roomModule,
    viewModelModule,
    useCaseModule,
    repositoryModule,
    internalDataSource,
    externalDataSource
)


