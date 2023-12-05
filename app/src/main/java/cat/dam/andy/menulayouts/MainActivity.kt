package cat.dam.andy.menulayouts

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import cat.dam.andy.menulayouts.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    // Enumeració per als diferents identificadors de disseny
    enum class LayoutId { LAYOUT1, LAYOUT2, LAYOUT3, LAYOUT4 }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Aplicació de l'estil temàtic de l'aplicació
            AppTheme {
                // Funció principal de Compose
                MyComposeApp()
            }
        }
    }

    @Composable
    fun MyComposeApp() {
        // Estat per recordar quin disseny està seleccionat
        var selectedLayoutId by rememberSaveable { mutableStateOf(LayoutId.LAYOUT1) }

        // Columna principal que conté botons i contingut
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Funció que mostra els botons per a la selecció de disseny
            // it és el paràmetre de retorn d'una funció lambda, en aquest cas el LayoutId del botó que s'ha clicat
            MyComposeButtons(selectedLayoutId = selectedLayoutId, onButtonClick = { selectedLayoutId = it })
            Spacer(modifier = Modifier.height(5.dp))
            // Contingut dinàmic basat en la selecció
            when (selectedLayoutId) {
                LayoutId.LAYOUT1 -> LayoutContent(LayoutId.LAYOUT1)
                LayoutId.LAYOUT2 -> LayoutContent(LayoutId.LAYOUT2)
                LayoutId.LAYOUT3 -> LayoutContent(LayoutId.LAYOUT3)
                LayoutId.LAYOUT4 -> LayoutContent(LayoutId.LAYOUT4)
            }
        }
    }

    @Composable
    fun MyComposeButtons(selectedLayoutId: LayoutId, onButtonClick: (LayoutId) -> Unit) {
        // Llista de parells que defineixen els botons i els seus textos
        val buttonData = listOf(
            Pair(LayoutId.LAYOUT1, R.string.btn_layout1),
            Pair(LayoutId.LAYOUT2, R.string.btn_layout2),
            Pair(LayoutId.LAYOUT3, R.string.btn_layout3),
            Pair(LayoutId.LAYOUT4, R.string.btn_layout4)
        )

        // Fila de botons amb espaiat i alineació personalitzats
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            // Iteració pels botons i la seva creació
            buttonData.forEach { (layoutId, stringResourceId) ->
                val isSelected = layoutId == selectedLayoutId

                Button(
                    onClick = { onButtonClick(layoutId) },
                    colors = ButtonDefaults.buttonColors(
                        Color.Transparent,
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .background(
                            if (isSelected) {
                                MaterialTheme.colorScheme.primaryContainer
                            } else {
                                MaterialTheme.colorScheme.secondaryContainer
                            }
                        ),
                ) {
                    Text(
                        text = stringResource(id = stringResourceId),
                        color = if (isSelected) {
                            MaterialTheme.colorScheme.onPrimaryContainer
                        } else {
                            MaterialTheme.colorScheme.onSecondaryContainer
                        }
                    )
                }
            }
        }
    }

    @Composable
    fun LayoutContent(layoutId: LayoutId) {
        // Context local per accedir als recursos d'Android
        val context = LocalContext.current
        // Identificador de recurs de disseny segons la selecció
        val layoutResId = when (layoutId) {
            LayoutId.LAYOUT1 -> R.layout.layout1
            LayoutId.LAYOUT2 -> R.layout.layout2
            LayoutId.LAYOUT3 -> R.layout.layout3
            LayoutId.LAYOUT4 -> R.layout.layout4
        }

        // Contenidor de caixa que conté una vista d'Android inflada
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
        ) {
            AndroidView(
                factory = { context ->
                    LayoutInflater.from(context).inflate(layoutResId, null)
                },
                modifier = Modifier.fillMaxSize(),
                update = {
                    // Actualitzar la vista si és necessari per exemple amb una variable observable
                }
            )
        }
    }
}
