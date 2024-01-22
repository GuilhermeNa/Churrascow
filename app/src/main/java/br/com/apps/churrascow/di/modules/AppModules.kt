package br.com.apps.churrascow.di.modules

import androidx.room.Room
import br.com.apps.churrascow.database.AppDataBase
import br.com.apps.churrascow.database.DATABASE_NAME
import br.com.apps.churrascow.database.dao.ActionDao
import br.com.apps.churrascow.database.dao.EventDao
import br.com.apps.churrascow.database.dao.ExpenseDao
import br.com.apps.churrascow.database.dao.GuestDao
import br.com.apps.churrascow.database.dao.TransferDao
import br.com.apps.churrascow.database.dao.UserDao
import br.com.apps.churrascow.datasource.external.ExternalActionDataSource
import br.com.apps.churrascow.datasource.external.ExternalEventDataSource
import br.com.apps.churrascow.datasource.external.ExternalExpenseDataSource
import br.com.apps.churrascow.datasource.external.ExternalGuestDataSource
import br.com.apps.churrascow.datasource.external.ExternalTransferDataSource
import br.com.apps.churrascow.datasource.external.ExternalUserDataSource
import br.com.apps.churrascow.datasource.internal.InternalActionDataSource
import br.com.apps.churrascow.datasource.internal.InternalEventDataSource
import br.com.apps.churrascow.datasource.internal.InternalExpenseDataSource
import br.com.apps.churrascow.datasource.internal.InternalGuestDataSource
import br.com.apps.churrascow.datasource.internal.InternalTransferDataSource
import br.com.apps.churrascow.datasource.internal.InternalUserDataSource
import br.com.apps.churrascow.repository.ActionRepository
import br.com.apps.churrascow.repository.EventRepository
import br.com.apps.churrascow.repository.ExpenseRepository
import br.com.apps.churrascow.repository.GuestRepository
import br.com.apps.churrascow.repository.TransferRepository
import br.com.apps.churrascow.repository.UserRepository
import br.com.apps.churrascow.ui.fragments.formEvent.FormEventFragmentViewModel
import br.com.apps.churrascow.ui.fragments.home.HomeFragmentViewModel
import br.com.apps.churrascow.ui.fragments.login.LoginFragmentViewModel
import br.com.apps.churrascow.ui.fragments.payment.PaymentFragmentViewModel
import br.com.apps.churrascow.ui.fragments.profile.ProfileFragmentViewModel
import br.com.apps.churrascow.ui.fragments.register.RegisterFragmentViewModel
import br.com.apps.churrascow.ui.fragments.support.SupportFragmentViewModel
import br.com.apps.churrascow.useCase.ActionUseCase
import br.com.apps.churrascow.useCase.CredentialUseCase
import br.com.apps.churrascow.useCase.EventUseCase
import br.com.apps.churrascow.useCase.ExpenseUseCase
import br.com.apps.churrascow.useCase.GuestUseCase
import br.com.apps.churrascow.useCase.TransferUseCase
import br.com.apps.churrascow.useCase.UserUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val roomModule = module {
    single<AppDataBase> {
        Room.databaseBuilder(get(), AppDataBase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
    single<UserDao> { get<AppDataBase>().userDao() }
    single<EventDao> { get<AppDataBase>().eventDao() }
    single<ActionDao> { get<AppDataBase>().actionDao() }
    single<GuestDao> { get<AppDataBase>().guestDao() }
    single<ExpenseDao> { get<AppDataBase>().expenseDao() }
    single<TransferDao> { get<AppDataBase>().transferDao() }
}

val viewModelModule = module {
    viewModel<LoginFragmentViewModel> { LoginFragmentViewModel(get(), get()) }
    viewModel<RegisterFragmentViewModel> { RegisterFragmentViewModel(get(), get()) }
    viewModel<HomeFragmentViewModel> { HomeFragmentViewModel(get()) }
    viewModel<FormEventFragmentViewModel> { FormEventFragmentViewModel(get()) }
    viewModel<PaymentFragmentViewModel> { PaymentFragmentViewModel() }
    viewModel<ProfileFragmentViewModel> { ProfileFragmentViewModel() }
    viewModel<SupportFragmentViewModel> { SupportFragmentViewModel() }
}

val internalDataSource = module {
    single<InternalUserDataSource> { InternalUserDataSource(get()) }
    single<InternalEventDataSource> { InternalEventDataSource(get()) }
    single<InternalActionDataSource> { InternalActionDataSource(get()) }
    single<InternalGuestDataSource> { InternalGuestDataSource(get()) }
    single<InternalExpenseDataSource> { InternalExpenseDataSource(get()) }
    single<InternalTransferDataSource> { InternalTransferDataSource(get()) }

}

val externalDataSource = module {
    single<ExternalUserDataSource> { ExternalUserDataSource() }
    single<ExternalEventDataSource> { ExternalEventDataSource() }
    single<ExternalActionDataSource> { ExternalActionDataSource() }
    single<ExternalGuestDataSource> { ExternalGuestDataSource() }
    single<ExternalExpenseDataSource> { ExternalExpenseDataSource() }
    single<ExternalTransferDataSource> { ExternalTransferDataSource() }
}

val repositoryModule = module {
    single<UserRepository> { UserRepository(get(), get()) }
    single<EventRepository> { EventRepository(get(), get()) }
    single<ActionRepository> { ActionRepository(get(), get()) }
    single<GuestRepository> { GuestRepository(get(), get()) }
    single<ExpenseRepository> { ExpenseRepository(get(), get()) }
    single<TransferRepository> { TransferRepository(get(), get()) }

}

val useCaseModule = module {
    single<UserUseCase> { UserUseCase(get()) }
    single<GuestUseCase> { GuestUseCase(get(), get()) }
    single<CredentialUseCase> { CredentialUseCase() }
    single<EventUseCase> { EventUseCase(get(), get()) }
    single<ActionUseCase> { ActionUseCase(get()) }
    single<ExpenseUseCase> { ExpenseUseCase(get(), get()) }
    single<TransferUseCase> { TransferUseCase(get(), get()) }
}

val appModules = listOf(
    roomModule,
    viewModelModule,
    useCaseModule,
    repositoryModule,
    internalDataSource,
    externalDataSource
)


