package com.example.quitandagrpc.libs

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.StrictMode
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.InetAddress
import java.net.URL
import java.nio.charset.StandardCharsets
import java.text.DecimalFormat
import java.util.Locale
import com.example.quitandagrpc.R
import com.example.quitandagrpc.classes.ErrorTextFieldState


@OptIn(ExperimentalMaterial3Api::class)
class Helpers {
    fun convertBr(Value:Float):String{
        val moeda = DecimalFormat.getCurrencyInstance(Locale("pt", "BR"))
        return moeda.format(Value).replace("R$", "R$ ")
    }

    suspend fun ip():String?{
        val localhost = withContext(Dispatchers.IO) {
            InetAddress.getLocalHost()
        }.hostAddress

        return localhost
    }

    @Composable
    fun LoadingComponent() {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally),
                color= MaterialTheme.colorScheme.tertiary
            )
        }
    }

    @Composable
    fun LoadingComponentButton() {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally),
                color=MaterialTheme.colorScheme.tertiary
            )
        }
    }

    @Composable
    fun LoadingComponentWihout() {
        CircularProgressIndicator(
            modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally),
            color=MaterialTheme.colorScheme.tertiary
        )

    }

    @Composable
    fun PoupLoading(){
        val dialogWidth = 500.dp
        val dialogHeight = 300.dp

        Dialog(onDismissRequest = {  }) {
            // Draw a rectangle shape with rounded corners inside the dialog
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    contentAlignment=Alignment.Center,
                    modifier = Modifier
                        .size(dialogWidth, dialogHeight)
                        .background(Color.White)
                        .padding(5.dp)){
                    CircularProgressIndicator(
                        modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally),
                        color=MaterialTheme.colorScheme.tertiary
                    )
                }
            }

        }
    }


    @Composable
    fun FullScreenLoading() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),  // Cor de fundo de sua escolha
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()  // Ou qualquer outra animação de carregamento
        }
    }

    @Composable
    fun Images(photo:String,title:String){
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(photo)
                .size(Size.ORIGINAL) // Set the target size to load the image at.
                .build()
        )

        val state = painter.state
        
        if (state is AsyncImagePainter.State.Loading) {
            CircularProgressIndicator()
        }else{
            Image(
                painter = painter,
                contentDescription = title,
                modifier = Modifier.width(100.dp)
            )
        }



//        AsyncImage(
//            model = photo,
//            contentDescription = title,
//            modifier = Modifier
//                .size(100.dp)
//                .clip(CircleShape)
//        )
//        AsyncImage(
//            model = ImageRequest.Builder(LocalContext.current)
//                .data(photo)
//                .crossfade(true)
//                .build(),
//            contentDescription = title,
//            contentScale = ContentScale.Crop,
//            modifier = Modifier
//                .size(100.dp)
//                .clip(CircleShape)
//        )

    }


    @Composable
    fun Label(text:Int){
        Text(
            text = stringResource(id = text), fontSize = 20.sp,
            color = Color.White, modifier = Modifier
                .background(
                    colorResource(id = R.color.cinza)
                )
                .fillMaxWidth(100f)
        )
    }

    @Composable
    fun ErrorTextField(
        state: ErrorTextFieldState,
        placeholderText: String,
        leadingIconVector: ImageVector,
        modifier: Modifier
    ) {
        Column {
            val error = state.error
            TextField(
                value = state.text,
                onValueChange = { if (it.length <= 255) state.updateText(it)  },
                placeholder = { Text(text = placeholderText) },
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorCursorColor = Color.Red,
                    cursorColor = Color.Red
                ),
                singleLine = true,
                isError = error != null,
                leadingIcon = { Icon(imageVector = leadingIconVector, contentDescription = null) },
                keyboardActions = KeyboardActions {
                    state.validate()
                },
                modifier = modifier
            )
            if (error != null) {
                Text(
                    error,
                    color = Color.Red,
                )
            }
        }
    }

    @Composable
    fun ErrorTextFieldEmail(
        state: ErrorTextFieldState,
        placeholderText: String,
        leadingIconVector: ImageVector,
        modifier: Modifier,
        keyboardType: KeyboardType
    ) {
        Column {
            val error = state.error
            TextField(
                value = state.text,
                onValueChange = { if (it.length <= 255) state.updateText(it) },
                placeholder = { Text(text = placeholderText) },
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorCursorColor = Color.Red,
                    cursorColor =  Color.Red
                ),
                singleLine = true,
                isError = error != null,
                leadingIcon = { Icon(imageVector = leadingIconVector, contentDescription = null) },
                keyboardActions = KeyboardActions {
                    state.validate()
                },
                modifier = modifier,
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
            )
            if (error != null) {
                Text(
                    error,
                    color = Color.Red,
                )
            }
        }
    }

    @Composable
    fun ErrorTextFieldCpf(
        state: ErrorTextFieldState,
        placeholderText: String,
        leadingIconVector: ImageVector,
        modifier: Modifier,
        maskInput: VisualTransformation
    ) {
        Column {
            val error = state.error
            TextField(
                value = state.text,
                onValueChange = { if (it.length <= 11 && it.isDigitsOnly()) state.updateText(it)  },
                placeholder = { Text(text = placeholderText) },
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorCursorColor = Color.Red,
                    cursorColor =  Color.Red
                ),
                singleLine = true,
                isError = error != null,
                leadingIcon = { Icon(imageVector = leadingIconVector, contentDescription = null) },
                keyboardActions = KeyboardActions {
                    state.validate()
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = modifier,
                visualTransformation = maskInput
            )
            if (error != null) {
                Text(
                    error,
                    color = Color.Red,
                )
            }
        }
    }

    @Composable
    fun ErrorTextFieldPhone(
        state: ErrorTextFieldState,
        placeholderText: String,
        leadingIconVector: ImageVector,
        modifier: Modifier,
        keyboardType: KeyboardType,
        maskInput:VisualTransformation
    ) {
        Column {
            val error = state.error
            TextField(
                value = state.text,
                onValueChange = {
                    if(it.length <= 11 && it.isDigitsOnly()) state.updateText(it)
                },
                placeholder = { Text(text = placeholderText) },
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorCursorColor = Color.Red,
                    cursorColor =  Color.Red
                ),
                singleLine = true,
                isError = error != null,
                leadingIcon = { Icon(imageVector = leadingIconVector, contentDescription = null) },
                keyboardActions = KeyboardActions {
                    state.validate()
                },
                modifier = modifier,
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                visualTransformation = maskInput
            )
            if (error != null) {
                Text(
                    error,
                    color = Color.Red,
                )
            }
        }
    }



    @Composable
    fun ErrorTextFieldPassword(
        state: ErrorTextFieldState,
        placeholderText: String,
        modifier: Modifier
    ) {
        Column {
            val error = state.error
            var passwordVisibility = rememberSaveable { mutableStateOf(false) }

            TextField(
                value = state.text,
                onValueChange = {
                    if (it.length <= 255) state.updateText(it)
                },
                placeholder = { Text(text = placeholderText) },
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorCursorColor = Color.Red,
                    cursorColor =  Color.Red
                ),
                singleLine = true,
                isError = error != null,
                keyboardActions = KeyboardActions {
                    state.validate()
                },
                modifier = modifier,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisibility.value)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff

                    IconButton(onClick = {
                        passwordVisibility.value = !passwordVisibility.value
                    }) {
                        Icon(imageVector  = image, "")
                    }
                }
            )
            if (error != null) {
                Text(
                    error,
                    color = Color.Red,
                )
            }
        }
    }

    @Composable
    fun Boxs(){
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(10.dp),
            colors= CardDefaults.cardColors(containerColor = colorResource(id = R.color.LightSlateGrey)),
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(80f)

        ) {
            Row{
                Icon(Icons.Default.Notifications,"", tint = Color.White,
                    modifier=Modifier.padding(top=5.dp,start=5.dp))
                Text(
                    text = stringResource(id = R.string.selecionado),
                    textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth(100f),
                    color=Color.White
                )
            }

        }
    }

    @Composable
    fun BoxText(text:String){
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.LightSlateGrey)),
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(80f)

        ) {
            Row{
                Icon(Icons.Default.Notifications,"", tint = Color.White,
                    modifier=Modifier.padding(top=5.dp,start=5.dp))
                Text(
                    text = text,
                    textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth(100f),
                    color=Color.White
                )
            }

        }
    }

    @Composable
    fun AlertText(text:String,b: MutableState<Boolean>){
        val dialogWidth = 200.dp
        val dialogHeight = 100.dp

        if (b.value) {
            Dialog(onDismissRequest = { b.value = false }) {
                // Draw a rectangle shape with rounded corners inside the dialog
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Button(
                        onClick = {
                            b.value = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.pastel_green),
                            contentColor = colorResource(id = R.color.marron)
                        )
                    ) {
                        Text(text = stringResource(id = R.string.close))
                    }

                    Box(
                        Modifier
                            .size(dialogWidth, dialogHeight)
                            .background(Color.White)
                            .padding(5.dp)){
                        Text(text = text,color=Color.Red)
                    }
                }

            }
        }
    }

    fun sendEmail(context: Context, email:String){
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:${email}")
        }
        ContextCompat.startActivity(
            context,
            Intent.createChooser(emailIntent, "Send feedback"),
            bundleOf()
        )
    }

    fun getRandomString(length: Int) : String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

    @Composable
    fun Alert(text:Int,b: MutableState<Boolean>){
        val dialogWidth = 200.dp
        val dialogHeight = 100.dp

        if (b.value) {
            Dialog(onDismissRequest = { b.value = false }) {
                // Draw a rectangle shape with rounded corners inside the dialog
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Button(
                        onClick = {
                            b.value = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.pastel_green),
                            contentColor = colorResource(id = R.color.marron)
                        )
                    ) {
                        Text(text = stringResource(id = R.string.close))
                    }

                    Box(
                        Modifier
                            .size(dialogWidth, dialogHeight)
                            .background(Color.White)
                            .padding(5.dp)){
                        Text(text = stringResource(id = text),color=Color.Red)
                    }
                }

            }
        }
    }

    fun toBase64(url:String): String {
        // val uri = Uri.parse(url)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val inputData: ByteArray = URL(url).openStream().readBytes()
        // val inputData = context.contentResolver.openInputStream(uri)?.readBytes()

        return String(
            android.util.Base64.encode(inputData, android.util.Base64.DEFAULT),
            StandardCharsets.UTF_8
        )
    }

//    fun convertTime(date:String):String{
//        val data = Date(date)
//        val local = Locale("pt", "BR")
//        val formato: DateFormat = SimpleDateFormat("dd 'de' MMMM 'de' yyyy", local)
//
//        return formato.format(data)
//    }

//    fun convertTime(date: String): String {
//        val zonedDateTime = ZonedDateTime.parse(date)
//        val dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale("pt", "BR"))
//
//        return dtf.format(zonedDateTime)
//    }


    fun fromBase64(content:String): String {
        return String(
            android.util.Base64.decode(content, android.util.Base64.DEFAULT),
            StandardCharsets.UTF_8
        )
    }

    fun bitMap(Image: String): Bitmap? {
        val imageBytes =  android.util.Base64.decode(Image, 0)
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }

    private fun makeACall(context: Context, phoneNumber: String) {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:$phoneNumber")
        //make sure you have permission to make a call
        ContextCompat.startActivity(context, intent, bundleOf())
    }

    fun buChargeEvent(context: Context, phoneNumber: String,act: Activity) {

        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CALL_PHONE
            ) -> {
                // You can use the API that requires the permission.
                makeACall(context,phoneNumber)
            }
            else -> {
                // You can directly ask for the permission.
                ActivityCompat.requestPermissions(
                    act,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    1
                )
            }
        }


    }

    @Composable
    fun ComposableLifecycle(
        lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
        onEvent: (LifecycleOwner, Lifecycle.Event) -> Unit
    ) {

        DisposableEffect(lifecycleOwner) {
            val observer = LifecycleEventObserver { source, event ->
                onEvent(source, event)
            }
            lifecycleOwner.lifecycle.addObserver(observer)

            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    }

    @Composable
    fun KeepScreenOn() {
        val currentView = LocalView.current
        DisposableEffect(Unit) {
            currentView.keepScreenOn = true
            onDispose {
                currentView.keepScreenOn = false
            }
        }
    }

    @Composable
    fun BackPressHandler(
        backPressedDispatcher: OnBackPressedDispatcher? =
            LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher,
        onBackPressed: () -> Unit
    ) {
        val currentOnBackPressed by rememberUpdatedState(newValue = onBackPressed)

        val backCallback = remember {
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    currentOnBackPressed()
                }
            }
        }

        DisposableEffect(key1 = backPressedDispatcher) {
            backPressedDispatcher?.addCallback(backCallback)

            onDispose {
                backCallback.remove()
            }
        }
    }

    @Composable
    fun rememberErrorTextFieldState(
        initialText: String,
        validate: (String) -> String? = { null },
    ): ErrorTextFieldState {
        return rememberSaveable(saver = ErrorTextFieldState.Saver(validate)) {
            ErrorTextFieldState(initialText, validate)
        }
    }

    @Composable
    fun requestUnique(
        Request:@Composable () -> Unit
    ) {
        var cond by rememberSaveable {
            mutableStateOf(false)
        }

        if (!cond) {
            Request()

            cond=true

        }

    }

}