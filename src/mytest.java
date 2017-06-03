import java.util.ArrayList;

/**
 * Created by shira on 03/06/2017.
 */
public class mytest {
    public static void main(String[] args){
        BTree tree = new BTree(2);
        ArrayList<Block> blocks = Block.blockFactory(11, 30);
        tree.insert(blocks.get(1));
        tree.insert(blocks.get(2));
        tree.insert(blocks.get(0));
        tree.insert(blocks.get(3));
        tree.insert(blocks.get(4));
        tree.insert(blocks.get(5));
        BTreeLatex ev = new BTreeLatex(tree, "maayan");
        ev.addTreeState("insert1");

        ev.commitBufferedStates();
        ev.finish();
    }
}
