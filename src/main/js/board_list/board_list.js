import React from 'react';

import { NavLink } from 'react-router-dom';

class BoardList extends React.Component {
	constructor(props) {
		super(props);
		
		if (this.props.list === undefined) {
			console.log("BoardList instantiated without list")
		}
	}
	
	render() {
		const boardList = [];
		
		this.props.list.forEach(board => {
			boardList.push(<BoardListItem board={ board }/>)
		})
		
		return (
			<div className="BoardList">
				{ boardList }
			</div>
		)
	}
}

class BoardListItem extends React.Component {
	constructor(props) {
		super(props);
		
		if (this.props.board === undefined) {
			console.log("BoardListItem instantiated without board")
		}
	}
	
	render() {
		return (
			<div className="BoardListItem">
				<h3><NavLink to={ "/board/" + this.props.board.name }>{ this.props.board.name }</NavLink></h3>
				<p>{ this.props.board.desc }</p>
			</div>
		)
	}
}

export default BoardList;