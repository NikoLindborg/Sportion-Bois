package fi.sportionbois.sportion.composables

import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fi.sportionbois.sportion.ButtonCHViolet
import fi.sportionbois.sportion.CenteredColumnMaxWidthAndHeight
import fi.sportionbois.sportion.entities.User
import fi.sportionbois.sportion.viewmodels.UserViewModel
import fi.sportionbois.sportion.R


@Composable
fun CreateUser(userViewModel: UserViewModel) {
    CenteredColumnMaxWidthAndHeight {
        Text(text = stringResource(id = R.string.greeting), style = MaterialTheme.typography.h1)
        Spacer(modifier = Modifier.padding(15.dp))
        Text(text = stringResource(id = R.string.createuser))
        var username by remember { mutableStateOf("") }
        var fname by remember { mutableStateOf("") }
        var lname by remember { mutableStateOf("") }
        var isEnabled by remember { mutableStateOf(false) }
        Spacer(modifier = Modifier.padding(5.dp))
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
        if (username != "" && fname != "" && lname != "") {
            isEnabled = true
        }
        Spacer(modifier = Modifier.padding(20.dp))
        ButtonCHViolet(text = "Insert user", isEnabled, onClick = {
            userViewModel.insert(User(username, fname, lname))
        })

    }
}