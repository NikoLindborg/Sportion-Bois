package fi.sportionbois.sportion.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fi.sportionbois.sportion.CenteredColumnMaxWidthAndHeight
import fi.sportionbois.sportion.R
import fi.sportionbois.sportion.components.ButtonCHViolet
import fi.sportionbois.sportion.entities.User
import fi.sportionbois.sportion.viewmodels.UserViewModel

/**
 * Composable for creating a user once the application launches.
 **/

@Composable
fun CreateUser(userViewModel: UserViewModel) {
    CenteredColumnMaxWidthAndHeight {
        Text(
            text = stringResource(id = R.string.greeting),
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colors.onBackground
        )
        Spacer(modifier = Modifier.padding(15.dp))
        Text(
            text = stringResource(id = R.string.createuser),
            color = MaterialTheme.colors.onBackground
        )
        var username by remember { mutableStateOf("") }
        var fname by remember { mutableStateOf("") }
        var lname by remember { mutableStateOf("") }
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
        Spacer(modifier = Modifier.padding(20.dp))
        //  If any of the fields is in default value, the button is enabled
        val filledFields = (fname != "" && lname != "" && username != "")
        ButtonCHViolet(text = "Insert user", filledFields, onClick = {
            userViewModel.insert(User(username, fname, lname))
        })

    }
}