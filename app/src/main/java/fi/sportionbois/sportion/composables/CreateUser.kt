package fi.sportionbois.sportion.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import fi.sportionbois.sportion.ButtonCHViolet
import fi.sportionbois.sportion.CenteredColumnMaxWidthAndHeight
import fi.sportionbois.sportion.entities.User
import fi.sportionbois.sportion.viewmodels.UserViewModel

@Composable
fun CreateUser(userViewModel: UserViewModel) {
    CenteredColumnMaxWidthAndHeight {
        Text(text = "Welcome to Sportion")
        var username by remember { mutableStateOf("") }
        var fname by remember { mutableStateOf("") }
        var lname by remember { mutableStateOf("") }
        var isEnabled by remember { mutableStateOf(false) }
        Column {
            TextField(value = username, label = {
                Text("Username")
            },
                onValueChange = { username = it })
            TextField(value = fname, label = {
                Text("First name")
            },
                onValueChange = { fname = it })
            TextField(value = lname, label = {
                Text("Last name")
            },
                onValueChange = { lname = it })
            if (username != "" && fname != "" && lname != "") { isEnabled = true }
            ButtonCHViolet(text = "Insert user", isEnabled, onClick = {
                userViewModel.insert(User(username, fname, lname))
            })
        }
    }
}
