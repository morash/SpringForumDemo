import React from 'react';

import CommentTree from '../comment_tree/comment_tree.js';

class CommentViewApp extends React.Component {
	constructor(props) {
		super(props);
		
		this.state = {
			comment: null
		}
		
		if (this.props.resource === undefined) {
			console.log("CommentViewApp instantiated without resource");
		}
	}
	
	componentDidMount() {
		fetch("/api/board/~/post/~/comment/" + this.props.resource)
		.then(response => response.json())
		.then(data => {
			this.setState({
				comment: data
			})
		})
	}
	
	render() {
		if (this.state.comment === null) {
			return (
				<div className="CommentViewApp">
					<p>Loading comment</p>
				</div>
			)
		}
		
		return (
			<div className="CommentViewApp">
				<h2>{this.state.comment.respondingToPost.title}</h2>
				<p>{this.state.comment.respondingToPost.content}</p>
				<div className="CommentTree">
					<div className="CommentTreeItem">
						<p>{this.state.comment.contents}</p>
						<CommentTree forComment={this.state.comment.id} maxDepth={5}/>
					</div>
				</div>
			</div>
		)
	}
}

export default CommentViewApp;