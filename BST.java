import java.util.ArrayList;

class Node1 {
	  Node1 left;
	  Node1 right;
	  Node1 root;
	  String code;
	  int data; 
	  char symbol;

	Node1(int v)
	{
		left=null;
		right=null;
		root=null;
		code=null;
		data=v;
		
		
	}

	}
public class BST {
	//ArrayList<Node> tree=new ArrayList<Node>();
	Node1 root;
	
	public void add(int value)
	{
		Node1 nodeToAdd= new Node1(value);
		if(root==null)
			root=nodeToAdd;
		// if data < node , traverse  left child , else traverse right child 
		// until we get to a node  that we can't traverse... insert our new node
		
		//Node traverseingnode=root;
		traverseAndAddNode(root, nodeToAdd);
			
	}
	private void traverseAndAddNode(Node1 node,Node1 nodeToAdd)
	{
		if (nodeToAdd.data < node.data)
		{
			if(nodeToAdd.left==null)
				node.left=nodeToAdd;
			else
				traverseAndAddNode(node.left,nodeToAdd);
		}
		else if(nodeToAdd.data > node.data)
		{
			if(node.right==null)
				node.right=nodeToAdd;
				
			else
				traverseAndAddNode(node.right,nodeToAdd);
		}
	}
	
	public void traverse()
	{
		//pre-order traverser-in-order traverser-post-order traverser
		
		if(root !=null )
		{
			Node1 nodetotraverse=root;
			if(nodetotraverse.left==null && nodetotraverse.right==null )
				System.out.println(nodetotraverse.data);
			else
			{
				
				if(nodetotraverse.left != null)
					preordertraverse(nodetotraverse.left);
				 if(nodetotraverse.right!= null)
					preordertraverse(nodetotraverse.right);
				
			}
		}
	}
	private void preordertraverse(Node1 node)
	{
		System.out.println(node.data);
		if(node.left !=null)
			preordertraverse(node.left);
		
		
		if(node.right !=null)
			preordertraverse(node.right);
	//	System.out.println(node.data);
		
	}
	
//	private void preordertraverse(Node node)
//	{
//		System.out.println(node.data);
//		
//		if(node.left !=null)
//			inordertraverse(node.left);
//		
//		if(node.right !=null)
//			inordertraverse(node.right);
//	//	System.out.println(node.data);
//		
//	}
	
}
