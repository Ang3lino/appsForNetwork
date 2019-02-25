import React, { Component } from 'react';
import './App.css';

import NavbarOptions from './component/navbar-options';

const fileDialog = require('file-dialog');

class App extends Component {
  constructor(props) {
    super(props);
    let treeDirectory;
    
  }

  selectFiles() {
    fileDialog({multiple: true}).then(files => {
      for (let i = 0; i < files.length; ++i) { 
        const file = files[i];
        console.log(file);
        let tmp = new Buffer(file.toString())
        console.log(tmp);
      }
    })
  }

  render() {
    return (
      <div className="App">

        <NavbarOptions />

        <header className="App-header">
          <button type="button" className="btn btn-primary"
          onClick={this.selectFiles}
          >
            Seleccionar archivos
          </button>
        </header>
      </div>
    );
  }

}

export default App;
