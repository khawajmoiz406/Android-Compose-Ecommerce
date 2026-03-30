package com.ecommerce.shoppy.ui.home.presentation

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.ecommerce.shoppy.R
import com.ecommerce.shoppy.config.components.layout.ItemProduct
import com.ecommerce.shoppy.config.components.layout.SwipeRefresh
import com.ecommerce.shoppy.config.components.state.EmptyState
import com.ecommerce.shoppy.config.components.state.ProgressBar
import com.ecommerce.shoppy.config.navigation.Destination
import com.ecommerce.shoppy.config.utils.AppCompositionLocals.LocalDrawerStateController
import com.ecommerce.shoppy.config.utils.AppCompositionLocals.LocalParentNavController
import com.ecommerce.shoppy.config.utils.PermissionUtils
import com.ecommerce.shoppy.core.model.Category
import com.ecommerce.shoppy.core.model.Product
import com.ecommerce.shoppy.core.model.User
import com.ecommerce.shoppy.core.pref.EncryptedSharedPref
import com.ecommerce.shoppy.core.pref.SharedPrefKeys.NOTIFICATION_PERMISSION_ASKED
import com.ecommerce.shoppy.core.pref.SharedPrefUtils
import com.ecommerce.shoppy.ui.dashboard.presentation.component.DashboardToolbar
import com.ecommerce.shoppy.ui.home.presentation.components.HeadingRow
import com.ecommerce.shoppy.ui.home.presentation.components.HomeFilters
import com.ecommerce.shoppy.ui.home.presentation.components.ItemCategory
import com.ecommerce.shoppy.ui.home.presentation.components.NotificationPermissionContent
import com.google.gson.reflect.TypeToken
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val drawerState = LocalDrawerStateController.current
    val parentNavController = LocalParentNavController.current
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val permissionAsked = SharedPrefUtils.isNotificationPermissionAsked(context)
    val currentUser = SharedPrefUtils.getCurrentUser(context)
    val showDialog = remember {
        mutableStateOf(currentUser?.notificationEnabled != true && !permissionAsked)
    }

    val products = viewModel.products.collectAsState()
    val categories = viewModel.categories.collectAsState()
    val uiState = viewModel.uiState.collectAsState()
    val selectedCategory = remember { mutableIntStateOf(0) }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { handlePermissionResult(context, it) }
    )

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            DashboardToolbar(
                drawerState = drawerState!!,
                cartCount = viewModel.cartCount,
                scrollBehavior = scrollBehavior,
                onFilterClicked = { scope.launch { bottomSheetState.show() } },
                onCartClicked = {
                    parentNavController?.navigate(Destination.CartScreen) {
                        popUpTo(parentNavController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onSearchTextChanged = {
                    selectedCategory.intValue = 0
                    if (it.isBlank()) {
                        viewModel.getProductsByCategory("all")
                    } else {
                        viewModel.searchProduct(it)
                    }
                })
        },
    ) { innerPadding ->
        when {
            uiState.value.mainLoading -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
                content = { CircularProgressIndicator() })

            products.value.isEmpty() && categories.value.isEmpty() && uiState.value.error.isNotEmpty() -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
                content = { EmptyState(message = uiState.value.error) })

            products.value.isEmpty() && categories.value.isEmpty() -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
                content = { EmptyState(message = stringResource(R.string.no_products)) })

            else -> SwipeRefresh(
                scrollBehavior = scrollBehavior,
                isRefreshing = uiState.value.isRefreshing,
                onRefresh = { viewModel.getHome(true) },
                modifier = Modifier
                    .padding(top = innerPadding.calculateTopPadding())
                    .fillMaxSize(),
            ) {
                LazyColumn(modifier = Modifier.fillMaxHeight()) {
                    if (categories.value.isNotEmpty()) item {
                        CategoryRow(categories.value, selectedCategory.intValue) { index, category ->
                            viewModel.getProductsByCategory(category.slug)
                            selectedCategory.intValue = index
                        }
                    }

                    item {
                        Spacer(Modifier.height(10.sdp))

                        Text(
                            fontSize = 14.ssp,
                            fontWeight = FontWeight.SemiBold,
                            text = stringResource(R.string.products),
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(horizontal = 10.sdp)
                        )
                    }

                    when {
                        uiState.value.categoryProductsLoading -> item {
                            Box(Modifier.fillParentMaxHeight(0.65f)) {
                                ProgressBar()
                            }
                        }

                        uiState.value.error.isNotEmpty() -> item {
                            Box(Modifier.fillParentMaxHeight(0.65f)) {
                                EmptyState(message = uiState.value.error, scrollable = false)
                            }
                        }

                        products.value.isEmpty() -> item {
                            Box(Modifier.fillParentMaxHeight(0.65f)) {
                                EmptyState(
                                    message = stringResource(R.string.no_products),
                                    scrollable = false
                                )
                            }
                        }

                        else -> itemsIndexed(
                            items = products.value.chunked(2),
                            key = { _, items -> items.joinToString(separator = "-") { it.id.toString() } }
                        ) { index, items ->
                            ProductsRow(
                                rowIndex = index,
                                rowItems = items,
                                onFavClicked = { viewModel.toggleFavourite(it) },
                                onProductsClick = { item ->
                                    parentNavController?.let { handleItemClicked(it, item.id) }
                                },
                            )
                        }
                    }
                }
            }
        }

        if (bottomSheetState.isVisible || bottomSheetState.currentValue != SheetValue.Hidden) {
            ModalBottomSheet(
                sheetState = bottomSheetState,
                windowInsets = WindowInsets(0),
                onDismissRequest = { scope.launch { bottomSheetState.hide() } },
                content = {
                    HomeFilters(defaultValue = viewModel.filters) {
                        viewModel.filterProduct(it)
                        scope.launch { bottomSheetState.hide() }
                    }
                })
        }

        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) && !permissionAsked && showDialog.value) {
            Dialog(
                onDismissRequest = { scope.launch { showDialog.value = false } },
                content = {
                    NotificationPermissionContent(
                        onCancelClicked = {
                            showDialog.value = false
                            EncryptedSharedPref.getInstance(context)
                                .putBool(NOTIFICATION_PERMISSION_ASKED, true)
                        },
                        onAllowClicked = {
                            showDialog.value = false
                            scope.launch {
                                PermissionUtils.requestPermission(
                                    permissionLauncher,
                                    Manifest.permission.POST_NOTIFICATIONS,
                                )
                            }
                        },
                    )
                }
            )
        }
    }
}

private fun handlePermissionResult(context: Context, it: Boolean) {
    val userUpdated = SharedPrefUtils.getCurrentUser(context)?.copy(notificationEnabled = it)
    EncryptedSharedPref.getInstance(context).putModel(userUpdated, object : TypeToken<User?>() {})
    EncryptedSharedPref.getInstance(context).putBool(NOTIFICATION_PERMISSION_ASKED, true)
}

@Composable
private fun ProductsRow(
    rowIndex: Int,
    rowItems: List<Product>,
    onProductsClick: (Product) -> Unit,
    onFavClicked: (Product) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = if (rowIndex == 0) 10.sdp else 0.sdp, bottom = 10.sdp, start = 10.sdp, end = 10.sdp
            )
    ) {
        repeat(2) { i ->
            val item = if (i < rowItems.size) rowItems[i] else null
            Box(modifier = Modifier.weight(1f)) {
                if (item != null) ItemProduct(
                    item = item,
                    index = i,
                    onClick = { onProductsClick.invoke(item) },
                    onFavClicked = { onFavClicked.invoke(item) },
                )
            }
        }
    }
}

@Composable
private fun CategoryRow(
    categories: List<Category>,
    selectedCategory: Int,
    onCategoryClicked: (Int, Category) -> Unit
) {
    Column {
        Spacer(Modifier.height(10.sdp))

        HeadingRow(heading = stringResource(R.string.categories))

        Spacer(Modifier.height(10.sdp))

        LazyRow {
            itemsIndexed(categories) { index, item ->
                ItemCategory(
                    category = item,
                    index = index,
                    selectedIndex = selectedCategory,
                    onItemClick = { onCategoryClicked.invoke(index, item) })
            }
        }
    }
}

private fun handleItemClicked(navController: NavController, productId: Int?) {
    if (productId == null) return
    navController.navigate(Destination.ProductDetail(productId)) {
        popUpTo(navController.graph.startDestinationId) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}