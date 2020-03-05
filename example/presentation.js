import React, { Component } from "react";
import { DeviceEventEmitter, StyleSheet, View } from "react-native";

class PresentationView extends Component {
  constructor(props) {
    super(props);
    this.state = {
      position: 0
    };
  }

  componentWillMount() {
    DeviceEventEmitter.addListener("skip", data => {
      this.updatePosition();
    });
  }

  render() {
    return <View style={styles.container} />;
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "green"
  }
});

export default PresentationView;
