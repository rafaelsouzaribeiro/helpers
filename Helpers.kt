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
}
