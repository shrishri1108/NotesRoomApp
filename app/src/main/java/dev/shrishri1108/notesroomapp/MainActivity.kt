package dev.shrishri1108.notesroomapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import dev.shrishri1108.notesroomapp.dataLayer.NoteDatabase
import dev.shrishri1108.notesroomapp.presentationLayer.AddNoteScreen
import dev.shrishri1108.notesroomapp.presentationLayer.NoteScreen
import dev.shrishri1108.notesroomapp.presentationLayer.NoteViewModel
import dev.shrishri1108.notesroomapp.ui.theme.NotesRoomAppTheme
class MainActivity : ComponentActivity() {

    private val dataBase by lazy {
        Room.databaseBuilder(
            this,
            NoteDatabase::class.java,
            "notes.db")
            .build()
    }

    private val viewModel by viewModels<NoteViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return NoteViewModel(noteDao = dataBase.dao) as T
                }
            }
        }
    )
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesRoomAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val state by viewModel.state.collectAsState()
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "NoteScreen") {
                        composable("NoteScreen") {
                            NoteScreen(state = state, navController = navController, onEvent = viewModel::onEvent)
                        }
                        composable("AddNoteScreen") {
                            AddNoteScreen(state = state, navController = navController, onEvent = viewModel::onEvent)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NotesRoomAppTheme {
        Greeting("Android")
    }
}
