import java.util.ArrayList;
import java.util.jar.Pack200;

//SUBMIT
public class BNode implements BNodeInterface {

	// ///////////////////BEGIN DO NOT CHANGE ///////////////////
	// ///////////////////BEGIN DO NOT CHANGE ///////////////////
	// ///////////////////BEGIN DO NOT CHANGE ///////////////////
	private final int t;
	private int numOfBlocks;
	private boolean isLeaf;
	private ArrayList<Block> blocksList;
	private ArrayList<BNode> childrenList;

	/**
	 * Constructor for creating a node with a single child.<br>
	 * Useful for creating a new root.
	 */
	public BNode(int t, BNode firstChild) {
		this(t, false, 0);
		this.childrenList.add(firstChild);
	}


	/**
	 * Constructor for creating a <b>leaf</b> node with a single block.
	 */
	public BNode(int t, Block firstBlock) {
		this(t, true, 1);
		this.blocksList.add(firstBlock);
	}

	public BNode(int t, boolean isLeaf, int numOfBlocks) {
		this.t = t;
		this.isLeaf = isLeaf;
		this.numOfBlocks = numOfBlocks;
		this.blocksList = new ArrayList<Block>();
		this.childrenList = new ArrayList<BNode>();
	}

	// For testing purposes.
	public BNode(int t, int numOfBlocks, boolean isLeaf,
				 ArrayList<Block> blocksList, ArrayList<BNode> childrenList) {
		this.t = t;
		this.numOfBlocks = numOfBlocks;
		this.isLeaf = isLeaf;
		this.blocksList = blocksList;
		this.childrenList = childrenList;
	}

	@Override
	public int getT() {
		return t;
	}

	@Override
	public int getNumOfBlocks() {
		return numOfBlocks;
	}

	@Override
	public boolean isLeaf() {
		return isLeaf;
	}

	@Override
	public ArrayList<Block> getBlocksList() {
		return blocksList;
	}

	@Override
	public ArrayList<BNode> getChildrenList() {
		return childrenList;
	}

	@Override
	public boolean isFull() {
		return numOfBlocks == 2 * t - 1;
	}

	@Override
	public boolean isMinSize() {
		return numOfBlocks == t - 1;
	}

	@Override
	public boolean isEmpty() {
		return numOfBlocks == 0;
	}

	@Override
	public int getBlockKeyAt(int indx) {
		return blocksList.get(indx).getKey();
	}

	@Override
	public Block getBlockAt(int indx) {
		return blocksList.get(indx);
	}

	@Override
	public BNode getChildAt(int indx) {
		return childrenList.get(indx);
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((blocksList == null) ? 0 : blocksList.hashCode());
		result = prime * result
				+ ((childrenList == null) ? 0 : childrenList.hashCode());
		result = prime * result + (isLeaf ? 1231 : 1237);
		result = prime * result + numOfBlocks;
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
		BNode other = (BNode) obj;
		if (blocksList == null) {
			if (other.blocksList != null)
				return false;
		} else if (!blocksList.equals(other.blocksList))
			return false;
		if (childrenList == null) {
			if (other.childrenList != null)
				return false;
		} else if (!childrenList.equals(other.childrenList))
			return false;
		if (isLeaf != other.isLeaf)
			return false;
		if (numOfBlocks != other.numOfBlocks)
			return false;
		if (t != other.t)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BNode [t=" + t + ", numOfBlocks=" + numOfBlocks + ", isLeaf="
				+ isLeaf + ", blocksList=" + blocksList + ", childrenList="
				+ childrenList + "]";
	}

	// ///////////////////DO NOT CHANGE END///////////////////
	// ///////////////////DO NOT CHANGE END///////////////////
	// ///////////////////DO NOT CHANGE END///////////////////

	public void setIsLeaf(boolean val){
		isLeaf=val;
	}

	@Override
	public Block search(int key){
		int i=0;
		while (i<numOfBlocks && blocksList.get(i).getKey()<key)
			i++;
		if (i<numOfBlocks && blocksList.get(i).getKey()==key)
			return blocksList.get(i);
		else if (!isLeaf())
			return childrenList.get(i).search(key);
		else
			return null;
	}
	/**
	 public Block search(int key) {
	 for (Block b: blocksList){
	 if (b.getKey()==key){
	 return b;
	 }
	 else if (!isLeaf() && b.getKey()<key){
	 int index =blocksList.indexOf(b);
	 if (index+1<=numOfBlocks-1 && blocksList.get(index+1).getKey()> key)
	 return childrenList.get(index+1).search(key);
	 }
	 else if(!isLeaf() && getMinKeyBlock().getKey() > key ){
	 return childrenList.get(0).search(key);
	 }
	 else if (!isLeaf() && getMaxKeyBlock().getKey()< key ){
	 return childrenList.get(numOfBlocks).search(key);
	 }
	 }
	 return null;
	 }**/


	@Override
	public void insertNonFull(Block d) {
		int i = numOfBlocks-1;
		if (isLeaf() == true)
		{
			while (i >= 0 && blocksList.get(i).getKey()>d.getKey())
				i--;
			blocksList.add(i+1,d);
			numOfBlocks = numOfBlocks+1;
		}
		else
		{
			while (i >= 0 &&blocksList.get(i).getKey()>d.getKey())
				i--;
			if (childrenList.get(i+1).getNumOfBlocks() == 2*t-1)
			{
				splitChild(i+1);
				if (blocksList.get(i+1).getKey()<d.getKey())
					i++;
			}
			childrenList.get(i+1).insertNonFull(d);
		}

	}
	/**public Block search(int key) {
	 for (Block b: blocksList){
	 if (b.getKey()==key){
	 return b;
	 }
	 else if (!isLeaf() && b.getKey()<key){
	 int index =blocksList.indexOf(b);
	 if (index+1<=numOfBlocks-1 && blocksList.get(index+1).getKey()> key)
	 if (!childrenList.get(index+1).isLeaf())
	 return childrenList.get(index+1).search(key);
	 else
	 return null;
	 }
	 else if(getMinKeyBlock().getKey() > key && !isLeaf()){
	 if (!childrenList.get(0).isLeaf())
	 return childrenList.get(0).search(key);
	 else
	 return null;
	 }
	 else if (getMaxKeyBlock().getKey()< key && !isLeaf()){
	 if (!childrenList.get(numOfBlocks-1).isLeaf())
	 return childrenList.get(numOfBlocks).search(key);
	 else
	 return null;
	 }
	 }
	 return null;
	 }

	 }**/

	@Override
	public void delete(int key) {
		int i=0;
		while(i<numOfBlocks &&key>blocksList.get(i).getKey())   //search the key in the current node
			i++;
		if(i<numOfBlocks && key==blocksList.get(i).getKey()) {    //if it the last block at the node remove it
			remove(i,key);
		}
		else if (i!=0)
			if (i<numOfBlocks && key>blocksList.get(i).getKey()){
				i--;
		}
		else if (!isLeaf()){
			if (childrenList.get(i).numOfBlocks==(t-1)) {
				shiftOrMergeChildIfNeeded(i);
				delete(key);
			}
			else
				childrenList.get(i).delete(key);                    //recursively delete it
		}
	}

	private void remove(int indexToRemove, int key) {
		if (isLeaf){
			blocksList.remove(indexToRemove);
			numOfBlocks--;
		}
		else {
			BNode y = getChildAt(indexToRemove);      // child num i
			if (y.numOfBlocks >= t) {
				Block replace = y.getMaxKeyBlock();          //save the max key block
				delete(replace.getKey());              //delete it from the tree
				blocksList.set(indexToRemove, replace);       //replace it with the removing element
				/**numOfBlocks--;*/
				return;
			}
			BNode z = getChildAt(indexToRemove+1);
			if(z.numOfBlocks>=t){                 // child num i+1
				Block replace = z.getMinKeyBlock();       //save the min key block
				delete(replace.getKey());           //delete it from the tree
				blocksList.set(indexToRemove, replace);    //replace it with the removing element
				//numOfBlocks--;//*******
				return;
			}
			mergeChildWithSibling(indexToRemove+1);    //merge z and y
			/**delete(getBlockKeyAt(indexToRemove));         //delete it recursively**/
			delete(key);
		}
	}
	/**

	 public void delete(int key){
	 Block blockToDelete = search(key);
	 if (blockToDelete!=null){
	 if (blocksList.contains(blockToDelete)) {//delete from this node
	 int curinx = blocksList.indexOf(blockToDelete);
	 if (isLeaf()) {///this node is a leaf
	 if (numOfBlocks > t - 1) {//this node can be delete from
	 blocksList.remove(blockToDelete);
	 numOfBlocks--;
	 } else {//this node has t-1
	 }
	 }
	 else { ///this node isn't a leaf
	 if (childrenList.get(curinx).numOfBlocks > t - 1) {//y has t elements
	 Block predecessor = childrenList.get(curinx).getMaxKeyBlock();
	 //childrenList.get(curinx).delete(predecessor.getKey());
	 //blocksList.add(predecessor);
	 //blocksList.remove(blockToDelete);
	 BNode father = this.getDeep(this, predecessor);
	 father.shiftOrMergeChildIfNeeded(curinx);
	 blocksList.add(predecessor);
	 blocksList.remove(blockToDelete);
	 }
	 else if (childrenList.get(curinx).numOfBlocks == t-1 && childrenList.get(curinx+1).numOfBlocks> t-1){
	 Block predecessor = childrenList.get(curinx+1).getMinKeyBlock();
	 //childrenList.get(curinx+1).delete(predecessor.getKey());
	 //blocksList.add(predecessor);
	 //blocksList.remove(blockToDelete);
	 BNode father = this.getDeep(this, predecessor);
	 father.shiftOrMergeChildIfNeeded(curinx);
	 blocksList.add(predecessor);
	 blocksList.remove(blockToDelete);
	 }
	 else
	 mergeChildWithSibling(curinx);
	 }
	 }
	 else {//this node does not contain this key
	 BNode fatherNode = this;
	 fatherNode = fatherNode.getDeep(fatherNode, blockToDelete);
	 fatherNode.delete(key);
	 //shiftOrMergeChildIfNeeded(fatherNode.childrenList.indexOf(blockToDelete));
	 }
	 }
	 }
	 public BNode getDeep (BNode cur, Block b){
	 if (!cur.blocksList.contains(b)){
	 int index =0;
	 while (cur.blocksList.get(index).getKey()< b.getKey()){
	 index++;
	 }
	 return getDeep(cur.getChildAt(index), b);
	 }
	 return cur;
	 }

	 public void delete(int key) {
	 Block blockToDelete = search(key);
	 if (blockToDelete!= null && blocksList.contains(blockToDelete)) {
	 if (isLeaf()) {//check if leave == t-1
	 //shiftOrMergeChildIfNeeded(key);
	 blocksList.remove(blockToDelete);
	 numOfBlocks--;
	 } //else
	 //shiftOrMergeChildIfNeeded(key);//not good
	 }
	 else if(blockToDelete!=null){
	 int i = 0;
	 if (!isLeaf()) {
	 BNode getDeep = childrenList.get(0);
	 while (i<numOfBlocks && blocksList.get(i).getKey() < key) {
	 getDeep = childrenList.get(i+1);
	 i++;
	 }
	 if (getDeep!= null)
	 shiftOrMergeChildIfNeeded(i);
	 getDeep.delete(key);
	 }
	 else return;
	 }
	 }**/

	@Override
	public MerkleBNode createHashNode() {
		ArrayList<MerkleBNode> childrenListMBT = new ArrayList<>(childrenList.size());
		ArrayList<byte[]> blockByte = new ArrayList<>(blocksList.size());
		if (!isLeaf()) {
			for (BNode b : childrenList) {
				childrenListMBT.add(b.createHashNode());
			}
		}
		for (Block b : blocksList){
			blockByte.add(b.getData());
		}
		byte[] hashMBT = HashUtils.sha1Hash(blockByte);
		boolean isLeafMBT = this.isLeaf();
		MerkleBNode output = new MerkleBNode(hashMBT,isLeafMBT,childrenListMBT);
		return output;
	}

	/**
	 * Splits the child node at childIndex into 2 nodes.
	 * @param childIndex
	 */
	public void splitChild(int childIndex)
	{
		BNode y=childrenList.get(childIndex);
		BNode z=new BNode(y.getT(),y.isLeaf(),t-1);
		z.numOfBlocks=t-1;
		int a=0;
		while(a+t<y.getBlocksList().size()) {
			z.getBlocksList().add(y.getBlocksList().get(a+t));
			y.getBlocksList().remove(a+t);
		}
		if(!y.isLeaf())
		{
			int i =0;
			while (i+t<y.getChildrenList().size())
			{
				z.getChildrenList().add(y.getChildrenList().get(i+y.getT()));
				y.getChildrenList().remove(y.getChildrenList().get(i+y.getT()));
			}
		}
		y.numOfBlocks=t-1;
		childrenList.add(childIndex+1,z);
		blocksList.add(childIndex,y.getBlocksList().get(t-1));
		y.getBlocksList().remove(t-1);
		numOfBlocks=numOfBlocks+1;
	}

	/**
	 * True iff the child node at childIndx-1 exists and has more than t-1 blocks.
	 * @param childIndx
	 * @return
	 */
	private boolean childHasNonMinimalLeftSibling(int childIndx){
		if (childIndx==0)
			return false;
		if (childrenList.get(childIndx-1).numOfBlocks>=t)
			return true;
		return false;
	}

	/**
	 * True iff the child node at childIndx+1 exists and has more than t-1 blocks.
	 * @param childIndx
	 * @return
	 */
	private boolean childHasNonMinimalRightSibling(int childIndx){
		if (childIndx==childrenList.size()-1)
			return false;
		if (childrenList.get(childIndx+1).numOfBlocks>=t)
			return true;
		return false;
	}

	/**
	 * Verifies the child node at childIndx has at least t blocks.<br>
	 * If necessary a shift or merge is performed.
	 *
	 * @param childIndx
	 */
	private void shiftOrMergeChildIfNeeded(int childIndx){
		if (childHasNonMinimalLeftSibling(childIndx))
			shiftFromLeftSibling(childIndx);
		else if (childHasNonMinimalRightSibling(childIndx))
			shiftFromRightSibling(childIndx);
		else
			mergeChildWithSibling(childIndx);
	}



	/**
	 * Add additional block to the child node at childIndx, by shifting from left sibling.
	 * @param childIndx
	 */
	private void shiftFromLeftSibling(int childIndx){
		//when the sibling have at least t element
		BNode nodeToShiftTo = childrenList.get(childIndx);
		BNode leftSibling = childrenList.get(childIndx-1);//childIndx-1
		Block blockToShift = leftSibling.getBlockAt(leftSibling.blocksList.size()-1);
		int index = leftSibling.blocksList.indexOf(blockToShift);
		if (!nodeToShiftTo.isLeaf()) {
			nodeToShiftTo.childrenList.add(0, leftSibling.getChildAt(index + 1));
			leftSibling.childrenList.remove(index + 1);
		}
		leftSibling.blocksList.remove(blockToShift);
		Block blockToAdd = this.blocksList.get(childIndx-1);
		this.blocksList.add(blockToShift);
		this.blocksList.remove(blockToAdd);
		nodeToShiftTo.blocksList.add(blockToAdd);
		leftSibling.numOfBlocks--;
		nodeToShiftTo.numOfBlocks++;
	}

	/**
	 * Add additional block to the child node at childIndx, by shifting from right sibling.
	 * @param childIndx
	 */
	private void shiftFromRightSibling(int childIndx){
		//when the sibling have at least t element
		BNode nodeToShiftTo = childrenList.get(childIndx);
		BNode rightSibling = childrenList.get(childIndx + 1);
		Block blockToShift = rightSibling.blocksList.get(0);
		//int index = blocksList.indexOf(blockToShift);
		if (!nodeToShiftTo.isLeaf()) {
			nodeToShiftTo.childrenList.add(nodeToShiftTo.childrenList.size(), rightSibling.getChildAt(0));
			rightSibling.childrenList.remove(0);
		}
		rightSibling.blocksList.remove(blockToShift);
		Block blockToAdd = this.blocksList.get(childIndx);
		this.blocksList.add(blockToShift);
		this.blocksList.remove(blockToAdd);
		nodeToShiftTo.blocksList.add(blockToAdd);
		nodeToShiftTo.numOfBlocks++;
		rightSibling.numOfBlocks--;
	}

	/**
	 * Merges the child node at childIndx with its left or right sibling.
	 * @param childIndx
	 */
	private void mergeChildWithSibling(int childIndx){
		//when there isn't sibling with t element
		if (childIndx!=0)
			mergeWithLeftSibling(childIndx);
		else
			mergeWithRightSibling(childIndx);
	}

	/**
	 * Merges the child node at childIndx with its left sibling.<br>
	 * The left sibling node is removed.
	 * @param childIndx
	 */
	private void mergeWithLeftSibling(int childIndx){
		BNode nodeToMerge = childrenList.get(childIndx);
		BNode leftSibling = childrenList.get(childIndx-1);
		Block headBlock = blocksList.get(childIndx-1);
		leftSibling.blocksList.add(headBlock);
		leftSibling.numOfBlocks++;
		for (Block b : nodeToMerge.blocksList){
			leftSibling.blocksList.add(b);
		}
		for (BNode Bn : nodeToMerge.childrenList){
			if (!leftSibling.isLeaf())
				leftSibling.childrenList.add(Bn);
			else
				break;
		}
		leftSibling.numOfBlocks = leftSibling.numOfBlocks+nodeToMerge.numOfBlocks;
		blocksList.remove(headBlock);
		childrenList.remove(nodeToMerge);
		numOfBlocks--;
	}

	/**
	 * Merges the child node at childIndx with its right sibling.<br>
	 * The right sibling node is removed.
	 * @param childIndx
	 */
	private void mergeWithRightSibling(int childIndx){
		BNode nodeToMerge = childrenList.get(childIndx);
		BNode rightSibling = childrenList.get(childIndx+1);
		Block headBlock = blocksList.get(childIndx);
		rightSibling.blocksList.add(0,headBlock);
		rightSibling.numOfBlocks++;
		for (int i=0; i<nodeToMerge.getBlocksList().size(); i++){
			rightSibling.blocksList.add(i,nodeToMerge.blocksList.get(i));
		}
		for (int i=0; i<nodeToMerge.getBlocksList().size()+1; i++){
			if (!rightSibling.isLeaf())
				rightSibling.childrenList.add(i,nodeToMerge.childrenList.get(i));
		}
		rightSibling.numOfBlocks = rightSibling.numOfBlocks+nodeToMerge.numOfBlocks;
		blocksList.remove(headBlock);
		childrenList.remove(nodeToMerge);
		numOfBlocks--;
	}
	/**
	 * Finds and returns the block with the min key in the subtree.
	 * @return min key block
	 */
	private Block getMinKeyBlock(){
		if(isLeaf())
		{
			return  blocksList.get(0);
		}
		return  childrenList.get(0).getMinKeyBlock();
	}
	/**
	 * Finds and returns the block with the max key in the subtree.
	 * @return max key block
	 */
	private Block getMaxKeyBlock(){
		if(isLeaf())
		{
			return  blocksList.get(blocksList.size()-1);
		}
		return  childrenList.get(childrenList.size()-1).getMaxKeyBlock();
	}
}