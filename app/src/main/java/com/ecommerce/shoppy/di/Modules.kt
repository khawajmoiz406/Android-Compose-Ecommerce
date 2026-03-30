package com.ecommerce.shoppy.di

import com.ecommerce.shoppy.core.shared.domain.repository.CartRepository
import com.ecommerce.shoppy.core.shared.domain.repository.FavouriteRepository
import com.ecommerce.shoppy.core.shared.domain.usecase.AddToCartUseCase
import com.ecommerce.shoppy.core.shared.domain.usecase.GetCartCountUseCase
import com.ecommerce.shoppy.core.shared.domain.usecase.RemoveFromCartUseCase
import com.ecommerce.shoppy.core.shared.domain.usecase.ToggleFavouriteUseCase
import com.ecommerce.shoppy.ui.address.data.local.AddressLocalDataSource
import com.ecommerce.shoppy.ui.address.data.remote.AddressRemoteDataSource
import com.ecommerce.shoppy.ui.address.data.repository.AddressRepositoryImpl
import com.ecommerce.shoppy.ui.address.domain.repository.AddressRepository
import com.ecommerce.shoppy.ui.address.domain.usecase.DeleteAddressUseCase
import com.ecommerce.shoppy.ui.address.domain.usecase.GetAddressUseCase
import com.ecommerce.shoppy.ui.address.domain.usecase.GetUserAddressUseCase
import com.ecommerce.shoppy.ui.address.domain.usecase.NewAddressUseCase
import com.ecommerce.shoppy.ui.address.domain.usecase.UpdateAddressUseCase
import com.ecommerce.shoppy.ui.address.presentation.add.NewAddressViewModel
import com.ecommerce.shoppy.ui.address.presentation.listing.AddressListingViewModel
import com.ecommerce.shoppy.ui.auth.data.local.AuthLocalDataSource
import com.ecommerce.shoppy.ui.auth.data.remote.AuthRemoteDataSource
import com.ecommerce.shoppy.ui.auth.data.repository.AuthRepositoryImpl
import com.ecommerce.shoppy.ui.auth.domain.repository.LoginRepository
import com.ecommerce.shoppy.ui.auth.domain.usecase.LoginUseCase
import com.ecommerce.shoppy.ui.auth.presentation.login.LoginViewModel
import com.ecommerce.shoppy.ui.cart.data.local.CartLocalDataSource
import com.ecommerce.shoppy.ui.cart.data.remote.CartRemoteDataSource
import com.ecommerce.shoppy.ui.cart.data.repository.CartRepositoryImpl
import com.ecommerce.shoppy.ui.cart.data.repository.CheckoutRepositoryImpl
import com.ecommerce.shoppy.ui.cart.domain.repository.CheckoutRepository
import com.ecommerce.shoppy.ui.cart.domain.usecase.CheckoutUseCase
import com.ecommerce.shoppy.ui.cart.domain.usecase.GetCartUseCase
import com.ecommerce.shoppy.ui.cart.domain.usecase.GetDefaultAddressUseCase
import com.ecommerce.shoppy.ui.cart.domain.usecase.UpdateQuantityUseCase
import com.ecommerce.shoppy.ui.cart.presentation.cart.CartViewModel
import com.ecommerce.shoppy.ui.cart.presentation.checkout.CheckoutViewModel
import com.ecommerce.shoppy.ui.favourite.data.local.FavouriteLocalDataSource
import com.ecommerce.shoppy.ui.favourite.data.remote.FavouriteRemoteDataSource
import com.ecommerce.shoppy.ui.favourite.data.repository.FavouriteRepositoryImpl
import com.ecommerce.shoppy.ui.favourite.domain.usecase.GetAllFavouriteUseCase
import com.ecommerce.shoppy.ui.favourite.presentation.FavouriteViewModel
import com.ecommerce.shoppy.ui.home.data.local.HomeLocalDataSource
import com.ecommerce.shoppy.ui.home.data.remote.HomeRemoteDataSource
import com.ecommerce.shoppy.ui.home.data.repository.HomeRepositoryImpl
import com.ecommerce.shoppy.ui.home.domain.repository.HomeRepository
import com.ecommerce.shoppy.ui.home.domain.usecase.GetHomeUseCase
import com.ecommerce.shoppy.ui.home.domain.usecase.GetProductsByFiltersUseCase
import com.ecommerce.shoppy.ui.home.domain.usecase.ObserverProductsFavouriteUseCase
import com.ecommerce.shoppy.ui.home.presentation.HomeViewModel
import com.ecommerce.shoppy.ui.order.data.local.OrderLocalDataSource
import com.ecommerce.shoppy.ui.order.data.remote.OrderRemoteDataSource
import com.ecommerce.shoppy.ui.order.data.repository.OrderRepositoryImpl
import com.ecommerce.shoppy.ui.order.domain.repository.OrderRepository
import com.ecommerce.shoppy.ui.order.domain.usecase.GetOrderDetailUseCase
import com.ecommerce.shoppy.ui.order.domain.usecase.GetUserOrdersUseCase
import com.ecommerce.shoppy.ui.order.presentation.detail.OrderDetailViewModel
import com.ecommerce.shoppy.ui.order.presentation.listing.OrdersViewModel
import com.ecommerce.shoppy.ui.product_detail.data.local.ProductDetailLocalDataSource
import com.ecommerce.shoppy.ui.product_detail.data.remote.ProductDetailRemoteDataSource
import com.ecommerce.shoppy.ui.product_detail.data.repository.ProductDetailRepositoryImpl
import com.ecommerce.shoppy.ui.product_detail.domain.repository.ProductDetailRepository
import com.ecommerce.shoppy.ui.product_detail.domain.usecase.GetProductDetailUseCase
import com.ecommerce.shoppy.ui.product_detail.presentation.ProductDetailViewModel
import com.ecommerce.shoppy.ui.profile.data.local.ProfileLocalDataSource
import com.ecommerce.shoppy.ui.profile.data.remote.ProfileRemoteDataSource
import com.ecommerce.shoppy.ui.profile.data.repository.ProfileRepositoryImpl
import com.ecommerce.shoppy.ui.profile.domain.repository.ProfileRepository
import com.ecommerce.shoppy.ui.profile.domain.usecase.ChangeNotificationSettingUseCase
import com.ecommerce.shoppy.ui.profile.domain.usecase.ChangeThemeModeUseCase
import com.ecommerce.shoppy.ui.profile.domain.usecase.GetTotalOrdersUseCase
import com.ecommerce.shoppy.ui.profile.domain.usecase.GetUserInfoUseCase
import com.ecommerce.shoppy.ui.profile.domain.usecase.LogoutUseCase
import com.ecommerce.shoppy.ui.profile.presentation.ProfileViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val localModule = module {
    single { createRoomDatabase(androidContext()) }
    single { createEncryptedSharedPref(androidContext()) }
}

val networkModule = module {
    single { createOkHttpClient() }
    single { createRetrofit(get()) }
    single { createApiService(get()) }
}

val authModule = module {
    single { AuthLocalDataSource(get()) }
    single { AuthRemoteDataSource(androidContext(), get()) }
    single<LoginRepository> { AuthRepositoryImpl(get(), get()) }

    factory { LoginUseCase(get()) }

    viewModel { LoginViewModel(loginUseCase = get()) }
}

val homeModule = module {
    single { HomeLocalDataSource(get()) }
    single { HomeRemoteDataSource(androidContext(), get()) }
    single<HomeRepository> { HomeRepositoryImpl(get(), get()) }

    factory { GetHomeUseCase(get()) }
    factory { GetProductsByFiltersUseCase(get()) }
    factory { ObserverProductsFavouriteUseCase(get()) }

    viewModel {
        HomeViewModel(
            homeUseCase = get(),
            getCartCountUseCase = get(),
            toggleFavouriteUseCase = get(),
            getProductsByFiltersUseCase = get(),
            observerProductsFavouriteUseCase = get()
        )
    }
}

val favouriteModule = module {
    single { FavouriteLocalDataSource(get()) }
    single { FavouriteRemoteDataSource(androidContext(), get()) }
    single<FavouriteRepository> { FavouriteRepositoryImpl(get(), get()) }

    factory { GetAllFavouriteUseCase(get()) }
    factory { ToggleFavouriteUseCase(get()) }

    viewModel {
        FavouriteViewModel(
            favouriteUseCase = get(),
            toggleFavouriteUseCase = get(),
        )
    }
}

val productDetailModule = module {
    single { ProductDetailLocalDataSource(get()) }
    single { ProductDetailRemoteDataSource(androidContext(), get()) }
    single<ProductDetailRepository> { ProductDetailRepositoryImpl(get(), get()) }

    factory { GetProductDetailUseCase(get()) }

    viewModel {
        ProductDetailViewModel(
            detailUseCase = get(),
            addToCartUseCase = get(),
            removeFromCartUseCase = get(),
            toggleFavouriteUseCase = get()
        )
    }
}

val cartModule = module {
    single { CartLocalDataSource(get()) }
    single { CartRemoteDataSource(androidContext(), get()) }
    single<CartRepository> { CartRepositoryImpl(get(), get()) }
    single<CheckoutRepository> { CheckoutRepositoryImpl(get(), get()) }

    //Cart Use Cases
    factory { GetCartCountUseCase(get()) }
    factory { AddToCartUseCase(get()) }
    factory { GetCartUseCase(get()) }
    factory { RemoveFromCartUseCase(get()) }
    factory { UpdateQuantityUseCase(get()) }

    //Checkout Use Cases
    factory { CheckoutUseCase(get()) }
    factory { GetDefaultAddressUseCase(get()) }

    viewModel {
        CartViewModel(
            getCartUseCase = get(),
            removeFromCartUseCase = get(),
            updateQuantityUseCase = get(),
            toggleFavouriteUseCase = get(),
        )
    }

    viewModel {
        CheckoutViewModel(
            checkoutUseCase = get(),
            getDefaultAddressUseCase = get()
        )
    }
}

val profileModule = module {
    single { ProfileLocalDataSource(get(), get()) }
    single { ProfileRemoteDataSource(androidContext(), get()) }
    single<ProfileRepository> { ProfileRepositoryImpl(get(), get()) }

    factory { LogoutUseCase(get()) }
    factory { GetUserInfoUseCase(get()) }
    factory { GetTotalOrdersUseCase(get()) }
    factory { ChangeThemeModeUseCase(get()) }
    factory { ChangeNotificationSettingUseCase(get()) }

    viewModel {
        ProfileViewModel(
            logoutUseCase = get(),
            getUserInfoUseCase = get(),
            getTotalOrdersUseCase = get(),
            changeThemeModeUseCase = get(),
            changeNotificationSettingUseCase = get()
        )
    }
}

val addressModule = module {
    single { AddressLocalDataSource(get(), get()) }
    single { AddressRemoteDataSource(androidContext(), get()) }
    single<AddressRepository> { AddressRepositoryImpl(get(), get()) }

    factory { GetAddressUseCase(get()) }
    factory { NewAddressUseCase(get()) }
    factory { UpdateAddressUseCase(get()) }
    factory { DeleteAddressUseCase(get()) }
    factory { GetUserAddressUseCase(get()) }

    viewModel {
        NewAddressViewModel(
            getAddressUseCase = get(),
            newAddressUseCase = get(),
            updateAddressUseCase = get(),
        )
    }

    viewModel {
        AddressListingViewModel(
            addressUseCase = get(),
            deleteAddressUseCase = get(),
        )
    }
}

val orderModule = module {
    single { OrderLocalDataSource(get()) }
    single { OrderRemoteDataSource(androidContext(), get()) }
    single<OrderRepository> { OrderRepositoryImpl(get(), get()) }

    factory { GetUserOrdersUseCase(get()) }
    factory { GetOrderDetailUseCase(get()) }

    viewModel { OrdersViewModel(getUserOrdersUseCase = get()) }
    viewModel { OrderDetailViewModel(getOrderDetailUseCase = get()) }
}