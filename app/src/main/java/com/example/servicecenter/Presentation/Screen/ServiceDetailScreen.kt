//package com.example.servicecenter.Presentation.Screens
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavController
//import com.example.servicecenter.Presentation.ViewModels.ViewModelMain
//import com.example.servicecenter.apiconnect.model.ServiceItem
//
//@Composable
//fun ServiceDetailScreen(serviceId: String?, navController: NavController, viewModel: ViewModelMain = viewModel()) {
//    var serviceItem by remember { mutableStateOf<ServiceItem?>(null) }
//
//    // Загружаем информацию о сервисе по ID
//    LaunchedEffect(serviceId) {
//        serviceId?.let {
//            serviceItem = viewModel.getServiceById(it) // Предполагаем, что у вас есть метод в ViewModel для получения данных по ID
//        }
//    }
//
//    // Если сервис не загружен или serviceId не найден
//    if (serviceItem == null) {
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            CircularProgressIndicator()
//        }
//    } else {
//        // Если сервис найден, отображаем его детали
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(text = "Сервис: ${serviceItem?.name}", style = MaterialTheme.typography.headlineSmall)
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(text = "Описание: ${serviceItem?.description ?: "Нет описания"}", style = MaterialTheme.typography.bodyMedium)
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(text = "Цена: ${serviceItem?.price} ₽", style = MaterialTheme.typography.bodyMedium)
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(text = "Категория: ${serviceItem?.category?.name}", style = MaterialTheme.typography.bodyMedium)
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Button(
//                onClick = { navController.popBackStack() },
//                modifier = Modifier.align(Alignment.CenterHorizontally)
//            ) {
//                Text("Назад")
//            }
//        }
//    }
//}
