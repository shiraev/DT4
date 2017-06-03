import java.util.ArrayList;

/**
 * Created by shira on 03/06/2017.
 */
public class mytest {
    public static void main(String[] args){
        BTree tree = new BTree(3);
        ArrayList<Block> blocks = Block.blockFactory(11, 14);
        tree.insert(blocks.get(1));
        tree.insert(blocks.get(2));
        tree.insert(blocks.get(0));
        BTreeLatex ev = new BTreeLatex(tree, "maayan");
        ev.addTreeState("insert1");

        ev.commitBufferedStates();
        ev.finish();
    }
}
