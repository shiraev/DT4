// SUBMIT
public class BTree implements BTreeInterface {

	// ///////////////////BEGIN DO NOT CHANGE ///////////////////
	// ///////////////////BEGIN DO NOT CHANGE ///////////////////
	// ///////////////////BEGIN DO NOT CHANGE ///////////////////
	private BNode root;
	private final int t;

	/**
	 * Construct an empty tree.
	 */
	public BTree(int t) { //
		this.t = t;
		this.root = null;
	}

	// For testing purposes.
	public BTree(int t, BNode root) {
		this.t = t;
		this.root = root;
	}

	@Override
	public BNode getRoot() {
		return root;
	}

	@Override
	public int getT() {
		return t;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((root == null) ? 0 : root.hashCode());
		result = prime * result + t;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BTree other = (BTree) obj;
		if (root == null) {
			if (other.root != null)
				return false;
		} else if (!root.equals(other.root))
			return false;
		if (t != other.t)
			return false;
		return true;
	}
	
	// ///////////////////DO NOT CHANGE END///////////////////
	// ///////////////////DO NOT CHANGE END///////////////////
	// ///////////////////DO NOT CHANGE END///////////////////


	@Override
	public Block search(int key) {
		return root.search(key);
	}

	@Override
	public void insert(Block b) {
		if (root == null)
		{
			root = new BNode(t,b);
		}
		else if(search(b.getKey())==null)
		{
			if (root.getNumOfBlocks() == 2*t-1)
			{
				BNode a= new BNode(t, root);
				a.splitChild(0);
				int i = 0;
				if (a.getBlocksList().get(0).getKey() < b.getKey())
					i++;
				a.getChildrenList().get(i).insertNonFull(b);
				root = a;
				root.setIsLeaf(false);
			}
			else
				root.insertNonFull(b);
		}
		
	}

	@Override
	public void delete(int key) {/*
		if (root.getBlocksList().contains(key) & root.getNumOfBlocks()>1)
			root.getBlocksList().remove(root.search(key));
		root.delete(key);
		if (root.getNumOfBlocks()==0 & !root.isLeaf()){
			root=root.getChildAt(0);
			if (root.getChildrenList().size()==0)
				root.setIsLeaf(true);
		}*/
		//add what happen if root.size = 1 and u need to delete this block

		if (root.isLeaf()){
			int index = root.getIndex(key);
			if (index<root.getNumOfBlocks() && root.getBlocksList().get(index).getKey()==key)
				root.getBlocksList().remove(index);
			else return;
		}
		root.delete(key);
		if (/*root.getNumOfBlocks()==0 &*/ !root.isLeaf()){
			root=root.getChildAt(0);
		}
	}

	@Override
	public MerkleBNode createMBT() {
		return root.createHashNode();
	}
}
