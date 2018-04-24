# Android_Project2_ExpenseManager

Project used the following concepts
1.splash screen
2.Navigation Drawer
3.Floating Action Button
4.Form creation
5.Date Picker
6.SQLite database
7.Tool bar
8. Menus

Splash screen pops up opening of the home screen for 3 seconds.

Navigation Drawer Screen on the top of App Bar loads the nav items.
Design have the slide in and slide out option on the click of
nav bar menu.

OnClick of the fab button produce two tabs.
With the tab names as expense and income.

On the Clicking on the first tab Expense form should be open the fields like Amount, category (Spinner), 
Source, Notes(Optional),Date(DatePicker), ClosingFab Button.

Second tab consist of same fields.

Data entered at the two tabs are saved in to Sqlite DB.
The saved data from the Sqlite Db is retrieved in to ListView which is home screen.

we can delete and update this listitems for the data loaded in the ListView by clicking.

On Click of the Switch button will produce the Balance View
which will load two list views Expense, Income seperately.

on pressing the back button will switch to previous Activity.
