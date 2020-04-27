import React, { Component } from "react";

class Home extends Component {
  render() {
    return (
      <div>
        <h2>HIGH SCORES</h2>
        <div className="boxwrapper">
          <div className="box whitebg">
            <p>Cras facilisis urna ornare ex volutpat, et
        convallis eat elementum. Ut aliquam, ipsum vitae
        gravida suscipit, metus dui bibendum est, eget rhoncus nibh
        metus nec massa. Maecenas hendrerit laoreet augue
        nec molestie. Cum sociis natoque penatibus et magnis
        dis parturient montes, nascetur ridiculus mus.</p>
          </div>
          <div className="box whitebg">
            <p>Duis a turpis sed lacus dapibus elementum sed eu lectus.</p>
          </div>
          <div className="box whitebg">
            <p>Maecenas hendrerit laoreet augue
        nec molestie. Cum sociis natoque penatibus et magnis
        dis parturient montes, nascetur ridiculus mus.</p>
          </div>
        </div>
      </div>
    );
  }
}

export default Home;
