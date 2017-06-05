import java.util.ArrayList;

/**
 * Created by shira on 03/06/2017.
 */
public class mytest {
    public static void main(String[] args){
        BTree tree = new BTree(3);
        ArrayList<Block> blocks = Block.blockFactory(0, 30);
        for (Block b : blocks){
            tree.insert(b);
        }


        BTreeLatex ev = new BTreeLatex(tree, "maayan");
        ev.addTreeState("insert1");
        tree.delete(8);
        ev.addTreeState("insert2");
        ev.commitBufferedStates();
        ev.finish();
    }
}
