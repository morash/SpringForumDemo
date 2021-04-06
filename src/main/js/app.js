import React from 'react';
import ReactDOM from 'react-dom';
import { Route, HashRouter } from 'react-router-dom';

import Header from './header/header.js';
import HomeApp from './home_app/home_app.js';
import BoardIndexApp from './board_index_app/board_index_app.js';
import BoardViewApp from './board_view_app/board_view_app.js';
import PostViewApp from './post_view_app/post_view_app.js';
import CommentViewApp from './comment_view_app/comment_view_app.js';

class App extends React.Component {
	constructor(props) {
		super(props);
		
		this.state = {
			navigation: "home",
			resource: null
		}
		
		this.navigationCallback = this.navigationCallback.bind(this);
	}
	
	navigationCallback(destination, resource = null) {
		this.setState({
			navigation: destination,
			resource: resource
		})
	}
	
	render() {
		return (
			<HashRouter>
				<div className="ReactApp">
					<Header onNavigate={ this.navigationCallback } />
					<div className="BodyContainer">
						<Route exact path="/" component={ HomeApp } />
						<Route exact path="/board" component={ BoardIndexApp } />
						<Route exact path="/board/:boardName" render={({match}) => (
							<BoardViewApp resource={match.params.boardName}/>
						)}/>
						<Route exact path="/board/:boardName/post/:postId" render={({match}) => (
							<PostViewApp resource={match.params.postId}/>
						)}/>
						<Route exact path="/board/:boardName/post/:postId/comment/:commentId" render={({match}) => (
							<CommentViewApp resource={match.params.commentId}/>
						)}/>
					</div>
				</div>
			</HashRouter>
		);
	}
}

ReactDOM.render(
	<App/>,
	document.getElementById("ReactAppContainer")
);