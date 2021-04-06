import React from 'react';

import PostList from '../post_list/post_list.js';

class BoardViewApp extends React.Component {
	constructor(props) {
		super(props);
		
		this.state = {
			board: null,
			post_list: []
		}
		
		if (this.props.resource === undefined) {
			console.log("BoardViewApp instantiated without resource");
		}
	}
	
	componentDidMount() {
		fetch("/api/board/" + this.props.resource)
		.then(response => response.json())
		.then(data => {
			this.setState({
				board: data
			});
		})
	}
	
	render() {
		if (this.state.board === null) {
			return (
				<div className="BoardViewApp">
					<p>Loading</p>
				</div>
			)
		}
		
		return (
			<div className="BoardViewApp">
				<h2>{ this.state.board.name }</h2>
				<PostList boardName={this.state.board.name}/>
			</div>
		)
	}
}

export default BoardViewApp;