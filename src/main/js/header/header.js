import React from 'react';
import { NavLink } from 'react-router-dom';

import Authentication from '../authentication/authentication.js';

class Header extends React.Component {
	constructor(props) {
		super(props);
		
		this.performNavigation = this.performNavigation.bind(this);
	}
	
	performNavigation(destination) {
		return () => {
			console.log(destination);
			if (this.props.onNavigate !== undefined) {
				this.props.onNavigate(destination);
			}
		}
	}
	
	render() {
		return (
			<header>
				<NavLink to="/">Home</NavLink>
				<NavLink to="/board">Boards</NavLink>
				<Authentication />
			</header>
		)
	}
}

export default Header;