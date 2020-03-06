# react-native-presentation-view-android

## Description

React Native app demonstrating how to configure an Android Presentation view in a React-Native app using `commonsware.cwac:layouts` and `commonsware.cwac:presentation`. JS view is used for the presentation view, with an Android control view being displayed on the device.

## Important

- Global dependencies: yarn, react-native-cli, android-studio

## :arrow_up: How to Setup Project

**Step 1:** git clone this repo:

**Step 2:** cd to the `example/` directory of the cloned repo:

**Step 3:** Install the Application with [`yarn`](https://yarnpkg.com/lang/en/)

**Step 4:** cd to the `android/` directory

**Step 5:** Run `gradlew clean` (maybe `gradle clean` depending on local configuration)

## :arrow_forward: How to Run Project

**Step 1:** Complete Setup instructions above

**Step 2:** Connect device to machine

`adb devices (can be run in terminal to check available devices)`

**Step 3:** From the `example/` directory of the repo dir then run following command:

`react-native start`

**Step 4:** In a 2nd terminal window in same location (`example/` directory):

`react-native run-android`

## Example

### Connect to Chromecast

**Step 1:** Connect device to Chromecast

- This can be done through `Google Home` app or something similar (EG `Smart View` on Samsung devices)

**Step 2:** Launch application

**Step 3:** Press `Presentation` button

- Chromecast will display `presentation.js` (`example/` directory) and device will show Android control view `presentation_activity.xml` (`android/` directory - res folder)
