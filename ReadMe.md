# Currency Converter App

This is a simple currency converter app made with Jetpack Compose.
The app converts UAH (Ukrainian hryvnia) to USD and EUR.
This project was created for practice and learning Android development with Compose
It is based on lessons 6.1–6.17: lessons 6.1-6.17 https://www.youtube.com/watch?v=vGx3GUEe7zU&list=PLV_vplloSltHSWsenFekvvAvhK3xM7X6Q&index=76

# Notes App
This is a simple note app made with Jetpack Compose.
In this app realised adds, delete and edit note.
This project was created for practice and learning Android development with Compose
It is based on lessons 7.1–7.14: https://www.youtube.com/watch?v=vGx3GUEe7zU&list=PLV_vplloSltHSWsenFekvvAvhK3xM7X6Q&index=76

# MyClicker App
This is a simple clicker app made with Jetpack Compose.
Here I learned the basic of ViewModel() that let me store data after rotation of the screen and 
also I acquainted MVMV architecture.
It is based on lessons 8.4 - 8.8 https://www.youtube.com/watch?v=vGx3GUEe7zU&list=PLV_vplloSltHSWsenFekvvAvhK3xM7X6Q&index=76

# MyCocktailsApp
This is a simple clicker app made with Jetpack Compose.
Here I learned the works with retrofit, converter.gson and coil for image.
After TestNavigationApp I added here Navigation but I cann't adds Parcerize from kotlinx after 4 hours working with GPT,
so I wrote Parcelable methods without @Parcelize
It is based on lessons 9.1 - 10.11 https://www.youtube.com/watch?v=vGx3GUEe7zU&list=PLV_vplloSltHSWsenFekvvAvhK3xM7X6Q&index=76
Here I used free API https://www.thecocktaildb.com/

# TestNavigationApp
This is a simple clicker app made with Jetpack Compose.
Here I learned the works with implementation(libs.androidx.navigation.compose), creates more than one Screen,
transition between screens and data transfer (String).

It is based on lessons 10.1 - 10.6 https://www.youtube.com/watch?v=vGx3GUEe7zU&list=PLV_vplloSltHSWsenFekvvAvhK3xM7X6Q&index=76


**Useful Keyboard Shortcuts (Android Studio):**
- Ctrl + Alt + T — Surround with (for example: fold comments)
- Ctrl + Alt + L — Reformat code
- Ctrl + Alt + O — Optimize / check imports
These shortcuts help to write cleaner and faster code.

**Interesting Code Parts**
1. Toast Message Example
Toast is a small message that appears on the screen for a short time.

val context = LocalContext.current

   `Button(onClick = {
      Toast.makeText(
      context,
      "You click calculate button",
      Toast.LENGTH_LONG   
   ).show()
   }) {
   Text("Calculate")
   }`

Explanation:
LocalContext.current gives access to Android context
Toast.makeText() creates a message
Toast.LENGTH_LONG controls how long the message is visible long = 4 seconds, short = 2 seconds
.show() displays the message


2. Different between
   2.1.` var rate = remember { mutableDoubleStateOf(1.0) }`
   - rate is a State object;
   - To get value → rate.value;
   - To change value → rate.value = 2.0;
   - UI will update automatically after change;
   - rate.value = 0.023

   2.2.`var rate by remember { mutableDoubleStateOf(1.0) }
      - rate is a Double value;
      - No need to use .value;
      - Cleaner and easier to read;
      - UI still updates automatically;
      - rate = 0.023
   
   2.3.`var rate = 1.0`
      - This is just a normal variable;
      - **Compose does NOT track this variable;
      - UI will NOT update when value changes;**
      - Value will reset after recomposition;

3.  Drop down list

`    DropdownMenu(
       expanded = isDropDownOpen,
       onDismissRequest = { isDropDownOpen = false },  //if click in another place (not drop down place)
   ){
       DropdownMenuItem(
       text = { Text("to USD: 0.023") },
       onClick = {
       isDropDownOpen = false
   })`

4.    LazyColumn and closures
      `LazyColumn{
            items(notes) { note ->
                if (note.isEdition) {
                    NoteEditView(
                        item = note,
                        onEditComplete = { editName,
                                           editDescription ->
                            notes = notes.map { it.copy(isEdition = false) }
                            val editNote = notes.find { it.id == note.id }
                            editNote?.let {
                                it.title = editName
                                it.description = editDescription
                            }
                        }
                    )} else {
                    NoteView(
                        item = note,
                        onEditClick = {
                            notes = notes.map {
                                it.copy(isEdition = (it.id == note.id))
                            }
                        },
                        onDeleteClick = {
                            notes = notes - note
                        }
                    )
                }}}`
`      fun NoteView(item: OneNote, onEditClick: () -> Unit, onDeleteClick: () -> Unit)  
       fun NoteEditView(item: OneNote, onEditComplete: (String, String) -> Unit)`

5. `NavHost(navController = navController, startDestination = "parentScreen") {
       composable("parentScreen") {
           ParentScreen { text ->
           navController.navigate("childScreen/$text")
           }
       } 
       composable("childScreen/{text}") {
           val text = it.arguments?.getString("text") ?: ""
           ChildScreen(text) { navController.navigate("parentScreen") }
       }
   }`
6. MyCoctailsApp/app/src/main/java/com/mycoctailsapp/CocktailApp.kt example of how transfer object (need : Parcelable in the data class with object)