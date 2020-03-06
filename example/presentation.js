import React, { Component } from "react";
import {
  DeviceEventEmitter,
  Dimensions,
  ScrollView,
  StyleSheet,
  View
} from "react-native";
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
  componentDidUpdate() {
    this.scrollToPosition(this.state.position);
  }
  componentWillUnmount() {
    this.scrollView = null;
  }
  updatePosition() {
    const position = this.state.position === 4 ? 0 : this.state.position + 1;
    this.setState({ position: position });
  }
  scrollToPosition = position => {
    const scrollYPos = Dimensions.get("window").height * position;
    this.scrollView.scrollTo({ y: scrollYPos, animated: true });
  };
  render() {
    return (
      <ScrollView
        style={styles.container}
        ref={scrollView => {
          this.scrollView = scrollView;
        }}
      >
        <View style={styles.panelOne} />
        <View style={styles.panelTwo} />
        <View style={styles.panelThree} />
        <View style={styles.panelFour} />
        <View style={styles.panelFive} />
      </ScrollView>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1
  },
  panelOne: {
    backgroundColor: "#0AA200",
    height: Dimensions.get("window").height
  },
  panelTwo: {
    backgroundColor: "#FE8603",
    height: Dimensions.get("window").height
  },
  panelThree: {
    backgroundColor: "#A60067",
    height: Dimensions.get("window").height
  },
  panelFour: {
    backgroundColor: "#fb3d38",
    height: Dimensions.get("window").height
  },
  panelFive: {
    backgroundColor: "#395e66",
    height: Dimensions.get("window").height
  }
});

export default PresentationView;
