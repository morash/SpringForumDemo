import React from 'react';

import CommentTree from '../comment_tree/comment_tree.js';

class PostViewApp extends React.Component {
	constructor(props) {
		super(props);
		
		this.state = {
			post: null
		}
		
		if (this.props.resource === undefined) {
			console.log("PostViewApp instantiated without resource");
		}
	}
	
	componentDidMount() {
		fetch("/api/board/~/post/" + this.props.resource)
		.then(response => response.json())
		.then(data => {
			this.setState({
				post: data
			})
		})
	}
	
	render() {
		if (this.state.post === null) {
			return (
				<div className="PostViewApp">
					<p>Loading posts</p>
				</div>
			)
		}
		
		return (
			<div className="PostViewApp">
				<h2>{ this.state.post.title }</h2>
				<p>{ this.state.post.contents }</p>
				<CommentTree forPost={this.state.post.id} maxDepth={5}/>
			</div>
		);
	}
}

export default PostViewApp;