# Merlin Technical Test - Android Team


![merlin](https://user-images.githubusercontent.com/36903172/40690518-1d96ca26-636d-11e8-96da-1099959227a2.jpeg)

# Our problem

Merlin Android Test is an application that obtains currencies information from fixer (https://fixer.io/documentation). The problem is that the application implements a deprecated version of the fixer API. Refer to the documentation to have a picture of the changes needed. You will need the key that have been given in other instructions.
The application should prompt the user to select some currencies of his preference being the default EUR, JPY. GBP and BRL. This being affected by the new version of the service. When the user enters a value on the text field a list of conversions shall be shown.
The applications has some compilation errors as well as runtime errors which you’ll have to solve. (The Android Project comes next to this document and is called CurrencyExchange).

# Your Challenges

1. The applications has some compilation errors as well as runtime errors or visualization errors which you’ll have to solve.
2. The app have some crashes that need to be addressed and the code have some issues that potentially can make the app crash. Find them and fix them
3. Once you have the application running, implement the MVVM design pattern on any view / screen you wish.
4. Please describe your technical decisions that allow us understand your new proposal. You can use the README.md file

# Solution

**Errors fixed**
```
- Core module and data module were added to App module
- Few layouts were fixed
- Api version was updated
- The GetExchangeRatesUseCase was subscribed to Schedulers.io()
```

**ExchangeActivity was migrated to MVVM**
```
I tried to migrate ExchangeActivity as it was possible without changed significantly the project structure
- I used DataBinding to observe textChanged on the ViewModel
- I used LiveData to observe the ListConversionExchange on ExchangeActivity (View)

```







