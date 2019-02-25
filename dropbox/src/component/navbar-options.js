import React, { Component } from 'react';
import logo from '../logo.svg';

class NavbarOptions extends Component {
  render() {
    return (
      <nav className="navbar navbar-expand-lg navbar-light bg-light">
        <a className="navbar-brand" href="#!">
          Duropubotsu
        </a>
        <a className="btn" href="#!"> Subir </a>
        <a className="btn" href="#!"> Bajar </a>
        <a className="btn" href="#!"> Mover </a>
        <a className="btn" href="#!"> Eliminar </a>
      </nav>
    )
  }
}
 
export default NavbarOptions;