import React, { Component } from "react";
import { Button, NativeModules, StyleSheet, Text, View } from "react-native";

export default class App extends Component {
  constructor(props) {
    super(props);
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
        <Text style={styles.welcome}>
          Welcome to React-Native-Presentation-View-Android!
        </Text>
        <Text style={styles.instructions}>
          To get started, connect device to screen then press the presentation
          button below.
        </Text>
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
