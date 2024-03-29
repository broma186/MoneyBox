# The Brief:

Create a mini version of the Moneybox app that will allow existing users to login, check their account and add money to their moneybox.

## Part A - Fix current bugs

In this repository you will find LoginActivity that allows users to enter their username, password and optionally their name.  We have implemented a basic screen for you that validates username, password and name against simple regular expressions but makes no calls to the API.

Unfortunately this screen has 3 bugs raised by our testers that they want you to fix and are listed below.  If you are struggling to fix any of these bugs, please give it your best attempt and then move onto the next bug or task.
 
Bug 3 -For the animation I set a max frame, then played the animation so that the animation stopps when the pig walks into the frame.
I then set an animation listener so that the fellow puts the money into the piggy bank repetitively. I set a min/max frame inside the animation listener block and then set an infinite repeat count with restart repeat mode.


### Bug 1 - Layout does not look as expected

Please re-arrange the views in the LoginActivity to match the expected layout.

![](/images/correct_layout.png)


### Bug 2 - Validation is incorrect
If the input entered by the user is correct then they should see a toast saying “Input is valid!”.  However if it is not correct we should show an error on the field that is incorrect.  Below is the following validation logic:

- Email is not optional and should match EMAIL_REGEX
- Password is not optional and should match PASSWORD_REGEX
- Name is optional, but if it contains any value it should match NAME_REGEX

There is some validation logic in LoginActivity, but it is currently incorrect. Please implement this feature to match this logic.

/*
The full name regex had to be changed to be optional, and all fields would fail if only one field was wrong. Now if you have three failures, you can fix one at a time and the warning will clear.*/

### Bug 3 - Animation is looping incorrectly

Above the login button is an animation of an owl and a pig.  We would like this animation to play every time the user starts the activity and then loop indefinitely.  The logic for this animation should be as follows:

- The animation should start from frame **0** to **109** when the user first starts the activity.  See below for animation.
![](/images/firstpig.gif)
- When the first stage of the animation has finished it should then loop from frame **131** to **158** continuously.  See below for animation.<br/>
![](/images/secondpig.gif)

To create animation in our app we use a helpful library called Lottie.  This has been added to the project for you, but currently it just plays the animation once and then stops.  Please implement the logic as described above.

There is lots of helpful documentation on Lottie [here](http://airbnb.io/lottie/#/android).  Please take a look at this page for information on how to loop the animation, play from a min and max frame and detect when an animation ends.


/*
I changed the constraints in the constraint layout xml files. I had to change margins and centrally align and lift up the sign up button, as well as position the animation correctly.
*/

## Part B - Add 2 new screens

We now want to give some useful functionality to our users. To allow them to log into the app, view and edit their account using our sandbox API.

### Screen 2 - User accounts screen
This screen should be shown after the user has successfully logged in and should show have the following functionality:
- Display "Hello {name} **only** if they provided it on previous screen"
- Show the **'TotalPlanValue'** of a user.
- Show the accounts the user holds, e.g. ISA, GIA, LISA, Pension.
- Show all of those account's **'PlanValue'**.
- Shhow all of those account's **'Moneybox'** total.

/*
Used an AppCompatActivity just like for the log in screen. I start off by declaring the textviews followed by retrieving the values passed through from the LoginActivity intent and setting the accounts to display. The investor data is pulled down after logging in success and passed through to user accounts by an intent. This is a quick, efficient way of storing data between screens and local storage can always be used in addition to this. The auth token is not stored in shared preferences due to security reasons but the time of log in success is. This is used to compare the time diff with now so I know when 5 minutes is up. If 5 minutes has elipsed, the app goes back to the login screen but only when the app is reloaded is this checked. There is a new main/launch activity called launchactivity which decides whether to display the user accounts first (If a login request has already been successful and stored) or the login screen (due to auth token invaliditiy or no auth token at all).
The user's accounts are displayed in a recyclerView list with an adapter carrying view holders. This sets up the textviews and image and provides an intent on click to the next screen.
The login request details are stored in Realm, and are used so that the user can open the app up with a valid auth token and not have to log in again.
Every network request uses interfaces, RxJava, okHttp3 and retrofit as this seems to be the most readable way to do network requests for Android.
I also used a GsonConverter for the Http3 JSON serialization and an HTTPLogging interceptor so I could read server responses.
*/

### Screen 3 - Individual account screen
If a user selects one of those accounts, they should then be taken to this screen.  This screen should have the following functionality:
- Show the **'Name'** of the account.
- Show the account's **'PlanValue'**.
- Show the accounts **'Moneybox'** total.
- Allow a user to add to a fixed value (e.g. £10) to their moneybox total.

A prototype wireframe of all 3 screens is provided as a guideline. You are free to change any elements of the screen and provide additional information if you wish.

![](/images/wireframe.png)

/*
 The plan value/money box is displayed in textviews on the third screen. The top up button is fixed to top up 10 pounds at a time.
Once the user has topped up, they are greeted to a toast displaying the success. The moneybox total updates immediately. If the user presses the back button, they will end up on the accounts screen again, where the investor deets are downloaded on postresume again. This is done so that we can have an up to date moneybox value reflected in the money box total of each account. After pulling the data again, the adapter for the recycler view is notified and the new amount is displayed.
*/

## What we are looking for:
 - An android application written in either Java or Kotlin.
 - Demonstration of coding style and design patterns.
 - Knowledge of common android libraries and any others that you find useful.
 - Storage of data between screens.
 - Consistency of data between screens.
 - Error handling.
 - Any form of unit or integration testing you see fit.
 - The application must run on Android 5.0 and above.
 - The application must compile and run in Android Studio.

Please feel free to refactor the LoginActivity and use any libraries/helper methods to make your life easier.

## How to Submit your solution:
 - Clone this repository
 - Create a public repo in github, bitbucket or a suitable alternative and provide a link to the repository.
 - Provide a readme in markdown which details how you solved the bugs in part A, and explains the structure of your solution in Part B and any libraries that you may have used.

## API Usage
This a brief summary of the api endpoints in the moneybox sandbox environment. There a lot of other additional properties from the json responses that are not relevant, but you must use these endpoints to retrieve the information needed for this application.

#### Base URL & Test User
The base URL for the moneybox sandbox environment is `https://api-test01.moneyboxapp.com/`.
You can log into test your app using the following user:

|  Username          | Password         |
| ------------- | ------------- |
| androidtest@moneyboxapp.com  | P455word12  |

#### Headers

In order to make requests https must be used and the following headers must be included in each request.

|  Key | Value |
| ------------- | ------------- |
| AppId  | 3a97b932a9d449c981b595  |
| Content-Type  | application/json  |
| appVersion | 5.10.0 |
| apiVersion | 3.0.0 |

#### Authentication
To login with this user to retrieve a bearer token you need to call `POST /users/login`.
```
POST /users/login
{
  "Email": "androidtest@moneyboxapp.com",
  "Password": "P455word12",
  "Idfa": "ANYTHING"
}
```
Sample json response
```
"Session": {
        "BearerToken": "TsMWRkbrcu3NGrpf84gi2+pg0iOMVymyKklmkY0oI84=",
        "ExternalSessionId": "4ff0eab7-7d3f-40c5-b87b-68d4a4961983", -- not used
        "SessionExternalId": "4ff0eab7-7d3f-40c5-b87b-68d4a4961983", -- not used
        "ExpiryInSeconds": 0 -- not used
    }
```
After obtaining a bearer token an Authorization header must be provided for all other endpoints along with the headers listed above (Note: The BearerToken has a sliding expiration of 5 mins).

|  Key          | Value         |
| ------------- | ------------- |
| Authorization  | Bearer TsMWRkbrcu3NGrpf84gi2+pg0iOMVymyKklmkY0oI84=  |

#### Investor Products
Provides product and account information for a user that will be needed for the two additional screens.
```
GET /investorproducts
```
### One off payments
Adds a one off amount to the users moneybox.
```
POST /oneoffpayments
{
  "Amount": 20,
  "InvestorProductId": 3230 ------> The InvestorProductId from /investorproducts endpoint
}
```
Good luck!
