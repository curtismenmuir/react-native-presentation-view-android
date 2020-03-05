import React, { Component } from "react";
import {
  BackHandler,
  Button,
  NativeModules,
  Platform,
  StyleSheet,
  Text,
  View
} from "react-native";

const instructions = Platform.select({
  ios: "Press Cmd+R to reload,\n" + "Cmd+D or shake for dev menu",
  android:
    "Double tap R on your keyboard to reload,\n" +
    "Shake or press menu button for dev menu"
});

export default class App extends Component {
  constructor(props) {
    super(props);
  }

  async requestPermissions() {
    try {
      var result = 1;
      // Request permissions
      var result = await new Promise((resolve, reject) => {
        NativeModules.PresentationViewAndroidModule.requestPermissions(
          "com.example",
          resolve,
          reject
        );
      });
      if (result !== 1) {
        return BackHandler.exitApp();
      }
    } catch (error) {
      return BackHandler.exitApp();
    }
  }

  async presentation() {
    try {
      await new Promise((resolve, reject) => {
        NativeModules.PresentationViewAndroidModule.presentation(
          "PresentationView",
          resolve,
          reject
        );
      });
    } catch (error) {
      return null;
    }
  }

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>Welcome to React Native!</Text>
        <Text style={styles.instructions}>To get started, edit App.js</Text>
        <Text style={styles.instructions}>{instructions}</Text>
        <Button
          title={"request Permissions"}
          onPress={this.requestPermissions.bind(this)}
        />
        <Button title={"Presentation"} onPress={this.presentation.bind(this)} />
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#F5FCFF"
  },
  welcome: {
    fontSize: 20,
    textAlign: "center",
    margin: 10
  },
  instructions: {
    textAlign: "center",
    color: "#333333",
    marginBottom: 5
  }
});
