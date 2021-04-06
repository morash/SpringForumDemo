import React from 'react';

import BoardList from '../board_list/board_list.js';

class HomeApp extends React.Component {
	constructor(props) {
		super(props);
		
		this.state = {
			boardList: []
		}
	}
	
	componentDidMount() {
		fetch("/api/board/")
		.then(response => response.json())
		.then(data => {
			this.setState({
				boardList: data
			})
		})
	}
	
	render() {
		return (
			<div className="HomeApp">
				<div className="TopBoardContainer">
					<h1>Top Boards</h1>
					<BoardList list={ this.state.boardList } />
				</div>
			</div>
		)
	}
}

export default HomeApp;
					