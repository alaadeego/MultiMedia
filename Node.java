
 class Node {
  Node left;
  Node right;
  Node parent ;
  String code;
  int frequency; 
  char symbol;

Node(char c,int v )
{
	this.left=null;
	this.right=null;
	this.parent=null;
	this.code=null;
	this.frequency=v;
	this.symbol=c;
	
	
}
Node(int v)
{
	this.left=null;
	this.right=null;
	this.parent=null;
	this.code=null;
	this.frequency=v;
	this.symbol=' ';	
}
Node()
{
	this.left=null;
	this.right=null;
	this.parent=null;
	this.code=null;
	this.frequency=0;
	this.symbol=' ';	
}


}
