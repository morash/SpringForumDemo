import React from 'react';

import BoardList from '../board_list/board_list.js';

class BoardIndexApp extends React.Component {
	constructor(props) {
		super(props);
		
		this.state = {
			board_list: []
		}
	}
	
	componentDidMount() {
		fetch("/api/board/")
		.then(response => response.json())
		.then(data => {
			this.setState({
				board_list: data
			})
		});
	}
	
	render() {
		return (
			<div className="BoardIndexApp">
				<BoardList list={ this.state.board_list }/>
			</div>
		);
	}
}

export default BoardIndexApp;